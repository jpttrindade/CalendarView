package br.com.jpttrindade.calendarview.data;

import android.util.Log;

/**
 * Created by jpttrindade on 10/09/16.
 */
public class Month {
    public int year;
    public Week[] weeks;
    public int value;
    public int lastDay;

    public Month(int value, int year) {
        this.year = year;
        this.value = value;
        weeks = WeekManager.getWeeks(value, year);
        lastDay = weeks[weeks.length-1].getLastDay();
    }

}
