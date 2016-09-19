package br.com.jpttrindade.calendarview.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import br.com.jpttrindade.calendarview.R;
import br.com.jpttrindade.calendarview.data.Day;
import br.com.jpttrindade.calendarview.data.WeekManager;
import br.com.jpttrindade.calendarview.data.Month;
import br.com.jpttrindade.calendarview.holders.MonthHolder;
import br.com.jpttrindade.calendarview.view.CalendarView;

/**
 * Created by joaotrindade on 06/09/16.
 */
public class CalendarAdapter extends RecyclerView.Adapter<MonthHolder> {


    private final List<String> mMonthLabels;
    private final ArrayList<Month> mMonths;
    private final Context mContext;

    private int startYear; //ano atual (real)
    private int startMonth; // mes atual (real)
    private int today;


    private int earlyMonthLoaded; //mes mais antigo ja carregado
    private int earlyYearLoaded; //ano mais antigo ja carregado

    private int laterMonthLoaded; //mes mais a frente ja carregado
    private int laterYearLoaded; //ano mais a frente ja carregado

    private WeekManager weekManager;


    private int PAYLOAD = 3; // o numero de meses que serao carregados antes e depois do mes atual.
    private CalendarView.OnDayClickListener onDayClickListener;
    private HashMap<String, Boolean> mEvents;
    private CalendarView.Attributes attrs;

    public CalendarAdapter(Context context, CalendarView.Attributes calendarAttrs) {
        mContext = context;
        attrs = calendarAttrs;
        mMonthLabels = Arrays.asList(context.getResources().getStringArray(R.array.months));
        Calendar c = Calendar.getInstance();
        startYear = c.get(Calendar.YEAR);
        startMonth = c.get(Calendar.MONTH)+1;
        today = c.get(Calendar.DAY_OF_MONTH);

        mMonths = new ArrayList<Month>();
        mEvents = new HashMap<>();

        earlyMonthLoaded = startMonth;
        earlyYearLoaded = startYear;
        laterYearLoaded = startYear;
        laterMonthLoaded = startMonth;

        mMonths.add(new Month(startMonth, startYear));
        getPreviousMonth();
        getNextMonths();
    }



    @Override
    public int getItemViewType(int position) {
        return mMonths.get(position).weeks.length;
    }

    @Override
    public MonthHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.month_view, parent, false);


        MonthHolder mh = new MonthHolder(v, viewType, attrs,new CalendarView.OnDayClickListener(){
            @Override
            public void onClick(int day, int month, int year) {
                if (onDayClickListener != null) {
                    onDayClickListener.onClick(day, month, year);
                }
            }
        });
        mh.generateWeekRows();
        return  mh;
    }


    @Override
    public void onBindViewHolder(MonthHolder holder, int position) {
        Month m = mMonths.get(position);
        setLabel(holder, m);
        setWeeks(holder, m);

        holder.mYear = m.year;
        holder.mMonth = m.value;


    }
    private void setLabel(MonthHolder holder, Month m) {
        String year = (m.year != startYear ? " de "+m.year : "");
        holder.label_month.setText(mMonthLabels.get(m.value-1) + year);

        if(m.value == startMonth && m.year == startYear) {
            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            holder.label_month.setTextSize(TypedValue.COMPLEX_UNIT_PX, (attrs.monthLabelSize+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6, displayMetrics)));
        } else {
            holder.label_month.setTextSize(TypedValue.COMPLEX_UNIT_PX, (attrs.monthLabelSize));
        }
    }



    private void setWeeks(MonthHolder holder, Month m) {
        MonthHolder.WeekDayView[] weekColumns;
        Day[] days;
        TextView tv_day;
        View v_circle;

        for (int i=0; i<holder.weekRowsCount; i++) {
            weekColumns = holder.weeksColumns.get(i);
            days = m.weeks[i].days;
            String key;
            for (int j=0; j<7; j++){
                v_circle = weekColumns[j].v_event_circle;
                tv_day = weekColumns[j].tv_value;
                tv_day.setText("" + days[j].value);


                key = String.format("%d%d%d", days[j].value, m.value, m.year);

                v_circle.setVisibility(mEvents.containsKey(key) ? View.VISIBLE : View.INVISIBLE);

                if (m.year == startYear && m.value == startMonth && days[j].value == today) {
                    tv_day.setTextColor(Color.WHITE);
                    weekColumns[j].v_today_circle.setVisibility(View.VISIBLE);
                } else {
                    tv_day.setTextColor((days[j].value == 0) ? Color.TRANSPARENT : Color.BLACK);
                    weekColumns[j].v_today_circle.setVisibility(View.GONE);
                }



            }
        }

    }

    @Override
    public int getItemCount() {
        return mMonths.size();
    }




    public void getPreviousMonth() {
        if (earlyMonthLoaded <= PAYLOAD) {
            for (int i=earlyMonthLoaded-1; i>0; i--) {
                mMonths.add(0, new Month(i, earlyYearLoaded));
                //notifyItemRangeInserted(0, 1);

            }

            earlyMonthLoaded = 12 - (PAYLOAD - earlyMonthLoaded);
            earlyYearLoaded--;

            for (int i=12; i>=earlyMonthLoaded; i--) {
                mMonths.add(0, new Month(i, earlyYearLoaded));
                //notifyItemRangeInserted(0, 1);
            }
        } else {
            for (int i=earlyMonthLoaded-1; i>=earlyMonthLoaded-PAYLOAD; i--) {
                mMonths.add(0, new Month(i, earlyYearLoaded));
                //notifyItemRangeInserted(0, 1);

            }
            earlyMonthLoaded -= PAYLOAD;
        }

        notifyItemRangeInserted(0, PAYLOAD);

    }

    public void getNextMonths() {
        int positionStart = mMonths.size()-1;
        if (laterMonthLoaded > (12 - PAYLOAD)) {
            for (int i=laterMonthLoaded+1; i<=12; i++) {
                mMonths.add(new Month(i, laterYearLoaded));
            }

            laterMonthLoaded = laterMonthLoaded + PAYLOAD - 12;
            laterYearLoaded++;

            for (int i=1; i <= laterMonthLoaded; i++) {
                mMonths.add(new Month(i, laterYearLoaded));
            }
        } else {
            for (int i=laterMonthLoaded+1; i <= laterMonthLoaded+PAYLOAD; i++) {
                mMonths.add(new Month(i, laterYearLoaded));
            }
            laterMonthLoaded += PAYLOAD;
        }
        notifyItemRangeInserted(positionStart, PAYLOAD);
    }

    public void setOnDayClickListener(CalendarView.OnDayClickListener onDayClickListener) {
        this.onDayClickListener = onDayClickListener;
    }

    public void addEvent(int day, int month, int year) {
        //Month m = getMonth(month, year);

        String key = String.format("%d%d%d", day,month, year);
        Log.d("DEBUG", "Key = "+ key);
        mEvents.put(key, true);
    }

}
