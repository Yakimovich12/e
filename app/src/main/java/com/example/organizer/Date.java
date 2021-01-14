package com.example.organizer;

public class Date
{
    private int year;
    private int month;
    private int day;

    public Date()
    {
        this.year=0;
        this.month=0;
        this.day=0;
    }
    public Date(int y, int m, int d)
    {
        this.year=y;
        this.month=m;
        this.day=d;
    }
    public Date(Date obj)
    {
        this.year=obj.year;
        this.month=obj.month;
        this.day=obj.day;
    }
    public void setYear(int y) {this.year=y;}
    public void setMonth(int m) {this.month=m;}
    public void setDay(int d) {this.day=d;}
    public int getYear() {return this.year;}
    public int getMonth() {return this.month;}
    public int getDay() {return this.day;}
    public boolean compare(Date date)
    {
        if(this.year > date.year || (this.year == date.year && this.month > date.month) || (this.year == date.year && this.month == date.month && this.day > date.day))
            return true;
        else
            return false;

    }
    public boolean equal(Date date)
    {
        if(this.day==date.day && this.month==date.month && this.year==date.year )
            return true;
        else
            return false;
    }
}
