package org.rock.paper.scissors.threads;

import org.rock.paper.scissors.entities.*;

import java.util.concurrent.Callable;

public class CallableThread implements Callable<GameWrapper> {

    private final GameWrapper gameWrapper;

    public CallableThread(GameWrapper gameWrapper) {
        this.gameWrapper = gameWrapper;
    }

    @Override
    public GameWrapper call() throws Exception {
        Thread.sleep(1000);
        int player1VSPlayer2 = gameWrapper.getFirstPlayer().play(gameWrapper.getSecondPlayer());
        System.out.println("round: "+(gameWrapper.getRound()+1)+ ", "+gameWrapper.getFirstPlayer().name()+" VS "+gameWrapper.getSecondPlayer().name()+": " + player1VSPlayer2);
        switch (player1VSPlayer2){
            case -1: {
                gameWrapper.getResults().addAndGet(2, 1);
            } break ;
            case 0: {
                gameWrapper.getResults().addAndGet(1, 1);
            } break ;
            case 1: {
                 gameWrapper.getResults().addAndGet(0, 1);
            } break ;
        }
        return gameWrapper;
    }
}
