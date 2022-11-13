package uv.fei.bussinesslogic;

import uv.fei.domain.Publicacion;

import java.sql.SQLException;
import java.util.List;

public interface IPublicacionDAO {
    public List<Publicacion> obtenerPublicaciones() throws SQLException;
    public boolean registrarPublicacion(Publicacion publicacion)throws SQLException;
}
