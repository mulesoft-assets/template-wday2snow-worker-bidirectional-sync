
# Anypoint Template: Workday Worker to ServiceNow User Bidirectional synchronization

+ [License Agreement](#licenseagreement)
+ [Use Case](#usecase)
	* [Template overview](#templateoverview)
+ [Considerations](#considerations)
	* [ServiceNow Considerations](#servicenowconsiderations)
	* [Workday Considerations](#workdayconsiderations)
+ [Run it!](#runit)
	* [Running on premise](#runonopremise)
	* [Running on Studio](#runonstudio)
	* [Running on Mule ESB stand alone](#runonmuleesbstandalone)
	* [Running on CloudHub](#runoncloudhub)
	* [Deploying your Anypoint Template on CloudHub](#deployingyouranypointtemplateoncloudhub)
	* [Properties to be configured (With examples)](#propertiestobeconfigured)
+ [API Calls](#apicalls)
+ [Customize It!](#customizeit)
	* [config.xml](#configxml)
	* [businessLogic.xml](#businesslogicxml)
	* [endpoints.xml](#endpointsxml)
	* [errorHandling.xml](#errorhandlingxml)


# License Agreement <a name="licenseagreement"/>
Note that using this template is subject to the conditions of this [License Agreement](AnypointTemplateLicense.pdf).
Please review the terms of the license before downloading and using this template. In short, you are allowed to use the template for free with Mule ESB Enterprise Edition, CloudHub, or as a trial in Anypoint Studio.

# Use Case <a name="usecase"/>
I want to have my workers/users synchronized between Workday and ServiceNow.

### Template overview <a name="templateoverview"/>
						
Let's say we want to keep Workday synchronized with ServiceNow. Then, the integration behavior can be summarized just with the following steps:

1. Ask Workday:
> *Which changes have there been since the last time I got in touch with you?*

2. For each of the updates fetched in the previous step (1.), ask ServiceNow:
> *Does the update received from Workday should be applied?*

3. If ServiceNow's answer for the previous question (2.) is *Yes*, then *upsert* (create or update depending on each particular case) ServiceNow with the belonging change

4. Repeat previous steps (1. to 3.) the other way around (using ServiceNow as the source and Workday as the target)

 Repeat *ad infinitum*:

5. Ask Workday:
> *Which changes have there been since the question I've made in the step 1.?*

And so on...
			  
The question for recent changes since a certain moment is nothing but a [poll inbound](http://www.mulesoft.org/documentation/display/current/Poll+Reference) with a [watermark](http://blogs.mulesoft.org/data-synchronizing-made-easy-with-mule-watermarks/) defined.

# Considerations <a name="considerations"/>

**Note:** This particular Anypoint Template illustrate the synchronization use case between Workday and ServiceNow.
There are a couple of things you should take into account before running this template:

1. **Workers cannot be deleted in Workday:** They are only set as terminated employees.

2. All times are stored in the ServiceNow platform in Coordinated Universal Time (UTC) so time data needs to be converted to UTC time zone before querying the ServiceNow.

3. **Required Fields:** The following fields should be filled in in ServiceNow instance as they are required in Workday: Street, City, State/Province, Zip/Postal Code, Country, Phone.





## ServiceNow Considerations <a name="servicenowconsiderations"/>

There may be a few things that you need to know regarding ServiceNow, in order for this template to work.

### As source of data

There are no particular considerations for this Anypoint Template regarding ServiceNow as data origin.
### As destination of data

There are no particular considerations for this Anypoint Template regarding ServiceNow as data destination.
## Workday Considerations <a name="workdayconsiderations"/>

### As source of data

There are no particular considerations for this Anypoint Template regarding Workday as data origin.
### As destination of data

There are no particular considerations for this Anypoint Template regarding Workday as data destination.





# Run it! <a name="runit"/>
Simple steps to get Workday Worker to ServiceNow User Bidirectional synchronization running.


## Running on premise <a name="runonopremise"/>
In this section we detail the way you should run your Anypoint Template on your computer.


### Where to Download Mule Studio and Mule ESB
First thing to know if you are a newcomer to Mule is where to get the tools.

+ You can download Mule Studio from this [Location](http://www.mulesoft.com/platform/mule-studio)
+ You can download Mule ESB from this [Location](http://www.mulesoft.com/platform/soa/mule-esb-open-source-esb)


### Importing an Anypoint Template into Studio
Mule Studio offers several ways to import a project into the workspace, for instance: 

+ Anypoint Studio generated Deployable Archive (.zip)
+ Anypoint Studio Project from External Location
+ Maven-based Mule Project from pom.xml
+ Mule ESB Configuration XML from External Location

You can find a detailed description on how to do so in this [Documentation Page](http://www.mulesoft.org/documentation/display/current/Importing+and+Exporting+in+Studio).


### Running on Studio <a name="runonstudio"/>
Once you have imported you Anypoint Template into Anypoint Studio you need to follow these steps to run it:

+ Locate the properties file `mule.dev.properties`, in src/main/resources
+ Complete all the properties required as per the examples in the section [Properties to be configured](#propertiestobeconfigured)
+ Once that is done, right click on you Anypoint Template project folder 
+ Hover you mouse over `"Run as"`
+ Click on  `"Mule Application"`


### Running on Mule ESB stand alone <a name="runonmuleesbstandalone"/>
Complete all properties in one of the property files, for example in [mule.prod.properties] (../master/src/main/resources/mule.prod.properties) and run your app with the corresponding environment variable to use it. To follow the example, this will be `mule.env=prod`. 


## Running on CloudHub <a name="runoncloudhub"/>
While [creating your application on CloudHub](http://www.mulesoft.org/documentation/display/current/Hello+World+on+CloudHub) (Or you can do it later as a next step), you need to go to Deployment > Advanced to set all environment variables detailed in **Properties to be configured** as well as the **mule.env**.


### Deploying your Anypoint Template on CloudHub <a name="deployingyouranypointtemplateoncloudhub"/>
Mule Studio provides you with really easy way to deploy your Template directly to CloudHub, for the specific steps to do so please check this [link](http://www.mulesoft.org/documentation/display/current/Deploying+Mule+Applications#DeployingMuleApplications-DeploytoCloudHub)


## Properties to be configured (With examples) <a name="propertiestobeconfigured"/>
In order to use this Mule Anypoint Template you need to configure properties (Credentials, configurations, etc.) either in properties file or in CloudHub as Environment Variables. Detail list with examples:
### Application configuration
**Note**: The watermark for ServiceNow is in the GMT timezone. The initial value you set in *snow.watermark.default.expression* will be therefore converted before the usage.			
			
+ poll.frequencyMillis `10000`
+ poll.startDelayMillis `500`

+ snow.watermark.default.expression `#[groovy: java.text.SimpleDateFormat df = new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss'); df.parse('2015-05-27 14:00:00').format('yyyy-MM-dd HH:mm:ss', TimeZone.getTimeZone('GMT'));]`
+ wday.watermark.default.expression `#[groovy: new GregorianCalendar(2015, Calendar.MAY, 27, 14, 00, 00)]`
			
#### Workday Connector configuration
+ wday.user `user@company`
+ wday.password `secret`
+ wday.endpoint `https://impl-cc.workday.com/ccx/service/company/Human_Resources/v21.1`
+ wday.integration.user.id `72d1073ba8f51050e3c83a48d7a9ead6`

#### Default values for required WorkDay fields
+ wday.country `USA`
+ wday.state `USA-CA`
+ wday.organization `SUPERVISORY_ORGANIZATION-1-435`
+ wday.jobprofileId `39905`
+ wday.postalCode `90001`
+ wday.city `San Francisco`
+ wday.location `San_Francisco_Site`
+ wday.currency `USD`

#### ServiceNow Connector configuration
+ snow.user `mule123` 
+ snow.password `secret`
+ snow.endpoint `https://dev.service-now.com`
+ snow.integration.user.id `eb3236d02bb87100c173448405da15de`

# API Calls <a name="apicalls"/>
There are no special considerations regarding API calls.


# Customize It!<a name="customizeit"/>
This brief guide intends to give a high level idea of how this Anypoint Template is built and how you can change it according to your needs.
As mule applications are based on XML files, this page will be organized by describing all the XML that conform the Anypoint Template.
Of course more files will be found such as Test Classes and [Mule Application Files](http://www.mulesoft.org/documentation/display/current/Application+Format), but to keep it simple we will focus on the XMLs.

Here is a list of the main XML files you'll find in this application:

* [config.xml](#configxml)
* [endpoints.xml](#endpointsxml)
* [businessLogic.xml](#businesslogicxml)
* [errorHandling.xml](#errorhandlingxml)


## config.xml<a name="configxml"/>
Configuration for Connectors and [Properties Place Holders](http://www.mulesoft.org/documentation/display/current/Configuring+Properties) are set in this file. **Even you can change the configuration here, all parameters that can be modified here are in properties file, and this is the recommended place to do it so.** Of course if you want to do core changes to the logic you will probably need to modify this file.

In the visual editor they can be found on the *Global Element* tab.


## businessLogic.xml<a name="businesslogicxml"/>
This file holds the functional aspect of the template (points 2. to 4. described in the [template overview](#templateoverview)). Its main component is a [*Batch job*][8], and it includes *steps* for both executing the synchronization from Workday to ServiceNow and the other way around.



## endpoints.xml<a name="endpointsxml"/>
This file should contain every inbound and outbound endpoint of your integration app. It is intended to contain the application API.
In this particular template, this file contains a couple of poll inbound endpoints that query Workday and ServiceNow for updates using watermark as mentioned before.



## errorHandling.xml<a name="errorhandlingxml"/>
This is the right place to handle how your integration will react depending on the different exceptions. 
This file holds a [Choice Exception Strategy](http://www.mulesoft.org/documentation/display/current/Choice+Exception+Strategy) that is referenced by the main flow in the business logic.



