package org.mule.templates;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public static GetWorkersRequestType create(Date startDate, int periodInMillis, int offset) throws ParseException, DatatypeConfigurationException {
		
		EffectiveAndUpdatedDateTimeDataType dateRangeData = new EffectiveAndUpdatedDateTimeDataType();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.SECOND, -offset / 1000);
		dateRangeData.setUpdatedThrough(xmlDate(cal.getTime()));
		cal.setTime(startDate);
		cal.add(Calendar.SECOND, - (periodInMillis + offset) / 1000);
		dateRangeData.setUpdatedFrom(xmlDate(cal.getTime()));
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

public static GetWorkersRequestType create(String startDateString, int periodInMillis, int offset) throws ParseException, DatatypeConfigurationException {
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date startDate = dateFormat.parse(startDateString);
	
	return create(startDate, periodInMillis, offset);
}

	private static XMLGregorianCalendar xmlDate(Date date) throws DatatypeConfigurationException {
		GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		gregorianCalendar.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
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
}
