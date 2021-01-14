package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class GeneralList extends AppCompatActivity {
    ArrayList<GeneralTask> genTaskL=new ArrayList<>();
    GeneralTask selectedTask;
    ListView genTaskView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_list);
        SetInitialData();
        genTaskView=(ListView) findViewById(R.id.GeneralList);
        GeneralTaskAdapter genTaskA=new GeneralTaskAdapter(this,R.layout.list_gitem,genTaskL);
        genTaskView.setAdapter(genTaskA);
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedTask=(GeneralTask)parent.getItemAtPosition(position);
                setContentView(R.layout.activity_explanation);
                TextView explanationT=(TextView)findViewById(R.id.ExplanationField);
                explanationT.setText(selectedTask.getExplanation());

            }
        };
        genTaskView.setOnItemClickListener(itemListener);
        Bundle arg=getIntent().getExtras();
        if(arg!=null)
        {
            TextView dateT=(TextView)this.findViewById(R.id.Test);
            dateT.setText(arg.getString("message"));
            //genTaskA.notifyDataSetChanged();
        }

    }
    public void OpenWeekList(View view)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void OpenGeneralList(View view) { }
    public void CreateNewTask(View view)
    {
        Intent intent=new Intent(this,GeneralTaskActivity.class);
        startActivity(intent);

    }
    private void SetInitialData()
    {
        selectedTask=new GeneralTask();
        List<String> list=new LinkedList<>();
        ReadMainFile("General",list);
        ListIterator<String> iterator=list.listIterator();
        while(iterator.hasNext())
        {
            readTaskFile(iterator.next());
            genTaskL.add(new GeneralTask(selectedTask));
        }
    }
    public void BackToList(View view)
    {
        setContentView(R.layout.activity_general_list);
        genTaskView=(ListView) findViewById(R.id.GeneralList);
        GeneralTaskAdapter genTaskA=new GeneralTaskAdapter(this,R.layout.list_gitem,genTaskL);
        genTaskView.setAdapter(genTaskA);
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedTask=(GeneralTask)parent.getItemAtPosition(position);
                setContentView(R.layout.activity_explanation);
                TextView explanationT=(TextView)findViewById(R.id.ExplanationField);
                explanationT.setText(selectedTask.getExplanation());
            }
        };
        genTaskView.setOnItemClickListener(itemListener);

        Bundle arg=getIntent().getExtras();
        if(arg!=null)
        {
            TextView dateT=(TextView)this.findViewById(R.id.Test);
            dateT.setText(arg.getString("message"));

        }


    }
    public void EditTask(View view)
    {

        Intent intent=new Intent(this,GeneralTaskActivity.class);
        startActivity(intent);
    }
    public void RelocateTask(View view)
    {
        Intent intent=new Intent(this,SelectActivity.class);
        intent.putExtra("Name",selectedTask.getName());
        intent.putExtra("Explanation",selectedTask.getExplanation());
        intent.putExtra("Flag",selectedTask.getFlag());
        intent.putExtra("Year",selectedTask.getDate().getYear());
        intent.putExtra("Month",selectedTask.getDate().getMonth());
        intent.putExtra("Day",selectedTask.getDate().getDay());
        startActivity(intent);
    }
    public void DeleteTask(View view)
    {
        deleteFile(selectedTask.getName()+".txt");
        List<String> list=new LinkedList<>();
        ReadMainFile("General",list);
        ListIterator<String> iterator=list.listIterator();
        while(iterator.hasNext())
        {
            if(iterator.next().equals(selectedTask.getName()))
                iterator.remove();
        }
        WriteMainFile("General",list);
        Intent intent=new Intent(this,GeneralList.class);
        startActivity(intent);
    }
    public void SortTasks(View view)
    {
        List<String> list=new LinkedList<>();
        List<GeneralTask> taskList= new LinkedList<>();
        ReadMainFile("General",list);
        ListIterator<String> iterator=list.listIterator();
        while(iterator.hasNext())
        {
            readTaskFile(iterator.next());
            taskList.add( new GeneralTask(selectedTask));
        }
        list=new LinkedList<>();
        ListIterator<GeneralTask> iteratorgtl=taskList.listIterator();
        ListIterator<GeneralTask> cur=null;
        GeneralTask minObj=null;
        GeneralTask curObj=null;

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
            ListIterator<GeneralTask> temp=taskList.listIterator();
            while(temp.hasNext())
            {
                if(temp.next().equal(minObj))
                    temp.remove();
            }
            iteratorgtl = taskList.listIterator();
        }
        WriteMainFile("General",list);
        Intent intent=new Intent(this,GeneralList.class);
        startActivity(intent);
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



}