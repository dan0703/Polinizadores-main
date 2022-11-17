package uv.fei.polinizadores;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import uv.fei.bussinesslogic.PublicacionDAO;
import uv.fei.domain.Publicacion;
import uv.fei.domain.TablaPublicaciones;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Publicaciones implements Initializable {
    public TableView<TablaPublicaciones> tablaPublicaciones;
    public TableColumn<TablaPublicaciones, String> columnaNombretable;
    public TableColumn<TablaPublicaciones, Integer> columnaId;
    public TableColumn<TablaPublicaciones, String> columnaEstado;
    @FXML
    private TableColumn<TablaPublicaciones, String> columnaFecha;

    @FXML
    private TextField textFieldSearch;
    @FXML
    private Button buttonBack;;
    private List<TablaPublicaciones> listaPublicaciones = new ArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        llenarListaPublicaciones();
        tablaPublicaciones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Desea guardar los cambios");
            alert.setContentText("¿Deseas realmente confirmar?");
            Optional<ButtonType> action = alert.showAndWait();
            int estado =0;
            if (action.get()==ButtonType.OK){
                if (tablaPublicaciones.getSelectionModel().getSelectedItem().getEstadoTabla().equals("Publicado")){
                    tablaPublicaciones.getSelectionModel().getSelectedItem().setEstadoTabla("Sin Publicar");
                    JOptionPane.showMessageDialog(null,"El estado se ha cambiado a: Sin publicar");
                }else{
                    tablaPublicaciones.getSelectionModel().getSelectedItem().setEstadoTabla("Publicado");
                    JOptionPane.showMessageDialog(null,"El estado se ha cambiado a: Publicado");
                    estado=1;
                }
                tablaPublicaciones.refresh();
                PublicacionDAO publicacionDAO = new PublicacionDAO();
                try {
                    publicacionDAO.actualizarPublicacion(estado,tablaPublicaciones.getSelectionModel().getSelectedItem().getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void llenarListaPublicaciones(){
        PublicacionDAO publicacionDAO = new PublicacionDAO();
        try {
            listaPublicaciones = publicacionDAO.obtenerPublicacionesParaTabla();
            columnaId.setCellValueFactory(new PropertyValueFactory<>("autorTabla"));
            columnaNombretable.setCellValueFactory(new PropertyValueFactory<>("tituloTabla"));
            columnaEstado.setCellValueFactory(new PropertyValueFactory<TablaPublicaciones, String>("estadoTabla"));
            columnaFecha.setCellValueFactory(new PropertyValueFactory<TablaPublicaciones,String>("fechaTabla"));

            ObservableList<TablaPublicaciones> observableList = FXCollections.observableList(listaPublicaciones);
            FilteredList<TablaPublicaciones> filteredData = new FilteredList<>(observableList, b -> true);
            textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(tablaPublicacion -> {
                    if (newValue == null || newValue.isEmpty()) {
                        llenarListaPublicaciones();
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (tablaPublicacion.getTituloTabla().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                        return true;
                    } else if (tablaPublicacion.getEstadoTabla().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (tablaPublicacion.getAutorTabla().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }else {
                        return false;
                    }
                });
            });
            SortedList<TablaPublicaciones> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tablaPublicaciones.comparatorProperty());
            tablaPublicaciones.setItems(sortedData);
        } catch (SQLException e) {
            System.out.println("No se puede acceder a la base de datos");
        }
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
            Stage stage = (Stage) this.buttonBack.getScene().getWindow();
            stage.setTitle(name);
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
    @FXML
    void tableOnStart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Desea guardar los cambios");
        alert.setContentText("¿Deseas realmente confirmar?");
        Optional<ButtonType> action = alert.showAndWait();
        int estado =0;
        if (action.get()==ButtonType.OK){
            if (tablaPublicaciones.getSelectionModel().getSelectedItem().getEstadoTabla().equals("Publicado")){
                tablaPublicaciones.getSelectionModel().getSelectedItem().setEstadoTabla("Sin Publicar");
                JOptionPane.showMessageDialog(null,"El estado se ha cambiado a: Sin publicar");
            }else{
                tablaPublicaciones.getSelectionModel().getSelectedItem().setEstadoTabla("Publicado");
                JOptionPane.showMessageDialog(null,"El estado se ha cambiado a: Publicado");
                estado=1;
            }
            tablaPublicaciones.refresh();
            PublicacionDAO publicacionDAO = new PublicacionDAO();
            try {
                publicacionDAO.actualizarPublicacion(estado,tablaPublicaciones.getSelectionModel().getSelectedItem().getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}