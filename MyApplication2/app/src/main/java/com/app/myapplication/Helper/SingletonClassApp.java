package com.app.myapplication.Helper;

import com.app.myapplication.Entity.ItemJson;

import java.io.Serializable;
import java.util.ArrayList;

public class SingletonClassApp implements Serializable {

    private static volatile SingletonClassApp sSoleInstance;


    public ArrayList<ItemJson> itemjson;
    private int position = 1;


    //private constructor.
    private SingletonClassApp() {
        //Prevent form the reflection api.
        if (sSoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SingletonClassApp getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SingletonClassApp.class) {
                if (sSoleInstance == null) sSoleInstance = new SingletonClassApp();
            }
        }

        return sSoleInstance;
    }

    public ArrayList<ItemJson> getItemjson() {
        return itemjson;
    }

    public void setItemjson(ArrayList<ItemJson> itemjson) {
        this.itemjson = itemjson;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //Make singleton from serialize and deserialize operation.
    protected SingletonClassApp readResolve() {
        return getInstance();
    }
}
