package com.example.javafx.service;

import com.example.javafx.entities.Libro;
import com.example.javafx.repository.LibroRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
public class LibroService {

    @Autowired
    private LibroRepository repository;

    @Transactional
    public void guardarDatosLibro(Libro libro) {
        try{
            libro.setEjemplaresPrestados(0);
            libro.setEjemplaresRestantes(libro.getEjemplares());
            repository.save(libro);
        }catch (Exception e){
            System.out.println("Ocurrio un error en la capa de servicio. --- guardar datos libro");
        }
    }


    public void actualizarDatosLibro(Libro libro){
        try{
            for(Libro lib : repository.findAll()){
                if(lib.getId().equals(libro.getId())){
                    lib.setTitulo(libro.getTitulo());
                    lib.setIsbn(libro.getIsbn());
                    lib.setAnio(libro.getAnio());
                    lib.setEjemplares(libro.getEjemplares());
                    lib.setEjemplaresPrestados(libro.getEjemplaresPrestados());
                    lib.setEjemplaresRestantes(libro.getEjemplaresRestantes());
                    lib.setEditorialNombre(libro.getEditorialNombre());
                    lib.setAutorNombre(libro.getAutorNombre());
                    repository.save(lib);
                }
            }
        }catch (Exception e){
            System.out.println("Ocurrio un erro en la capa de servicio. --- actualizar datos libro");
        }
    }

    public void eliminarDatosLibro(Libro libro){
        try{
        repository.delete(libro);
        }catch (Exception e){
            System.out.println("Ocurrio un error en la capa de servicio. --- eliminar datos libro");
        }
    }

    private void ejemplaresAPrestar(Libro libro){
        if(libro.getEjemplaresPrestados() > 0 || libro.getEjemplares() >= libro.getEjemplaresPrestados()){
            libro.setEjemplaresRestantes(libro.getEjemplares()-libro.getEjemplaresPrestados());
        }
    }

    private void libroEnAlta(Libro libro){
        if(libro.getEjemplaresRestantes() == 0){
            libro.setAlta(false);
        }
    }

    public void alquilarLibro(Libro libro) {
        if(libro.getAlta()){
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados()+1);
            ejemplaresAPrestar(libro);
            libroEnAlta(libro);
            repository.save(libro);
        }
    }
}
