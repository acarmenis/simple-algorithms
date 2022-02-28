# Getting Started

### Reference Documentation

For further reference, please contact the author at:  a.karmenis@outlook.com

### JAVA Developer Assessment (Deliverables: Rock-Paper-Scissors Game)
        
    Requirement: 
     
           Task: Rock-Paper-Scissors

                    Build a game in which two players compete in a game of Rock-Paper-Scissors with different
                    strategies. Who will win more rounds? The rules:

                    * Scissors beats Paper
                    * Rock beat Scissors
                    * Paper beats Rock
                    * If both players choose the same, the round is counted as a tie.

                    Implement two players:
                    * Player A always chooses Paper
                    * Player B chooses randomly

                    The game consists of 100 rounds of above two players competing. The output of the program

                    should be like the following:

                    "Player A wins 31 of 100 games"
                    "Player B wins 37 of 100 games"
                    "Tie: 32 of 100 games"



### Application explanations.
- The Rock-Paper-Scissors Game application facilitates the software design principles.
- An attempt of Object-Oriented implementation which emphasises the flexibility and maintainability of code to further 
extent.
* Designed object-oriented code for Rock-Paper-Scissors Game application complex modeling. 
* Abstraction the replacement for explicit loops and branching.
* Readable and ease to manage extensions.
* Interface designs

* Alternatives to hard-coded decisions with Using Dynamic Dispatching
* [Using Dynamic Dispatching](https://en.wikipedia.org/wiki/Dynamic_dispatch)
* [Interface Callable<V>](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Callable.html)
* [AtomicIntegerArray](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/AtomicIntegerArray.html)
* [Interface IntSupplier](https://docs.oracle.com/javase/8/docs/api/java/util/function/IntSupplier.html)

 
Application's tech stack
* JAVA 11
* MAVEN

### Guides on how to run/test the application.
* It is all about a java mavenized application.
* Building tool is the maven
* Make a maven [clean + install]
* It simply can be tested/run by Running the Run 'App.main()'.
* Results, can be seen in the IDE's console.


### Application successful console's output.
        round: 1, Paper VS Paper: 0
        round: 2, Paper VS Scissors: -1        
            .
            .
            .
            .
        round: 90, Paper VS Scissors: -1
        round: 91, Paper VS Paper: 0
        round: 92, Paper VS Scissors: -1
        round: 93, Paper VS Rock: 1
        round: 94, Paper VS Rock: 1
        round: 95, Paper VS Rock: 1
        round: 96, Paper VS Scissors: -1
        round: 97, Paper VS Scissors: -1
        round: 98, Paper VS Scissors: -1
        round: 99, Paper VS Rock: 1
        round: 100, Paper VS Rock: 1
        
        Player A wins: 32 of 100 games
        Player B wins: 35 of  100 games
        Tie: 33 of 100 games

