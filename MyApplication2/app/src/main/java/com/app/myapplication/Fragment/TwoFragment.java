package com.app.myapplication.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.myapplication.Entity.Data;
import com.app.myapplication.Helper.SingletonClassApp;
import com.app.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class TwoFragment extends Fragment {
    View view;
    RecyclerView horizontal_recycler_view;
    HorizontalAdapter horizontalAdapter;
    private List<Data> data;
    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_two, container, false);
        horizontal_recycler_view = (RecyclerView) view.findViewById(R.id.horizontal_recycler_view);
        position = getArguments().getInt("position", 0);
        data = fill_with_data();
        horizontalAdapter = new HorizontalAdapter(data, getContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManager);
        horizontal_recycler_view.setAdapter(horizontalAdapter);
        horizontal_recycler_view.scrollToPosition(position);
        // Inflate the layout for this fragment

        return view;
    }

    public List<Data> fill_with_data() {
        SingletonClassApp data_sing = SingletonClassApp.getInstance();
        List<Data> data = new ArrayList<>();
        for (int i = 0; i < data_sing.getItemjson().size(); i++) {
            data.add(new Data(data_sing.getItemjson().get(i).getImage(), data_sing.getItemjson().get(i).getDescription(),
                    data_sing.getItemjson().get(i).getName(),
                    data_sing.getItemjson().get(i).getTime()));
        }

        return data;

    }


}
