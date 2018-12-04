package ar.edu.utn.frsf.dam.isi.laboratorio02.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;
@Dao
public interface DAOProducto {
    @Query("SELECT * FROM Producto")
    List<Producto> getAll();

    @Insert
    long insert(Producto p);
    @Update
    int update(Producto id);
    @Delete
    void delete(Producto id);
    @Query("SELECT * FROM Producto WHERE id=:idProd")
    List<Producto> buscarPorId(long idProd);


}
