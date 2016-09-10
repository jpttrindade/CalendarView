package br.com.jpttrindade.calendarview.data;

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
            if (day == maxDay) {
                i = 8;
            }
            day++;
        }
    }

    public Week (int firstDay, int weekDay) {
        this(firstDay, weekDay, firstDay+6);
    }

    public int getLastDay() {
        return days[6];
    }

}
