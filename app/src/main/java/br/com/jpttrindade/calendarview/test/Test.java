package br.com.jpttrindade.calendarview.test;

import java.util.Calendar;

import br.com.jpttrindade.calendarview.WeekManager;

/**
 * Created by joaotrindade on 09/09/16.
 */
public class Test {
    public static void main (String[] args) {
        WeekManager manager = new WeekManager();



        int weeDay = manager.getWeekDay(1,4,2016);

        System.out.println("WeekDay = "+weeDay);
    }
}
