package Utils;

public enum TipoDeCapasitacion {
    CONFERENCIA(new TextosDeCapasitacion("Conferencia Especializada","Conferencias Especializadas",false))
    ,TALLER(new TextosDeCapasitacion("Taller","Talleres",true))
    ,SEMINARIO(new TextosDeCapasitacion("Seminario","Seminarios",true))
    ,CURSO(new TextosDeCapasitacion("Curso de Capacitación","Cursos de Capacitación",true))
    ,;



    public TextosDeCapasitacion texto;
    TipoDeCapasitacion(TextosDeCapasitacion texto) {
        this.texto = texto;
    }

    public TextosDeCapasitacion getTexto() {
        return texto;
    }

    public void setTexto(TextosDeCapasitacion texto) {
        this.texto = texto;
    }
}
