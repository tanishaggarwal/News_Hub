package com.example.newshub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity implements NewsAdapter.Clicked {

    ListView lv;
    NewsAdapter adapter;
    ArrayList<News> list;
    TextView tvTitle;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText(StringUtils.capitalize(getIntent().getStringExtra("cat"))+" News");

        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP |ActionBar.DISPLAY_USE_LOGO);
        //actionBar.setIcon(R.drawable.icon2);



        lv=findViewById(R.id.lv);
        list=new ArrayList<>();

        final ProgressDialog dialog= new ProgressDialog(Main3Activity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loading...");
        dialog.setMessage("Please Wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        RequestQueue r= Volley.newRequestQueue(this);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Main3Activity.this.finish();
            }
        });


        JsonObjectRequest j= new JsonObjectRequest(Request.Method.GET,
                "https://newsapi.org/v2/top-headlines?country=in&language=en&category="+ getIntent().getStringExtra("cat") +"&apiKey=ed9ca03faf794285b4a88748b0ad820c",
                null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray j1= (JSONArray)response.getJSONArray("articles");
                    for (int i=0;i<j1.length();i++)
                    {
                        JSONObject j2= (JSONObject)j1.getJSONObject(i);

                        list.add(new News(j2.getString("title"),
                                j2.getString("content"),j2.getString("description"),
                                j2.getString("publishedAt"),j2.getString("urlToImage"),j2.getString("url"), response.getInt("totalResults")));
                    }

                    adapter= new NewsAdapter(Main3Activity.this,list);
                    lv.setAdapter(adapter);
                    dialog.dismiss();
                  //  list.clear();
                   // Toast.makeText(getApplicationContext(), getIntent().getStringExtra("cat"),Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
            }
        });

        r.add(j);



    }

    @Override
    public void onItemClicked(int index) {

        Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(index).getWeb()));
        startActivity(i);

    }
}
