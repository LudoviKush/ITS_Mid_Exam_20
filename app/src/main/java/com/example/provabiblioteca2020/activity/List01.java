package com.example.provabiblioteca2020.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.provabiblioteca2020.R;
import com.example.provabiblioteca2020.adapter.ListaLibriAdapter;
import com.example.provabiblioteca2020.data.LibriMovimentiProvider;
import com.example.provabiblioteca2020.data.LibriTableHelper;
import com.example.provabiblioteca2020.fragment.CancDialog;

public class List01 extends AppCompatActivity implements CancDialog.ICancDialog, LoaderManager.LoaderCallbacks<Cursor> {

    ListView listaLibri;
    ListaLibriAdapter adapter;
    public static final int MY_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list01);
        getSupportActionBar().setTitle("LIST01");

        listaLibri = findViewById(R.id.listaLibri);

        adapter = new ListaLibriAdapter(this, null);
        listaLibri.setAdapter(adapter);
        getSupportLoaderManager().initLoader(MY_LOADER, null, this);

        listaLibri.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {

                Cursor vCursor = getContentResolver().query(Uri.parse(LibriMovimentiProvider.LIBROS_URI + "/" + (int)id), null, null, null, null);
                vCursor.moveToNext();
                String vTitolo = vCursor.getString(vCursor.getColumnIndex(LibriTableHelper.TITOLO));
                CancDialog vDialog = new CancDialog("ELIMINA", "Vuoi cancellare il libro " + vTitolo + " ?", (int) id);
                vDialog.show(getSupportFragmentManager(), null);
                return true;
            }
        });

        listaLibri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Va a MOD01
                Intent vIntent = new Intent(List01.this, Mod01.class);
                Bundle vBundle = new Bundle();

                vBundle.putInt("_ID", (int) id);
                vIntent.putExtras(vBundle);
                startActivity(vIntent);

            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, LibriMovimentiProvider.LIBROS_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }

    @Override
    public void onResponse(boolean aResponse, int aId) {
        if (aResponse) {
            getContentResolver().delete(Uri.parse(LibriMovimentiProvider.LIBROS_URI + "/" + aId), null, null);
        }
    }
}