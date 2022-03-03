package com.example.bd1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    private String onCreateTable="CREATE TABLE alumnos (codigo INT PRIMARY KEY, nombre TEXT)";
    private String insert1="INSERT INTO alumnos (codigo, nombre) VALUES (1, 'Alguien')";
    //Constructor
    public SQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Sentencias de creación cuando la bd NO EXISTA
        //Creamos la(s) tabla(s)
        sqLiteDatabase.execSQL(onCreateTable);
        //Podriamos insertar datos iniciales
        sqLiteDatabase.execSQL(insert1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //todo operaciones para modificar la bd (estructura) en sucesivas versiones

    }
}
