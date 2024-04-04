package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Baza  extends SQLiteOpenHelper{

        private static final String DATABASE_NAME = "Fiszki";
        private static final String TABLE_NAME = "Tabela";
        private static final String COL_1 = "id";
        private static final String COL_2 = "Nazwa";
        private static final String COL_3 = "text1";
        private static final String COL_4 = "text2";
        private static final String COL_5 = "waga";

        public Baza(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }


        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " + COL_5 + " INTEGER)");
        }


        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean insertData(String Nazwa, String text1, String text2, int waga) {
            SQLiteDatabase dp = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_2, Nazwa);
            values.put(COL_3, text1);
            values.put(COL_4, text2);
            values.put(COL_5, waga);

            long result = dp.insert(TABLE_NAME, null, values);
            return result != -1;
        }

    public Cursor Wyswietl() {
        SQLiteDatabase dp = this.getReadableDatabase();
        String[] columns = {COL_2, COL_3, COL_4, COL_5};
        return dp.query(TABLE_NAME, columns, null, null, null, null, null);
    }

    public Cursor WyswietlPoNazwie(String Nazwa) {
        SQLiteDatabase dp = this.getReadableDatabase();
        String[] columns = {COL_2, COL_3, COL_4, COL_5};
        String selection = COL_2 + " = ?";
        String[] selectionArgs = {Nazwa};
        return dp.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }
    public Cursor WyswietlPosortowanePoWadze(String Nazwa) {
        SQLiteDatabase dp = this.getReadableDatabase();
        String[] columns = {COL_2, COL_3, COL_4, COL_5};
        String selection = COL_2 + " = ?";
        String[] selectionArgs = {Nazwa};
        String orderBy = COL_5 + " ASC"; // Sortowanie według kolumny COL_5 (wagi) w kolejności rosnącej
        return dp.query(TABLE_NAME, columns, selection, selectionArgs, null, null, orderBy);
    }


    public void aktualizacjaWagi(String Nazwa, String text1, String text2, Integer newValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_5, newValue);

        String selection = COL_2 + " = ? AND " + COL_3 + " = ? AND " + COL_4 + " = ?";
        String[] selectionArgs = {Nazwa, text1, text2};

        int rowsAffected = db.update(TABLE_NAME, values, selection, selectionArgs);

        if (rowsAffected > 0) {
            Log.d("Aktualizacja", "Zaktualizowano wiersz pomyślnie");
        } else {
            Log.d("Aktualizacja", "Nie udało się zaktualizować wiersza");
        }

        db.close();
    }


    public boolean wszystkie3(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_2 + " = ?"; // Warunek na kolumnę 2
        String[] selectionArgs = {name};
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL_5}, selection, selectionArgs, null, null, null);

        boolean wszystkieWagi3 = true; // Zakładamy, że wszystkie wagi są 3, jeśli okaże się, że chociaż jedna nie jest, zmienimy wartość na false
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String waga = cursor.getString(cursor.getColumnIndexOrThrow(COL_5));
                if (!waga.equals("3")) {
                    wszystkieWagi3 = false; // Zmieniamy na false, ponieważ przynajmniej jedna waga nie jest równa 3
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return wszystkieWagi3;
    }




    public int liczbaElementow(String nazwa) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_2 + " = ?";
        String[] selectionArgs = {nazwa};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        int count = 0;
        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        db.close();
        return count;
    }





    public void usuwanie(String name) {
            SQLiteDatabase db = this.getWritableDatabase();
            String whereClause = COL_2 + " = ?";
            String[] whereArgs = {name};
            db.delete(TABLE_NAME, whereClause, whereArgs);
            db.close();
        }

        public boolean nazwaAlreadyExists(String nazwa) {
            SQLiteDatabase dp = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + " = ?";
            Cursor cursor = dp.rawQuery(query, new String[]{nazwa});
            boolean result = cursor.getCount() > 0;
            cursor.close();
            dp.close();
            return result;
        }
    public void aktualizujWaga(String id, int nowaWaga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_5, nowaWaga);

        String whereClause = COL_2 + " = ?";
        String[] whereArgs = {id};

        int rowsAffected = db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }


}
