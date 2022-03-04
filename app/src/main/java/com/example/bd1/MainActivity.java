package com.example.bd1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tvInicio;
    private String DBname="DB_Alumnos";
    private SQLiteDatabase dbalumnos; //El objeto bd propiamente dicho
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInicio=findViewById(R.id.tvInicio);

        //Instanciamos un objeto de la clase auxiliar que hemos creado
        //(clase que hereda de SQLiteOpenHelper)
        SQLiteOpenHelper auxiliar=new SQLiteOpenHelper(this,DBname,null,1);
        //invoco el método de apertura correspondiente (getReadableDatabase o getWritableDatabase)
        dbalumnos=auxiliar.getWritableDatabase();
        //Inserción directa. Con execSQL --NO PUEDEN HACERSE CONSULTAS-- se haría con rawQuery o query
        dbalumnos.execSQL("INSERT INTO alumnos (codigo, nombre) VALUES (2, 'Nadie')"); //habría que try-catch por si 2 existe
        //Es mejor hacer la inserción con método específico con objeto ContentValues
        ContentValues nuevaTupla=new ContentValues();
        nuevaTupla.put("codigo",3);
        nuevaTupla.put("nombre","Juana");
        //insert devuelve un long con el resultado de la operación
        long l=dbalumnos.insert("alumnos",null,nuevaTupla);
        if (l!=0){ //Inserción correcta
            Toast.makeText(this, "Inserción OK!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Inserción... o más bien no.", Toast.LENGTH_SHORT).show();
        }
        //Borrado directo
        dbalumnos.execSQL("DELETE FROM alumnos WHERE codigo=2");
        //Borrado con método específico
        dbalumnos.delete("alumnos","codigo=1",null);

        //Update directo
        dbalumnos.execSQL("UPDATE alumnos SET nombre='JUUUANA' WHERE codigo=3");
        //Update con método específico, necesitamos un ContentValues con los cambios
        ContentValues tuplaModificada=new ContentValues();
        tuplaModificada.put("nombre","Juana :D");
        dbalumnos.update("alumnos", tuplaModificada, "codigo=3", null);

        //Unos inserts para contar con ellos en las consultas:
        dbalumnos.execSQL("INSERT INTO alumnos (codigo, nombre) VALUES (5, 'Juana')");
        dbalumnos.execSQL("INSERT INTO alumnos (codigo, nombre) VALUES (6, 'Juana')");
        dbalumnos.execSQL("INSERT INTO alumnos (codigo, nombre) VALUES (7, 'Juana')");
        dbalumnos.execSQL("INSERT INTO alumnos (codigo, nombre) VALUES (8, 'JUANA')");

        //Consulta directa con rawQuery
        Cursor cursor=dbalumnos.rawQuery("SELECT nombre FROM alumnos WHERE codigo=3",null);
        if(cursor.moveToFirst()){ //Si hay algo en la primera posición del resultado
            String strNombre=cursor.getString(0); //Posición 0 porque solo recuperamos el nombre
            Toast.makeText(this, "La tercera es "+strNombre, Toast.LENGTH_SHORT).show();
            //Recordar que varios Toast en api 27 se pisan
            tvInicio.setText("La tercera es "+strNombre);
        }else{ //Resultado vacío
            Toast.makeText(this, "No he encontrado nada.", Toast.LENGTH_SHORT).show();
        }
        //HAY que cerrar el cursor
        cursor.close();

        //Consulta con método específico -> query
        String[] datosARecuperar={"nombre"};
        Cursor cursor2=dbalumnos.query("alumnos", datosARecuperar, "codigo=8",null,null,null,null);
        if(cursor2.moveToFirst()){ //Hay resultado
            String strNombre2=cursor2.getString(0); //Posición 0 porque solo recuperamos el nombre
            Toast.makeText(this, "La octava es "+strNombre2, Toast.LENGTH_SHORT).show();
            //Recordar que varios Toast en api 27 se pisan
            tvInicio.append("\nLa octava es "+strNombre2);
        }else{ //Resultado vacío
            Toast.makeText(this, "No he encontrado nada.", Toast.LENGTH_SHORT).show();
        }
        //HAY que cerrar el cursor
        cursor2.close();
    }
}