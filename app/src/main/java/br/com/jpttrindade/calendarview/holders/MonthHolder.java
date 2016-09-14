package br.com.jpttrindade.calendarview.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.jpttrindade.calendarview.R;
import br.com.jpttrindade.calendarview.data.Week;
import br.com.jpttrindade.calendarview.view.CalendarView;

/**
 * Created by joaotrindade on 06/09/16.
 */
public class MonthHolder extends RecyclerView.ViewHolder {

    private CalendarView.OnDayClickListener mOnDayClickListener;
    public Context mContext;
    public TextView label_month;
    public LinearLayout weeks_container;
    public ArrayList<WeekDayView[]> weeksColumns;
    public int weekRowsCount;
    private int weekRowHeight;

    public int mMonth;
    public int mYear;


    public MonthHolder(View itemView, int weekRowsCount, CalendarView.OnDayClickListener onDayClickListener) {
        super(itemView);

        this.weekRowsCount = weekRowsCount ;

        mContext = itemView.getContext();
        label_month = (TextView) itemView.findViewById(R.id.label_month);
        weeks_container = (LinearLayout) itemView.findViewById(R.id.weeks_container);
        weeksColumns = new ArrayList<WeekDayView[]>();
        //generateWeekRows();

        mOnDayClickListener = onDayClickListener;
    }

    public void generateWeekRows() {
        LinearLayout linearLayout;
        weeks_container.setWeightSum(weekRowsCount);
        for (int i=0; i<weekRowsCount; i++) {
            linearLayout = new LinearLayout(mContext);
            linearLayout.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            weekRowHeight,
                            1f));
            linearLayout.getLayoutParams().height = weekRowHeight;
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(7);
            //linearLayout.setBackgroundColor(Color.GREEN);
            generateWeekColumns(linearLayout);

            weeks_container.addView(linearLayout);
        }
    }

    void generateWeekColumns(LinearLayout linearLayout) {
        WeekDayView[] columns = new WeekDayView[7];


        LayoutInflater inflater = LayoutInflater.from(mContext);

        TextView tv_dayValue;
        View container;
        for (int i=0; i<7; i++) {
            container = inflater.inflate(R.layout.day_view, linearLayout, false);


            //tv = new TextView(mContext);
            View circle = container.findViewById(R.id.circle);
            tv_dayValue = (TextView) container.findViewById(R.id.tv_day);

            tv_dayValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int day = Integer.parseInt(((TextView)view).getText().toString());
                    if (day > 0) {
                        mOnDayClickListener.onClick(day, mMonth, mYear);
                    }
                }
            });

            linearLayout.addView(container);

            columns[i] = new WeekDayView(tv_dayValue, circle);
        }
        weeksColumns.add(columns);

    }


    public void setLabelMonthHeight(int labelMonthHeight) {
        this.label_month.getLayoutParams().height = labelMonthHeight;
    }

    public void setWeekRowHeight(int weekRowHeight) {
        this.weekRowHeight = weekRowHeight;
    }

    public class WeekDayView {
        public TextView tv_value;
        public View v_circle;
        public WeekDayView(TextView value, View circle) {
            this.tv_value = value;
            this.v_circle = circle;
            this.v_circle.setVisibility(View.INVISIBLE);
        }
    }
}
