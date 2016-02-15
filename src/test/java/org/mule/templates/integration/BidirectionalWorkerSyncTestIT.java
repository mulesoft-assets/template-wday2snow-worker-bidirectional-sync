/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.integration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.construct.Flow;
import org.mule.processor.chain.InterceptingChainLifecycleWrapper;
import org.mule.templates.object.Worker;

import com.mulesoft.module.batch.BatchTestHelper;

/**
 * The objective of this class is validating the correct behavior of the flows
 * for this Mule Anypoint Template
 * 
 */
@SuppressWarnings("unchecked")
public class BidirectionalWorkerSyncTestIT extends AbstractTemplateTestCase {

	private static String WORKDAY_WORKER_ID;
	private static String SERVICE_NOW_USER_ID;
	private static final String VAR_ID = "sysId";
	private static final String VAR_USER_NAME = "userName";
	private static final String VAR_LAST_NAME = "lastName";
	private static final String VAR_FIRST_NAME = "firstName";
	private static final String VAR_EMAIL = "email";
	private static final String TEMPLATE_NAME = "wday2snow-bidi-worker";
	
	private static final String SERVICE_NOW_INBOUND_FLOW_NAME = "triggerSyncFromServiceNowFlow";
	private static final String WORKDAY_INBOUND_FLOW_NAME = "triggerSyncFromWorkdayFlow";
	private static final int TIMEOUT_MILLIS = 30000;

	private Flow insertUserInServiceNowFlow;
	private Flow updateUserInServiceNowFlow;
	private Flow queryUserFromServiceNowFlow;
	private Flow updateWorkerInWorkdayFlow;
	private InterceptingChainLifecycleWrapper queryWorkerFromWorkdayFlow;
	private BatchTestHelper batchTestHelper;
	
	private static final String PATH_TO_TEST_PROPERTIES = "./src/test/resources/mule.test.properties";
	
	private List<Map<String, Object>> createdUsersInServiceNow = new ArrayList<Map<String, Object>>();
	private List<Worker> createdWorkersInWorkday = new ArrayList<Worker>();

	@BeforeClass
	public static void beforeTestClass() {
		
		final Properties props = new Properties();
		try {
			props.load(new FileInputStream(PATH_TO_TEST_PROPERTIES));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		WORKDAY_WORKER_ID = props.getProperty("wday.test.worker.id");
		SERVICE_NOW_USER_ID = props.getProperty("snow.test.user.id");
		
		System.setProperty("page.size", "1000");

		System.setProperty("poll.startDelayMillis", "8000");
        System.setProperty("poll.frequencyMillis", "30000");
		
        Date initialDate = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(initialDate);
        System.setProperty(
        		"wday.watermark.default.expression", 
        		"#[groovy: new GregorianCalendar("
        				+ cal.get(Calendar.YEAR) + ","
        				+ cal.get(Calendar.MONTH) + ","
        				+ cal.get(Calendar.DAY_OF_MONTH) + ","
        				+ cal.get(Calendar.HOUR_OF_DAY) + ","
        				+ cal.get(Calendar.MINUTE) + ","
        				+ cal.get(Calendar.SECOND) + ") ]");

		DateTime dt = new DateTime(cal.getTimeInMillis());
		dt = dt.withZone(DateTimeZone.forID("GMT"));
        System.err.println("aaaa: " + dt.toString());
        System.setProperty(
        		"snow.watermark.default.expression", dt.toString());
	}

	@Before
	public void setUp() throws Exception {		
		stopAutomaticPollTriggering();
		getAndInitializeFlows();

		batchTestHelper = new BatchTestHelper(muleContext);
	}
	
	private void stopAutomaticPollTriggering() throws MuleException {
		stopFlowSchedulers(SERVICE_NOW_INBOUND_FLOW_NAME);
		stopFlowSchedulers(WORKDAY_INBOUND_FLOW_NAME);
	}
	
	private void getAndInitializeFlows() throws MuleException {
		// Flow for inserting a user in Service Now
		insertUserInServiceNowFlow = getFlow("insertUserInServiceNowFlow");
				
		// Flow for updating a user in Service Now
		updateUserInServiceNowFlow = getFlow("updateUserInServiceNowFlow");

		// Flow for querying the user from Service Now
		queryUserFromServiceNowFlow = getFlow("queryUserFromServiceNowFlow");
		
		// Flow for updating a user in Workday
		updateWorkerInWorkdayFlow = getFlow("updateWorkerInWorkdayFlow");

		// Flow for querying the worker from Workday
		queryWorkerFromWorkdayFlow = getSubFlow("queryWorkerFromWorkdayFlow");
		queryWorkerFromWorkdayFlow.initialise();
	}

	private void createTestDataInServiceNowSandbox() throws MuleException, Exception{
		Map<String, Object> serviceNowUser0 = new HashMap<String, Object>();
		String infixServiceNow = "_0_SNOW_" + TEMPLATE_NAME + "_" + System.currentTimeMillis();
		serviceNowUser0.put(VAR_FIRST_NAME, "fn" + infixServiceNow);
		serviceNowUser0.put(VAR_LAST_NAME, "ln" + infixServiceNow);
		serviceNowUser0.put(VAR_USER_NAME, "32604");
		serviceNowUser0.put(VAR_EMAIL, "e" + infixServiceNow + "@example.com");
		
		serviceNowUser0.put(VAR_ID, SERVICE_NOW_USER_ID);
		
		logger.info("updating a Service Now user: " + serviceNowUser0.get(VAR_EMAIL));
		Object userSysId = updateServieNowUser(serviceNowUser0, updateUserInServiceNowFlow);
		
		// if there is no such a user with specified ID insert new one to trigger flow
		if(userSysId == null) {
			logger.warn("User " +  serviceNowUser0.get(VAR_EMAIL) + " was not found.");
			logger.info("inserting service now user: " + serviceNowUser0.get(VAR_EMAIL));
			userSysId = insertServiceNowUser(serviceNowUser0, insertUserInServiceNowFlow);
		}	
		
		createdUsersInServiceNow.clear();
		serviceNowUser0.put(VAR_ID, userSysId);
		createdUsersInServiceNow.add(serviceNowUser0);
	}
	
	private Object insertServiceNowUser(Map<String, Object> user, Flow isertFlow) throws MuleException, Exception {
		return isertFlow.process(getTestEvent(Collections.unmodifiableMap(user), MessageExchangePattern.REQUEST_RESPONSE)).getMessage().getPayload();
	}
	
	private Object updateServieNowUser(Map<String, Object> user, Flow updateUserFlow) throws MuleException, Exception {
		return updateUserFlow.process(getTestEvent(user, MessageExchangePattern.REQUEST_RESPONSE)).getMessage().getPayload();
	}
	
	private Object queryServieNowUser(Map<String, Object> user, Flow queryUserFlow) throws MuleException, Exception {
		return queryUserFlow.process(getTestEvent(user, MessageExchangePattern.REQUEST_RESPONSE)).getMessage().getPayload();
	}

	private void createTestDataInWorkdaySandBox() throws MuleException, Exception {
		Worker worker = prepareWorker();
		logger.info("Updating a workday worker: " + worker.getEmail());
		
		try {
			updateWorkerInWorkdayFlow.process(getTestEvent(worker, MessageExchangePattern.REQUEST_RESPONSE));						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private Worker prepareWorker(){
		String infixWorkday = "_0_WDAY_" + TEMPLATE_NAME + System.currentTimeMillis();
		Worker worker = new Worker("fn" + infixWorkday, "ln" + infixWorkday, "e" + infixWorkday + "@example.com", "232-2323", "999 Main St", "San Francisco", "CA", "94105", "US", "o7aHYfwG", 
				"2014-04-17-07:00", "2014-04-21-07:00", "QA Engineer", "San_Francisco_site", "Regular", "Full Time", "Salary", "USD", "140000", "Annual", null, null, WORKDAY_WORKER_ID);
		createdWorkersInWorkday.clear();
		createdWorkersInWorkday.add(worker);
		return worker;
	}
    
	@Test
	public void testServiceNowDirection() throws MuleException, Exception {
		createTestDataInServiceNowSandbox();
		
		// test service now -> workday	
		Thread.sleep(1000);
		executeWaitAndAssertBatchJob(SERVICE_NOW_INBOUND_FLOW_NAME);
		Map<String, Object> user = createdUsersInServiceNow.get(0);
		
		List<Map<String, Object>> snowResponse = (List<Map<String,Object>>) queryServieNowUser(user , queryUserFromServiceNowFlow);
		
		Thread.sleep(20000);
		Map<String, Object> workerData = queryWorkdayWorker((String)snowResponse.get(0).get("user_name"), queryWorkerFromWorkdayFlow);
		
		for(String key : workerData.keySet()){
			String newValue = workerData.get(key).toString().substring(1, workerData.get(key).toString().length() - 1); // we have to cut the brackets
			workerData.put(key, newValue);
		}
		
		Assert.assertFalse("Synchronized user should not be null payload", workerData == null);
		Assert.assertEquals("The user should have been sync and the first name must match", user.get(VAR_FIRST_NAME), 
				workerData.get("firstName"));
		Assert.assertEquals("The user should have been sync and the last name must match", user.get(VAR_LAST_NAME), 
				workerData.get("lastName"));
			
	}

	@Test
	public void testWorkdayDirection() throws Exception{
		// because of workday delay in processing, test data is created here
		createTestDataInWorkdaySandBox();
		// test workday -> service now
		Thread.sleep(15000);
		
		executeWaitAndAssertBatchJob(WORKDAY_INBOUND_FLOW_NAME);

		Map<String, Object> workdayUser = new HashMap<String, Object>();
		Worker worker = createdWorkersInWorkday.get(0);
		workdayUser.put(VAR_USER_NAME, worker.getMgrID());
		
		List<?> snowResponse =  (List<?>) queryServieNowUser(workdayUser , queryUserFromServiceNowFlow);
		
		Assert.assertFalse("Synchronized user should not be null payload", snowResponse == null);
		
		Map<String, Object> user = (Map<String, Object>) snowResponse.get(0);
		
		Assert.assertEquals("The user should have been sync and the first name must match", worker.getFirstName(), user.get("first_name"));
		Assert.assertEquals("The user should have been sync and the last name must match", worker.getLastName(), user.get("last_name"));
	}
	
	private Map<String, Object> queryWorkdayWorker(String id, InterceptingChainLifecycleWrapper queryWorkerFromWorkdayFlow2) {
		try {
			MuleEvent response = queryWorkerFromWorkdayFlow2.process(getTestEvent(id, MessageExchangePattern.REQUEST_RESPONSE));
			Map<String, Object> res = (Map<String, Object>) response.getMessage().getPayload();
			return res;
		} catch (InitialisationException e) {
			e.printStackTrace();
		} catch (MuleException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void executeWaitAndAssertBatchJob(String flowConstructName) throws Exception {
		// Execute synchronization
		runSchedulersOnce(flowConstructName);

		// Wait for the batch job execution to finish
		batchTestHelper.awaitJobTermination(TIMEOUT_MILLIS * 1000, 500);
	}
}
