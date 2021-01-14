package com.example.organizer;

public class DailyTask extends GeneralTask
{
    private int copyNumber;
    public DailyTask()
    {
        super();
        this.copyNumber=0;
    }
    public DailyTask(DailyTask obj)
    {
        super(obj);
        this.copyNumber=obj.copyNumber;
    }
    public DailyTask(String n,String e,boolean f,Date d,int copyN)
    {
        super(n,e,f,d);
        this.copyNumber=copyN;
    }
    public Object getTask()
    {
        Object o=this;
        return o;
    }
    public Object clone()
    {
        DailyTask obj=new DailyTask(this);
        Object o=obj;
        return o;
    }
    public void setCopyNumber(int value){this.copyNumber=value;}
    public int getCopyNumber() {return this.copyNumber;}
    public void editTask()
    {
        //DailyTask.InputFunction(this);
    }
    /*public static void InputFunction(DailyTask o)
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
        o.setCopyNumber(0);
        in.close();
    }*/
    public void OutputFunction()
    {
        System.out.println("Название задачи "+this.getName());
        System.out.println("Дополнение "+this.getExplanation());
        System.out.println("День "+this.getDate().getDay());
        System.out.println("Месяц "+this.getDate().getMonth());
        System.out.println("Год "+this.getDate().getYear());
        System.out.println("Копия "+ this.getCopyNumber());
    }

    public static void main(String [] args)
    {

    }
}