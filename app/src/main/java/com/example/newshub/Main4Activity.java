package com.example.newshub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity implements NewsAdapter.Clicked {

    ArrayList<News>list;
    ListView lv;
    NewsAdapter adapter;
    ImageView search;
    EditText et;
    LinearLayout ll,lvTitle;
    TextView tvTitle;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        list= new ArrayList<>();
        tvTitle=findViewById(R.id.tvTitle);
        lv=findViewById(R.id.lv);
        et=findViewById(R.id.editText);
        search=findViewById(R.id.search);
        ll=findViewById(R.id.ll);
        lvTitle=findViewById(R.id.lvTitle);

        lvTitle.setVisibility(View.GONE);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main4Activity.this.finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ll.setVisibility(View.GONE);
                lvTitle.setVisibility(View.VISIBLE);
                final ProgressDialog dialog= new ProgressDialog(Main4Activity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
              dialog.setTitle("Loading...");
                dialog.setMessage("Please Wait");
dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                tvTitle.setText("Showing Results For \""+et.getText().toString().trim()+"\"");

                RequestQueue r = Volley.newRequestQueue(Main4Activity.this);


                JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET,
                        "https://newsapi.org/v2/everything?q="+ et.getText().toString().trim()+"&apiKey=ed9ca03faf794285b4a88748b0ad820c",
                        null, new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray j1 = (JSONArray) response.getJSONArray("articles");
                            for (int i = 0; i < j1.length(); i++) {
                                JSONObject j2 = (JSONObject) j1.getJSONObject(i);

                                list.add(new News(j2.getString("title"), j2.getString("content"),
                                        j2.getString("description"), j2.getString("publishedAt"),
                                        j2.getString("urlToImage"),j2.getString("url"), response.getInt("totalResults")));
                            }
                           // Collections.shuffle(list);

                            adapter = new NewsAdapter(Main4Activity.this, list);
                            lv.setAdapter(adapter);
                            dialog.dismiss();
                            //  refreshLayout.setRefreshing(false);
                            // dialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("asdf",e.getMessage());
                            Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("asdf",error.getMessage());

                        //Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
                    }
                });

                r.add(j);
            }
        });



    }

    @Override
    public void onItemClicked(int index) {

        Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(index).getWeb()));
        startActivity(i);
    }
}
