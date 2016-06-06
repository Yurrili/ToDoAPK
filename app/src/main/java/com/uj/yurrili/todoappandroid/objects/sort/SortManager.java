package com.uj.yurrili.todoappandroid.objects.sort;

import com.uj.yurrili.todoappandroid.objects.Task;

import java.util.List;

/**
 * Created by Yuri on 2016-06-04.
 */
public class SortManager {
    List<Task> items;

    public SortManager(List<Task> items) {
        this.items = items;
    }

    public void setTasks(List<Task> items){
        this.items = items;
    }

    public List<Task> sort(SortStrategy strategy){
        return strategy.sort(items);
    }
}
