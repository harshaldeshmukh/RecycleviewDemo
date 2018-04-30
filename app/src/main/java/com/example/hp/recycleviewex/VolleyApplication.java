package com.example.hp.recycleviewex;

import android.app.Application;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



//Created By Harshal

public  class VolleyApplication extends Application {

    private static VolleyApplication sInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);
        sInstance = this;
        }

    public synchronized static VolleyApplication getInstance() {
        return sInstance;
    }

    public com.android.volley.RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

}
