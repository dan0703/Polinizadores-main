package uv.fei.bussinesslogic;

import uv.fei.domain.Usuario;

import java.sql.SQLException;

public interface IUsuarioDAO {
    public int agregarUsuario(Usuario usuario) throws SQLException;
    public boolean login(String email, String contrasenia) throws  SQLException;
}
