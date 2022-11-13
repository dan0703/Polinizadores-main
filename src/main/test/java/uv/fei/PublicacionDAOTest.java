package uv.fei;
import org.junit.Before;
import org.junit.Test;
import uv.fei.bussinesslogic.PublicacionDAO;
import uv.fei.domain.Publicacion;

import java.sql.SQLException;

public class PublicacionDAOTest {
    private PublicacionDAO publicacionDAO;

    @Before
    public void init(){
        publicacionDAO = new PublicacionDAO();
    }
    @Test
    public void probarRetornar() throws SQLException {
        for (Publicacion publicacion : publicacionDAO.obtenerPublicaciones()) {
            System.out.println(publicacion);
        }

    }
}
