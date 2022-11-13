package uv.fei.polinizadores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import uv.fei.bussinesslogic.UsuarioDAO;
import uv.fei.domain.Usuario;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Registro implements  Initializable{
    public TextField tfNombre;
    public TextField tfEmail;
    public PasswordField pfContrasenia;
    public TextField tfAcreditacion;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
