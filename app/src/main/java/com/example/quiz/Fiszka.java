package com.example.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Fiszka extends AppCompatActivity {

    private int index = 0;
    private String id;
    private int i=0;
    private Baza database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiszki);

        Button button = findViewById(R.id.button3);
        database = new Baza(this);
        TextView text = findViewById(R.id.textView3);
        String nazwa = getIntent().getStringExtra("Nazwa");
        text.setText(nazwa);


        Cursor cursor = database.WyswietlPosortowanePoWadze(nazwa);
        List<String> text1List = new ArrayList<>();
        List<String> text2List = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        List<String> wagaList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                String text1 = cursor.getString(cursor.getColumnIndex("text1"));
                String text2 = cursor.getString(cursor.getColumnIndex("text2"));
                String waga = cursor.getString(cursor.getColumnIndex("waga"));
                text1List.add(text1);
                text2List.add(text2);
                idList.add(id);
                wagaList.add(waga);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] text1Array = text1List.toArray(new String[0]);
        String[] text2Array = text2List.toArray(new String[0]);
        String[] idArray = idList.toArray(new String[0]);
        String[] wagaArray = wagaList.toArray(new String[0]);

        int długość = text1Array.length;

//wyswietlanie fiszek


        String text11 = text1Array[i];
        button.setText(text11);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = (index + 1) % 2;
                String text11 = text1Array[i];
                String text12 = text2Array[i];
                String[] buttonTexts = {text11, text12};
                button.setText(buttonTexts[index]);

            }
        });


        findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Fiszka.this, Nauka.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.aktualizacjaWagi(nazwa, text1Array[i],text2Array[i],1);
                if(i<długość- 1){
                    i++;
                    String text11 = text1Array[i];
                    button.setText(text11);
                }
                else{
                    i=0;
                    String text11 = text1Array[i];
                    button.setText(text11);
                }


            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.aktualizacjaWagi(nazwa, text1Array[i],text2Array[i],3);
                if(i<długość- 1){
                    i++;
                    String text11 = text1Array[i];
                    button.setText(text11);
                }
                else{
                    i=0;
                    String text11 = text1Array[i];
                    button.setText(text11);
                }

                }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.aktualizacjaWagi(nazwa, text1Array[i],text2Array[i],2);
                if(i<długość- 1){
                    i++;
                    String text11 = text1Array[i];
                    button.setText(text11);
                }
                else{
                    i=0;
                    String text11 = text1Array[i];
                    button.setText(text11);
                }

            }
        });

        findViewById(R.id.button12).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String nazwaDoUsuniecia = text.getText().toString();
                database.usuwanie(nazwaDoUsuniecia);
                Intent intent = new Intent(Fiszka.this, MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fiszka.this, Statystyki.class);
                intent.putExtra("Nazwa", nazwa);
                startActivity(intent);

            }
        });

        if (database.wszystkie3(nazwa)) {
            button.setText("Gratulacje nauszyłeś się");
        }

    }
}