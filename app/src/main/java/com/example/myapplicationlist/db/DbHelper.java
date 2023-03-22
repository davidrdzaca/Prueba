package com.example.myapplicationlist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplicationlist.MainActivity;

import java.util.Calendar;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {

    private static final Integer version = 1;
    private static final String dbNombre = "Tareas";
    public static  final String NombreTabla = "Curso";

    public static Context context;

    public DbHelper(@Nullable Context context) {
        super(context, dbNombre, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table "+NombreTabla+"" +
                "(Id Integer primary key autoincrement," +
                "[NubeID] Integer ," +
                "[Columna1] text ," +
                "[Columna2] text ," +
                "[Estatus] Integer ," +
                "[Fecha] text )"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table " + NombreTabla);

        onCreate(sqLiteDatabase);
    }


}
