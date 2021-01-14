package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android .view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;


import java.util.Calendar;

public class DateActivity extends AppCompatActivity {
    private int choseYear = 0;
    private int choseMonth = 0;
    private int choseDay = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        final CalendarView CV = (CalendarView) findViewById(R.id.date);
        CV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                choseDay = dayOfMonth;
                choseMonth = month + 1;
                choseYear = year;
            }

        });
    }

    public void SaveDate(View view)
    {
        if (choseDay == 0) {
            Calendar c = Calendar.getInstance();
            choseDay = c.get(Calendar.DAY_OF_MONTH);
            choseMonth = c.get(Calendar.MONTH) + 1;
            choseYear = c.get(Calendar.YEAR);
        }
        Intent intent=new Intent(this,GeneralTaskActivity.class);
        intent.putExtra("day", choseDay);
        intent.putExtra("month",choseMonth);
        intent.putExtra("year",choseYear);
        startActivity(intent);
    }
    public void Back(View view)
    {
        Intent intent=new Intent(this,GeneralTaskActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}