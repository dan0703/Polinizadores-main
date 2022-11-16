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
import javafx.stage.Stage;
import uv.fei.bussinesslogic.UsuarioDAO;
import uv.fei.domain.Usuario;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Registro implements  Initializable{
    public TextField tfNombre;
    public TextField tfEmail;
    public PasswordField pfContrasenia;
    public TextField tfAcreditacion;

    @FXML
    private Button btnCancelar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void btnCancelClic(ActionEvent event) {
        openWindowMenu();
    }
    private void openWindowMenu(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.btnCancelar.getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }

    public void clicRegistrarse(ActionEvent actionEvent){
        String nombre = tfNombre.getText();
        String email = tfEmail.getText();
        String contrasenia = pfContrasenia.getText();
        String acreditacion = tfAcreditacion.getText();
        String rol = "Experto";

        if (nombre.isEmpty() || email.isEmpty() || contrasenia.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Advertencia");
            alert.setContentText("No se pueden dejar campos vacíos");
            alert.showAndWait();
        }
        else {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setContrasenia(contrasenia);
            usuario.setAcreditacion(acreditacion);
            usuario.setRol(rol);
            UsuarioDAO usuarioDao = new UsuarioDAO();
            int resultado = -1;
            try {
                resultado = usuarioDao.agregarUsuario(usuario);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle(null);
                alert.setContentText("Registro exitoso");
                alert.showAndWait();
                JOptionPane.showMessageDialog(null,"email: "+ usuario.getEmail()+"\n"+"nombre: "+usuario.getNombre()+"\n"+"rol: "+ usuario.getRol());
                openWindowMenu();
            } catch (SQLException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText(String.valueOf(e));
                alert.showAndWait();
            }
        }
    }
}
