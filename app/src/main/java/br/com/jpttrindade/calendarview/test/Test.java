package br.com.jpttrindade.calendarview.test;

import java.util.Arrays;

import br.com.jpttrindade.calendarview.data.Week;
import br.com.jpttrindade.calendarview.data.WeekManager;

/**
 * Created by joaotrindade on 09/09/16.
 */
public class Test {
    public static void main (String[] args) {
        WeekManager manager = new WeekManager();



        int weeDay = manager.getWeekDay(10,9,2016);

        Week[] weeks = manager.getWeeks(1, 2016);

        for (Week wk : weeks) {
            System.out.println(Arrays.toString(wk.days));
        }

        System.out.println("WeekDay = "+weeDay);
    }
}
