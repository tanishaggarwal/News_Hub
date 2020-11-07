package com.example.newshub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.makeRestartActivityTask;

public class NewsAdapter extends ArrayAdapter<News> {

    public  interface Clicked
    {
        public void onItemClicked(int index);
    }
    Clicked c;


    private final Context context;
    private ArrayList<News> list;

    public NewsAdapter(@NonNull Context context,  ArrayList<News> list) {
        super(context, R.layout.news_layout,list);
        this.context = context;
        c=(Clicked)context;
        this.list = list;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v= inflater.inflate(R.layout.news_layout,parent,false);

        LinearLayout ln=v.findViewById(R.id.ln);

        TextView tvtitle= v.findViewById(R.id.title);
        TextView tvdate= v.findViewById(R.id.date);
        ImageView iv= v.findViewById(R.id.imageView3);
        TextView tvcontent=v.findViewById(R.id.content);
        ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c.onItemClicked(position);

            }
        });


if(!list.get(position).getUrl().equals("null")) {
    Glide.with(context).load(list.get(position).getUrl()).into(iv);
}


        tvtitle.setText(list.get(position).getTitle());
        tvcontent.setText(list.get(position).getDescription());
        tvdate.setText((list.get(position).getDate().substring(0,10)));



        return v;
    }



}
