package com.example.quiz;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NoweFiszki extends AppCompatActivity {
    private EditText editTextCategory;
    private EditText editTextText1;
    private EditText editTextText2;
    private Baza dbHelper;
    private int suma = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nowe_fiszki);

        dbHelper = new Baza(this);
        editTextCategory = findViewById(R.id.editTextText);
        editTextText1 = findViewById(R.id.editTextText2);
        editTextText2 = findViewById(R.id.editTextText3);
        TextView napis = findViewById(R.id.textView4);

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = editTextCategory.getText().toString();
                Intent intent = new Intent(NoweFiszki.this, Fiszka.class);
                intent.putExtra("Nazwa", category);
                startActivity(intent);

            }
        });

        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = editTextCategory.getText().toString();
                String text1 = editTextText1.getText().toString();
                String text2 = editTextText2.getText().toString();
                Baza dbHelper = new Baza(NoweFiszki.this);
                boolean inserted = dbHelper.insertData(category, text1, text2, 0);


                if (inserted) {
                    Toast.makeText(NoweFiszki.this, "Dane zostały pomyślnie zapisane", Toast.LENGTH_SHORT).show();
                    editTextCategory.setEnabled(false);
                    editTextText1.setText("");
                    editTextText2.setText("");
                    suma++;
                    napis.setText(String.valueOf(suma));
                } else {
                    Toast.makeText(NoweFiszki.this, "Wystąpił problem podczas zapisywania danych", Toast.LENGTH_SHORT).show();
                }

            }

        });

        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoweFiszki.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
