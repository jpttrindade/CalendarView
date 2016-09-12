package br.com.jpttrindade.calendarview.view;

import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import br.com.jpttrindade.calendarview.R;
import br.com.jpttrindade.calendarview.data.WeekManager;
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

        final TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CalendarView, defStyle, 0);





        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE);

        View content = layoutInflater.inflate(R.layout.calendar_view, null, false);
        addView(content);

        rl_calendar = (RecyclerView) findViewById(R.id.rl_calendar);
        rl_calendar.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(mContext);


        rl_calendar.setLayoutManager(mLayoutManager);


        setAdapter(a);


        a.recycle();

        invalidate();

    }

    private void setAdapter(TypedArray a) {
        mCalendarAdapter = new CalendarAdapter(mContext);
        final int monthLabelHeight = (int) a.getDimension(R.styleable.CalendarView_monthLabelHeight, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 48, getResources().getDisplayMetrics()));
        final int weekRowHeight = (int) a.getDimension(R.styleable.CalendarView_weekRowHeight, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 48, getResources().getDisplayMetrics()));

        mCalendarAdapter.setMonthLabelHeight(monthLabelHeight);

        mCalendarAdapter.setWeekRowHeight(weekRowHeight);

        rl_calendar.setAdapter(mCalendarAdapter);
    }


}
