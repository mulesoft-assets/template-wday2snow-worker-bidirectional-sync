/**
 * Mule Anypoint Template
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.workday.hr.WorkerType;
import com.workday.integrations.ActionEventObjectIDType;
import com.workday.integrations.ActionEventObjectType;
import com.workday.integrations.EventRequestReferencesType;
import com.workday.integrations.GetEventDetailsRequestType;

public class EventDetailRequest {

	public static GetEventDetailsRequestType create(Map<String, Object> data){
		
		return prepareRequest(
				data.get("lastEventType").toString(),
				data.get("lastEventValue").toString());		
	}

	public static GetEventDetailsRequestType prepareRequest(String type, String value) {
		GetEventDetailsRequestType get = new GetEventDetailsRequestType();
		EventRequestReferencesType req = new EventRequestReferencesType();
		List<ActionEventObjectType> eventReference = new ArrayList<ActionEventObjectType>();
		ActionEventObjectType ot = new ActionEventObjectType();
		List<ActionEventObjectIDType> list = new ArrayList<ActionEventObjectIDType>();
		ActionEventObjectIDType e = new ActionEventObjectIDType();
		e.setType(type);
		e.setValue(value);
		list.add(e );
		ot.setID(list );
		eventReference.add(ot );
		req.setEventReference(eventReference );
		get.setRequestReferences(req);
		return get;
	}
}
