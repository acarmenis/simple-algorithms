# Getting Started

### Reference Documentation

For further reference, please contact the author at:  a.karmenis@outlook.com

### JAVA Developer Assessment (Deliverables: ATM machine)
        
    Requirement: 
     
           ATM machine


                 We all know how ATM machines work… don’t we? You are tasked with developing software for one that:
                 - should initialize with the following accounts:


     Account Number	PIN	Opening Balance	Overdraft
     123456789		1234	800			200
     987654321		4321	1230			150
     

            - should initialize with €1500 made up of 10 x €50s, 30 x €20s, 30 x €10s and 20 x €5s
            - should not dispense funds if the pin is incorrect,
            - cannot dispense more money than it holds,
            - cannot dispense more funds than customer have access to
            - should not expose the customer balance if the pin is incorrect,
            - should only dispense the exact amounts requested,
            - should dispense the minimum number of notes per withdrawal,
              The application should receive the data, process the operations and then output the results, it is 
              responsible for validating customer account details and performing basic operations as described by 
              API requirements:
            - User (assume any rest client – curl, postman, browser) should be able to request a balance check along 
              with maximum withdrawal amount (if any),
            - User should be able to request a withdrawal. If successful - details of the notes that would be dispensed 
              along with remaining balance,
            - If anything goes wrong, user should receive meaningful message, and there should be no changes in 
              user’s account,


    Assume application will be distributed as Docker image. Provide Dockerfile, but don’t waste too much time for 
           building and testing docker image, focus on functionality.
    Assume importance levels:
            1 – Code working as described in requirements,
            2 – Application is building with simple javac, mvn install or gradle build command (or any basic build 
                command working on behalf of programming language you choose),
            3 - Unit tests are included. Coverage level depends on time you have left to complete the assignment, 
                but we would like to see business logic (service layer) coverage at 60%,
            4 – Other things you would like to implement for this project (ex. Database, application test coverage over 
                90%, API for gathering different statistics,  UI or whatever else you think would make your 
                application extraordinary),



### Application explanations.
- The ATM Machine application facilitates the software design principles.
- An attempt of Object-Oriented implementation which emphasises the flexibility and maintainability of code to further 
extent.
* Designed object-oriented code for ATM complex modeling. 
* Abstraction the replacement for explicit loops and branching.
* Readable and ease to manage extensions.
* Interface designs
* Domain-related code
* Composable multi module layers(domain, persistence, services, controllers)
* Alternatives to hard-coded decisions with the Rules design pattern (Behavioral design pattern)
* [Chain-of-responsibility pattern](https://en.wikipedia.org/wiki/Chain-of-responsibility_pattern)
* [State pattern](https://en.wikipedia.org/wiki/State_pattern)
* Business logic rules.

 
Application's tech stack
* JAVA 11
* MAVEN
* Spring Boot
* H2 Database (in mem)
* JPA - and JPA annotated field restrictions.
* H2 Database can be accessed in the browser after the applications gets up and running @: http://localhost:8080/h2-console/
* \resources\app.properties

### Guides on how to run/test the application.
* It is all about a SpringBoot java application.
* Building tool is the maven
* Make a maven [clean + install]
* It simply can be tested/run by Running the Run 'App.main()' method (SpringApplication.run(App.class).
* Results, can be seen in the IDE's console.
* End point of the application is the http://localhost:8080/api/atm/client/request

### Guides on what to expect from the application.
* The rest client – Postman, may be used to make the requests.
* A post request body should look like the below json entity.
 
                Request example

                {
                  "pin":"4321",
                  "money":"25"
                }

* The response, in case of any error is returned in many variations, according to any related issue. A sample is listed below.

                Response example

                 {
                   "timestamp": "20-02-2022 11:17:54",
                   "code": 400,
                   "status": "BAD_REQUEST",
                   "message": "Exceeds issuer withdrawal limit.",
                   "stackTrace": null,
                   "data": null
                 }


 
* whilst, the error in the console's IDE looks as below. The app stops at that step and does not proceed any further.
* The transaction fails and does not do any change at the balances and atm available amounts.


                Error console example

                    EnterCashCardState   : 1. Cash card entrance in the atm' slot.
                         EnterPinState   : 2. Pin entered at the atm machine.
                                         : 		user's input pin: 4321
                                         : 		is user's input a valid pin: true
                                         : 		was successfully, retrieved user's account from database: true
                       WithdrawalState   : 3. Withdrawal attempt.
                                         : 		Requested money: 205€.
                                         : 		was successfully, retrieved user's account from database: true
                                         :      Error code: D0024, Error message: Exceeds issuer withdrawal limit. 150€.

 

### Application successful console's output.
        
        : 1. Cash card entrance in the atm' slot.
        : 2. Pin entered at the atm machine.
        : 		user's input pin: 4321
        : 		is user's input a valid pin: true
        : 		was successfully, retrieved user's account from database: true
        : 3. Withdrawal attempt.
        : 		Requested money: 25€.
        : 		was successfully, retrieved user's account from database: true
        : 4. Dispense from ATM transaction.
        : 		was successfully, retrieved user's account from database: true
        : Bank Account Balance Before Deduction 1205€.
        : ATM Balance Before Deduction 1250€, 50€->6, 20€->28, 10€->30, 5€->18
        : 		Dispensing 1 note of 20€
        : 		Dispensing 1 note of 5€
        : Bank Account Balance After Deduction 1180€.
        : ATM Balance After Deduction 1225€, 50€->6, 20€->27, 10€->30, 5€->17
        : 5. Cash card exported from atm' slot.


