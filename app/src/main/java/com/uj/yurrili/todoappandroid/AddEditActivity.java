package com.uj.yurrili.todoappandroid;

import android.app.DatePickerDialog;
import android.app.FragmentManager;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelper;
import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelperImpl;
import com.uj.yurrili.todoappandroid.objects.Task;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.sql.Timestamp;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddEditActivity extends AppCompatActivity {

    @InjectView(R.id.awesome_font_clock)
    TextView fontAwesomeClock;
    @InjectView(R.id.awesome_font_calendar)
    TextView fontAwesomeCalendar;
    @InjectView(R.id.awesome_font_eraser)
    TextView fontAwesomeEraser;
    @InjectView(R.id.date)
    EditText date;
    @InjectView(R.id.time)
    EditText time;
    @InjectView(R.id.input_title)
    EditText title;
    @InjectView(R.id.input_description)
    EditText description;
    @InjectView(R.id.input_url)
    EditText url;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    DataBaseHelper dba_Task;
    FragmentManager manager;
    private DatePickerDialog DatePickerDialog;
    private TimePickerDialog TimePickerDialogG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        ButterKnife.inject(this);
        JodaTimeAndroid.init(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        manager = getFragmentManager();
        dba_Task = new DataBaseHelperImpl(getApplicationContext());

        Typeface typeFace = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.font_place));

        initDatePicker();
        initTimePicker();

        fontAwesomeClock.setTypeface(typeFace);
        fontAwesomeCalendar.setTypeface(typeFace);
        fontAwesomeEraser.setTypeface(typeFace);
    }

    @OnClick(R.id.date)
    void datePicker() {
        DatePickerDialog.show();
    }

    private void initDatePicker() {
        LocalDate local = LocalDate.now();

        DatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int month, int day) {
                date.setText(Utilities.setDatePick(year, month, day));
            }

        }, local.getYear(), local.getMonthOfYear(), local.getDayOfMonth());
    }

    @OnClick(R.id.time)
    void timePicker() {
        TimePickerDialogG.show();
    }

    private void initTimePicker() {
        LocalDateTime local = LocalDateTime.now();

        // Launch Time Picker Dialog
        TimePickerDialogG = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        time.setText(Utilities.setTimePick(hourOfDay, minute));
                    }
                }, local.getHourOfDay(), local.getMinuteOfHour(), false);

    }

    @OnClick(R.id.btn_add)
    void submit() {
        if (validate()) {
            String desc = description.getText().toString();
            if (desc.equals("")) {
                desc = null;
            }
            String urls = url.getText().toString();
            if (urls.equals("")) {
                urls = null;
            }

            Timestamp timestamp = null;
            String[] date;
            if (!this.date.getText().toString().equals("") &&
                    this.time.getText().toString().equals("")) {

                date = this.date.getText().toString().split("/");
                timestamp = Utilities.jodaToSQLTimestamp(
                        new LocalDateTime(Integer.parseInt(date[2]),
                                Integer.parseInt(date[1]),
                                Integer.parseInt(date[0]),
                                0,
                                0));

            } else if (!this.date.getText().toString().equals("") &&
                    !this.time.getText().toString().equals("")) {

                date = this.date.getText().toString().split("/");
                String[] time = this.time.getText().toString().split(":");
                timestamp = Utilities.jodaToSQLTimestamp(
                        new LocalDateTime(Integer.parseInt(date[2]),
                                Integer.parseInt(date[1]),
                                Integer.parseInt(date[0]),
                                Integer.parseInt(time[0]),
                                Integer.parseInt(time[1])));
            }

            Task newTask = new Task(
                    title.getText().toString(),
                    desc,
                    urls,
                    timestamp);

            dba_Task.insertTask(newTask);
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
        }
    }


    public boolean validate() {
        boolean valid = true;

        String title_text = title.getText().toString();

        if (title_text.isEmpty() || title_text.length() < 4) {
            title.setError("Title must have more than 4 character");
            valid = false;
        } else {
            title.setError(null);
        }

        return valid;
    }

    @OnClick(R.id.awesome_font_eraser)
    void eraser() {
        date.setText("");
        time.setText("");
    }
}
