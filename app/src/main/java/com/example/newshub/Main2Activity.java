package com.example.newshub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    CardView cvBusiness, cvEntertainment, cvHealth, cvGeneral, cvScience, cvSports, cvTechnology;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        cvBusiness=findViewById(R.id.cvBusiness);
        cvEntertainment=findViewById(R.id.cvEntertainment);
        cvGeneral=findViewById(R.id.cvGeneral);
        cvHealth=findViewById(R.id.cvHealth);
        cvScience=findViewById(R.id.cvScience);
        cvSports=findViewById(R.id.cvSports);
        cvTechnology=findViewById(R.id.cvTechnology);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Main2Activity.this.finish();
            }
        });

        cvBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main2Activity.this,Main3Activity.class);
                intent.putExtra("cat","business");
                startActivity(intent);
            }
        });

        cvEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main2Activity.this,Main3Activity.class);
                intent.putExtra("cat","entertainment");
                startActivity(intent);
            }
        });
        cvGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main2Activity.this,Main3Activity.class);
                intent.putExtra("cat","general");
                startActivity(intent);
            }
        });
        cvHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main2Activity.this,Main3Activity.class);
                intent.putExtra("cat","health");
                startActivity(intent);
            }
        });
        cvScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main2Activity.this,Main3Activity.class);
                intent.putExtra("cat","science");
                startActivity(intent);
            }
        });
        cvSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main2Activity.this,Main3Activity.class);
                intent.putExtra("cat","sports");
                startActivity(intent);
            }
        });
        cvTechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Main2Activity.this,Main3Activity.class);
                intent.putExtra("cat","technology");
                startActivity(intent);
            }
        });

    }
}
