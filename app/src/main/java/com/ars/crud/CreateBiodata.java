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
import android.widget.EditText;
import android.widget.Toast;

import com.ars.crud.MainActivity;
import com.ars.crud.R;
import com.ars.crud.SQLHelper;

public class CreateBiodata extends AppCompatActivity {
    protected Cursor cursor;
    SQLHelper database;
    Button btn_simpan, btn_back;
    EditText nomor, nama, tgl, jenisKelamin, alamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_biodata);

        btn_back = (Button)findViewById(R.id.btn_kembali);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(CreateBiodata.this, MainActivity.class);
                startActivity(intentBack);
            }
        });

        database = new SQLHelper(this);
        nomor = findViewById(R.id.nomor);
        nama = findViewById(R.id.nama);
        tgl = findViewById(R.id.tgl);
        jenisKelamin = findViewById(R.id.jenisKelamin);
        alamat = findViewById(R.id.alamat);
        btn_simpan = findViewById(R.id.btn_simpan);

//        nama.setText("coba");
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = database.getWritableDatabase();
                String putNama = nama.getText().toString();
                Log.d("data", "pea1 : "+putNama);
//                db.execSQL("INSERT INTO biodata (nim, nama, tgl, jk, alamat) values ('1', 'mf', '2020','l' , 'bdg')");
//              Log.d("data", "data nim "+nomor.getText().toString());
                db.execSQL("INSERT INTO biodata (nim, nama, tgl, jk, alamat) values('" +
                        nomor.getText().toString() + "','" +
                        nama.getText().toString() + "','" +
                        tgl.getText().toString() + "','" +
                        jenisKelamin.getText().toString() + "','" +
                        alamat.getText().toString() + "')");
                Toast.makeText(CreateBiodata.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                MainActivity.ma.RefreshList();
                finish();
            }
        });

    }
}