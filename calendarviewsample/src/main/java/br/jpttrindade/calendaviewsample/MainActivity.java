package br.jpttrindade.calendaviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.jpttrindade.calendarview.view.CalendarView;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.addEvent(16,9,2016);
        calendarView.addEvent(17,9,2016);
        calendarView.addEvent(18,9,2016);
        calendarView.addEvent(19,9,2016);

        calendarView.setOnDayClickListener(new CalendarView.OnDayClickListener() {
            @Override
            public void onClick(int day, int month, int year, boolean hasEvent) {
                Toast.makeText(MainActivity.this, day+"/"+month+"/"+year + " hasEvent="+hasEvent, Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void addEvent(View view) {
        String strDate = editText.getText().toString();
        if (strDate.isEmpty()) {
            Toast.makeText(this, "define a day", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "date="+strDate, Toast.LENGTH_SHORT).show();

            String[] date = strDate.split("/");

            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);
            calendarView.addEvent(day, month, year);

        }
    }
}
