package com.ars.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ars.crud.SQLHelper;

public class UpdateBiodata extends AppCompatActivity {
    protected Cursor cursor;
    SQLHelper database;
    Button btn_update, btn_back;
    EditText nim, xNama, tgl,  alamat;
    String jenisKelamin;
    RadioGroup radioGrup1;
    RadioButton radiobutton1, rdLaki, rdCewe;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_update_biodata);

        btn_back = (Button)findViewById(R.id.btn_kembali);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(UpdateBiodata.this, MainActivity.class);
                startActivity(intentBack);
            }
        });

        database = new SQLHelper(this);
        nim = findViewById(R.id.nim);
        xNama = findViewById(R.id.nama);
        tgl = findViewById(R.id.tgl);
        rdLaki = findViewById(R.id.radio_laki);
        rdCewe = findViewById(R.id.radio_cewe);
//        jenisKelamin = findViewById(R.id.jenisKelamin);
        alamat = findViewById(R.id.alamat);
        btn_update = findViewById(R.id.btn_update);

        SQLiteDatabase db = database.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM biodata WHERE id = '" +
                getIntent().getStringExtra("id")+"'", null);
        String getNIM = getIntent().getStringExtra("id");
        Log.d("nama", "cekNama : "+getNIM);
        Log.d("Data", "hasilUpdate : " + cursor.getCount());

        cursor.moveToFirst();
        if (cursor.getCount() >0){
            cursor.moveToPosition(0);
            nim.setText(cursor.getString(1).toString());
            xNama.setText(cursor.getString(2).toString());
            tgl.setText(cursor.getString(3).toString());
            jenisKelamin = cursor.getString(4).toString();

            String equ = rdLaki.getText().toString();
            if (jenisKelamin.equals("Laki - Laki")){
                rdLaki.setChecked(true);
            }else {
                rdCewe.setChecked(true);
            }

            alamat.setText(cursor.getString(5).toString());
        }
        String putNama = xNama.getText().toString();


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jk = "";
                if (rdLaki.isChecked()){
                    jk+= "Laki - Laki";
                }
                if (rdCewe.isChecked()){
                    jk+= "Perempuan";
                }
                Log.d("nama", "cekNama1 : "+getNIM);

                SQLiteDatabase db = database.getWritableDatabase();
//                db.execSQL("UPDATE biodata SET nim = '" +
//                        nim.getText().toString()+"', nama = '" +
//                        xNama.getText().toString()+"', tgl = '" +
//                        tgl.getText().toString()+"', jk = '" +
//                        jenisKelamin.getText().toString()+"', '" +
//                        alamat.getText().toString()+"' WHERE id = '" +
//                        getNIM.toString()+"'");
                db.execSQL("UPDATE biodata SET nim = '" +
                        nim.getText().toString()+"', nama = '" +
                        xNama.getText().toString()+"', tgl = '" +
                        tgl.getText().toString()+"', jk = '" +
                        jk+"', alamat = '" +
                        alamat.getText().toString()+"' WHERE id = '" +
                        getIntent().getStringExtra("id")+"'");
                Log.d("data", "peasn : "+xNama.getText().toString());
//                Log.d("data", "peasn : "+xNama.getText().toString());
                Toast.makeText(UpdateBiodata.this, "Data Berhasil Di Update", Toast.LENGTH_SHORT).show();
                MainActivity.ma.RefreshList();
                finish();
            }
        });
    }
}