package br.com.jpttrindade.calendarview.data;

import android.util.Log;

/**
 * Created by joaotrindade on 08/09/16.
 */
public class Week {
    public Day days[];


    public Week (int firstDay, int weekDay, int maxDay) {
        days = new Day[7];

        int day = 0;
        int incr = 0;
        for (int i=1; i<=7; i++) {
            if(i==weekDay){
                day = firstDay;
                incr = 1;
            }
            days[i-1] = new Day(day);
            if (day==maxDay) {
                day=0;
                incr=0;
            }
            day+=incr;
        }
    }

    public Week (int firstDay, int weekDay) {
        this(firstDay, weekDay, firstDay+6);
    }

    public int getLastDay() {
        int i=6;
        while (days[i].value == 0) {
            i--;
        }
        return days[i].value;
    }

}
