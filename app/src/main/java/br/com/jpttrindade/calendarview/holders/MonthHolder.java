package br.com.jpttrindade.calendarview.holders;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.jpttrindade.calendarview.R;
import br.com.jpttrindade.calendarview.view.CalendarView;

/**
 * Created by joaotrindade on 06/09/16.
 */
public class MonthHolder extends RecyclerView.ViewHolder {

    private CalendarView.OnDayClickListener mOnDayClickListener;
    public Context mContext;
    public TextView label_month;
    public LinearLayout weeks_container;
    public ArrayList<TextView[]> weeksColumns;
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
        weeksColumns = new ArrayList<TextView[]>();
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
        TextView[] columns = new TextView[7];

        TextView tv;
        for (int i=0; i<7; i++) {
            tv = new TextView(mContext);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int day = Integer.parseInt(((TextView)view).getText().toString());
                    if (day > 0) {
                        mOnDayClickListener.onClick(day, mMonth, mYear);
                    }
                }
            });

            tv.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            1f));

            tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            //tv.setBackgroundColor(Color.YELLOW);
            linearLayout.addView(tv);
            columns[i] = tv;
        }
        weeksColumns.add(columns);

    }


    public void setLabelMonthHeight(int labelMonthHeight) {
        this.label_month.getLayoutParams().height = labelMonthHeight;
    }

    public void setWeekRowHeight(int weekRowHeight) {
        this.weekRowHeight = weekRowHeight;
    }
}
