package org.rock.paper.scissors.entities;

import java.util.List;
import java.util.Optional;
import java.util.function.IntSupplier;

/**
 * IHand interface
 */
public interface IHand {

    String name();

    // adding hand Dispatcher
    void add(IHand hand, IntSupplier action);

    // list of hand dispatchers
    List<IHandDispatcher> getHands();

    // default method
    default int dispatch(IHand hand){

        /**
         * The Class.isAssignableFrom method will return true if the Class on the left-hand side
         * of the statement is the same as or is a superclass or superinterface
         * of the provided Class parameter.
         * if 0, it means that they are of the same class i.e Paper and Paper and so on
         */
      if(this.getClass().isAssignableFrom(hand.getClass())) return 0;


        /** isAssignableFrom()
         * this method tests whether the type represented by
         * the specified Class parameter can be converted to the type
         * represented by this Class object via an identity conversion
         * or via a widening reference conversion.
         */
      Optional<IHandDispatcher> dispatcher = getHands()
             .stream()
             .filter(d -> d.hand().getClass().isAssignableFrom(hand.getClass()))
             .findAny();

      // Two types: we can examine whether one type is compatible with another type, such as the isAssignableFrom method.
      // will return the IntSupplier from HandFactory configurations factory - depending on the pre-set values
      return dispatcher.map(handDispatcher -> handDispatcher.action().getAsInt()).orElse(-1);
    }

    // accepts Hand's instances , namely, Rock, Paper, Scissors
    default int play(IHand hand){
       return  dispatch(hand);
    }

}
