package com.uj.yurrili.todoappandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.uj.yurrili.todoappandroid.objects.Task;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Yuri on 2016-06-01.
 */
public class myAdapter extends RecyclerView.Adapter<myAdapter.CustomViewHolder> {
    private List<Task> feedItemList;
    private Context mContext;

    public myAdapter(Context context, List<Task> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public myAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_text_view, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myAdapter.CustomViewHolder holder, int position) {

        Task feedItem = feedItemList.get(position);


        //Setting text view title

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomViewHolder holder = (CustomViewHolder) view.getTag();
                int position = holder.getPosition();

                Task feedItem = feedItemList.get(position);
                Toast.makeText(mContext, feedItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        };

        //Download image using picasso library
        if(feedItem.getUrl_to_icon()!= null)
            Picasso.with(mContext)
                    .load(feedItem.getUrl_to_icon())
                    .into(holder.imageView);


        holder.title.setText(feedItem.getTitle());
        if(feedItem.getTime_end().getTime() >0) {
            holder.timestamp.setText(Utilities.convertTime(feedItem.getTime_end()));
            holder.timestamp.setTag(holder);
        }

//        holder.textView.setOnClickListener(clickListener);
////        holder.imageView.setOnClickListener(clickListener);


        holder.title.setTag(holder);
//        holder.imageView.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView title;
        protected TextView timestamp;
        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.image);
            this.title = (TextView) view.findViewById(R.id.title);
            this.timestamp = (TextView) view.findViewById(R.id.timestamp);
        }
    }
}