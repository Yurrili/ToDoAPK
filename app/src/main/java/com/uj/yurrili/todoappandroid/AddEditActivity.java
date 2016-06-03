package com.uj.yurrili.todoappandroid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelper;
import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelperImpl;
import com.uj.yurrili.todoappandroid.objects.Task;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.LocalDateTime;

import java.sql.Timestamp;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddEditActivity extends AppCompatActivity {

    @InjectView(R.id.awesome_font_clock) TextView fontAwesomeClock;
    @InjectView(R.id.awesome_font_calendar) TextView fontAwesomeCalendar;
    static @InjectView(R.id.date) TextView date;
    static @InjectView(R.id.time) TextView time;
    @InjectView(R.id.input_title) EditText title;
    @InjectView(R.id.input_description) EditText description;
    @InjectView(R.id.input_url) EditText url;
    DataBaseHelper dba_Task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        ButterKnife.inject(this);
        JodaTimeAndroid.init(this);
        dba_Task = new DataBaseHelperImpl(getApplicationContext());
    }

    @OnClick(R.id.btn_add) void submit() {
       if (validate()){
           String desc = description.getText().toString();
           if( desc.equals("")){
               desc = null;
           }
           String urls = url.getText().toString();
           if( urls.equals("")){
               urls = null;
           }
           Task newTask = new Task(
                   title.getText().toString(),
                   desc,
                   urls,
                   Utilities.jodaToSQLTimestamp(LocalDateTime.now()));

           dba_Task.insertTask(newTask);
           Intent intent = new Intent(getApplicationContext(), ListActivity.class);
           startActivity(intent);
       }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public boolean validate() {
        boolean valid = true;

        String title_text = title.getText().toString();

        if (title_text.isEmpty() || title_text.length() < 4 ) {
            title.setError("Title must have more than 4 character");
            valid = false;
        } else {
            title.setError(null);
        }

        return valid;
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            date.setText(Utilities.setDatePick(year, month, day));
        }
    }
}
