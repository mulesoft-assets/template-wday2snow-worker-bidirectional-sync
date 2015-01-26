/**
 * Mule Anypoint Template
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates;

public class Worker {

	  private String firstName;
	  private String lastName;
	  private String email;
	  private String phone;
	  private String addr1;
	  private String city;
	  private String state;
	  private String postal;
	  private String zip;
	  private String country;
	  private String reqID;
	  private String hireDate;
	  private String startDate;
	  private String title;
	  private String location;
	  private String eeType;
	  private String timeType;
	  private String payRateType;
	  private String basePayCurrency;
	  private String basePay;
	  private String basePayFreq;
	  private String jobProfile;
	  private String mgrID;
	  
	  public Worker(String firstNameValue, String lastNameValue, String emailValue, String phoneValue, String addr1Value, String cityValue, String stateValue,
	  				String postalValue, String zipValue, String countryValue, String reqIDValue, String hireDateValue, String startDateValue, String titleValue,
	  				String locationValue, String eeTypeValue, String timeTypeValue, String payRateTypeValue, String basePayCurrencyValue, 
	  				String basePayValue, String basePayFreqValue, String jobProfileValue, String mgrIDValue) {
	  	firstName = firstNameValue;
	  	lastName = lastNameValue;
	    email = emailValue;
	    addr1 = addr1Value;
	    city = cityValue;
	    phone = phoneValue;
	    state = stateValue;
	    postal = postalValue;
	    zip = zipValue;
	    country = countryValue;
	    reqID = reqIDValue;
	    hireDate = hireDateValue;
	    startDate = startDateValue;
	    title = titleValue;
		location = locationValue;
		eeType = eeTypeValue;
		timeType = timeTypeValue;
		payRateType = payRateTypeValue;
	    basePayCurrency = basePayCurrencyValue;
	    basePay = basePayValue;
	    basePayFreq = basePayFreqValue;
	    jobProfile = jobProfileValue;
	    mgrID = mgrIDValue;
	  }
	  
	  public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getAddr1() {
			return addr1;
		}

		public void setAddr1(String addr1) {
			this.addr1 = addr1;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getPostal() {
			return postal;
		}

		public void setPostal(String postal) {
			this.postal = postal;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getReqID() {
			return reqID;
		}

		public void setReqID(String reqID) {
			this.reqID = reqID;
		}

		public String getHireDate() {
			return hireDate;
		}

		public void setHireDate(String hireDate) {
			this.hireDate = hireDate;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getEeType() {
			return eeType;
		}

		public void setEeType(String eeType) {
			this.eeType = eeType;
		}

		public String getTimeType() {
			return timeType;
		}

		public void setTimeType(String timeType) {
			this.timeType = timeType;
		}

		public String getPayRateType() {
			return payRateType;
		}

		public void setPayRateType(String payRateType) {
			this.payRateType = payRateType;
		}

		public String getBasePayCurrency() {
			return basePayCurrency;
		}

		public void setBasePayCurrency(String basePayCurrency) {
			this.basePayCurrency = basePayCurrency;
		}

		public String getBasePay() {
			return basePay;
		}

		public void setBasePay(String basePay) {
			this.basePay = basePay;
		}

		public String getBasePayFreq() {
			return basePayFreq;
		}

		public void setBasePayFreq(String basePayFreq) {
			this.basePayFreq = basePayFreq;
		}
		public String getJobProfile() {
			return jobProfile;
		}

		public void setJobProfile(String jobProfile) {
			this.jobProfile = jobProfile;
		}
		public String getMgrID() {
			return mgrID;
		}

		public void setMgrID(String mgrID) {
			this.mgrID = mgrID;
		}

		@Override
		public String toString() {
			return "Worker: " + firstName + " " + lastName;
					
		}
}
