package br.com.jpttrindade.calendarview.data;

/**
 * Created by jpttrindade on 10/09/16.
 */
public class Month {
    public int year;
    public Week[] weeks;
    public int value;

    public Month(int value, int year) {
        this.year = year;
        this.value = value;
        weeks = WeekManager.getWeeks(value, year);
    }

}
