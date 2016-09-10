package br.com.jpttrindade.calendarview.data;

import br.com.jpttrindade.calendarview.data.Week;

/**
 * Created by joaotrindade on 08/09/16.
 */
public class WeekManager {

    //private final int t[] = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
    private final int t[] = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};

    public int getCentury(int year) {
        return year / 100 + ((year % 100) == 0 ? 0 : 1);
    }


    public static Week[] getWeeks(int month, int year) {
        int wkCount = getWeekCount(month, year);

        Week[] weeks = new Week[wkCount];

        int weekDay = getWeekDay(1, month, year);
        int firstDay = 1;
        int weekMaxDay = getWeekMaxDay(month, year);
        System.out.println("weekMaxDay = "+ weekMaxDay);

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
    public static int getWeekDay(int day, int month, int year) {

        if ((day < 0) || (day > 31)) {
            //invalido
        }

        if ((month < 0) || (month > 12)) {
            //invalido
        }
        int val2x = month;
        if (year < 1900) {
            //nao suportado
        }

        if (month == 1) {
            val2x = 13;
            year = year-1;
        }
        if (month == 2) {
            val2x = 14;
            year = year-1;
        }
        int val4 = ((val2x+1)*3)/5;
        int val5 = year/4;
        int val6 = year/100;
        int val7 = year/400;

        int val8 = day+(val2x*2)+val4+year+val5-val6+val7+2;
        int val9 = val8/7;
        int val0 = val8-(val9*7);

/*
        days[0] = "Sábado"
        days[1] = "Domingo"
        days[2] = "Segunda-Feira"
        days[3] = "Terça-Feira"
        days[4] = "Quarta-Feira"
        days[5] = "Quinta-Feira"
        days[6] = "Sexta-Feira"*/
        return val0 == 0? 7 : val0;
    }


    // retorna o numero total de semanas de um dado mes/ano
    public static int getWeekCount(int month, int year) {
        int wd = getWeekDay(1, month, year);

//        Log.i("CALENDAR_VIEW", "WeekDay = " + wd);

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

    public static int getWeekMaxDay(int month, int year) {
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
    private static boolean isLeapYear(int year) {
        if ((year % 4) > 0) {
            return false;
        } else if ((year % 100) > 0) {
            return true;
        } else if ((year % 400) > 0) {
            return false;
        } else {
            return true;
        }

    }

}