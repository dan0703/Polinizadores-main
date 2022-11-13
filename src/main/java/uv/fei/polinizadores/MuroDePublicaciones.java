package uv.fei.polinizadores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import uv.fei.bussinesslogic.PublicacionDAO;
import uv.fei.domain.Publicacion;
import uv.fei.domain.Singleton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MuroDePublicaciones implements Initializable {
    public TextFlow tfPublicacion;

    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonNewPublication;
    public ChoiceBox<Publicacion> cbPublicaciones;
    public ImageView ivImagen;
    public Button btnVer;

    private Text titulo;
    private Text descripcion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        tfPublicacion.getChildren().removeAll(text_1,text_2);
        llenarListaDePublicaciones();
        btnVer.setDisable(false);

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
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.buttonNewPublication.getScene().getWindow();
            stage.setTitle("Muro de Publicaciones");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }

    public void llenarListaDePublicaciones(){
        PublicacionDAO publicacionDAO = new PublicacionDAO();
        List<Publicacion> publicaciones = null;
        ObservableList<Publicacion> publicacionObservableList =
                FXCollections.observableArrayList();

        try {
            publicaciones = publicacionDAO.obtenerPublicaciones();
        } catch (SQLException e) {
            e.printStackTrace();
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
        tfPublicacion.getChildren().removeAll(titulo,descripcion);
        Publicacion publicacion = cbPublicaciones.getValue();
        titulo  = new Text(publicacion.getTitulo() +"\n");
        titulo.setFill(Color.BLACK);
        descripcion = new Text(publicacion.getDescripcion() + "\n");
        descripcion.setFill(Color.BLACK);
        tfPublicacion.getChildren().addAll(titulo, descripcion);
    }
    @FXML
    void buttonNewPublicationClic(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("RegistrarPublicacion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.buttonNewPublication.getScene().getWindow();
            stage.setTitle("Muro de Publicaciones");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
}