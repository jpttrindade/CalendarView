package br.com.jpttrindade.calendarview;

/**
 * Created by joaotrindade on 08/09/16.
 */
public class WeekManager {

    private int t[] = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};

    public int getCentury(int year) {
        return year / 100 + ((year % 100) == 0 ? 0 : 1);
    }


    public Week[] getWeeks(int month, int year) {


        return null;
    }



    public int getWeekDay(int day, int month, int year) {
        year -= (month < 3) ? month : 0;
        return 1 + (year + (year / 4) - (year / 100) + (year / 400) + t[month - 1] + day) % 7;
    }


    // retorna o numero total de semanas de um dado mes/ano
    public int getWeekCount(int month, int year) {
        int wd = getWeekDay(1, month, year);

        int daysInMonth = 0;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30;
        } else {
            if (month == 2) {
                daysInMonth = (isLeapYear(year)) ? 29 : 28;
            } else {
                daysInMonth = 31;
            }
        }



        return (daysInMonth/7) + (((daysInMonth%7) + wd) >= 7 ? 1 : 0);

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