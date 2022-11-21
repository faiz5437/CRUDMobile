package com.ars.crud;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView cvData;
    String[] daftar, namaShow;
    int[] idItem;
    ListView ListView01;
    protected Cursor cursor;
    SQLHelper dbcenter;
    @SuppressLint("StaticFieldLeak")
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent inte = new Intent(MainActivity.this, CreateBiodata.class);
                startActivity(inte);
            }
        });

        FloatingActionButton fab_hapus = findViewById(R.id.fab_hapus);
        fab_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                    // set title dialog
                    alertDialogBuilder.setTitle("Yakin Ingin HAPUS SEMUA DATA?");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setMessage("Klik Ya untuk Hapus!")
                            .setIcon(R.drawable.ic_warning_hapus)
                            .setCancelable(false)
                            .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    SQLiteDatabase db2 = dbcenter.getWritableDatabase();
                                    db2.execSQL("delete from biodata");
                                    RefreshList();
                                }
                            })
                            .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // jika tombol ini diklik, akan menutup dialog
                                    // dan tidak terjadi apa2
                                    dialog.cancel();
                                }
                            });
                    // membuat alert dialog dari builder
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // menampilkan alert dialog
                    alertDialog.show();
            }
        });
        ma = this;
        dbcenter = new SQLHelper(this);
        RefreshList();
        onResume();
    }


    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata ",null);
//        Log.v("Data", DatabaseUtils.dumpCursorToString(cursor)); vardump dari hasil cursor ( sql ) ke array
        daftar = new String[cursor.getCount()];
        idItem = new int[cursor.getCount()];
        namaShow = new String[cursor.getCount()];
        cursor.moveToFirst();

        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(2);
//            namaShow[cc] = cursor.getString(2);
            Log.d("data", "nama "+cursor.getString(2));
//              arrNim.add(cursor.getString(1));
        }
        ListView01 = findViewById(R.id.listView1);
//        cvData = findViewById(R.id.listView1);
//        Log.d("data : ", "nama : "+namaShow[0]);
//        String name = namaShow[0];
//        ListView01.setAdapter(this, R.layout.list_item, R.id.text_view, daftar));
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
//        ListView01.setAdapter(new ArrayAdapter(this, R.layout.list_item, R.id.tv_id_item, idItem));
//        cvData.setAdapter(new ArrayAdapter(this, arrNama, arrNim)   );

        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
//                String[] getId = new String[cursor.getCount()];
//                for(int c = 0; c < cursor.getCount(); c++){
//                    getId[c] = cursor.getString(0);
//                }
                Log.d("data : ", "nama : "+arg2);
                int[] id_item = new int[cursor.getCount()];
                cursor.moveToFirst();
                for(int x = 0; x < cursor.getCount(); x++){
                    cursor.moveToPosition(x); // urutan row atau baris ke x dari record database
                    id_item[x] = cursor.getInt(0); // urutan nama kolom ke 0 dari database = id
                }
                // disini id item sudah menjadi array dengan length ssuai listview dan sama dengan nama kolom ke 0 dari database
                //.getItemAtPosition(arg2).toString();
                String selection = String.valueOf(id_item[arg2]);
                Log.d("data", "nama "+id_item);
                Log.d("test", "selection "+selection);
                final CharSequence[] dialogitem = {"Lihat Biodata", "Update Biodata", "Hapus Biodata"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), ReadBiodata.class);
                                i.putExtra("id", selection);
//                                i.putExtra("id", getId);
                                startActivity(i);
                                break;
                            case 1 :
                                Intent in = new Intent(getApplicationContext(), UpdateBiodata.class);
                                in.putExtra("id", selection);
//                                in.putExtra("id", getId);
                                startActivity(in);
                                break;
                            case 2 :
                                showDialog(selection);
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        //noinspection rawtypes
        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();
    }
    private void showDialog(String selection){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Yakin Ingin Menghapus?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Ya untuk Hapus!")
                .setIcon(R.drawable.ic_warning_hapus)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        SQLiteDatabase db = dbcenter.getWritableDatabase();
                        db.execSQL("delete from biodata where id = '"+selection+"'");
                        RefreshList();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });
        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

}