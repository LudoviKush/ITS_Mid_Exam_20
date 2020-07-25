package com.example.provabiblioteca2020.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.provabiblioteca2020.R;
import com.example.provabiblioteca2020.adapter.ListaMovimentiAdapter;
import com.example.provabiblioteca2020.data.LibriMovimentiProvider;
import com.example.provabiblioteca2020.data.LibriTableHelper;

public class Home01 extends AppCompatActivity {

    Button btnListaLibri;
    Button btnInserisciLibro;
    TextView numeroLibri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home01);
        getSupportActionBar().setTitle("Home01");

        btnListaLibri = findViewById(R.id.btnListaLibri);
        btnInserisciLibro = findViewById(R.id.btnInserisciLibro);
        numeroLibri = findViewById(R.id.tvLibri);

        btnListaLibri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vIntent = new Intent(Home01.this, List01.class);
                startActivity(vIntent);
            }
        });

        btnInserisciLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vIntent = new Intent(Home01.this, New01.class);
                startActivity(vIntent);
            }
        });
    }

    private void mostraNumeroLibri() {
        int vLibriTotali, vNoleggi;

        Cursor vCursorLibri = getContentResolver().query(LibriMovimentiProvider.LIBROS_URI, null, null, null, null);
        vLibriTotali = vCursorLibri.getCount();

        Cursor vCursorNoleggi = getContentResolver().query(LibriMovimentiProvider.LIBROS_URI, null, LibriTableHelper.IS_NOLEGGIATO + " = 1" , null, null);
        vNoleggi = vCursorNoleggi.getCount();

        numeroLibri.setText("Sono presenti " + vLibriTotali + " libri di cui noleggiati " + vNoleggi);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mostraNumeroLibri();
    }
}