# Getting Started

### Reference Documentation

For further reference, please consider the following sections:
 

### Problem Definition
A busy hospital has a list of dates that a doctor is available to see patients. Their process is manual and error prone leading to overbooking. 
They also have a hard time visualizing all of the available time for a doctor for specific dates.
  * Interview Task
    * Create a REST API that enables a simple scheduling system that manages doctor availabilities and allows patients to book appointments.
  * Data Model
###  Define a set of data models that include:
 *  a way track assign booked appointments
 *  a way to track patients
 *  a way to track doctors
 *  a way to track a doctor's working hours and days
### REST API
Implement the following functionality:
*	Find a doctor's working hours
*	Book a doctor opening
*	Create and update the list of doctor's working hours
### Non-Functional Requirements
*	Tech stack: Java 11, Spring Boot, Maven or Gradle
*	Use JPA 2.x and Spring Data for persistence
*	All API calls are unsecured, you don’t need to implement security

### Tools & functionalities used for the implementation

### 1 Swagger
  * It is a mavenized application 3.6.8 version
  * java 11
  * a layered application separated into modules (domain, persistence, services, web, utils)
  * Jpa + jpa specification at the persistence layer
  * map util to convert from to entity to dto and vice versa
  * Enabled the Cashel2 for the jpa repos
  * enabled json identify tool for avoiding the infinite looping on the responses from entities/dtos
  * use of H2 database accessed @  [http://localhost:8080/h2-console] 
  * multi profiles usage for dev, test
  * ant run scripts into maven for information
  * time added at the jar files to separate buildings
  * transaction context enabled at the service and persistence layers
  * swagger enabled @  http://localhost:8080/swagger-ui/
  * enabled the auditing for tables for auditing
  * It uses exception handlers to deal with exceptions and answers with a proper error message
 
### Guides

The following guides illustrate how to use some features concretely:

the application is a springboot one.
It can be downloaded and clean + build
Then it can be started as a normal springboot application
When the application starts, then it fills up the H2 database with initial 
data to work with.
That is made at the public class DoctorAppointmentApplication implements CommandLineRunner {
and it loads from there before the spring context gets running.
It would have been used the sql scripts but preferred to init by implementing code for that.
The rest endpoints can be found at web module @  com.doctor.appointment.web.api  package
There are a few endpoints that access the resources for each domain
There is also a DashBoard Controller which can be used to list doctor's, availabilities and some of crud operations 
for all domains.

After running the application, may be used the below queries in the database to look even further to that.
  * select * from appointment;
  * select * from appointment_status;
  * select * from doctor;
  * select * from doctor_office_availability;
  * select * from doctor_specialization;
  * select * from hospital;
  * select * from office;
  * select * from patient;
  * select * from specialization;
  * select * from seeing_a_doctor;

DoctorResource, DashBoardController, AppointmentResource can be found the main endpoints to serve the application purposes.

Among other useful endpoints, the below return a list for available openings and for a certain specialization of
the doctor, in this particular case it lookup for Dematologist, and for a certain hospital
the response is a jsoan format listed below (1)
http://localhost:8080/backend/api/v1/booking/true/Dermatologist/Health Riverside Methodist Hospital
[
    {
    "availability": true,
    "availabilityDate": "2022-11-12",
    "startTime": "01:31:08",
    "endTime": "02:11:08",
    "officeId": 2,
    "timeSlotPerClientInMinutes": 40,
    "doctorId": 2,
    "hospitalId": 2,
    "firstName": "Fouad",
    "lastName": "Abbas",
    "doctorSpecializationId": 5,
    "doctorSpecializationName": "Dermatologist"
    }
]

another endpoint for getting the full json for the same purpose is  (2)
http://localhost:8080/backend/api/v1/doctors/doctor/Endocrinologist/Phoenix Hospital Center


in order to create a appointment, please after (1) endpoint 
send at the below endpoint the 
http://localhost:8080/backend/api/v1/booking/book-appointment

{ 
    "patientId":1,
        "availability":  {
        "availability": true,
        "availabilityDate": "2022-11-12",
        "startTime": "01:31:08",
        "endTime": "02:11:08",
        "officeId": 2,
        "timeSlotPerClientInMinutes": 40,
        "doctorId": 2,
        "hospitalId": 2,
        "firstName": "Fouad",
        "lastName": "Abbas",
        "doctorSpecializationId": 5,
        "doctorSpecializationName": "Dermatologist"
        }
}


### For access to database for the dev env, please, have a look at the dev properties 
 * there are the necessary info for db connectivity. The same applies for the test env too.

