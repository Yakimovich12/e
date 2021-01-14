package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimeActivity extends AppCompatActivity {

    public int hour=0;
    public int min=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        TimePicker timePicker=(TimePicker) this.findViewById(R.id.time);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour=hourOfDay;
                min=minute;
            }
        });
    }
    public void SaveDate(View view)
    {
        Intent intent=new Intent(this,GeneralList.class);
        intent.putExtra("hour", hour);
        intent.putExtra("minute",min);
        startActivity(intent);
    }
    public void Back(View view)
    {
        Intent intent=new Intent(this,GeneralList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}

