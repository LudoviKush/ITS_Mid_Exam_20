package com.example.provabiblioteca2020.data;

import android.provider.BaseColumns;

public class MovimentiTableHelper implements BaseColumns {

    public static final String TABLE_NAME = "movimenti";
    public static final String ID_LIBRO = "id_libro";
    public static final String DATA_ORA = "data_ora";
    public static final String IS_USCITO = "is_uscito"; // 1 = è uscito    0 = è rientrato

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ID_LIBRO + " INTEGER,"
            + DATA_ORA + " TEXT,"
            + IS_USCITO + " INTEGER);";
}