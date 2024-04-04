package com.example.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Nauka extends AppCompatActivity {
    private Baza database;
private List<String> tablicaNazw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nauka);
        tablicaNazw = new ArrayList<>();
        database = new Baza(this);
       LinearLayout scrollViewLayout = findViewById(R.id.scrollView2);

        Cursor cursor = database.Wyswietl();
        if (cursor.moveToFirst()) {
            do {

                String nazwa = cursor.getString(cursor.getColumnIndex("Nazwa"));
                if(!tablicaNazw.contains(nazwa)){
                    TextView textView = new TextView(this);
                    textView.setText(nazwa);
                    textView.setTextSize(20);
                    textView.setTextColor(Color.BLACK);
                    textView.setPadding(20, 20, 20, 20);
                    tablicaNazw.add(nazwa);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Przechodzenie do ekranu Fiszka po kliknięciu
                            Intent intent = new Intent(Nauka.this, Fiszka.class);
                            intent.putExtra("Nazwa", nazwa);
                            startActivity(intent);
                        }
                    });
                    scrollViewLayout.addView(textView);
                }


            } while (cursor.moveToNext());
        }
        cursor.close();

        findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Nauka.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
