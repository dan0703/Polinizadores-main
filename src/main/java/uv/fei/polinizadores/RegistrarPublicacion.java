package uv.fei.polinizadores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import uv.fei.bussinesslogic.PublicacionDAO;
import uv.fei.domain.Publicacion;
import uv.fei.domain.Singleton;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RegistrarPublicacion implements Initializable {

    @FXML
    private Button buttonPublish;
    @FXML
    private Label labelAutor;
    @FXML
    private TextField tituloField;

    @FXML
    private Button buttonCancel;

    @FXML
    private HTMLEditor htmlEditor;


    @FXML
    void buttonPublish_Clicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Nueva Publicación");
        alert.setContentText("¿Deseas realmente guardar Publicacion?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get().equals(ButtonType.OK)){
            registrarNuevapublicacion();
            htmlEditor.setHtmlText("");
            tituloField.setText("");
        }

    }

    @FXML
    void ButtonCancel_Clicked(ActionEvent event) {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("MuroDePublicaciones.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.buttonCancel.getScene().getWindow();
            stage.setTitle("Muro de Publicaciones");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelAutor.setText(String.valueOf(Singleton.getUserName()));
    }
    public void registrarNuevapublicacion() {
        DateFormat fecha = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss z");
        Publicacion publicacion = new Publicacion();
        publicacion.setFecha(fecha.toString());
        publicacion.setTitulo(tituloField.getText());
        publicacion.setDescripcion(htmlEditor.getHtmlText());
        publicacion.setFecha(fecha.format(new Date()));
        publicacion.setEstado("");
        publicacion.setReferencia("");

        try {
            PublicacionDAO publicacionDAO = new PublicacionDAO();
            if (publicacionDAO.registrarPublicacion(publicacion)){
                JOptionPane.showMessageDialog(null,"La publicacion se ha registrado exitosamente");
            }else {
                JOptionPane.showMessageDialog(null,"No se ha podido registrar la publicacion");
            }
        }
        catch (SQLException e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null,e);
        }
    }
}