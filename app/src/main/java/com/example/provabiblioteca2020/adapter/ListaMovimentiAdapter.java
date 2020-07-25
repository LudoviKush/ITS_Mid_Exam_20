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
import com.example.provabiblioteca2020.data.MovimentiTableHelper;

public class ListaMovimentiAdapter extends CursorAdapter {

    public ListaMovimentiAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View vView = LayoutInflater.from(context).inflate(R.layout.dettaglio_cella_movimenti, parent, false);
        return vView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView vDataString = view.findViewById(R.id.tvMovimenti);
        String vData;
        int vIsUscito;

        vData = cursor.getString(cursor.getColumnIndex(MovimentiTableHelper.DATA_ORA));
        vIsUscito = cursor.getInt(cursor.getColumnIndex(MovimentiTableHelper.IS_USCITO));

        if(vIsUscito == 0)
        {
            vDataString.setText("Entrato in data " + vData);
            view.setBackgroundColor(Color.GREEN);
        }
        else
        {
            vDataString.setText("Uscito in data " + vData);
            view.setBackgroundColor(Color.RED);
        }
    }
}