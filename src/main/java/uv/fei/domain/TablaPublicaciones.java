package uv.fei.domain;

public class TablaPublicaciones {
    private String tituloTabla;
    private String fechaTabla;
    private String estadoTabla;
    public String autorTabla;
    public int id;


    public String getTituloTabla() {
        return tituloTabla;
    }

    public String getFechaTabla() {
        return fechaTabla;
    }

    public String getAutorTabla() {return autorTabla;}

    public String getEstadoTabla() {return estadoTabla;}

    public int getId() {return id;}

    public void setAutorTabla(String autor){this.autorTabla = autor;}

    public void setTituloTabla(String titulo) {this.tituloTabla = titulo;}

    public void setFechaTabla(String fecha) {
        this.fechaTabla = fecha;
    }

    public void setEstadoTabla(String estado) {
        this.estadoTabla = estado;
    }

    public void setId(int id) {this.id = id;}

    public TablaPublicaciones() {
        this.tituloTabla = "";
        this.fechaTabla = "";
        this.estadoTabla = "";
        this.autorTabla = "";
        this.id=0;
    }

    @Override
    public String toString() {
        return tituloTabla;
    }
}
