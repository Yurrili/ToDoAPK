package com.uj.yurrili.todoappandroid.objects.sort;

import com.uj.yurrili.todoappandroid.objects.Task;

import java.util.List;

/**
 * Created by Yuri on 2016-06-04.
 */
public interface SortStrategy {
    List<Task> sort(List<Task> list);
}
