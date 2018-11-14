package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Room;
import android.content.Context;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Categoria;

public class CategoriaRepository {
    private static CategoriaRepository _REPO=null;
    private DAOCategoria categoriaDao;

    private  CategoriaRepository(Context ctx){
        MyDataBase db = Room.databaseBuilder(ctx,MyDataBase.class,"CAT-REQ4").fallbackToDestructiveMigration().build();
        categoriaDao= db.daoCategoria();

    }

    public static CategoriaRepository getInstance(Context ctx){
        if(_REPO==null)_REPO= new CategoriaRepository(ctx);
        return _REPO;
    }

    public void crearCategoria (Categoria c){
        categoriaDao.insert(c);

    }

    public void actualizarCategoria(Categoria c){
        categoriaDao.update(c);
    }
}
