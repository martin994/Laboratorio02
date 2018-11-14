package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
@Database(entities = {Categoria.class},version = 3)
public abstract class MyDataBase extends RoomDatabase {
    public abstract DAOCategoria daoCategoria();
}
