package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] weekDays={"Понедельник","Вторник","Среда","Четверг","Пятница","Суббота","Воскресенье"};
    String selectedStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView weekList=(ListView) this.findViewById(R.id.WeekList);
        Intent intent = new Intent(this,DailyListActivity.class);
        weekList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedStr=weekDays[position];
                intent.putExtra("weekDay",selectedStr);
                startActivity(intent);
            }
        });
    }

    public void OpenWeekList(View view) { }
    public void OpenGeneralList(View view)
    {
        Intent intent=new Intent(this,GeneralList.class);
        startActivity(intent);
    }

}