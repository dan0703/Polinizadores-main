package uv.fei.polinizadores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uv.fei.bussinesslogic.UsuarioDAO;
import uv.fei.domain.Singleton;
import uv.fei.domain.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Login implements Initializable {

    @FXML
    private CheckBox checkBoxShowPassword;
    @FXML
    private PasswordField fieldPassword2;
    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField passwordField;

    @FXML
    private Button buttonLogin;


    @FXML
    void emailAction(ActionEvent event) {
        fieldPassword2.requestFocus();

    }
    @FXML
    void passwordFieldAction(ActionEvent event) {
        buttonLogin.requestFocus();
    }

    @FXML
    void buttonLoginClic(ActionEvent event) {
        if(!areItemsEmpty()){
            logging();
        }
        else {
            JOptionPane.showMessageDialog(null,"Por favor, llena todos los campos");
        }
    }

    @FXML
    void buttonEntrarInvitadoClic(ActionEvent event) {
        Usuario usuario = new Usuario();
        Singleton.setLogin(usuario);
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("MuroDePublicaciones.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.buttonLogin.getScene().getWindow();
            stage.setTitle("Muro de Publicaciones");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
    @FXML
    void buttonRegisterClic(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Registro.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.buttonLogin.getScene().getWindow();
            stage.setTitle("Registrar Usuario");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
    @FXML
    void checkBoxShow_OnAction(ActionEvent event) {
        fieldPassword2.setVisible(!checkBoxShowPassword.selectedProperty().getValue());
        fieldPassword2.setEditable(!checkBoxShowPassword.selectedProperty().getValue());
        passwordField.setEditable(checkBoxShowPassword.selectedProperty().getValue());
        passwordField.setVisible(checkBoxShowPassword.selectedProperty().getValue());
    }

    private boolean areItemsEmpty(){
        return fieldPassword2.getText().isEmpty() || fieldEmail.getText().isEmpty();
    }
    private void logging(){
        String contrasenia = fieldPassword2.getText();
        String email = fieldEmail.getText();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        boolean result;
        try {
            result = usuarioDAO.login(email, contrasenia);
            if (!result) {
                JOptionPane.showMessageDialog(null,"Contraseña o correo electronico incorrecto");
            }else{
                JOptionPane.showMessageDialog(null,"Bienvenido "+ Singleton.getUserName() );
                openWindowMenu();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fieldPassword2.textProperty().addListener((observableValue, s, t1) -> passwordField.setText(fieldPassword2.getText()));
        passwordField.textProperty().addListener((observableValue, s, t1) -> fieldPassword2.setText(passwordField.getText()));
    }
    private void openWindowMenu(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("MuroDePublicaciones.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) this.buttonLogin.getScene().getWindow();
            stage.setTitle("Muro de Publicaciones");
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioException){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
}