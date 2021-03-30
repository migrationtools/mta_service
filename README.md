# mta_service
Spring Boot Based Backend Service that is used to invoke the red hat migration toolkit and generate reports.  Works in conjunction with the angular mta_client.  <BR />


Pre-requisites:  Maven 3.6.2 or later  <BR />


A bunch of properties in application.properties need to be updated.  Watch for below properties and update them to your local settings.  

#Upload Directory  
mta.upload.ear.directory=C:\\Users\\sures\\Documents\\projects\\redhat_conf_2021\\mta_upload_ear_directory\\  

mta.upload.ear.output=C:\\Users\\sures\\Documents\\projects\\redhat_conf_2021\\mta_output\\  

mta.bin.path=C:\\Users\\sures\\Documents\\tools\\mta-cli-5.1.0.Final\\bin\\  

jgit.src.checkout.dir=C:\\Users\\sures\\Documents\\projects\\redhat_conf_2021\\gitcheckout\\  

#Accessing the folder content  
spring.resources.static-locations=file:C:\\Users\\sures\\Documents\\projects\\redhat_conf_2021\\mta_output  


To build, open a command prompt to the project root folder and run:  
mvn clean install  

To run:  

mvn spring-boot:run  


