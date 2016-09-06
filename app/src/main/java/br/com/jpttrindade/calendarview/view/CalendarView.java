package br.com.jpttrindade.calendarview.view;

import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Calendar;

import br.com.jpttrindade.calendarview.R;
import br.com.jpttrindade.calendarview.adapters.CalendarAdapter;

public class CalendarView extends FrameLayout {
    private Context mContext;
    private int mYear;
    private RecyclerView rl_calendar;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] mMonths;
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


        mContext = getContext();
        mMonths = getResources().getStringArray(R.array.months);

        mYear = Calendar.getInstance().get(Calendar.YEAR);

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
