package uv.fei.bussinesslogic;

import uv.fei.dataaccess.ConexionBD;
import uv.fei.domain.Singleton;
import uv.fei.domain.Usuario;

import java.sql.*;

public class UsuarioDAO implements IUsuarioDAO{
    @Override
    public int agregarUsuario(Usuario usuario) throws SQLException {
        //agregar usuario
        ConexionBD conexionBD = new ConexionBD();
        int idUsuario;
        String query = "INSERT INTO usuario(nombre, contrasenia, email, acreditacion, rol) VALUES (?, MD5(?), ?, ?, ?)";
        try (Connection connection =  conexionBD.openConnection()){
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getContrasenia());
            statement.setString(3, usuario.getEmail());
            statement.setString(4, usuario.getAcreditacion());
            statement.setString(5, usuario.getRol());
            int executeUpdate = statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(executeUpdate == 0){
                throw new SQLException("Error al agregar un usuario");
            }else {
                resultSet.next();
                idUsuario = resultSet.getInt(1);
            }
        }finally {
            conexionBD.closeConnection();
        }
        return idUsuario;
    }

    @Override
    public boolean login(String email, String contrasenia) throws SQLException {
        boolean flag = false;
        ConexionBD conexionBD = new ConexionBD();
        String query="Select * from usuario where email = ? and contrasenia = MD5(?)";
        try (Connection connection = conexionBD.openConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, contrasenia);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                flag = true;
                Usuario user1 = new Usuario();

                user1.setId(resultSet.getInt("id"));
                user1.setContrasenia(resultSet.getString("contrasenia"));
                user1.setAcreditacion(resultSet.getString("acreditacion"));
                user1.setRol(resultSet.getString("rol"));
                user1.setNombre(resultSet.getString("nombre"));
                user1.setEmail(resultSet.getString("email"));
                Singleton.setLogin(user1);
            }
            return flag;
        }
    }
}
