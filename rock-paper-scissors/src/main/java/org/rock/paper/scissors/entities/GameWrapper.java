package org.rock.paper.scissors.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicIntegerArray;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GameWrapper {

    private int players;
    private AtomicIntegerArray results;
    private Paper firstPlayer;
    private Hand secondPlayer;
    private int round;

    public GameWrapper(int players, Paper firstPlayer, Hand secondPlayer, int round) {
        this.players = players;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        if((results == null)||(results.length()<=0 )){
           this.results = new AtomicIntegerArray(players);
        }
        this.round = round;
    }
}
