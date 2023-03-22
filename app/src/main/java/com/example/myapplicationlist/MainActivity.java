package com.example.myapplicationlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplicationlist.Adaptadores.ListaAdapter;
import com.example.myapplicationlist.db.DbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.ArrayList;

import java.util.List;

import java.util.HashMap;
import java.util.Map;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    View view1;
    Button btn;
    JSONArray lstarr;

    ListView lstView1;
    RecyclerView rlstView;

    ListaAdapter radapter;
    ArrayList<Curso> langListG = new ArrayList<Curso>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view1 = findViewById(R.id.view1);
        //btn = findViewById(R.id.button2);

        //lstView1 = findViewById(R.id.LstView1);
        rlstView = findViewById(R.id.rlstView);
        rlstView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

/*
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);
*/

        Field[] fields = Curso.class.getFields(); // returns inherited members but not private members.
        Field[] field2s = Curso.class.getDeclaredFields();

        Object fieeeee = fields[0].getType();

        llenarGrid();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuNuevo:
                nuevoRegistro();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void nuevoRegistro() {
        Intent intent = new Intent(this, NuevoActivity.class);
        startActivity(intent);
    }

    private void LeerWs() {
        String url = Configuracion.url + "api/clase";

        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //JSONObject jsonObject = new JSONObject(response);
                    JSONArray arr = new JSONArray(response);

                    String jstring = arr.toString();

                    List<Person> langListP = new ArrayList<Person>();
                    Person p = new Person();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObject = arr.getJSONObject(i);
                        p.Nombre = jsonObject.getString("Nombre");
                        p.Apellido = jsonObject.getString("Apellido");

                        langListP.add(p);
                    }

                    List<Person> langList = new ArrayList<Person>();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postResquest);


    }

    private void enviarWs() {

        String url = Configuracion.url + "api/clase";

        StringRequest postResquest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    //JSONObject jsonObject = new JSONObject(response);
                    JSONArray arr = new JSONArray(response);
                    lstarr = arr;
                    String jstring = arr.toString();
                    List<String> lstdeString = new ArrayList();

                    List<Curso> langListP = new ArrayList<Curso>();
                    Curso p = new Curso();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObject = arr.getJSONObject(i);
                        p.NubeID = Integer.parseInt(jsonObject.getString("NubeID"));
                        p.Columna1 = jsonObject.getString("Columna1");
                        p.Columna2 = jsonObject.getString("Columna2");
                        p.Estatus = Integer.parseInt(jsonObject.getString("Estatus"));

                        langListP.add(p);

                        lstdeString.add(p.Columna1);
                    }


                    ArrayAdapter adapterxx = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, lstdeString);
                    lstView1.setAdapter(adapterxx);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(MainActivity.this, "RESULTADO POST = " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Error", error.getMessage());
                error.printStackTrace();
            }
        }) {


            protected Map<String, String> getParams() {
                lstarr = new JSONArray();
                JSONArray inarr = new JSONArray();
                JSONObject jsonObject;
                JSONObject injsonObject;
                for (int i = 0; i < 3; i++) {
                    try {
                        jsonObject = new JSONObject();
                        jsonObject.put("Columna1", "prueb" + String.valueOf(i));
                        jsonObject.put("Columna2", "prueb" + String.valueOf(i));
                        jsonObject.put("Estatus", "0");


                        for (int ii = 0; ii < 1; ii++) {
                            try {
                                injsonObject = new JSONObject();
                                injsonObject.put("Columna1", "prueb" + String.valueOf(i));
                                injsonObject.put("Columna2", "prueb" + String.valueOf(i));
                                injsonObject.put("Estatus", "3");
                                inarr.put(injsonObject);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        String inst = inarr.toString();
                        jsonObject.put("lst", inst);

                        lstarr.put(jsonObject);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Map<String, String> params = new HashMap<>();


                params.put("Columna1", lstarr.toString());


                return params;


            }

        };

        Volley.newRequestQueue(this).add(postResquest);
    }

    public void onClick(View v) {

       List<Curso> lstcursos = llenarList();
        UpdateNube(lstcursos);

        llenarGrid();
    }

    private void llenarGrid() {

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {

            if (db != null) {
                Date currentTime = Calendar.getInstance().getTime();
                // db.execSQL("delete from curso");

                Cursor lscursoDB = db.rawQuery("select * from Curso", null);


                ArrayList<String> lstdeString = new ArrayList();
                ArrayList<Curso> langListP = new ArrayList<Curso>();

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


                langListG = langListP;
              /*  ArrayAdapter adapterxx = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, lstdeString);
                lstView1.setAdapter(adapterxx);*/

                ListaAdapter adapter = new ListaAdapter(langListP);
                rlstView.setAdapter(adapter);

                //Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
            }

            Integer ssss = 10;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.close();
        }
    }

    private List<Curso> llenarList() {

        List<Curso> langListP = new ArrayList<Curso>();

        DbHelper dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            if (db != null) {
                Date currentTime = Calendar.getInstance().getTime();


                Cursor lscursoDB = db.rawQuery("select * from curso where Estatus=1", null);

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


                    } while (lscursoDB.moveToNext());
                }




                //Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
            }

            Integer ssss = 10;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return langListP;
    }

    private void UpdateNube(List<Curso> lstcursos) {

        DbHelper dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String url = Configuracion.url + "api/clase";

        StringRequest postResquest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    //JSONObject jsonObject = new JSONObject(response);
                    JSONArray arr = new JSONArray(response);
                    lstarr = arr;
                    String jstring = arr.toString();
                    List<String> lstdeString = new ArrayList();

                    List<Curso> langListP = new ArrayList<Curso>();
                    Curso p = new Curso();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObject = arr.getJSONObject(i);
                        p.ID = Integer.parseInt(jsonObject.getString("ID"));
                        p.NubeID = Integer.parseInt(jsonObject.getString("NubeID"));
                        p.Columna1 = jsonObject.getString("Columna1");
                        p.Columna2 = jsonObject.getString("Columna2");
                        p.Estatus = Integer.parseInt(jsonObject.getString("Estatus"));

                        langListP.add(p);
                        if (p.Estatus == 0) {
                            db.execSQL("update curso set NubeID=" + p.NubeID + ", Estatus=0  where ID=" + p.ID);
                        } else if (p.Estatus == 1){
                            Date currentTime = Calendar.getInstance().getTime();
                            String Datefrt = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(currentTime).toString();

                            ContentValues values = new ContentValues();
                            values.put("NubeID", p.ID);
                            values.put("Columna1", p.Columna1);
                            values.put("Columna2", p.Columna1);
                            values.put("Estatus", 1);
                            values.put("Fecha", Datefrt);

                            Long id = db.insert(dbHelper.NombreTabla, null, values);

                        Integer ins = 0;
                        }
                        lstdeString.add(p.Columna1);
                    }


                    /*ArrayAdapter adapterxx = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, lstdeString);
                    lstView1.setAdapter(adapterxx);*/

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    db.close();
                }

                //Toast.makeText(MainActivity.this, "RESULTADO POST = " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Error", error.getMessage());
                error.printStackTrace();
            }
        }) {


            protected Map<String, String> getParams() {
                lstarr = new JSONArray();
                JSONArray inarr = new JSONArray();
                JSONObject jsonObject;
                JSONObject injsonObject;
                for (int i = 0; i < lstcursos.size(); i++) {
                    try {
                        Curso c = lstcursos.get(i);

                        jsonObject = new JSONObject();
                        jsonObject.put("NubeID", c.NubeID);
                        jsonObject.put("ID", c.ID);
                        jsonObject.put("Columna1", c.Columna1);
                        jsonObject.put("Columna2", c.Columna2);



                       /* for (int ii = 0; ii < 1; ii++) {
                            try {
                                injsonObject = new JSONObject();
                                injsonObject.put("Columna1", "prueb" + String.valueOf(i));
                                injsonObject.put("Columna2", "prueb" + String.valueOf(i));
                                injsonObject.put("Estatus", "3");
                                inarr.put(injsonObject);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        String inst = inarr.toString();
                        jsonObject.put("lst", inst);*/

                        lstarr.put(jsonObject);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Map<String, String> params = new HashMap<>();


                params.put("Columna1", lstarr.toString());


                return params;


            }

        };

        Volley.newRequestQueue(this).add(postResquest);

    }

    public void insertCurso() {
        //enviarWs();
        DbHelper dbHelper = new DbHelper(MainActivity.this);
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db != null) {
                Date currentTime = Calendar.getInstance().getTime();
                String Datefrt = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(currentTime).toString();
                ContentValues values = new ContentValues();
                values.put("Columna1", "");
                values.put("Columna2", "test");
                values.put("Estatus", 0);
                values.put("Fecha", Datefrt);

                Long id = db.insert(dbHelper.NombreTabla, null, values);

                Toast.makeText(this, Datefrt, Toast.LENGTH_SHORT).show();
            }

            Integer ssss = 10;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
