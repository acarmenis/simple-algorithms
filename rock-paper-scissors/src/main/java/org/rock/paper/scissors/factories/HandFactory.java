package org.rock.paper.scissors.factories;

import org.rock.paper.scissors.entities.*;

public class HandFactory {

    public static Hand createHand(HandType handType) throws Exception {

        Hand hand = null;

        switch(handType){
            case ROCK:{
                Rock rock = new Rock("Rock");
                rock.add(new Scissors("Scissors"),  () -> 1); // Rock beat Scissors
                rock.add(new Paper("Paper"),  () -> -1); // Paper beats Rock
                hand = rock;
            } break ;
            case PAPER:{
                Paper paper = new Paper("Paper");
                paper.add(new Rock("Rock"),  () -> 1); // Paper beats Rock
                paper.add(new Scissors("Scissors"),  () -> -1); // Paper beats Scissors
                hand = paper;
            } break ;
            case SCISSORS:{
                Scissors scissors = new Scissors("Scissors");
                scissors.add(new Paper("Paper"),  () -> 1); // Scissors beat Paper
                scissors.add(new Rock("Rock"),  () -> -1); // Rock beat Scissors
                hand = scissors;
            } break ;

            default: throw new Exception("Unknown handType " + handType) ;
        }

        return hand;
    }



}
