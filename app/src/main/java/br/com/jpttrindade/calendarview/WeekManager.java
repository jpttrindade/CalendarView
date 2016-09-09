package br.com.jpttrindade.calendarview;

import android.util.Log;

/**
 * Created by joaotrindade on 08/09/16.
 */
public class WeekManager {

    //private final int t[] = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
    private final int t[] = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};

    public int getCentury(int year) {
        return year / 100 + ((year % 100) == 0 ? 0 : 1);
    }


    public Week[] getWeeks(int month, int year) {
        int wkCount = getWeekCount(month, year);

        Week[] weeks = new Week[wkCount];

        int weekDay = getWeekDay(1, month, year);
        int firstDay = 1;
        int weekMaxDay = getWeekMaxDay(month, year);

        Week wk;
        for (int week=0; week<wkCount; week++) {
            wk = new Week(firstDay, weekDay, weekMaxDay);
            weeks[week] = wk;
            firstDay = wk.getLastDay()+1;
            weekDay = 1;
        }

        return weeks;
    }

    // retorna o dia da semana de uma determinada data
    public int getWeekDay(int day, int month, int year) {

        year -= month < 3 ? month : 0;
        int x = (year + (year / 4) - (year / 100) + (year / 400) + t[month - 1] + day);

        //int x = (day + month + year + (year/4) + getCentury(year));



        return x % 7;
    }


    // retorna o numero total de semanas de um dado mes/ano
    public int getWeekCount(int month, int year) {
        int wd = getWeekDay(1, month, year);

        Log.i("CALENDAR_VIEW", "WeekDay = " + wd);

        if (month == 4 || month == 6 || month == 9 || month == 11) {
            //30
            if (wd == 7) {
                return 6;
            }
            return 5;
        } else {
            if (month == 2) {
                if (isLeapYear(year)) {
                    //29
                    return 5;
                }
                if (wd > 1) {
                    //28
                    return 5;
                }
                //28
                return 4;
            } else {
                if (wd > 5) {
                    //31
                    return 6;
                }
                //31
                return 5;
            }
        }
    }

    public int getWeekMaxDay(int month, int year) {
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            if (month == 2) {
                return (isLeapYear(year)) ? 29 : 28;
            } else {
                return 31;
            }
        }
    }
    // diz se o ano eh bissexto
    private boolean isLeapYear(int year) {
        if ((year % 4) > 0) {
            return false;
        } else if ((year % 100) > 0) {
            return false;
        } else if ((year % 400) > 0) {
            return false;
        } else {
            return true;
        }

    }

}