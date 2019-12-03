package com.app.myapplication.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.myapplication.Activity.StartActivity;
import com.app.myapplication.Entity.ItemJson;
import com.app.myapplication.Helper.SingletonClassApp;
import com.app.myapplication.R;
import com.app.myapplication.WebDataProvider.IWebAPI;
import com.app.myapplication.WebDataProvider.WebAPIClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;


public class OneFragment extends Fragment implements
        FilmAdapter.RSSFeedAdapterOnClickHandler {
    IWebAPI IWebApi;
    View view;
    ArrayList<ItemJson> json_data = new ArrayList<>();
    private FilmAdapter filmAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_one, container, false);
        IWebApi = WebAPIClient.getClient().create(IWebAPI.class);

        mRecyclerView = view.findViewById(R.id.rv_rss_list1);
        mSwipeLayout = view.findViewById(R.id.swipeRefreshLayout);
        filmAdapter = new FilmAdapter(this);
        // result = (TextView) view.findViewById(R.id.result);
        mRecyclerView.setAdapter(filmAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));













        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
                                                               @Override

                                                               public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                                                                   //Use onNext to emit each item in the stream//
                                                                   e.onNext(1);
                                                                   e.onNext(2);
                                                                   e.onNext(3);
                                                                   e.onNext(4);

                                                                   //Once the Observable has emitted all items in the sequence, call onComplete//
                                                                   e.onComplete();
                                                               }
                                                           }
        );

        Observer<Integer> observer = new Observer<Integer>() {
            private static final String TAG ="Obser" ;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: All Done!");
            }
        };

//Create our subscription//
        observable.subscribe(observer);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                getItemsJson();
            }
        });

        getItemsJson();

        return view;
    }


    public void getItemsJson() {

        Call<List<ItemJson>> call1 = null;
        call1 = IWebApi.getItems();
        call1.enqueue(new Callback<List<ItemJson>>() {

            @Override
            public void onResponse(Call<List<ItemJson>> call, retrofit2.Response<List<ItemJson>> response) {

                json_data = (ArrayList<ItemJson>) response.body();
                SingletonClassApp.getInstance().setItemjson(json_data);


                Observable.just(json_data)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new Observer<ArrayList>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(ArrayList arrayList) {
                                //handling the result
                                filmAdapter.setRSSItems(json_data);
                                filmAdapter.notifyDataSetChanged();
                                mSwipeLayout.setRefreshing(false);
                            }
                            @Override
                            public void onError(Throwable e) {
                                //error handling made simple
                            }
                            @Override
                            public void onComplete() {
                                //cleaning up tasks
                            }
                        });







                Log.v("http", json_data.get(1).getImage());

            }

            @Override
            public void onFailure(Call<List<ItemJson>> call, Throwable t) {
                mSwipeLayout.setRefreshing(false);
                call.cancel();
            }
        });
    }

    public void set_Fiter(String s) {
        filmAdapter.getFilter().filter(s);
        filmAdapter.notifyDataSetChanged();
    }

    public void reset_item() {
        filmAdapter.setRSSItems(json_data);
        filmAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(ItemJson rssItem, int position) {
        ((StartActivity) getActivity()).openFragmentTow(position);
    }
}
