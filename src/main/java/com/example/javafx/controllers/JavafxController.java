package com.example.javafx.controllers;


import com.example.javafx.entities.Libro;
import com.example.javafx.service.LibroService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component @Data
public class JavafxController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableColumn<Libro, Integer> colAnio;

    @FXML
    private TableColumn<Libro, String> colAutor;

    @FXML
    private TableColumn<Libro, String> colEditorial;

    @FXML
    private TableColumn<Libro, Integer> colEjemPrest_;

    @FXML
    private TableColumn<Libro, Integer> colEjemRest_;

    @FXML
    private TableColumn<Libro, Integer> colEjemplares;

    @FXML
    private TableColumn<Libro, String> colID;

    @FXML
    private TableColumn<Libro, Integer> colISBN;

    @FXML
    private TableColumn<Libro, String> colTitle;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtEditorial;

    @FXML
    private TextField txtEjempPrest;

    @FXML
    private TextField txtEjempResta;

    @FXML
    private TextField txtEjemplares;

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtTitulo;

    @FXML
    private TableView<Libro> tblLibros;

    @Autowired
    LibroService libroService;

    @FXML
    void agregarDatosLibro(ActionEvent event) {
        agregarDatosLibro();
    }

    void agregarDatosLibro(){
        try{
            Libro libro = new Libro();
            libro.setTitulo(txtTitulo.getText());
            libro.setIsbn(Integer.valueOf(txtISBN.getText()));
            libro.setAnio(Integer.valueOf(txtAnio.getText()));
            libro.setEjemplares(Integer.valueOf(txtEjemplares.getText()));
            libro.setEjemplaresPrestados(Integer.valueOf(txtEjempPrest.getText()));
            libro.setEjemplaresRestantes(Integer.valueOf(txtEjempResta.getText()));
            libro.setAlta(false);
            libro.setAutorNombre(txtAutor.getText());
            libro.setEditorialNombre(txtEditorial.getText());
            libroService.guardarDatosLibro(libro);
            actualizarTabla();
            limpiarCampo();
        }catch (NumberFormatException e){
            System.out.println("Se ingreso un tipo de dato incorrecto.");
        }catch (Exception e){
            System.out.println("Ocurrio un error");
        }
    }



    @FXML
    void eliminarDatosLibro(ActionEvent event) {
        eliminarDatosLibro();
    }

    void eliminarDatosLibro(){
        Libro libro = tblLibros.getSelectionModel().getSelectedItem();
        libroService.eliminarDatosLibro(libro);
        actualizarTabla();
        limpiarCampo();
    }


    @FXML
    void actualizarDatosLibro(ActionEvent event) {

    }



    void actualizarTabla(){
        tblLibros.getItems().clear();
        tblLibros.getItems().addAll(libroService.getRepository().findAll());
        tblLibros.refresh();
    }
    void limpiarCampo(){
        txtISBN.setText("");
        txtTitulo.setText("");
        txtAnio.setText("");
        txtEjemplares.setText("");
        txtEjempResta.setText("");
        txtEjempPrest.setText("");
        txtAutor.setText("");
        txtEditorial.setText("");

    }

    private void cargarCampo() {
        Libro libro = tblLibros.getSelectionModel().getSelectedItem();
        txtISBN.setText(libro.getIsbn().toString());
        txtTitulo.setText(libro.getTitulo());
        txtAnio.setText(libro.getAnio().toString());
        txtEjemplares.setText(libro.getEjemplares().toString());
        txtEjempPrest.setText(libro.getEjemplaresPrestados().toString());
        txtEjempResta.setText(libro.getEjemplaresRestantes().toString());
        txtEditorial.setText(libro.getEditorialNombre());
        txtAutor.setText(libro.getAutorNombre());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory( new PropertyValueFactory<>("id"));
        colISBN.setCellValueFactory( new PropertyValueFactory<>("isbn"));
        colTitle.setCellValueFactory( new PropertyValueFactory<>("titulo"));
        colAnio.setCellValueFactory( new PropertyValueFactory<>("anio"));
        colEjemplares.setCellValueFactory( new PropertyValueFactory<>("ejemplares"));
        colEjemPrest_.setCellValueFactory( new PropertyValueFactory<>("ejemplaresPrestados"));
        colEjemRest_.setCellValueFactory( new PropertyValueFactory<>("ejemplaresRestantes"));
        colAutor.setCellValueFactory( new PropertyValueFactory<>("autorNombre"));
        colEditorial.setCellValueFactory( new PropertyValueFactory<>("editorialNombre"));

        tblLibros.setOnMouseClicked(mouseEvent -> {
            if(tblLibros.getSelectionModel().getSelectedItem() != null) cargarCampo();
        });
    }
}
