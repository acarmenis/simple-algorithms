package org.weather.api;


import org.weather.api.schedules.RepeatedlyTask;

/**
 * App class the entry point.
 *
 */
public class App {

    public static void main( String[] args ) {

        System.out.println("\n\nAbout to schedule task.");

        /** Scheduler which kicks-off the task processing **/
        new RepeatedlyTask();

        System.out.println(".. Task scheduled.");

    }
}
