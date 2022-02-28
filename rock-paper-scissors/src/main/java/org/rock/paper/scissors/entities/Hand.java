package org.rock.paper.scissors.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;

/**
 * Abstract class which must be derived from its children
 */
public abstract class Hand implements IHand {

    //  the hand name
    public String name;

    // list of hand dispatches
    List<IHandDispatcher> hands;

    // constructs hand's name and
    public Hand(String name) {
        this.name = name;
        this.hands = new ArrayList<>();
    }

    // overrides the hand's name
    @Override
    public String name() {
        return name;
    }

    // adds
    @Override
    public void add(IHand hand, IntSupplier action) {
        // inner class implements the dispatcher
       hands.add(new IHandDispatcher() {
           @Override
           public IntSupplier action() {
               // returns -1,1
               return action;
           }

           @Override
           public IHand hand() {
               // returns the hand's children, i.e, Scissors, Paper and Rock
               return hand;
           }
       });
    }

    @Override
    public List<IHandDispatcher> getHands() {
        return hands;
    }
}
