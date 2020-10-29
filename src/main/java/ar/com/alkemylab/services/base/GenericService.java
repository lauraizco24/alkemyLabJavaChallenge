package ar.com.alkemylab.services.base;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.alkemylab.repos.base.IGeneric;

//Creo una clase que me servira como un service generico, 
//lo cual me va a permitir reusar estos metodos 
//las veces que los necesite sin tener que volver a crearlos
public class GenericService<T> implements IGeneric<T> {

    @Autowired
    protected JpaRepository<T, Integer> repo;

    // Metodo para crear
    @Override
    public boolean create(T entity) {
        repo.save(entity);
        return true;
    }

    // Metodo para actualizar
    @Override
    public T update(T entity) {
        repo.save(entity);
        return entity;
    }

    // Metodo para buscar por Id
    @Override
    public T findById(Integer id) {
        Optional<T> opT = repo.findById(id);

        if (opT.isPresent())
            return opT.get();
        else
            return null;

    }

    // Metodo para traer una lista de todos los registros
    @Override
    public List<T> listAll() {

        return repo.findAll();
    }
// Metodo para eliminar un registro segun su ID
    @Override
    public boolean deleteById(Integer id) {
        if (findById(id) != null) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    
   

}
