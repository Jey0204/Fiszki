package com.example.quiz;

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

public class Statystyki extends AppCompatActivity {
    private Baza database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statystyki);

        database = new Baza(this);
        LinearLayout scrollViewLayout = findViewById(R.id.scrollView3);
        String nazwa = getIntent().getStringExtra("Nazwa");

        Cursor cursor = database.WyswietlPoNazwie(nazwa);
        if (cursor.moveToFirst()) {
            do {
                String text1 = cursor.getString(cursor.getColumnIndex("text1"));
                String text2 = cursor.getString(cursor.getColumnIndex("text2"));
                int waga = cursor.getInt(cursor.getColumnIndex("waga"));
                String znak = ""; // Zmienna typu String dla znaku Unicode
                int color = 0;
                if (waga == 3) {
                    znak = "\uD83D\uDE01";
                } else if (waga == 2) {
                    znak = "\uD83D\uDE00";
                } else {
                    znak = "\uD83D\uDE41";
                }

                TextView textView = new TextView(this);
                textView.setText( text1 + "  <->  " + text2 + "   "+ znak);
                textView.setTextSize(20);
                textView.setTextColor(Color.BLACK);
                textView.setPadding(20, 20, 20, 20);

                scrollViewLayout.addView(textView); // Dodanie TextView do ScrollView

            } while (cursor.moveToNext());
        }
        cursor.close();

        findViewById(R.id.button14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statystyki.this, Fiszka.class);
                intent.putExtra("Nazwa", nazwa);
                startActivity(intent);
            }
        });
    }
}

