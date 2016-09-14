package br.com.jpttrindade.calendarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.com.jpttrindade.calendarview.view.CalendarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_calendar_view);
        //setContentView(R.layout.activity_main);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        final TextView tv_title = (TextView) findViewById(R.id.tv_title);



        calendarView.addEvent(13,2,2016);
        calendarView.addEvent(30,3,2016);
        calendarView.addEvent(7,7,2016);
        calendarView.addEvent(20,12,2016);



        calendarView.setOnDayClickListener(new CalendarView.OnDayClickListener() {
            @Override
            public void onClick(int day, int month, int year) {
                tv_title.setText(day+"/"+month+"/"+year);
            }
        });
    }
}
