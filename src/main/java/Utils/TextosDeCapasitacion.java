package Utils;

public  class TextosDeCapasitacion {
    private String nombre;
    private String nombrePlural;
    private String yaExisteUnaConEsteNombre;
    public String existenErroresAlEditar;
    public String existenErroresAlEliminar;
    private String adicionado;
    private boolean masculino;
    private String inexistente;
    public String actualizado;
    private String eliminado;
    public String noSePudoEliminar;
    public String adminstrar;
    public String adicionar;
    private String eliminar;
    public TextosDeCapasitacion(String nombre, String nombrePlural,boolean masculino) {
        this.nombre = nombre;
        this.nombrePlural = nombrePlural;
        this.masculino=masculino;
        this.yaExisteUnaConEsteNombre="Ya existe "+(masculino?"un":"una")+" "+nombre+" con este titulo";
        this.adicionado=nombre+" adicionad"+(masculino?"o":"a");
        this.existenErroresAlEditar ="Existen errores al editar "+(masculino?"el":"la")+" "+nombre;
        this.inexistente=nombre+" inexistente";
        this.actualizado=nombre+" actualizad"+(masculino?"o":"a");
        this.existenErroresAlEliminar="Existen errores al eliminar "+(masculino?"el":"la")+" "+nombre;
        this.eliminado=nombre+" eliminad"+(masculino?"o":"a");
        this.noSePudoEliminar="No se pudo eliminar "+(masculino?"el":"la")+" "+nombre;

        this.adminstrar="Administrar "+nombrePlural;
        this.adicionar="Adicionar "+nombre;
        this.eliminar="Eliminar "+(masculino?"el":"la")+" "+nombre;

        //this.eliminado=nombre+" ";
    }

    public String getEliminar() {
        return eliminar;
    }

    public void setEliminar(String eliminar) {
        this.eliminar = eliminar;
    }

    public String getAdicionar() {
        return adicionar;
    }

    public void setAdicionar(String adicionar) {
        this.adicionar = adicionar;
    }

    public String getAdminstrar() {
        return adminstrar;
    }

    public void setAdminstrar(String adminstrar) {
        this.adminstrar = adminstrar;
    }

    public String getNoSePudoEliminar() {
        return noSePudoEliminar;
    }

    public void setNoSePudoEliminar(String noSePudoEliminar) {
        this.noSePudoEliminar = noSePudoEliminar;
    }

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombrePlural() {
        return nombrePlural;
    }

    public void setNombrePlural(String nombrePlural) {
        this.nombrePlural = nombrePlural;
    }

    public String getYaExisteUnaConEsteNombre() {
        return yaExisteUnaConEsteNombre;
    }

    public void setYaExisteUnaConEsteNombre(String yaExisteUnaConEsteNombre) {
        this.yaExisteUnaConEsteNombre = yaExisteUnaConEsteNombre;
    }

    public boolean isMasculino() {
        return masculino;
    }

    public void setMasculino(boolean masculino) {
        this.masculino = masculino;
    }

    public String getAdicionado() {
        return adicionado;
    }

    public void setAdicionado(String adicionado) {
        this.adicionado = adicionado;
    }

    public String getExistenErroresAlEditar() {
        return existenErroresAlEditar;
    }

    public void setExistenErroresAlEditar(String existenErroresAlEditar) {
        this.existenErroresAlEditar = existenErroresAlEditar;
    }

    public String getInexistente() {
        return inexistente;
    }

    public void setInexistente(String inexistente) {
        this.inexistente = inexistente;
    }

    public String getActualizado() {
        return actualizado;
    }

    public void setActualizado(String actualizado) {
        this.actualizado = actualizado;
    }

    public String getExistenErroresAlEliminar() {
        return existenErroresAlEliminar;
    }

    public void setExistenErroresAlEliminar(String existenErroresAlEliminar) {
        this.existenErroresAlEliminar = existenErroresAlEliminar;
    }
}
