package br.com.jpttrindade.calendarview.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import br.com.jpttrindade.calendarview.R;
import br.com.jpttrindade.calendarview.data.Day;
import br.com.jpttrindade.calendarview.data.WeekManager;
import br.com.jpttrindade.calendarview.data.Month;
import br.com.jpttrindade.calendarview.holders.MonthHolder;

/**
 * Created by joaotrindade on 06/09/16.
 */
public class CalendarAdapter extends RecyclerView.Adapter<MonthHolder> {


    private final List<String> mMonthLabels;
    private final ArrayList<Month> mMonths;
    private final Context mContext;

    private final int startYear; //ano atual (real)
    private final int startMonth; // mes atual (real)



    private int earlyMonthLoaded; //mes mais antigo ja carregado
    private int earlyYearLoaded; //ano mais antigo ja carregado

    private int laterMonthLoaded; //mes mais a frente ja carregado
    private int laterYearLoaded; //ano mais a frente ja carregado

    private WeekManager weekManager;


    private int monthLabelHeight;
    private int weekRowHeight;


    private int PAYLOAD = 3; // o numero de meses que serao carregados antes e depois do mes atual.

    public CalendarAdapter(Context context) {
        mContext = context;
        mMonthLabels = Arrays.asList(context.getResources().getStringArray(R.array.months));
        Calendar c = Calendar.getInstance();
        startYear = c.get(Calendar.YEAR);
        startMonth = c.get(Calendar.MONTH)+1;

        PAYLOAD++;
        mMonths = new ArrayList<Month>();

        if (startMonth < PAYLOAD) {
            earlyYearLoaded = startYear - 1;
            earlyMonthLoaded = 12 - (PAYLOAD - startMonth);
            laterMonthLoaded = startMonth + 3;
            laterYearLoaded = startYear;

        } else if (startMonth > (12-PAYLOAD)) {
            earlyYearLoaded = startYear;
            //TODO conferir esse earlyMonthLoaded
            earlyMonthLoaded = startMonth - PAYLOAD;
            laterYearLoaded = startYear+1;



           // getMonths();
        } else {

        }

        getMonths(earlyMonthLoaded, earlyYearLoaded, laterMonthLoaded, laterYearLoaded);





//        if (startMonth > 1) {
//            lastMonthLoaded = startMonth+1;
//            getMonths(startMonth-1, lastMonthLoaded , startYear);
//
//        } else {
//            lastMonthLoaded = startMonth+2;
//            getMonths(startMonth, lastMonthLoaded, startYear);
//        }

    }



    @Override
    public int getItemViewType(int position) {
        return mMonths.get(position).weeks.length;
    }

    @Override
    public MonthHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.month_view, parent, false);

        MonthHolder mh = new MonthHolder(v, viewType);

        mh.setLabelMonthHeight(monthLabelHeight);
        mh.setWeekRowHeight(weekRowHeight);

        mh.generateWeekRows();

        return  mh;
    }


    @Override
    public void onBindViewHolder(MonthHolder holder, int position) {
        Month m = mMonths.get(position);
        setLabel(holder, m);
        setWeeks(holder, m);
        //Log.d("DEBUG", "position = "+position);
        //Log.d("DEBUG", "month = "+mMonthLabels.get(m.value-1));

    }

    private void setLabel(MonthHolder holder, Month m) {
        String year = (m.year != startYear ? " de "+m.year : "");
        holder.label_month.setText(mMonthLabels.get(m.value-1) + year);

        if(m.value == startMonth) {
            holder.label_month.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        } else {
            holder.label_month.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
    }



    private void setWeeks(MonthHolder holder, Month m) {
        TextView[] weekColumns;
        Day[] days;
        TextView tv_day;


        for (int i=0; i<holder.weekRowsCount; i++) {
            weekColumns = holder.weeksColumns.get(i);
            days = m.weeks[i].days;

            for (int j=0; j<7; j++){
                tv_day = weekColumns[j];
                tv_day.setText("" + days[j].value);
                if(days[j].value == 0) {
                    tv_day.setTextColor(Color.TRANSPARENT);
                } else {
                    tv_day.setTextColor(Color.BLACK);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mMonths.size();
    }


    public void setMonthLabelHeight(int monthLabelHeight) {
        this.monthLabelHeight = monthLabelHeight;
    }

    public void setWeekRowHeight(int weekRowHeight) {
        this.weekRowHeight = weekRowHeight;
    }

    public void onLoadMore(int currentPage) {
        Log.d("DEBUG", "CalendarAdapter.onLoadMore()");
//        if(lastMonthLoaded == 12) {
//            getMonths(1, 3, startYear + 1);
//            lastMonthLoaded = 3;
//        }
//        if (lastMonthLoaded == 11) {
//            getMonths(lastMonthLoaded+1, lastMonthLoaded+1, startYear);
//            getMonths(1, 2, startYear+1);
//            lastMonthLoaded = 2;
//        }
//
//        if (lastMonthLoaded == 10) {
//            getMonths(lastMonthLoaded+1, lastMonthLoaded+2, startYear);
//            getMonths(1, 1, startYear+1);
//            lastMonthLoaded = 1;
//        }


        notifyDataSetChanged();
        //getMonths(lastMonthLoaded+1, lastMonthLoaded+3, );
    }


    private void getMonths(int earlyMonth, int earlyYear, int laterMonth, int laterYear){
        ArrayList<Month> tempMonths = new ArrayList<>();

        for (int i=earlyMonth; i<13; i++) {
            tempMonths.add(new Month(i, earlyYear));
        }

        for (int i=1; i<laterMonth+1; i++) {
            tempMonths.add(new Month(i, laterYear));
        }

        if (laterYear > startYear) {
            mMonths.addAll(tempMonths);
            return;
        }

        if (laterYear == startYear){
            if(laterMonth > startMonth){
                mMonths.addAll(tempMonths);
                return;
            }
        }

        mMonths.addAll(0,tempMonths);

    }

    private void getMonths(int firtMonth, int lastMonth, int year) {
        for(int i=firtMonth; i<lastMonth+1; i++) {
            mMonths.add(new Month(i, year));
        }
    }

}
