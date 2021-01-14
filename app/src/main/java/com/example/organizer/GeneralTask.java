package com.example.organizer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;

public class GeneralTask extends Task
{
    private String name;
    private String explanation;
    private boolean flag;
    private Date deadline;

    public GeneralTask()
    {
        this.name="";
        this.explanation="";
        this.flag=false;
        this.deadline=new Date();
    }
    public GeneralTask(String n,String e,boolean f,Date d)
    {
        this.name=n;
        this.explanation=e;
        this.flag=f;
        this.deadline=new Date(d);
    }
    public GeneralTask(String n,String e,boolean f,int y,int m,int d)
    {
        this.name=n;
        this.explanation=e;
        this.flag=f;
        this.deadline=new Date(y,m,d);
    }
    public GeneralTask(GeneralTask obj)
    {
        this.name=obj.name;
        this.explanation=obj.explanation;
        this.flag=obj.flag;
        this.deadline=new Date(obj.deadline);
    }
    public Object clone()
    {
        GeneralTask obj= new GeneralTask(this);
        Object o=obj;
        return o;
    }
    public void setName(String n) {this.name=n;}
    public String getName() {return this.name;}
    public void setExplanation(String e) {this.explanation=e;}
    public String getExplanation() {return this.explanation;}
    public void setFlag(boolean flg) {this.flag=flg;}
    public boolean getFlag() {return this.flag;}
    public void setDate(Date dl)
    {
        this.deadline.setYear(dl.getYear());
        this.deadline.setMonth(dl.getMonth());
        this.deadline.setDay(dl.getDay());
    }

    public Date getDate() {return this.deadline;}
    public boolean compareName(GeneralTask gt)
    {
        if(this.name.compareTo(gt.name)>0)
            return true;
        else
            return false;
    }

    public boolean equal(GeneralTask task)
    {
        if(this.name.equals(task.name) && this.explanation.equals(this.explanation)&& this.deadline.equal(task.deadline))
            return true;
        else
            return false;
    }

   /* public static void InputFunction(GeneralTask o)
    {
        Scanner in= new Scanner(System.in);
        System.out.println("Введите название задачи");
        String name=in.nextLine();
        o.setName(name);
        System.out.println("Введите пояснение к задаче");
        String explanation=in.nextLine();
        o.setExplanation(explanation);
        Date date=new Date();
        System.out.println("Введите дедлайн: Год");
        int year=in.nextInt();
        date.setYear(year);
        System.out.println("Введите дедлайн: Месяц");
        int month=in.nextInt();
        date.setMonth(month);
        System.out.println("Введите дедлайн: День");
        int day=in.nextInt();
        date.setDay(day);
        o.setDate(date);
        in.close();
    }*/

    public void OutputFunction()
    {
        System.out.println("Название задачи "+this.getName());
        System.out.println("Дополнение "+this.getExplanation());
        System.out.println("День "+this.getDate().getDay());
        System.out.println("Месяц "+this.getDate().getMonth());
        System.out.println("Год "+this.getDate().getYear());
    }

    public static Object createTask()
    {
        GeneralTask task= new GeneralTask();
       // GeneralTask.InputFunction(task);
        //newTask.createTaskFiles();
        return task;
    }

    public void editTask()
    {
        //GeneralTask.InputFunction(this);
        //файловые операции
    }
    public DailyTask createDailyTask()
    {
        DailyTask dTask= new DailyTask(this.name,this.explanation,this.flag,this.deadline,0);
        //DailyTask.InputFunction(dTask);
        return dTask;
    }



    public static void main(String[] args)
    {
        GeneralTask obj= new GeneralTask();
       // GeneralTask.InputFunction(obj);
        Object clon=obj.clone();
        GeneralTask j=(GeneralTask) clon;
        obj.OutputFunction();
        System.out.println();
        System.out.println(j.getName());
        System.out.println(j.getExplanation());
        System.out.println(j.getDate().getDay());
        System.out.println(j.getDate().getMonth());
        System.out.println(j.getDate().getYear());
    }
}



