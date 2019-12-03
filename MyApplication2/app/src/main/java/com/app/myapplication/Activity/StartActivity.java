package com.app.myapplication.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.app.myapplication.Fragment.OneFragment;
import com.app.myapplication.Fragment.TwoFragment;
import com.app.myapplication.Helper.SingletonClassApp;
import com.app.myapplication.R;
import com.google.android.material.navigation.NavigationView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SearchView searchView;
    Fragment fragment;
    private SingletonClassApp setting = SingletonClassApp.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startInitView();
    }

    private void startInitView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        try {
            if (setting.getPosition() == 1) {

                Log.d("Start", "Fragment One");
                setting.setPosition(1);
                openFragmentOne();

            } else {

                setting.setPosition(2);
                Log.d("Start", "Fragment Tow");
                setTitle(getResources().getString(R.string.description_title));
                openFragmentTow(0);
            }

        } catch (NullPointerException e) {

            //   setting.setPosition(1);
            //   openFragmentOne();
            Log.d("Start", "Fragment One First Start");

        }



    }

    public void openFragment(Fragment fr, int position) {
        fragment = fr;
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fr.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.conteinerf, fragment)
                .commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            if (setting.getPosition() == 2) {
                searchView.setVisibility(View.VISIBLE);
                openFragmentOne();
                return;
            } else {
                super.onBackPressed();
            }


            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        if (setting.getPosition() == 2) {
            searchView.setVisibility(View.GONE);
        }

        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.GRAY);
        searchAutoComplete.setTextColor(Color.WHITE);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                ((OneFragment) fragment).reset_item();
                return false;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ((OneFragment) fragment).set_Fiter(query);
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });



        return true;
    }

    public void openFragmentTow(int position) {
        setting.setPosition(2);
        setTitle(getResources().getString(R.string.description_title));
        searchView.setVisibility(View.GONE);
        openFragment(new TwoFragment(), position);
    }

    public void openFragmentOne() {
        setting.setPosition(1);
        setTitle(getResources().getString(R.string.app_name));
        openFragment(new OneFragment(), 0);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {


        super.onResume();
    }


}
