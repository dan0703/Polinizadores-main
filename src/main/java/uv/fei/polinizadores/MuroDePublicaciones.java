package uv.fei.polinizadores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import uv.fei.bussinesslogic.PublicacionDAO;
import uv.fei.domain.Publicacion;
import uv.fei.domain.Singleton;

import javax.swing.*;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MuroDePublicaciones implements Initializable {
    @FXML
    private Button buttonLogin;
    @FXML
    private WebView webview;
    @FXML
    private Button buttonNewPublication;
    public ChoiceBox<Publicacion> cbPublicaciones;
    public ImageView ivImagen;
    public Button btnVer;
    @FXML
    private Label labelTitulo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            llenarListaDePublicaciones();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"no existen publicaciones disponibles");
        }
        btnVer.setDisable(false);
        if (Singleton.rol().equals("Administrador")){
            buttonNewPublication.setText("Administrar Publicaciones");
        }
        buttonNewPublication.setVisible(!Singleton.rol().isEmpty());
        buttonNewPublication.setDisable(Singleton.rol().isEmpty());
        if(!Singleton.rol().isEmpty()){
            buttonLogin.setText("Cerrar Sesion");
        }else {
            buttonLogin.setText("Iniciar Sesion");
        }
    }
    @FXML
    void buttonLoginClic(ActionEvent event) {
        openWindow("Login.fxml","Login");
    }

    public void llenarListaDePublicaciones() throws SQLException{
        PublicacionDAO publicacionDAO = new PublicacionDAO();
        List<Publicacion> publicaciones = null;
        ObservableList<Publicacion> publicacionObservableList = FXCollections.observableArrayList();

        try {
            publicaciones = publicacionDAO.obtenerPublicaciones();
        } catch (SQLException e) {
            throw e;
        }
        if (publicaciones != null) {
            publicacionObservableList.addAll(publicaciones);
        }
        cbPublicaciones.setValue(publicacionObservableList.get(0));
        cbPublicaciones.getItems().addAll(publicacionObservableList);

    }
    public void seleccionarPublicacion(MouseEvent mouseEvent){

    }

    public void clicVerPublicacion(ActionEvent actionEvent) {
        Publicacion publicacion = cbPublicaciones.getValue();
        WebEngine webEngine = webview.getEngine();
        webEngine.loadContent(publicacion.getDescripcion());
        labelTitulo.setText(publicacion.getTitulo());
    }
    @FXML
    void buttonPublicationsClic(ActionEvent event) {
        if (Singleton.rol().equals("Administrador")){
            openWindow("publicaciones.fxml","Administrar publicaciones");
        }else
        {
            JOptionPane.showMessageDialog(null,Singleton.rol());
            openWindow("RegistrarPublicacion.fxml", "Registrar Publicacion");
        }
    }

    public  void openWindow(String fxml, String titulo){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.buttonNewPublication.getScene().getWindow();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }

}