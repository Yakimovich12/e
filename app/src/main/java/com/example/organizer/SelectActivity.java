package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SelectActivity extends AppCompatActivity
{
    String[] weekDays={"Понедельник","Вторник","Среда","Четверг","Пятница","Суббота","Воскресенье"};
    String selectedStr;
    static int copyNumber;
    DailyTask dTask;

    {
        copyNumber++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ListView weekList=(ListView) this.findViewById(R.id.SelectDay);
        Intent intent = new Intent(this,GeneralList.class);
        weekList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedStr = weekDays[position];
                Bundle arg=getIntent().getExtras();
                if(arg!=null)
                {
                    dTask=new DailyTask(arg.getString("Name")+copyNumber,arg.getString("Explanation"),
                            arg.getBoolean("Flag"),new Date(arg.getInt("Year"),arg.getInt("Month"),arg.getInt("Day")),
                            copyNumber);
                    List<String>  list=new LinkedList<>();
                    ReadMainFile(selectedStr,list);
                    list.add(dTask.getName());
                    deleteFile(selectedStr+".txt");
                    WriteMainFile(selectedStr,list);
                    writeTaskFile();
                    startActivity(intent);
                }

            }
        });
    }
    public void writeTaskFile()  {
        FileOutputStream fou=null;
        try {
            fou=openFileOutput((dTask.getName()+".txt"),MODE_PRIVATE);
            fou.write((dTask.getName()+"///"+dTask.getExplanation()+"///"+dTask.getFlag()+"///"+
                    dTask.getDate().getYear()+"::"+dTask.getDate().getMonth()+"::"+dTask.getDate().getDay()+"::").getBytes());
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
            dTask.setName(res.substring(0,i));
            res=res.substring(i+3);
            i=res.indexOf("///");
            dTask.setExplanation(res.substring(0,i));
            res=res.substring(i+3);
            i=res.indexOf("///");
            dTask.setFlag(Boolean.parseBoolean(res.substring(0,i)));
            res=res.substring(i+3);
            i=res.indexOf("::");
            int y=Integer.parseInt(res.substring(0,i));
            res=res.substring(i+2);
            i=res.indexOf("::");
            int m=Integer.parseInt(res.substring(0,i));
            res=res.substring(i+2);
            i=res.indexOf("::");
            int d=Integer.parseInt(res.substring(0,i));
            dTask.setDate(new Date(y,m,d));

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