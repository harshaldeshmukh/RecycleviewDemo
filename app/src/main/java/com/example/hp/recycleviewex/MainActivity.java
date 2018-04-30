package com.example.hp.recycleviewex;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycleview;
    RecycleViewAdapter recycleViewAdapter;
    ArrayList <DataPojo>  arralylist_dataPojo;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arralylist_dataPojo=new ArrayList<>();
        recycleview=(RecyclerView)findViewById(R.id.recycleview);
        recycleview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycleview.setLayoutManager(mLayoutManager);
        fetchdata();

    }

    public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
        ArrayList<DataPojo> datalist;
        Context context;
        public RecycleViewAdapter(Context context, ArrayList<DataPojo> followerlist) {
            super();
            this.context = context;
            this.datalist = followerlist;
            }
            @Override
        public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list, viewGroup, false);
            RecycleViewAdapter.ViewHolder viewHolder = new RecycleViewAdapter.ViewHolder(v);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(RecycleViewAdapter.ViewHolder viewHolder, int i) {
            viewHolder.firstname.setText(datalist.get(i).getProduct_name());
            }
            @Override
        public int getItemCount() {
            return datalist.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView firstname;

            public ViewHolder(View itemView) {
                super(itemView);
                firstname = (TextView) itemView.findViewById(R.id.text);
            }
        }
        }


    private  void   fetchdata(){
      JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(AppConstants.URL, null,
              new Response.Listener<JSONObject>() {
                  @Override
                  public void onResponse(JSONObject response) {
                      try {
                          ArrayList<DataPojo> imageRecords = parseData(response);
                          recycleViewAdapter = new RecycleViewAdapter(getApplicationContext(), arralylist_dataPojo);
                          recycleview.setAdapter(recycleViewAdapter);
                          recycleViewAdapter.notifyDataSetChanged();
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }

                  }
              }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

          }
      });

      VolleyApplication.getInstance().getRequestQueue().add(jsonObjectRequest);
    }

    private  ArrayList<DataPojo> parseData (JSONObject json) throws JSONException{

        JSONArray jsonArray= json.getJSONArray("products");
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject= jsonArray.getJSONObject(i);
            DataPojo dataPojo= new DataPojo();
            dataPojo.setProduct_name(jsonObject.getString("product"));
            arralylist_dataPojo.add(dataPojo);
            }
        return  arralylist_dataPojo;
        }


}
