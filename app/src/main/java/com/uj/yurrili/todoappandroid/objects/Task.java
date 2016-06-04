package com.uj.yurrili.todoappandroid.objects;

import java.sql.Timestamp;

/**
 * Created by Yuri on 2016-06-01.
 */
public class Task {
    private int id;
    private String title;
    private String description;
    private String url_to_icon;
    private Timestamp time_end;
    private Timestamp create_at;

    public Task() {

    }

    public Timestamp getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Timestamp create_at) {
        this.create_at = create_at;
    }

    public Task(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_to_icon() {
        return url_to_icon;
    }

    public void setUrl_to_icon(String url_to_icon) {
        this.url_to_icon = url_to_icon;
    }

    public Timestamp getTime_end() {
        return time_end;
    }

    public void setTime_end(Long time_end) {
        if (time_end != null) {
            this.time_end = new Timestamp(time_end);
        } else {
            this.time_end = null;
        }
    }
}
