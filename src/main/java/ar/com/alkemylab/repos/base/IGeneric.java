package ar.com.alkemylab.repos.base;

import java.util.List;

/* Repo Generico para utilizarlo
 en la clase de Service generico*/
public interface IGeneric<T> {

     // crear
     boolean create(T entity);

     // actualizar
     T update(T entity);

     // buscar por Id
     T findById(Integer id);

     // traer los registros de la BD
     List<T> listAll();

     //Eliminarde la BD
     boolean deleteById(Integer id);


}
