package com.example.myapplicationlist;

import static com.example.myapplicationlist.R.id.txt1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationlist.db.DbHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class NuevoActivity extends AppCompatActivity {
    View view1;
    Button btn;
    EditText text1;
    EditText text2;
    ListView lstView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        view1 = findViewById(R.id.view1);
        btn = findViewById(R.id.btn);
        text1 = findViewById(R.id.txt1);
        text2 = findViewById(R.id.txt2);
        lstView1 = findViewById(R.id.LstView1);
    }
    private void llenarGrid() {

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {

            if (db != null) {
                Date currentTime = Calendar.getInstance().getTime();
                // db.execSQL("delete from curso");

                Cursor lscursoDB = db.rawQuery("select * from Curso", null);


                List<String> lstdeString = new ArrayList();
                List<Curso> langListP = new ArrayList<Curso>();

                Curso cur = new Curso();
                if (lscursoDB.moveToFirst()) {
                    do {
                        cur = new Curso();
                        cur.ID = lscursoDB.getInt(0);
                        cur.NubeID = lscursoDB.getInt(1);
                        cur.Columna1 = lscursoDB.getString(2);
                        cur.Columna2 = lscursoDB.getString(3);
                        cur.Estatus = lscursoDB.getInt(4);
                        cur.Fecha = lscursoDB.getString(5);

                        langListP.add(cur);

                        lstdeString.add(cur.Columna1 + "-" + cur.Columna2 + "-" + cur.Estatus + "-" + cur.NubeID);

                    } while (lscursoDB.moveToNext());
                }



                ArrayAdapter adapterxx = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, lstdeString);
                lstView1.setAdapter(adapterxx);
                //Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
            }

            Integer ssss = 10;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.close();
        }
    }

    public void onClick(View v)
    {

        DbHelper dbHelper = new DbHelper(this);
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db != null) {

                Date currentTime = Calendar.getInstance().getTime();
                String Datefrt = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(currentTime).toString();

                ContentValues values = new ContentValues();
                values.put("Columna1", text1.getText().toString());
                values.put("Columna2", text2.getText().toString());
                values.put("Estatus", 1 );
                values.put("Fecha",Datefrt);

                Long id = db.insert(dbHelper.NombreTabla,null,values );

                Toast.makeText(this, Datefrt, Toast.LENGTH_SHORT).show();

                Cursor lscursoDB = db.rawQuery("select * from Curso", null);


                List<String> lstdeString = new ArrayList();
                List<Curso> langListP = new ArrayList<Curso>();

                Curso cur = new Curso();
                if (lscursoDB.moveToFirst()) {
                    do {
                        cur = new Curso();
                        cur.ID = lscursoDB.getInt(0);
                        cur.NubeID = lscursoDB.getInt(1);
                        cur.Columna1 = lscursoDB.getString(2);
                        cur.Columna2 = lscursoDB.getString(3);
                        cur.Estatus = lscursoDB.getInt(4);
                        cur.Fecha = lscursoDB.getString(5);

                        langListP.add(cur);

                        lstdeString.add(cur.Columna1 + "-" + cur.Columna2 + "-" + cur.Estatus + "-" + cur.NubeID);

                    } while (lscursoDB.moveToNext());
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        llenarGrid();
    }
}