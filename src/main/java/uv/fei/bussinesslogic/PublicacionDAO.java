package uv.fei.bussinesslogic;

import uv.fei.dataaccess.ConexionBD;
import uv.fei.domain.Publicacion;
import uv.fei.domain.Singleton;
import uv.fei.domain.TablaPublicaciones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicacionDAO implements IPublicacionDAO{
    @Override
    public List<Publicacion> obtenerPublicaciones() throws SQLException {
        ConexionBD conexionBD = new ConexionBD();
        List<Publicacion> publicaciones = new ArrayList<>();
        try (Connection connection = conexionBD.openConnection()){
            String query = "SELECT * FROM Publicacion Where estado = 1 ";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                throw new SQLException("No se encontraron publicaciones");
            }else {
                do {
                    publicaciones.add(getPublicacion(resultSet));
                }while (resultSet.next());
            }
        }catch (SQLException sqlException){
            throw sqlException;
        }
        return publicaciones;
    }

    @Override
    public boolean registrarPublicacion(Publicacion publicacion) throws SQLException {
        boolean flag;
        ConexionBD conexionBD = new ConexionBD();
        String query = "INSERT INTO publicacion ( `titulo`, `fecha`, `descripcion`, `referencias`, `estado`, `idUsuario`)VALUES (?,?,?,?,?,?)";
        try (Connection connection =  conexionBD.openConnection()){
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, publicacion.getTitulo());
            statement.setString(2, publicacion.getFecha());
            statement.setString(3, publicacion.getDescripcion());
            statement.setString(4, publicacion.getReferencia());
            statement.setInt(5, 0);
            statement.setInt(6, Singleton.getId());
            int executeUpdate = statement.executeUpdate();
            if(executeUpdate == 0){
                throw new SQLException("Error al agregar un usuario");
            }else {
                flag=true;
            }
        }finally {
            conexionBD.closeConnection();
        }
        return flag;
    }

    @Override
    public void actualizarPublicacion(int estado, int id) throws SQLException {

        ConexionBD conexionBD = new ConexionBD();
        try (Connection connection = conexionBD.openConnection()){
            String query = "UPDATE Publicacion set Estado=? WHERE Id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,estado);
            statement.setInt(2,id);
            int resultado = statement.executeUpdate();
            if (resultado < 1){
                throw new SQLException("Error al actualizar la publicacion");
            }
        }catch (SQLException sqlException){
            throw sqlException;
        }
    }

    @Override
    public List<TablaPublicaciones> obtenerPublicacionesParaTabla() throws SQLException {
        ConexionBD conexionBD = new ConexionBD();
        List<TablaPublicaciones> publicaciones = new ArrayList<>();
        try (Connection connection = conexionBD.openConnection()){
            String query = "SELECT nombre, titulo, fecha, estado, publicacion.id from publicacion inner join usuario on publicacion.idUsuario= usuario.id";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                throw new SQLException("Error al obtener las publicaciones");

            }else {
                int id;
                String titulo;
                String fecha;
                String autor;
                String estado;

                do {
                    id = resultSet.getInt("id");
                    titulo = resultSet.getString("titulo");
                    fecha = resultSet.getString("fecha");
                    autor = resultSet.getString("nombre");
                    estado = (resultSet.getInt("estado")==1?"Publicado":"Sin Publicar");

                    TablaPublicaciones publicacion = new TablaPublicaciones();
                    publicacion.setId(id);
                    publicacion.setTituloTabla(titulo);
                    publicacion.setFechaTabla(fecha);
                    publicacion.setAutorTabla(autor);
                    publicacion.setEstadoTabla(estado);

                    publicaciones.add(publicacion);

                }while (resultSet.next());
            }
        }catch (SQLException sqlException){
            throw sqlException;
        }
        return publicaciones;
    }

    private Publicacion getPublicacion(ResultSet resultSet) throws SQLException {
        Publicacion publicacion = new Publicacion();
        int id;
        String titulo;
        String fecha;
        String descripcion;
        String referencia;
        String estado;
        try {
            id = resultSet.getInt("id");
            titulo = resultSet.getString("titulo");
            fecha = resultSet.getString("fecha");
            descripcion = resultSet.getString("descripcion");
            referencia = resultSet.getString("referencias");
            estado = (resultSet.getInt("estado")==0?"Publicado":"Sin Publicar");

            publicacion.setId(id);
            publicacion.setTitulo(titulo);
            publicacion.setFecha(fecha);
            publicacion.setDescripcion(descripcion);
            publicacion.setReferencia(referencia);
            publicacion.setEstado(estado);

        } catch (SQLException e) {
            throw e;
        }
        return publicacion;
    }
}
