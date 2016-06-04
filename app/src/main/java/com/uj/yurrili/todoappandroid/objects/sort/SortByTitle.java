package com.uj.yurrili.todoappandroid.objects.sort;

import com.uj.yurrili.todoappandroid.objects.Task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Yuri on 2016-06-04.
 */
public class SortByTitle  implements SortStrategy{

    @Override
    public List<Task> sort(List<Task> list) {
        Collections.sort(list, new TitleComparator());
        return list;
    }

    class TitleComparator implements Comparator<Task> {
        @Override
        public int compare(Task a, Task b) {
            return a.getTitle().compareToIgnoreCase(b.getTitle());
        }
    }
}
