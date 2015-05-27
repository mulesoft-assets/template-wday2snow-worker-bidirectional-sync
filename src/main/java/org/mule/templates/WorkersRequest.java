/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.workday.hr.EffectiveAndUpdatedDateTimeDataType;
import com.workday.hr.GetWorkersRequestType;
import com.workday.hr.TransactionLogCriteriaType;
import com.workday.hr.WorkerObjectIDType;
import com.workday.hr.WorkerObjectType;
import com.workday.hr.WorkerRequestCriteriaType;
import com.workday.hr.WorkerRequestReferencesType;
import com.workday.hr.WorkerResponseGroupType;

public class WorkersRequest {

public static GetWorkersRequestType create(GregorianCalendar startDate) throws ParseException, DatatypeConfigurationException {
		
		EffectiveAndUpdatedDateTimeDataType dateRangeData = new EffectiveAndUpdatedDateTimeDataType();
	    GregorianCalendar current = new GregorianCalendar();
	    current.add(Calendar.SECOND, -1);
	    dateRangeData.setUpdatedFrom(getXMLGregorianCalendar(startDate));
	    dateRangeData.setUpdatedThrough(getXMLGregorianCalendar(current));
	    
	    TransactionLogCriteriaType transactionLogCriteria = new TransactionLogCriteriaType();
		transactionLogCriteria.setTransactionDateRangeData(dateRangeData);
		
		WorkerRequestCriteriaType workerRequestCriteria = new WorkerRequestCriteriaType();
		workerRequestCriteria.getTransactionLogCriteriaData().add(transactionLogCriteria);
		GetWorkersRequestType getWorkersType = new GetWorkersRequestType();
		getWorkersType.setRequestCriteria(workerRequestCriteria);
		
		WorkerResponseGroupType resGroup = new WorkerResponseGroupType();
		resGroup.setIncludeRoles(true);	
		resGroup.setIncludePersonalInformation(true);
		resGroup.setIncludeOrganizations(true);
		resGroup.setIncludeEmploymentInformation(true);
		resGroup.setIncludeReference(true);
		resGroup.setIncludeUserAccount(true);
		resGroup.setIncludeTransactionLogData(true);
		getWorkersType.setResponseGroup(resGroup);
		
		return getWorkersType;
	}

	public static GetWorkersRequestType getWorker(String id) throws ParseException, DatatypeConfigurationException {
		
		WorkerRequestReferencesType reqRefs = new WorkerRequestReferencesType();
		List<WorkerObjectType> workerReferences = new ArrayList<WorkerObjectType>();
		WorkerObjectType wot = new WorkerObjectType();
		List<WorkerObjectIDType> woids = new ArrayList<WorkerObjectIDType>();
		WorkerObjectIDType woidd = new WorkerObjectIDType();
		woidd.setType("Employee_ID");
		woidd.setValue(id);
		woids.add(woidd );
		wot.setID(woids );
		workerReferences.add(wot );
		reqRefs.setWorkerReference(workerReferences );
		GetWorkersRequestType getWorkersType = new GetWorkersRequestType();

		getWorkersType.setRequestReferences(reqRefs);
		WorkerResponseGroupType resGroup = new WorkerResponseGroupType();
		resGroup.setIncludeRoles(true);	
		resGroup.setIncludePersonalInformation(true);
		resGroup.setIncludeOrganizations(true);
		resGroup.setIncludeEmploymentInformation(true);
		resGroup.setIncludeReference(true);
		resGroup.setIncludeUserAccount(true);
		resGroup.setIncludeTransactionLogData(true);
		getWorkersType.setResponseGroup(resGroup);
							
		return getWorkersType;
	}
	
	private static XMLGregorianCalendar getXMLGregorianCalendar(GregorianCalendar date) throws DatatypeConfigurationException {
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
	}	
}


