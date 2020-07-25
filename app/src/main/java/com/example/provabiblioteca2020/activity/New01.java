package com.example.provabiblioteca2020.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.provabiblioteca2020.R;
import com.example.provabiblioteca2020.data.LibriMovimentiProvider;
import com.example.provabiblioteca2020.data.LibriTableHelper;

public class New01 extends AppCompatActivity {

    EditText etNuovoTitolo, etNuovoAutore;
    Button btnSalva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new01);
        getSupportActionBar().setTitle("New01");

        etNuovoTitolo = findViewById(R.id.etTitoloNew);
        etNuovoAutore = findViewById(R.id.etAutoreNew);
        btnSalva = findViewById(R.id.btnSalva);

        btnSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titoloLibro = etNuovoTitolo.getText().toString();
                String autoreLibro = etNuovoAutore.getText().toString();

                if(titoloLibro.isEmpty() || autoreLibro.isEmpty())
                {
                    Toast.makeText(New01.this, "I campi Titolo e Autore devono essere compilati", Toast.LENGTH_LONG).show();
                }
                else
                {
                    ContentValues vValue = new ContentValues();
                    vValue.put(LibriTableHelper.TITOLO, titoloLibro);
                    vValue.put(LibriTableHelper.AUTORE, autoreLibro);
                    vValue.put(LibriTableHelper.IS_NOLEGGIATO, ""); //false

                    getContentResolver().insert(LibriMovimentiProvider.LIBROS_URI, vValue);
                    finish();
                }
                finish();
            }
        });
    }
}