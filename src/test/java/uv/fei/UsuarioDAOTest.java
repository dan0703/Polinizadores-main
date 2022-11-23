package uv.fei;

import org.junit.Before;
import org.junit.Test;
import uv.fei.bussinesslogic.UsuarioDAO;
import uv.fei.domain.Usuario;
import static org.junit.Assert.*;


import java.sql.SQLException;

public class UsuarioDAOTest {
    Usuario usuario;
    UsuarioDAO usuarioDAO;
    @Before
    public void init(){
        usuario = new Usuario();
        usuario.setNombre("Jose Pepe Maximo Tercero");
        usuario.setContrasenia("MemementoMori");
        usuario.setAcreditacion("Biologo");
        usuario.setEmail("kloq@gmail.com");
        usuarioDAO = new UsuarioDAO();
    }
    @Test
    public void agregarUsuario() throws SQLException {
        assertEquals(6,usuarioDAO.agregarUsuario(usuario));
    }
    @Test
    public void login() throws SQLException{
        assertTrue(usuarioDAO.login(usuario.getEmail(),usuario.getContrasenia()));
    }
}
