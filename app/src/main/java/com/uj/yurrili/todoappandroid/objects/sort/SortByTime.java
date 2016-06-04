package com.uj.yurrili.todoappandroid.objects.sort;

import com.uj.yurrili.todoappandroid.objects.Task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Yuri on 2016-06-04.
 */
public class SortByTime implements SortStrategy{

    @Override
    public List<Task> sort(List<Task> list) {
        Collections.sort(list, new TimeComparator());
        return list;
    }

    class TimeComparator implements Comparator<Task> {
        @Override
        public int compare(Task a, Task b) {
            if(a.getTime_end().getTime() == 0 && b.getTime_end().getTime() > 0){
                return 1;
            } else if (a.getTime_end().getTime() > 0 && b.getTime_end().getTime() == 0) {
                return -1;
            } else if ( a.getTime_end().getTime() == 0){
                return 0;
            } else {
                return a.getTime_end().getTime() < b.getTime_end().getTime() ? -1 :
                        a.getTime_end().getTime() == b.getTime_end().getTime() ? 0 : 1;
            }
        }
    }
}
