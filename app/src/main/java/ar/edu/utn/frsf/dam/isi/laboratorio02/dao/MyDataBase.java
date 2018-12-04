package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class MyDataBase {
    // variable de clase privada que almacena una instancia unica de esta entidad
    private static MyDataBase _INSTANCIA_UNICA=null;

    // metodo static publico que retorna la unica instancia de esta clase
    // si no existe, cosa que ocurre la primera vez que se invoca
    // la crea, y si existe retorna la instancia existente.

    public static MyDataBase getInstance(Context ctx){
        if(_INSTANCIA_UNICA==null) _INSTANCIA_UNICA = new MyDataBase(ctx);
        return _INSTANCIA_UNICA;
    }

    private Gestor db;
    private DAOCategoria daoCategoria;

    // constructor privado para poder implementar SINGLETON
    // al ser privado solo puede ser invocado dentro de esta clase
    // el único lugar donde se invoca es en la linea 16 de esta clase
    // y se invocará UNA Y SOLO UNA VEZ, cuando _INSTANCIA_UNICA sea null
    // luego ya no se invoca nunca más. Nos aseguramos de que haya una
    // sola instancia en toda la aplicacion
    private MyDataBase(Context ctx){
        db = Room.databaseBuilder(ctx,
                Gestor.class, "DBLab04B")
                .fallbackToDestructiveMigration()
                .build();
        daoCategoria = db.daoCategoria();

    }
    public DAOCategoria getCategoriaDao() {
        return daoCategoria;
    }
}
