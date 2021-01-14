package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ExplanationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);
    }
    public void BackToList(View view) { setContentView(R.layout.activity_general_list); }
    public void EditTask(View view){}
    public void RelocateTask(View view){}
    public void DeleteTask(View view){}
    public void SortTasks(View view){}
}