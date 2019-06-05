
# Anypoint Template: Workday Worker to ServiceNow User Bidirectional synchronization

<!-- Header (start) -->
Bi-directionally synchronizes workers data between Workday and ServiceNow. Configure this template quickly by only modifying the fields to be synchronized, how they map, and criteria on when to trigger the synchronization. Real time synchronization is achieved via rapid polling of both systems or can be extended to include outbound notifications.

This template leverages watermarking functionality to ensure that only the most recent items are synchronized and batch to efficiently process many records at a time.

![bb71bf4f-8822-4404-bb30-b041825073a4-image.png](https://exchange2-file-upload-service-kprod.s3.us-east-1.amazonaws.com:443/bb71bf4f-8822-4404-bb30-b041825073a4-image.png)

## Workday Requirements

Install Workday System - Integrations, Workday HCM - Human Resources and Workday HCM - Staffing modules that you can find on the [Workday connector page](/exchange/org.mule.modules/mule-module-workday-connector/).
<!-- Header (end) -->

## License Agreement
This template is subject to the conditions of the <a href="https://s3.amazonaws.com/templates-examples/AnypointTemplateLicense.pdf">MuleSoft License Agreement</a>. Review the terms of the license before downloading and using this template. You can use this template for free with the Mule Enterprise Edition, CloudHub, or as a trial in Anypoint Studio.
## Use Case
<!-- Use Case (start) -->
I want to have my workers/users synchronized between Workday and ServiceNow.

### Template Overview

Keeps Workday synchronized with ServiceNow. The integration behavior is:

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

The question for recent changes since a certain moment is nothing but a Scheduler with a watermark defined.
<!-- Use Case (end) -->

# Considerations
<!-- Default Considerations (start) -->

<!-- Default Considerations (end) -->

<!-- Considerations (start) -->
This template illustrates the synchronization use case between Workday and ServiceNow.
There are a couple of things you should take into account before running this template:

1. **Workers cannot be deleted in Workday:** They are only set as terminated employees.

2. All times are stored in the ServiceNow platform in Coordinated Universal Time (UTC) so time data needs to be converted to UTC time zone before querying the ServiceNow.

3. **Required Fields:** The following fields should be filled in in ServiceNow instance as they are required in Workday: Street, City, State/Province, Zip/Postal Code, Country, Phone.			

4. **Username field:** The username has to be valid for both instancies or username can be null in ServiceNow.
<!-- Considerations (end) -->

## ServiceNow Considerations

Here's what you need to know to get this template to work with ServiceNow.

### As a Data Source

There are no considerations with using ServiceNow as a data origin.
### As a Data Destination

There are no considerations with using ServiceNow as a data destination.
## Workday Considerations

### As a Data Source

There are no considerations with using Workday as a data origin.
### As a Data Destination

There are no considerations with using Workday as a data destination.

# Run it!
Simple steps to get this template running.
<!-- Run it (start) -->

<!-- Run it (end) -->

## Run On Premises
In this section we help you run this template on your computer.
<!-- Running on premise (start) -->

<!-- Running on premise (end) -->

### Download Anypoint Studio and the Mule Runtime
If you are new to Mule, download this software:

+ [Download Anypoint Studio](https://www.mulesoft.com/platform/studio)
+ [Download Mule runtime](https://www.mulesoft.com/lp/dl/mule-esb-enterprise)

**Note:** Anypoint Studio requires JDK 8.
<!-- Where to download (start) -->

<!-- Where to download (end) -->

### Import a Template into Studio
In Studio, click the Exchange X icon in the upper left of the taskbar, log in with your Anypoint Platform credentials, search for the template, and click Open.
<!-- Importing into Studio (start) -->

<!-- Importing into Studio (end) -->

### Run on Studio
After you import your template into Anypoint Studio, follow these steps to run it:

1. Locate the properties file `mule.dev.properties`, in src/main/resources.
2. Complete all the properties required per the examples in the "Properties to Configure" section.
3. Right click the template project folder.
4. Hover your mouse over `Run as`.
5. Click `Mule Application (configure)`.
6. Inside the dialog, select Environment and set the variable `mule.env` to the value `dev`.
7. Click `Run`.

<!-- Running on Studio (start) -->

<!-- Running on Studio (end) -->

### Run on Mule Standalone
Update the properties in one of the property files, for example in mule.prod.properties, and run your app with a corresponding environment variable. In this example, use `mule.env=prod`.


## Run on CloudHub
When creating your application in CloudHub, go to Runtime Manager > Manage Application > Properties to set the environment variables listed in "Properties to Configure" as well as the mule.env value.
<!-- Running on Cloudhub (start) -->

<!-- Running on Cloudhub (end) -->

### Deploy a Template in CloudHub
In Studio, right click your project name in Package Explorer and select Anypoint Platform > Deploy on CloudHub.
<!-- Deploying on Cloudhub (start) -->

<!-- Deploying on Cloudhub (end) -->

## Properties to Configure
To use this template, configure properties such as credentials, configurations, etc.) in the properties file or in CloudHub from Runtime Manager > Manage Application > Properties. The sections that follow list example values.
### Application Configuration
<!-- Application Configuration (start) -->
**Note**: The watermark for ServiceNow is in the GMT timezone. The initial value you set in *snow.watermark.default.expression* will be therefore converted before the usage.			

- page.size `10`			
- scheduler.frequency `10000`
- scheduler.startDelay `500`


#### Workday Connector Configuration

- wday.username `user`
- wday.password `secret`
- wday.tenant `company`
- wday.hostname `wd2-impl-services1.workday.com`
- wday.watermark.default.expression `2018-01-01T07:53:00Z`

#### Default Values for Required WorkDay Fields

- wday.country `USA`
- wday.state `USA-CA`
- wday.organization `50006855`
- wday.jobprofileId `39905`
- wday.postalCode `90001`
- wday.address `San Francisco 123`
- wday.city `San Francisco`
- wday.location `San_Francisco_Site`
- wday.currency `USD`
- wday.responseTimeout `25000`

#### ServiceNow Connector Configuration

- snow.user `user`
- snow.password `secret`
- snow.endpoint `https://dev.service-now.com`
- snow.integration.user.id `user`
- snow.watermark.default.expression `2018-01-01T07:53:00Z`
- snow.version `snow_version`
<!-- Application Configuration (end) -->

# API Calls
<!-- API Calls (start) -->
There are no special considerations regarding API calls.
<!-- API Calls (end) -->

# Customize It!
This brief guide provides a high level understanding of how this template is built and how you can change it according to your needs. As Mule applications are based on XML files, this page describes the XML files used with this template. More files are available such as test classes and Mule application files, but to keep it simple, we focus on these XML files:

* config.xml
* businessLogic.xml
* endpoints.xml
* errorHandling.xml
<!-- Customize it (start) -->

<!-- Customize it (end) -->

## config.xml
<!-- Default Config XML (start) -->
This file provides the configuration for connectors and configuration properties. Only change this file to make core changes to the connector processing logic. Otherwise, all parameters that can be modified should instead be in a properties file, which is the recommended place to make changes.
<!-- Default Config XML (end) -->

<!-- Config XML (start) -->

<!-- Config XML (end) -->

## businessLogic.xml
<!-- Default Business Logic XML (start) -->
This file holds the functional aspect of the template (points 2. to 4. described in the template overview). Its main component is a batch job, and it includes *steps* for both executing the synchronization from Workday to ServiceNow and the other way around.
<!-- Default Business Logic XML (end) -->

<!-- Business Logic XML (start) -->

<!-- Business Logic XML (end) -->

## endpoints.xml
<!-- Default Endpoints XML (start) -->
This file should contain every inbound and outbound endpoint of your integration app. It is intended to contain the application API.
In this particular template, this file contains a couple of poll inbound endpoints that query Workday and ServiceNow for updates using watermark as mentioned before.
<!-- Default Endpoints XML (end) -->

<!-- Endpoints XML (start) -->

<!-- Endpoints XML (end) -->

## errorHandling.xml
<!-- Default Error Handling XML (start) -->
This file handles how your integration reacts depending on the different exceptions. This file provides error handling that is referenced by the main flow in the business logic.
<!-- Default Error Handling XML (end) -->

<!-- Error Handling XML (start) -->

<!-- Error Handling XML (end) -->

<!-- Extras (start) -->

<!-- Extras (end) -->
