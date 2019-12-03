package com.app.myapplication.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.Entity.ItemJson;
import com.app.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by vitaliybv on 3/20/18.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ItemViewHolder> implements Filterable {

    private final RSSFeedAdapterOnClickHandler mClickHandler;
    int pos;
    Context mContext;
    private ArrayList<ItemJson> items;
    private ArrayList<ItemJson> itemsfilter;

    FilmAdapter(RSSFeedAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public void setRSSItems(ArrayList<ItemJson> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.main_feed_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);


        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ItemJson rssItem = items.get(position);
        ImageView a = ((ImageView) holder.FilmFeedView.findViewById(R.id.imageLoad));

        try {
            Picasso.with(mContext).load(rssItem.getImage()).resize(0, 440).
                    into(a);
        } catch (Exception e) {
        }

        ((TextView) holder.FilmFeedView.findViewById(R.id.tv_title)).setText(rssItem.getName());
        ((TextView) holder.FilmFeedView.findViewById(R.id.tv_pub_date)).setText(getDate(Long.valueOf(rssItem.getTime()), "dd-MMM-yyyy hh:mm"));
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemsfilter = items;
                } else {
                    ArrayList<ItemJson> filteredList = new ArrayList<>();
                    for (ItemJson row : items) {

                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }

                    }

                    itemsfilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsfilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (itemsfilter.size() > 0) {
                    items = (ArrayList<ItemJson>) filterResults.values;
                    notifyDataSetChanged();
                } else {

                }
            }
        };
    }

    public interface RSSFeedAdapterOnClickHandler {
        void onClick(ItemJson rssItem, int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View FilmFeedView;


        public ItemViewHolder(View itemView) {
            super(itemView);
            this.FilmFeedView = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            ItemJson rssItem = items.get(adapterPosition);
            mClickHandler.onClick(rssItem, adapterPosition);
        }
    }
}


