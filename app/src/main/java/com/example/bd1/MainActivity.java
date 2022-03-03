package com.example.bd1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private String DBname="DB_Alumnos";
    private SQLiteDatabase dbalumnos; //El objeto bd propiamente dicho
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciamos un objeto de la clase auxiliar que hemos creado
        //(clase que hereda de SQLiteOpenHelper)
        SQLiteOpenHelper auxiliar=new SQLiteOpenHelper(this,DBname,null,1);
        //invoco el método de apertura correspondiente (getReadableDatabase o getWritableDatabase)
        dbalumnos=auxiliar.getWritableDatabase();
        //Inserción directa
        dbalumnos.execSQL("INSERT INTO alumnos (codigo, nombre) VALUES (2, 'Nadie')");
        //Es mejor hacer la inserción con método específico con objeto ContentValues
        ContentValues nuevaTupla=new ContentValues();
        nuevaTupla.put("codigo",3);
        nuevaTupla.put("nombre","Juana");
        //insert devuelve un long con el resultado de la operación
        long l=dbalumnos.insert("alumnos",null,nuevaTupla);
        if (l!=0){
            //Inserción correcta

        }
    }
}