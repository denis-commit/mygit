package com.app.myapplication.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.Entity.Data;
import com.app.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {
    List<Data> horizontalList = Collections.emptyList();
    Context context;


    public HorizontalAdapter(List<Data> horizontalList, Context context) {
        this.horizontalList = horizontalList;
        this.context = context;
    }

    public static String getDate(long milliSeconds, String dateFormat) {

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        Picasso.with(context).load(horizontalList.get(position).imageUrl).resize(0, 440).into(holder.imageView);
        holder.film_name.setText(horizontalList.get(position).film_name);
        holder.film_description.setText(horizontalList.get(position).film_description);
        holder.film_time.setText(getDate(Long.valueOf(horizontalList.get(position).film_time), "dd-MMM-yyyy hh:mm"));


    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView film_name;
        TextView film_description;
        TextView film_time;

        public MyViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.film_item_image);

            film_name = view.findViewById(R.id.film_name);

            film_description = view.findViewById(R.id.film_description);

            film_time = view.findViewById(R.id.film_time);


        }
    }
}