package com.ars.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ReadBiodata extends AppCompatActivity {
    protected Cursor cursor;
    SQLHelper database;
    private Button btn_back;
    TextView nim, nama, tgl, jenisKelamin, alamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_read_biodata);

        btn_back = (Button)findViewById(R.id.button1);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(ReadBiodata.this, MainActivity.class);
                startActivity(intentBack);
            }
        });

        database = new SQLHelper(this);
        nim = findViewById(R.id.NIM);
        nama = findViewById(R.id.nama);
        tgl = findViewById(R.id.tgl);
        jenisKelamin = findViewById(R.id.jenisKelamin);
        alamat = findViewById(R.id.alamat);

        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata WHERE id = '" +
                getIntent().getStringExtra("id")+"'", null);
        Log.d("Data", "hasil : " + cursor.getCount());
        cursor.moveToFirst();
        if (cursor.getCount() >0){
            cursor.moveToPosition(0);
            nim.setText(cursor.getString(1).toString());
            nama.setText(cursor.getString(2).toString());
            tgl.setText(cursor.getString(3).toString());
            jenisKelamin.setText(cursor.getString(4).toString());
            alamat.setText(cursor.getString(5).toString());
        }
    }
}