package com.uj.yurrili.todoappandroid.db_managment;

import com.uj.yurrili.todoappandroid.objects.Task;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by Yuri on 2016-06-01.
 */
public interface DataBaseHelper {

    // CRUD - TASK

    void insertTask(Task task);
    void removeTask(Task task);
    void updateTask(Task task);


    Task getTask(String id) throws MalformedURLException;
    List<Task> getTasks() throws MalformedURLException;
}
