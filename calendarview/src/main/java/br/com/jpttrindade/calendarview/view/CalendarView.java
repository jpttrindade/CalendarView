package br.com.jpttrindade.calendarview.view;

import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Debug;
import android.support.annotation.StyleableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import br.com.jpttrindade.calendarview.R;
import br.com.jpttrindade.calendarview.adapters.CalendarAdapter;

public class CalendarView extends FrameLayout {

    private Context mContext;
    private int mYear;
    private String[] mMonths;

    private RecyclerView rl_calendar;
    private RecyclerView.LayoutManager mLayoutManager;
    private CalendarAdapter mCalendarAdapter;
    private OnDayClickListener mOnDayClickListener;
    private Attributes calendarAttrs;


    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 1; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;


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
        mContext = getContext();

        calendarAttrs = new Attributes();
        getAttrs(attrs, defStyle);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE);

        View content = layoutInflater.inflate(R.layout.calendar_view, null, false);
        addView(content);

        LinearLayout weekDayNames = (LinearLayout) findViewById(R.id.label_days);
        weekDayNames.getLayoutParams().height = calendarAttrs.weekdayHeight;

        for (int i = 0; i< weekDayNames.getChildCount(); i++) {
            weekDayNames.getChildAt(i).getLayoutParams().width = calendarAttrs.dayWidth;
        }

        rl_calendar = (RecyclerView) findViewById(R.id.rl_calendar);
        mLayoutManager = new LinearLayoutManager(mContext);
        rl_calendar.setLayoutManager(mLayoutManager);

        setAdapter();

        mLayoutManager.scrollToPosition(3);

        rl_calendar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mCalendarAdapter.getItemCount();
                firstVisibleItem = ((LinearLayoutManager)mLayoutManager).findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    mCalendarAdapter.getNextMonths();
                    loading = true;
                }

                if (!loading && (firstVisibleItem <= 1+visibleThreshold)) {
                    // Start has been reached
                    mCalendarAdapter.getPreviousMonth();
                    loading = true;
                }
            }
        });



//        a.recycle();

        invalidate();

    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        final TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CalendarView, defStyle, 0);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();


        calendarAttrs.weekdayHeight = (int) a.getDimension(R.styleable.CalendarView_weekdayNameHeight, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 24, displayMetrics));
        calendarAttrs.dayHeight = (int) a.getDimension(R.styleable.CalendarView_dayHeight, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 48, displayMetrics));
        calendarAttrs.dayWidth = (int) a.getDimension(R.styleable.CalendarView_dayWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 48, displayMetrics));

        calendarAttrs.todayCircleSize = (int) a.getDimension(R.styleable.CalendarView_todayCircleSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 30, displayMetrics));

        if (TypedValue.TYPE_REFERENCE == a.getType(R.styleable.CalendarView_todayCircleColor)) {
            calendarAttrs.todayCircleColor = ContextCompat.getColor(mContext, a.getResourceId(R.styleable.CalendarView_todayCircleColor, R.color.default_todayCircleColor));
        } else {
            calendarAttrs.todayCircleColor = a.getColor(R.styleable.CalendarView_todayCircleColor, ContextCompat.getColor(mContext, R.color.default_todayCircleColor));
        }


        if (TypedValue.TYPE_REFERENCE == a.getType(R.styleable.CalendarView_eventCircleColor)) {
            calendarAttrs.eventCircleColor = ContextCompat.getColor(mContext, a.getResourceId(R.styleable.CalendarView_eventCircleColor, R.color.default_eventCircleColor));
        } else {
            calendarAttrs.eventCircleColor = a.getColor(R.styleable.CalendarView_eventCircleColor, ContextCompat.getColor(mContext, R.color.default_eventCircleColor));
        }

        calendarAttrs.monthDividerSize = (int) a.getDimension(R.styleable.CalendarView_monthDividerSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 48, displayMetrics));

        calendarAttrs.monthLabelSize = a.getDimension(R.styleable.CalendarView_monthLabelSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 14, displayMetrics));

        calendarAttrs.monthLabelHeight = (int) a.getDimension(R.styleable.CalendarView_monthLabelHeight, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 24, displayMetrics));

        a.recycle();
    }

    private void setAdapter() {
        mCalendarAdapter = new CalendarAdapter(mContext, calendarAttrs);

        //mCalendarAdapter.setMonthLabelHeight(monthLabelHeight);

        //mCalendarAdapter.setDayHeight(dayHeight);

        rl_calendar.setAdapter(mCalendarAdapter);

        mCalendarAdapter.setOnDayClickListener(new OnDayClickListener(){

            @Override
            public void onClick(int day, int month, int year) {

                //Toast.makeText(getContext(), day+"/"+month+"/"+year, Toast.LENGTH_SHORT).show();
                if (mOnDayClickListener != null) {
                    mOnDayClickListener.onClick(day, month, year);
                }
            }
        });
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        mOnDayClickListener = onDayClickListener;
    }


    public void addEvent(int day, int month, int year) {
        mCalendarAdapter.addEvent(day, month, year);
    }



    /* Classes & Interfaces*/

    public interface OnDayClickListener {
        public void onClick(int day, int month, int year);
    }


    public class Attributes {
        public int weekdayHeight;
        public int dayWidth;
        public int dayHeight;

        public int todayCircleColor;
        public int todayCircleSize;

        public float monthLabelSize;
        public int monthLabelHeight;

        public int monthDividerSize;


        public int eventCircleColor;
    }
}
