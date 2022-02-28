# Getting Started

### Reference Documentation

For further reference, please contact the author at:  a.karmenis@outlook.com

### JAVA Developer Assessment (Deliverables: Assessment  N° 4)
    Assesment 4
    
    Requirement: 
     
           Weather Api

                 As a User I want a mechanism capable to examine whether data and depending 
                 on the temperature send an sms message to a specified number.
                 Use Weather Api from https://openweathermap.org/  to access current weather data for the Thessaloniki.
                 API documentation https://openweathermap.org/api
                 Use the endpoint api.openweathermap.org for your API calls
                 For the api calls use the key  b385aa7d4e568152288b3c9f5c2458a5
                 If the temperature is greater than 20C send SMS message to +30 6922222222 with text 
                 "Your name and Temperature more than 20C. <the actual temperature>" 
                 else send sms message to +30  6922222222 with text 
                 "Your name and Temperature less than 20C. <the actual temperature>"
                 where <the actual temperature> the temperature that the weather api returns for Thessaloniki.
      
                 Repeat the above procedure every 10 minutes for 10 times and then stop.

          SMS
                 In order to send the SMS use the Routee API
                 https://docs.routee.net/docs/
                 Application ID for Routee API:
                 5c5d5e28e4b0bae5f4accfec
                 Application secret:  MGkNfqGud0

                 * For testing purposes, use the credentials only 2-3 times. It is very important to stop 
                   the process after the required tests since the credits might end otherwise.
 

### Application explanations.

Application's tech stack
* JAVA 11
* MAVEN

Application uses as all the assignments, java core functionalities.
it just make usage of three out of java core dependencies.

       .json-simple
       .okhttp3
       .lombok
 
Also, it externalizes all the application properties in the below file.

       \resources\app.properties
       
The class RepeatedlyTask, is a Thread class that creates the Timer scheduler,
and the TimerTask to perform the tasks.

Into the properties file mentioned just above, will be found among the rest, 
the below properties:


        # Delay the attempt in seconds (10 minutes -> 10*60 sec)
        default.delay.in.seconds=600
        
        # tries repetitions (please, change it accordingly)
        default.tries.number=10

That means that any user of the application, may change their values to suits to his/her needs.

 
### Guides on how to run/test the application.
* It is all about a very simple java mavenized application.
* Make a maven [clean + install]
* It simply can be tested/run by Running the Run 'App.main()' method.
* Results, can be seen in the IDE's console.



### Application console's output.
        
        INTO THREAD - countdown: (10), Time: 2022-02-12 19:59:11
        message to send: {"from":"amdTelecom","to":"+306922222222","body":"Andreas Karmenis, Temperature less than 20C. Live temperature: 11.67C."}
        SUCCESSFULLY Response status:	Queued
        
        INTO THREAD - countdown: (9), Time: 2022-02-12 20:09:11
        message to send: {"from":"amdTelecom","to":"+306922222222","body":"Andreas Karmenis, Temperature less than 20C. Live temperature: 11.6C."}
        SUCCESSFULLY Response status:	Queued
            .
            .
            .
            .
            .
            .
        Time's up!

