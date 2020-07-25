package com.example.provabiblioteca2020.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.provabiblioteca2020.R;
import com.example.provabiblioteca2020.data.LibriTableHelper;

public class ListaLibriAdapter extends CursorAdapter {

    public ListaLibriAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View vView = LayoutInflater.from(context).inflate(R.layout.dettaglio_cella_libri, parent, false);
        return vView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtTitolo = view.findViewById(R.id.tvTitolo);
        TextView txtAutore = view.findViewById(R.id.tvAutore);
        int isNoleggiato;


        txtTitolo.setText(cursor.getString(cursor.getColumnIndex(LibriTableHelper.TITOLO)));
        txtAutore.setText(cursor.getString(cursor.getColumnIndex(LibriTableHelper.AUTORE)));

        isNoleggiato = cursor.getInt(cursor.getColumnIndex(LibriTableHelper.IS_NOLEGGIATO));

        if(isNoleggiato == 0)
        {
            view.setBackgroundColor(Color.GREEN);
        }
        else
        {
            view.setBackgroundColor(Color.RED);
        }
    }
}