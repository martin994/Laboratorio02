package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

@Database(entities = {Categoria.class, Producto.class}, version = 2)
public abstract class Gestor extends RoomDatabase {
    public abstract DAOCategoria daoCategoria();
    public abstract DAOProducto daoProducto();

}
