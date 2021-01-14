package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import java.lang.String;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class GeneralTaskActivity extends AppCompatActivity {

    public static GeneralTask task=new GeneralTask();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_task);
        Bundle arg=getIntent().getExtras();
        if(arg!=null)
        {
            Date date= new Date(arg.getInt("year"),arg.getInt("month"),arg.getInt("day"));
            task.setDate(date);
            EditText name1=(EditText)this.findViewById(R.id.taskName);
            EditText explanation1=(EditText)this.findViewById(R.id.taskExplanation);
            name1.setText(task.getName());
            explanation1.setText(task.getExplanation());
        }

    }

    public void SaveGeneralTask(View view)
    {

        EditText name1=(EditText)this.findViewById(R.id.taskName);
        EditText explanation1=(EditText)this.findViewById(R.id.taskExplanation);
        task.setName(name1.getText().toString());
        task.setExplanation(explanation1.getText().toString());
        task.setFlag(false);

        List<String> list=new LinkedList<>();
        ReadMainFile("General",list);
        list.add(task.getName());
        deleteFile("General");
        WriteMainFile("General",list);
        writeTaskFile();
        // удаление лишних активити
        Intent intent=new Intent(this,GeneralList.class);
        intent.putExtra("message",task.getName()+" / "+task.getExplanation()+ " / "+task.getDate().getYear()+":"+task.getDate().getMonth()+":"+task.getDate().getDay());
        startActivity(intent);
    }
    public void ChooseDeadline(View view)
    {
        EditText name1=(EditText)this.findViewById(R.id.taskName);
        EditText explanation1=(EditText)this.findViewById(R.id.taskExplanation);
        task.setName(name1.getText().toString());
        task.setExplanation(explanation1.getText().toString());
        Intent intent=new Intent(this,DateActivity.class);
        startActivity(intent);
    }
    public void writeTaskFile()  {
        FileOutputStream fou=null;
        try {
            fou=openFileOutput((task.getName()+".txt"),MODE_PRIVATE);
            fou.write((task.getName()+"///"+task.getExplanation()+"///"+task.getFlag()+"///"+
                    task.getDate().getYear()+"::"+task.getDate().getMonth()+"::"+task.getDate().getDay()+"::").getBytes());
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
           task.setName(res.substring(0,i));
           res=res.substring(i+3);
           i=res.indexOf("///");
           task.setExplanation(res.substring(0,i));
           res=res.substring(i+3);
           i=res.indexOf("///");
           task.setFlag(Boolean.parseBoolean(res.substring(0,i)));
           res=res.substring(i+3);
           i=res.indexOf("::");
           int y=Integer.parseInt(res.substring(0,i));
           res=res.substring(i+2);
           i=res.indexOf("::");
           int m=Integer.parseInt(res.substring(0,i));
           res=res.substring(i+2);
           i=res.indexOf("::");
           int d=Integer.parseInt(res.substring(0,i));
           task.setDate(new Date(y,m,d));

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
            if(fin.available()!=0)
            {
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