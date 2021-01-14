package com.example.organizer;

public interface Object
{
    public Object createTaskList();
    public static Object createTask() {return null;}
    public Object addTask();
    public void deleteTask(Object obj);
    public void editTask(Object obj);
    public void setFlag(boolean flg);
    public void relocateTask(Object obj);
    public Object clone();
    public Object SortTaskListN();
    public Object SortTaskListD();
    public Object getTask();
    public void setPriorityManually();
    public void returnTaskinGeneralTask(Object obj1);
    public void relocateTask(Object obj,Object list);
}
