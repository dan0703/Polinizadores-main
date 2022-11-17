package uv.fei.bussinesslogic;

import uv.fei.domain.Publicacion;
import uv.fei.domain.TablaPublicaciones;

import java.sql.SQLException;
import java.util.List;

public interface IPublicacionDAO {
    public List<Publicacion> obtenerPublicaciones() throws SQLException;
    public boolean registrarPublicacion(Publicacion publicacion)throws SQLException;
    public void actualizarPublicacion(int estado, int id) throws SQLException;
    public List<TablaPublicaciones> obtenerPublicacionesParaTabla()throws SQLException;
}
