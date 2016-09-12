package br.com.jpttrindade.calendarview.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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
    private final int startYear;
    private WeekManager weekManager;
    private int mMonth;


    private int monthLabelHeight;
    private int weekRowHeight;


    public CalendarAdapter(Context context) {
        mContext = context;
        mMonthLabels = Arrays.asList(context.getResources().getStringArray(R.array.months));
        Calendar c = Calendar.getInstance();
        startYear = c.get(Calendar.YEAR);
        mMonths = new ArrayList<Month>();

        getMonths(startYear);

    }

    private void getMonths(int year) {
        for(int i=1; i<13; i++) {
            mMonths.add(new Month(i, year));
        }
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
    }

    private void setLabel(MonthHolder holder, Month m) {
        holder.label_month.setText(mMonthLabels.get(m.value-1));
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
}
