package com.uj.yurrili.todoappandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uj.yurrili.todoappandroid.objects.Task;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yuri on 2016-06-01.
 */
public class myAdapter extends RecyclerView.Adapter<myAdapter.CustomViewHolder> {
    private List<Task> items;
    private Context mContext;
    private Typeface typeFace;
    private final OnItemClickListener listener;
    private final OnItemLongClickListener listenerLong;

    public interface OnItemClickListener {
        public void onItemClicked(int position, Task task);

    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position, Task task);
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position).getUrl_to_icon()!= null) {
            return 1;
        } else {
            return 0;
        }
    }


    public Task getItem(int position) {
        return items.get(position);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public myAdapter(Context context, List<Task> items,
                     OnItemClickListener listener,
                     OnItemLongClickListener listenerLong) {
        this.items = items;
        this.mContext = context;
        this.listener = listener;
        this.listenerLong = listenerLong;
        this.typeFace = Typeface.createFromAsset(mContext.getAssets(),
                mContext.getResources().getString(R.string.font_place));
    }

    @Override
    public myAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_text_view, viewGroup, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myAdapter.CustomViewHolder holder, int position) {
        holder.bind(holder, items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.image) ImageView imageView;
        @InjectView(R.id.title) TextView title;
        @InjectView(R.id.time) TextView timePlace;
        @InjectView(R.id.date) TextView datePlace;
        @InjectView(R.id.awesome_font_clock) TextView fontAwesomeClock;
        @InjectView(R.id.awesome_font_calendar) TextView fontAwesomeCalendar;

        public CustomViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void bind(RecyclerView.ViewHolder holder, final Task item, final int position) {
            setImageView(item);
            title.setText(item.getTitle());

            if(item.getTime_end().getTime() > 0) {
                Pair<String,String> datetime = Utilities.convertTime(item.getTime_end());
                timePlace.setText(datetime.first);
                datePlace.setText(datetime.second);
                fontAwesomeCalendar.setTypeface(typeFace);
                fontAwesomeClock.setTypeface(typeFace);
            } else {
                timePlace.setVisibility(View.INVISIBLE);
                datePlace.setVisibility(View.INVISIBLE);
                fontAwesomeCalendar.setVisibility(View.INVISIBLE);
                fontAwesomeClock.setVisibility(View.INVISIBLE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(position, item);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listenerLong.onItemLongClicked(position, item);
                    return true;
                }
            });
        }

        private void setImageView(final Task item){
            if(item.getUrl_to_icon()!= null) {
                Picasso.with(mContext)
                        .load(item.getUrl_to_icon())
                        .into(imageView);
            }
            imageView.setBackground(mContext.getResources().getDrawable(R.drawable.ic_circle4));

        }


    }


}