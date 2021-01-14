package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class DailyListActivity extends AppCompatActivity {

    ArrayList<DailyTask> dayTaskL=new ArrayList<>();
    DailyTask selectedTask;
    ListView dayTaskView;
    String selectedString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_list);
        Bundle arg=getIntent().getExtras();
        if(arg!=null)
        {
            selectedString=arg.getString("weekDay");
        }
        SetInitialData();
        dayTaskView=(ListView) findViewById(R.id.DayList);
        DailyTaskAdapter genTaskA=new DailyTaskAdapter(this,R.layout.list_gitem,dayTaskL);
        dayTaskView.setAdapter(genTaskA);
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedTask=(DailyTask)parent.getItemAtPosition(position);
                setContentView(R.layout.activity_explanation);
                TextView explanationT=(TextView)findViewById(R.id.ExplanationField);
                explanationT.setText(selectedTask.getExplanation());
            }
        };
        dayTaskView.setOnItemClickListener(itemListener);

    }
    public void BackToWList(View view)
    {
        Intent intent=new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    private void SetInitialData()
    {
        selectedTask=new DailyTask();
        List<String> list=new LinkedList<>();
        ReadMainFile(selectedString,list);
        ListIterator<String> iterator=list.listIterator();
        while(iterator.hasNext())
        {
            readTaskFile(iterator.next());
            dayTaskL.add(new DailyTask(selectedTask));
        }
    }
    public void writeTaskFile()  {
        FileOutputStream fou=null;
        try {
            fou=openFileOutput((selectedTask.getName()+".txt"),MODE_PRIVATE);
            fou.write((selectedTask.getName()+"///"+selectedTask.getExplanation()+"///"+selectedTask.getFlag()+"///"+
                    selectedTask.getDate().getYear()+"::"+selectedTask.getDate().getMonth()+"::"+selectedTask.getDate().getDay()+"::").getBytes());
        } catch (IOException e) { }
        finally
        {
            try
            {
                if(fou!=null)
                    fou.close();
            }
            catch(IOException e){}
        }
    }
    public void readTaskFile(String name)
    {
        FileInputStream fin=null;
        try
        {
            fin=openFileInput(name+".txt");
            byte[] bytes=new byte[fin.available()];
            fin.read(bytes);
            String res=new String(bytes);
            int i=res.indexOf("///");
            selectedTask.setName(res.substring(0,i));
            res=res.substring(i+3);
            i=res.indexOf("///");
            selectedTask.setExplanation(res.substring(0,i));
            res=res.substring(i+3);
            i=res.indexOf("///");
            selectedTask.setFlag(Boolean.parseBoolean(res.substring(0,i)));
            res=res.substring(i+3);
            i=res.indexOf("::");
            int y=Integer.parseInt(res.substring(0,i));
            res=res.substring(i+2);
            i=res.indexOf("::");
            int m=Integer.parseInt(res.substring(0,i));
            res=res.substring(i+2);
            i=res.indexOf("::");
            int d=Integer.parseInt(res.substring(0,i));
            selectedTask.setDate(new Date(y,m,d));

        }
        catch (IOException e) { }
        finally
        {
            try
            {
                if(fin!=null)
                    fin.close();
            }
            catch(IOException e){}
        }

    }
    public void WriteMainFile(String name, List<String> list)
    {
        StringBuilder output=new StringBuilder();
        ListIterator<String> iterator=list.listIterator();
        while(iterator.hasNext())
        {
            String cur=iterator.next();
            StringBuilder curBuilder=new StringBuilder(cur);
            output.append(curBuilder);
            output.append('\n');
        }
        output.append(' ');
        FileOutputStream fou=null;
        try {
            fou=openFileOutput((name+".txt"),MODE_PRIVATE);
            fou.write(output.toString().getBytes());
        } catch (IOException e) { }
        finally
        {
            try
            {
                if(fou!=null)
                    fou.close();
            }
            catch(IOException e){}
        }

    }
    public void ReadMainFile(String name,List<String> list)
    {
        FileInputStream fin=null;
        try
        {
            fin=openFileInput(name+".txt");
            byte[] bytes=new byte[fin.available()];
            fin.read(bytes);
            String res=new String(bytes);
            int i=0;
            while(i!=-1)
            {
                i=res.indexOf('\n');
                if(i!=-1)
                {
                    list.add(res.substring(0,i));
                    res=res.substring(i+1);
                }
            }
        }
        catch (IOException e) { }
        finally
        {
            try
            {
                if(fin!=null)
                    fin.close();
            }
            catch(IOException e){}
        }
    }
    public void BackToList(View view)
    {
        setContentView(R.layout.activity_daily_list);
        dayTaskView=(ListView) findViewById(R.id.DayList);
        DailyTaskAdapter dayTaskA=new DailyTaskAdapter(this,R.layout.list_gitem,dayTaskL);
        dayTaskView.setAdapter(dayTaskA);///////////
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedTask=(DailyTask)parent.getItemAtPosition(position);
                setContentView(R.layout.activity_explanation);
                TextView explanationT=(TextView)findViewById(R.id.ExplanationField);
                explanationT.setText(selectedTask.getExplanation());
            }
        };
        dayTaskView.setOnItemClickListener(itemListener);

    }
    public void RelocateTask(View view)
    {
        GeneralTask genTask=new GeneralTask(selectedTask.getName(),selectedTask.getExplanation(),selectedTask.getFlag(),
                selectedTask.getDate());
        List<String> list=new LinkedList<>();
        ReadMainFile("General",list);
        list.add(genTask.getName());
        deleteFile("General.txt");
        WriteMainFile("General",list);
        list=new LinkedList<>();
        ReadMainFile(selectedString,list);
        ListIterator<String> iterator=list.listIterator();
        while(iterator.hasNext())
        {
            if(iterator.next().equals(selectedTask.getName()))
                iterator.remove();
        }
        WriteMainFile(selectedString,list);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void DeleteTask(View view)
    {
        deleteFile(selectedTask.getName()+".txt");
        List<String> list=new LinkedList<>();
        ReadMainFile(selectedString,list);
        ListIterator<String> iterator=list.listIterator();
        while(iterator.hasNext())
        {
            if(iterator.next().equals(selectedTask.getName()))
                iterator.remove();
        }
        WriteMainFile(selectedString,list);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void SortTasks(View view)
    {
        List<String> list=new LinkedList<>();
        List<DailyTask> taskList= new LinkedList<>();
        ReadMainFile(selectedString,list);
        ListIterator<String> iterator=list.listIterator();
        while(iterator.hasNext())
        {
            readTaskFile(iterator.next());
            taskList.add( new DailyTask(selectedTask));
        }
        list=new LinkedList<>();
        ListIterator<DailyTask> iteratorgtl=taskList.listIterator();
        ListIterator<DailyTask> cur=null;
        DailyTask minObj=null;
        DailyTask curObj=null;

        while(iteratorgtl.hasNext())
        {
            minObj = iteratorgtl.next();
            if (iteratorgtl.hasNext()) {
                cur = iteratorgtl;
                while (cur.hasNext())
                {
                    curObj = cur.next();
                    if (minObj.getDate().compare(curObj.getDate()))
                        minObj = curObj;
                }
            }
            list.add(minObj.getName());
            ListIterator<DailyTask> temp=taskList.listIterator();
            while(temp.hasNext())
            {
                if(temp.next().equal(minObj))
                    temp.remove();
            }
            iteratorgtl = taskList.listIterator();
        }
        WriteMainFile(selectedString,list);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}