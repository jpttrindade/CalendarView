package br.com.jpttrindade.calendarview;

/**
 * Created by joaotrindade on 08/09/16.
 */
public class Week {
    public int days[];


    public Week (int firstDay, int weekDay, int maxDay) {
        days = new int[7];
        int day = firstDay;
        for (int i=weekDay; i<=7; i++) {
            days[i-1] =  day;
            day++;
            if (day == maxDay) {
                i = 8;
            }
        }
    }

    public Week (int firstDay, int weekDay) {
        this(firstDay, weekDay, firstDay+6);
    }

    public int getLastDay() {
        return days[6];
    }

}
