package com.example.newshub;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements NewsAdapter.Clicked {

    ListView lv;
    NewsAdapter adapter;
    ArrayList<News>list, list1;
    ImageView iv;
    LinearLayout lv1;
    TextView tv;
    ImageView search;
    EditText et;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et=findViewById(R.id.editText);
        search=findViewById(R.id.search);
       // et.setVisibility(View.GONE);
        tv=findViewById(R.id.tvTitle);
        refreshLayout=findViewById(R.id.refresh);

        iv=findViewById(R.id.imageView4);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });



        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP |ActionBar.DISPLAY_USE_LOGO);
        //actionBar.setIcon(R.drawable.icon2);


        lv1=findViewById(R.id.lv1);
        lv1.setVisibility(View.GONE);

        lv=findViewById(R.id.lv);
        list=new ArrayList<>();
        final ProgressDialog dialog= new ProgressDialog(MainActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //dialog.setProgressStyle(android.R.attr.dial);
        dialog.setTitle("Loading...");
        dialog.setMessage("Please Wait");
dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        final internet i= new internet();
        if(i.CheckInternetConnectivity(this)) {

            RequestQueue r = Volley.newRequestQueue(this);


            JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET,
                    "https://newsapi.org/v2/top-headlines?country=in&language=en&apiKey=ed9ca03faf794285b4a88748b0ad820c",
                    null, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray j1 = (JSONArray) response.getJSONArray("articles");
                        for (int i = 0; i < j1.length(); i++) {
                            JSONObject j2 = (JSONObject) j1.getJSONObject(i);

                            list.add(new News(j2.getString("title"), j2.getString("content"), j2.getString("description"), j2.getString("publishedAt"), j2.getString("urlToImage"),j2.getString("url"),
                                    response.getInt("totalResults")));
                        }

                        adapter = new NewsAdapter(MainActivity.this, list);
                        lv.setAdapter(adapter);
                        dialog.dismiss();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("asdf",e.getMessage());
                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
                }
            });

            r.add(j);
        }
        else
        {
            lv1.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
            iv.setVisibility(View.GONE);
            search.setVisibility(View.GONE);


            dialog.dismiss();
        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                Handler handler= new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Collections.shuffle(list);

                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                },1000);







            }
        });

        Button btn=findViewById(R.id.tryagain);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(i.CheckInternetConnectivity(MainActivity.this)) {


                    tv.setVisibility(View.VISIBLE);
                    lv1.setVisibility(View.GONE);
                    iv.setVisibility(View.VISIBLE);
                    RequestQueue r = Volley.newRequestQueue(MainActivity.this);

dialog.show();


                    JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET,
                            "https://newsapi.org/v2/top-headlines?country=in&language=en&apiKey=ed9ca03faf794285b4a88748b0ad820c",
                            null, new Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                dialog.dismiss();
                                JSONArray j1 = (JSONArray) response.getJSONArray("articles");
                                for (int i = 0; i < j1.length(); i++) {
                                    JSONObject j2 = (JSONObject) j1.getJSONObject(i);

                                    list.add(new News(j2.getString("title"), j2.getString("content"), j2.getString("description"),
                                            j2.getString("publishedAt"), j2.getString("urlToImage"),j2.getString("url"), response.getInt("totalResults")));
                                }


                                adapter = new NewsAdapter(MainActivity.this, list);
                                lv.setAdapter(adapter);
                                // dialog.dismiss();


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
                        }
                    });

                    r.add(j);
                }
                else
                {
                    lv1.setVisibility(View.GONE);
                    lv1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lv1.setVisibility(View.VISIBLE);
                        }
                    },250);


                   // dialog.dismiss();
                }
            }
        });



search.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      //  et.setVisibility(View.VISIBLE);
   //     tv.setVisibility(View.GONE);
        Intent i= new Intent(MainActivity.this,Main4Activity.class);
//        i.putExtra( "search",et.getText().toString());
        startActivity(i);


    }
});



    }

    public void notifyitem()
    {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int index) {
        Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(index).getWeb()));
        startActivity(i);
    }

    public class internet
    {


        public boolean CheckInternetConnectivity(Context context)
        {
            NetworkInfo info=(NetworkInfo)((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

            if(info==null)

            {

                return false;


            }
 else
 {
     return  true;
 }
        }
    }


}
