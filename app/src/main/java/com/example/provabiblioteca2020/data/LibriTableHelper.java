package com.example.provabiblioteca2020.data;

import android.provider.BaseColumns;

public class LibriTableHelper implements BaseColumns {
    public static final String TABLE_NAME = "libri";
    public static final String TITOLO = "titolo";
    public static final String AUTORE = "autore";
    public static final String IS_NOLEGGIATO = "isnoleggiato";  // 1 = è noleggiato     0 = è rientrato

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TITOLO + " TEXT,"
            + AUTORE + " TEXT,"
            + IS_NOLEGGIATO + " INTEGER);";

}