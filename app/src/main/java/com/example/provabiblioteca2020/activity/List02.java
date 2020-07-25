package com.example.provabiblioteca2020.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.example.provabiblioteca2020.R;
import com.example.provabiblioteca2020.adapter.ListaMovimentiAdapter;
import com.example.provabiblioteca2020.data.LibriMovimentiProvider;
import com.example.provabiblioteca2020.data.MovimentiTableHelper;

public class List02 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView listViewMovimenti;
    ListaMovimentiAdapter listaMovimentiAdapter;
    public static final int MY_LOADER = 0;
    int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list02);
        getSupportActionBar().setTitle("LIST02");

        listViewMovimenti = findViewById(R.id.listaMovimenti);

        if (getIntent().getExtras() != null) {
            mId = getIntent().getExtras().getInt("_ID");
        }
        listaMovimentiAdapter = new ListaMovimentiAdapter(this, null);
        listViewMovimenti.setAdapter(listaMovimentiAdapter);

        getSupportLoaderManager().initLoader(MY_LOADER, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, LibriMovimentiProvider.MOVIMENTOS_URI, null, MovimentiTableHelper.ID_LIBRO + " = " + mId, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        listaMovimentiAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        listaMovimentiAdapter.changeCursor(null);
    }
}