package uv.fei;

import org.junit.Before;
import org.junit.Test;
import uv.fei.bussinesslogic.PublicacionDAO;
import uv.fei.domain.Publicacion;
import uv.fei.domain.Singleton;
import uv.fei.domain.Usuario;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class PublicacionDAOTest {
    PublicacionDAO publicacionDAO;
    Publicacion publicacion;
    Usuario user;
    @Before
    public void init(){
        publicacion = new Publicacion();
        publicacion.setDescripcion("<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><p>Las abejas es un problema muy grave para nuestra comunidad lorem <span style=\"font-weight: bold;\">ipson</span></p></body></html>");
        publicacion.setFecha("mié, 23 nov. 2022, 10:47:16 CST");
        publicacion.setTitulo("Las abejas son nuestra arma");
        user = new Usuario();
        user.setId(4);
        user.setAcreditacion("isof");
        user.setNombre("paulo");
        publicacionDAO = new PublicacionDAO();
    }
    @Test
    public void publicacionRegistrada() throws SQLException {
        Singleton.setLogin(user);
        assertTrue(publicacionDAO.registrarPublicacion(publicacion));
    }
    @Test
    public void actualizarPublicacion() throws SQLException{
        publicacionDAO.actualizarPublicacion(1,3);
    }
    @Test
    public void obtenerPublicaciones() throws SQLException{
        assertNotNull(publicacionDAO.obtenerPublicaciones());
    }
    @Test
    public void obtenerPublicacionParaTabla() throws SQLException{
        assertNotNull(publicacionDAO.obtenerPublicacionesParaTabla());
    }

}
