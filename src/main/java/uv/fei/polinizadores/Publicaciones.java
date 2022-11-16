package uv.fei.polinizadores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import uv.fei.bussinesslogic.PublicacionDAO;
import uv.fei.domain.Publicacion;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Publicaciones implements Initializable {
    public ComboBox comboEstado;
    public TableView<Publicacion> tablaPublicaciones;
    public TableColumn<Publicacion, String> columnaNombretable;
    public TableColumn<Publicacion, Integer> columnaId;
    public TableColumn<Publicacion, CheckBox> columnaEstado;
    @FXML
    private TextField textFieldSearch;
    public Button btnGuardar;
    private List<Publicacion> listaPublicaciones = new ArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        llenarListaPublicaciones();
        //llenarTabla();
    }
    @FXML
    void comboEstadoOnAction(ActionEvent event) {

    }

    public void clicGuardar(ActionEvent actionEvent) {
        /*boolean flag = false;
        boolean message = true;
        boolean unassignation = false;
        String studentStaffNumber = "";
        PublicacionDAO publicacionDAO = new PublicacionDAO();
        Publicacion publicacion = new Publicacion();

        for (int i = 0; i < tablaPublicaciones.getItems().size(); i++){
            unassignation = tablaPublicaciones.getItems().get(i).isEstado();
            publicacion = tablaPublicaciones.getSelectionModel().getSelectedItem();
            //Metodo para guardar las publicaciones
            try {
                publicacionDAO.actualizarPublicacion(publicacion);
            } catch (SQLException e) {
                System.out.println("No se pudo actualizar la publicacion con id "+publicacion.getId());
            }
        }*/
    }

    private void llenarListaPublicaciones(){
        PublicacionDAO publicacionDAO = new PublicacionDAO();
        try {
            listaPublicaciones = publicacionDAO.obtenerPublicaciones();
            columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnaNombretable.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            columnaEstado.setCellValueFactory(new PropertyValueFactory<Publicacion, CheckBox>("estado"));
            columnaEstado.setEditable(true);

            ObservableList<Publicacion> observableList = FXCollections.observableList(listaPublicaciones);
            FilteredList<Publicacion> filteredData = new FilteredList<>(observableList, b -> true);

            textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(publicaciones -> {
                    if (newValue == null || newValue.isEmpty()) {
                        llenarListaPublicaciones();
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (publicaciones.getTitulo().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                        return true;
                    } else{
                        return false;
                    }
                });
            });
            SortedList<Publicacion> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tablaPublicaciones.comparatorProperty());
            tablaPublicaciones.setItems(sortedData);
        } catch (SQLException e) {
            System.out.println("No se puede acceder a la base de datos");
        }
    }

    private void llenarTabla(){
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaNombretable.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnaEstado.setCellValueFactory(new PropertyValueFactory<Publicacion, CheckBox>("estado"));
        columnaEstado.setEditable(true);

        ObservableList<Publicacion> observableList = FXCollections.observableList(listaPublicaciones);
        tablaPublicaciones.setItems(observableList);
        tablaPublicaciones.setEditable(false);
    }
    @FXML
    void buttonBackClic(ActionEvent event) {
        openWindowMenu("MuroDePublicaciones.fxml", "Muro de Publicaciones");
    }
    private void openWindowMenu(String fxml, String name){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
            stage.setTitle(name);
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
}