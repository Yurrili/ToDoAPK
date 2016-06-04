package com.uj.yurrili.todoappandroid;

import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelper;
import com.uj.yurrili.todoappandroid.db_managment.DataBaseHelperImpl;
import com.uj.yurrili.todoappandroid.objects.Task;
import com.uj.yurrili.todoappandroid.objects.sort.SortByCreatedTime;
import com.uj.yurrili.todoappandroid.objects.sort.SortByTime;
import com.uj.yurrili.todoappandroid.objects.sort.SortByTitle;
import com.uj.yurrili.todoappandroid.objects.sort.SortManager;
import com.uj.yurrili.todoappandroid.objects.sort.SortStrategy;

import net.danlew.android.joda.JodaTimeAndroid;

import java.net.MalformedURLException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ListActivity extends AppCompatActivity {

    DataBaseHelper dba_Task;
    @InjectView(R.id.rv)
    RecyclerView mRecyclerView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    SortManager sortManager;
    private List<Task> tasks;

    private RecyclerView.Adapter mAdapter;
    private Paint p = new Paint();
    // Temps for dialog
    private Task temp;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initLibraries();
        setSupportActionBar(toolbar);
        dba_Task = new DataBaseHelperImpl(getApplicationContext());
        initRecyclerView();
        initFloatingActionButton();

    }

    private void initLibraries() {
        // Library to binding views with valuables
        ButterKnife.inject(this);
        // Library to managing time
        JodaTimeAndroid.init(this);
    }

    private void initFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_plus));
    }

    @OnClick(R.id.fab)
    void startAddActivity() {
        Intent intent = new Intent(this, AddEditActivity.class);
        startActivity(intent);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Swipe behaviour created in createSimpleCall() method
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createSimpleCall());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        try {
        tasks = dba_Task.getTasks();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        setmAdapter();

    }

    public void setmAdapter(){
            sortManager = new SortManager(tasks);
            // Pair - first : onClick-info, second : onLongClick-edit
            Pair<myAdapter.OnItemClickListener, myAdapter.OnItemLongClickListener> listeners =  createListeners();
            mAdapter = new myAdapter(getApplicationContext(), tasks, listeners.first, listeners.second);



        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_Back_up_export:

                return true;
            case R.id.action_Back_up_import:
                return true;
            case R.id.submenu_item1:
                tasks = sortManager.sort(new SortByTitle());
                setmAdapter();
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.submenu_item2:
                tasks = sortManager.sort(new SortByTime());
                setmAdapter();
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.submenu_item3:
                tasks = sortManager.sort(new SortByCreatedTime());
                setmAdapter();
                mAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(
                getResources().getString(R.string.q_dialog_removing) +
                temp.getTitle());

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                ((myAdapter) mAdapter).removeItem(position);
                dba_Task.removeTask(temp);
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mAdapter.notifyDataSetChanged();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private ItemTouchHelper.SimpleCallback createSimpleCall(){
        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                position = viewHolder.getAdapterPosition();
                temp = ((myAdapter) mAdapter).getItem(viewHolder.getAdapterPosition());
                showAlertDialog();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    p.setColor(getResources().getColor(R.color.colorRedSwipe));
                    RectF background = new RectF((float) itemView.getRight() + dX,
                            (float) itemView.getTop(),
                            (float) itemView.getRight(),
                            (float) itemView.getBottom());
                    c.drawRect(background, p);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
    }

    private Pair<myAdapter.OnItemClickListener, myAdapter.OnItemLongClickListener> createListeners(){
        myAdapter.OnItemClickListener onClick = new myAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, Task task) {
                Snackbar.make(mRecyclerView, "Long click to edit", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        };

        myAdapter.OnItemLongClickListener onLongClick = new myAdapter.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClicked(int position, Task task) {
                Intent intent = new Intent(getApplicationContext(), AddEditActivity.class);
                intent.putExtra("id", task.getId()+"");
                startActivity(intent);
                return false;
            }
        };

        return new Pair<>(onClick,onLongClick);
    }
}
