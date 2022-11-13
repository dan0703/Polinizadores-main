package uv.fei;

import org.junit.Before;
import org.junit.Test;
import uv.fei.bussinesslogic.UsuarioDAO;
import uv.fei.domain.Usuario;

import java.sql.SQLException;

public class UsuarioDAOTest {
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    private Usuario usuario1;

    @Before
    public void init(){
        usuario1 = new Usuario();
        usuarioDAO = new UsuarioDAO();
    }
    @Test
    public void agregarUnUsuarioExitoso() throws SQLException {
       usuarioDAO.agregarUsuario(usuario);

    }
    @Test
    public void agregarUnUsuarioFail()throws SQLException{
        usuarioDAO.agregarUsuario(usuario1);
    }
}
