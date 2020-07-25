package com.example.provabiblioteca2020.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.provabiblioteca2020.R;
import com.example.provabiblioteca2020.data.LibriMovimentiProvider;
import com.example.provabiblioteca2020.data.LibriTableHelper;
import com.example.provabiblioteca2020.data.MovimentiTableHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Mod01 extends AppCompatActivity {

    EditText editTextModificaTitoloLibro, editTextModificaAutoreloLibro;
    Button btnModificaLibro, btnNoleggiaLibro, btnRientraLibro, btnStoricoMovimenti;

    int mId;
    int mIsNoleggiato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod01);

        getSupportActionBar().setTitle("MOD01");
        editTextModificaTitoloLibro = findViewById(R.id.etTitoloNew);
        editTextModificaAutoreloLibro = findViewById(R.id.etAutore);

        btnModificaLibro = findViewById(R.id.btnModifica);
        btnNoleggiaLibro = findViewById(R.id.btnNoleggia);
        btnRientraLibro = findViewById(R.id.btnRientra);
        btnStoricoMovimenti = findViewById(R.id.btnMovimenti);


        if (getIntent().getExtras() != null) {
            mId = getIntent().getExtras().getInt("_ID");
            Cursor vCursor = getContentResolver().query(Uri.parse(LibriMovimentiProvider.LIBROS_URI + "/" + mId), null, null, null, null, null);
            vCursor.moveToNext();
            String vTitolo = vCursor.getString(vCursor.getColumnIndex(LibriTableHelper.TITOLO));
            String vAutore = vCursor.getString(vCursor.getColumnIndex(LibriTableHelper.AUTORE));

            mIsNoleggiato = vCursor.getInt(vCursor.getColumnIndex(LibriTableHelper.IS_NOLEGGIATO));
            controlloNoleggio();

            editTextModificaTitoloLibro.setText(vTitolo);
            editTextModificaAutoreloLibro.setText(vAutore);
        }


        btnModificaLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vTitolo = editTextModificaTitoloLibro.getText().toString();
                String vAutore = editTextModificaAutoreloLibro.getText().toString();

                if (vTitolo.isEmpty() || vAutore.isEmpty()) {
                    Toast.makeText(Mod01.this, "I campi Titolo e Autore devono essere compilati", Toast.LENGTH_LONG).show();
                } else {
                    ContentValues vValue = new ContentValues();
                    vValue.put(LibriTableHelper.TITOLO, vTitolo);
                    vValue.put(LibriTableHelper.AUTORE, vAutore);

                    getContentResolver().update(Uri.parse(LibriMovimentiProvider.LIBROS_URI + "/" + mId), vValue, null, null);
                    finish();
                }
            }
        });

        btnNoleggiaLibro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mIsNoleggiato == 0) {
                    mIsNoleggiato = 1;
                    controlloNoleggio();
                    ContentValues vValue = new ContentValues();
                    vValue.put(LibriTableHelper.IS_NOLEGGIATO, mIsNoleggiato);

                    getContentResolver().update(Uri.parse(LibriMovimentiProvider.LIBROS_URI + "/" + mId), vValue, null, null);

                    String vDataNoleggio;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                    vDataNoleggio = sdf.format(new Date());

                    ContentValues vValueMovimento = new ContentValues();
                    vValueMovimento.put(MovimentiTableHelper.ID_LIBRO, mId);
                    vValueMovimento.put(MovimentiTableHelper.DATA_ORA, vDataNoleggio);
                    vValueMovimento.put(MovimentiTableHelper.IS_USCITO, 1);

                    getContentResolver().insert(LibriMovimentiProvider.MOVIMENTOS_URI, vValueMovimento);
                    finish();
                } else {
                    Toast.makeText(Mod01.this, "Il libro risulta già noleggiato", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRientraLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsNoleggiato == 0) {
                    Toast.makeText(Mod01.this, "Il libro risulta già rientrato", Toast.LENGTH_LONG).show();
                } else {
                    mIsNoleggiato = 0;
                    controlloNoleggio();
                    ContentValues vValue = new ContentValues();
                    vValue.put(LibriTableHelper.IS_NOLEGGIATO, mIsNoleggiato);

                    getContentResolver().update(Uri.parse(LibriMovimentiProvider.LIBROS_URI + "/" + mId), vValue, null, null);

                    String vDataNoleggio;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                    vDataNoleggio = sdf.format(new Date());

                    ContentValues vValueMovimento = new ContentValues();
                    vValueMovimento.put(MovimentiTableHelper.ID_LIBRO, mId);
                    vValueMovimento.put(MovimentiTableHelper.DATA_ORA, vDataNoleggio);
                    vValueMovimento.put(MovimentiTableHelper.IS_USCITO, 0);

                    getContentResolver().insert(LibriMovimentiProvider.MOVIMENTOS_URI, vValueMovimento);
                    finish();
                }
            }
        });

        btnStoricoMovimenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vIntent = new Intent(Mod01.this, List02.class);
                Bundle vBundle = new Bundle();

                vBundle.putInt("_ID", mId);
                vIntent.putExtras(vBundle);
                startActivity(vIntent);
            }
        });

    }

    private void controlloNoleggio() {
        if (mIsNoleggiato == 0) {
            btnRientraLibro.setVisibility(View.GONE);
            btnNoleggiaLibro.setVisibility(View.VISIBLE);
        } else {
            btnNoleggiaLibro.setVisibility(View.GONE);
            btnRientraLibro.setVisibility(View.VISIBLE);
        }

    }
}