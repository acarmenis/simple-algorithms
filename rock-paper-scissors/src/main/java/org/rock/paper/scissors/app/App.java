package org.rock.paper.scissors.app;


import org.rock.paper.scissors.entities.*;
import org.rock.paper.scissors.factories.HandFactory;
import org.rock.paper.scissors.threads.CallableThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * App class the entry point.
 *
 */
public class App {

    static int ROUNDS = 100;

    public static void main( String[] args )  {

        Rock rock = null;
        Paper paper = null;
        Scissors scissors = null;
        try {
             rock = (Rock) HandFactory.createHand(HandType.ROCK);
             paper = (Paper) HandFactory.createHand(HandType.PAPER);
             scissors = (Scissors) HandFactory.createHand(HandType.SCISSORS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Hand [] hands = {rock, paper, scissors};

        // Create pool of threads - just 1 in our case
        ExecutorService executor = Executors.newFixedThreadPool(1);

        List<Future<GameWrapper>> games = new ArrayList<>();

        AtomicIntegerArray results = new AtomicIntegerArray(3);

        for(int round = 0; round < ROUNDS; round++){
            int index = new Random().nextInt(hands.length);
            GameWrapper gameWrapper = new GameWrapper(3, paper, hands[index], round);
            Callable<GameWrapper> callable = new CallableThread(gameWrapper);
            //submit Callable tasks to be executed by thread pool
            Future<GameWrapper> game = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            games.add(game);
        }

        for(Future<GameWrapper> game : games){
            try {
                // print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                results.getAndSet(0, results.get(0)+game.get().getResults().get(0));
                results.getAndSet(1, results.get(1)+game.get().getResults().get(1));
                results.getAndSet(2, results.get(2)+game.get().getResults().get(2));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nPlayer A wins: " + results.get(0) + " of "+ROUNDS+" games");
        System.out.println("Player B wins: " + results.get(2) + " of  "+ROUNDS+" games");
        System.out.println("Tie: " + results.get(1) + " of "+ROUNDS+" games");

        //shut down the executor service now
        executor.shutdown();

    }
}
