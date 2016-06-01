package com.uj.yurrili.todoappandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.uj.yurrili.todoappandroid.objects.Task;

import java.util.List;

/**
 * Created by Yuri on 2016-06-01.
 */
public class myAdapter extends RecyclerView.Adapter<myAdapter.CustomViewHolder> {
    private List<Task> items;
    private Context mContext;
    private Typeface typeFace;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Task item);
    }

    public myAdapter(Context context, List<Task> items, OnItemClickListener listener) {
        this.items = items;
        this.mContext = context;
        this.listener = listener;
        typeFace = Typeface.createFromAsset(mContext.getAssets(), "font/fontawesome-webfont.ttf");
    }

    @Override
    public myAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_text_view, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myAdapter.CustomViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView title;
        protected TextView timestamp;
        protected TextView fontAwesomeTask;
        protected TextView fontAwesomeCalendar;
        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.image);
            this.title = (TextView) view.findViewById(R.id.title);
            this.timestamp = (TextView) view.findViewById(R.id.timestamp);
            this.fontAwesomeTask = (TextView) view.findViewById(R.id.awesome_font_task);
            this.fontAwesomeCalendar = (TextView) view.findViewById(R.id.awesome_font_calendar);
        }

        public void bind(final Task item, final OnItemClickListener listener) {

            if(item.getUrl_to_icon()!= null) {
                Picasso.with(mContext)
                        .load(item.getUrl_to_icon())
                        .into(imageView);
                imageView.setBackground(mContext.getResources().getDrawable(R.drawable.ic_circle4));
            } else {
                imageView.setBackground(mContext.getResources().getDrawable(R.drawable.ic_circle4));
            }

            title.setText(item.getTitle());

            if(item.getTime_end().getTime() > 0) {
                timestamp.setText(Utilities.convertTime(item.getTime_end()));
                fontAwesomeCalendar.setTypeface(typeFace);
            } else {
                fontAwesomeCalendar.setVisibility(View.INVISIBLE);
            }

            fontAwesomeTask.setTypeface(typeFace);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


}