package org.rock.paper.scissors.entities;

import java.util.function.IntSupplier;

public interface IHandDispatcher {
    IntSupplier action();
    IHand hand();
}
