package br.jpttrindade.calendaviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import br.com.jpttrindade.calendarview.view.CalendarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.addEvent(16,9,2016);
        calendarView.addEvent(17,9,2016);
        calendarView.addEvent(18,9,2016);
        calendarView.addEvent(19,9,2016);

        calendarView.setOnDayClickListener(new CalendarView.OnDayClickListener() {
            @Override
            public void onClick(int day, int month, int year) {
                Toast.makeText(MainActivity.this, day+"/"+month+"/"+year, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
