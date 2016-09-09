package br.com.jpttrindade.calendarview.view;

import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Arrays;
import java.util.Calendar;

import br.com.jpttrindade.calendarview.R;
import br.com.jpttrindade.calendarview.Week;
import br.com.jpttrindade.calendarview.WeekManager;
import br.com.jpttrindade.calendarview.adapters.CalendarAdapter;

public class CalendarView extends FrameLayout {

    private Context mContext;
    private int mYear;
    private String[] mMonths;

    private RecyclerView rl_calendar;
    private RecyclerView.LayoutManager mLayoutManager;
    private CalendarAdapter mCalendarAdapter;

    public CalendarView(Context context) {
        super(context);
        init(null, 0);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes

        Log.i("CALENDAR_VIEW", "Century = "+new WeekManager().getWeekDay(8,9,2016));
        mContext = getContext();
        mMonths = getResources().getStringArray(R.array.months);

        mYear = Calendar.getInstance().get(Calendar.YEAR);
        int mMonth = Calendar.getInstance().get(Calendar.MONTH);

        Log.i("CALENDAR_VIEW", "Month = "+ mMonth);
        Log.i("CALENDAR_VIEW", "Week Count = "+ new WeekManager().getWeekCount(2,2016));

        Week[] weeks = new WeekManager().getWeeks(2,2016);

        for (Week wk : weeks) {
            Log.i("CALENDAR_VIEW", "Weeks = " + Arrays.toString(wk.days));
        }


        final TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CalendarView, defStyle, 0);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE);

        View content = layoutInflater.inflate(R.layout.calendar_view, null, false);
        addView(content);

        rl_calendar = (RecyclerView) findViewById(R.id.rl_calendar);
        rl_calendar.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(mContext);
        rl_calendar.setLayoutManager(mLayoutManager);


        setAdapter();


        a.recycle();

        invalidate();

    }

    private void setAdapter() {
        mCalendarAdapter = new CalendarAdapter(mContext, mMonths, mYear);
        rl_calendar.setAdapter(mCalendarAdapter);
    }

}
