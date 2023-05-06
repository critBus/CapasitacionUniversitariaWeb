package beans;

import Entity.*;
import Utils.*;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// es ViewScoped para poder tener varios del mismo tipo sin problemas, este tipo solo debe de
//durar mientras la pagina es visible en el navegador
@ManagedBean
@ViewScoped
public class AdminCapasitacion {
    private TipoDeCapasitacion tipo;
    private  List<Capasitacion> lista_de_entidades = new ArrayList<Capasitacion>();



    private  String titulo = "";
    private  String tema = "";
    private  String edicion = "";

    private  String programa = "";
    private Date fechaDeInicio=new Date();
    private Date fechaDeFin=new Date();

    public  String ID_DLG_ADD="addDialog";
    public  String ID_DLG_EDIT="editDialog";
    public  String ID_DATA_TABLE="dt-entidad";

    private static Capasitacion entidad;


    private static ConexionBD bd=new ConexionBD();
    public void init(){
        try{
            lista_de_entidades.clear();
            lista_de_entidades =bd.obtenerListaDeCapasitacion(tipo);
        }catch (Exception ex){
            responderException(ex);
        }



    }
    public void ponerTipo(String tipoC){
        tipo=TipoDeCapasitacion.valueOf(tipoC);
        //System.out.println(tipo);
    }
    public void ponerCurso(){
        tipo=TipoDeCapasitacion.CURSO;
        //System.out.println(tipo);
    }
    private void responderException(Exception ex){
        System.out.println("error en Capasitacion "+tipo.toString());
        ex.printStackTrace();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                , "Errores en el servidor", ex.getMessage()));
        PrimeFaces.current().ajax().update("form:messages");//, "form:"+ID_DATA_TABLE
    }
    public void clean_variables(){

        titulo = "";
         tema = "";
        edicion = "";

        programa = "";

        fechaDeFin=new Date();
        fechaDeInicio=DateUtils.subtractHoursFromDate(fechaDeFin,2);
        System.out.println("se mando a limpiar las variables");
    }

    public void create(){
        try{
            titulo=titulo.trim();
            Capasitacion finded=bd.obtenerCapasitacion(tipo,titulo);
            if(finded!=null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                        , tipo.texto.getYaExisteUnaConEsteNombre(), ""));
                PrimeFaces.current().ajax().update("form:messages");
                return;
            }
            if(!Validaciones_bean.validarFechasValidacion(fechaDeInicio,fechaDeFin)){
                PrimeFaces.current().ajax().update("form:messages");
                return;
            }
            tema=tema.trim();
            edicion=edicion.trim();
            programa=programa.trim();


            Capasitacion e=bd.crearCapasitacion(titulo,tema,edicion,programa,fechaDeInicio,fechaDeFin,tipo);
            if(e!=null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO
                        , tipo.texto.getAdicionado(), ""));

                init();

                PrimeFaces.current().executeScript("PF('"+ID_DLG_ADD+"').hide()");
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                        , "Existen errores en el formulario", ""));
            }
        }catch (Exception ex){
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                    , "Errores en el servidor", ex.getMessage()));
        }

        PrimeFaces.current().ajax().update("form:messages", "form:"+ID_DATA_TABLE);


    }
    public void copyedit(Capasitacion e) {
        try{
            if(e!=null){
                entidad=bd.copiarCapasitacion(e);
            }
        }catch (Exception ex){
            responderException(ex);
        }
    }

    public void edit(){
        try{
            entidad.setTitulo(entidad.getTitulo().trim());


            Capasitacion finded=bd.obtenerCapasitacion(entidad);
            if(finded == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                        , tipo.texto.getExistenErroresAlEditar(), tipo.texto.getInexistente()));
            }
            if(!Validaciones_bean.validarFechasValidacion(entidad.getFechaInicio(),entidad.getFechaFin())){
                PrimeFaces.current().ajax().update("form:messages");
                return;
            }
            else{
                if((!finded.getTitulo().equals(entidad.getTitulo()))
                        &&bd.obtenerCapasitacion(tipo,entidad.getTitulo())!=null
                ){

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                            , tipo.texto.getYaExisteUnaConEsteNombre(), ""));
                    PrimeFaces.current().ajax().update("form:messages");
                    return;
                }
                entidad.setEdicion(entidad.getEdicion().trim());
                entidad.setPrograma(entidad.getPrograma().trim());
                entidad.setTema(entidad.getTema().trim());


                if(bd.editar_Capasitacion(entidad)==null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                            , tipo.texto.getExistenErroresAlEditar(), "Errores en el formulario"));


                }
                else{

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO
                            , tipo.texto.getActualizado(), ""));

                    init();

                    PrimeFaces.current().executeScript("PF('"+ID_DLG_EDIT+"').hide()");
                }
            }


        }catch (Exception ex){
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                    , "Errores en el servidor", ex.getMessage()));
        }

        PrimeFaces.current().ajax().update("form:messages", "form:"+ID_DATA_TABLE);
    }
    public void delete(){
        try{
            if(entidad==null)
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                        , tipo.texto.getExistenErroresAlEliminar(), tipo.texto.getInexistente()));
            else {

                if (bd.eliminar_Capasitacion_Cascade(entidad)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO
                            , tipo.texto.getEliminado(), ""));
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                            , "Errores en el servidor", tipo.texto.getNoSePudoEliminar()));
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                    , "Errores en el servidor", ex.getMessage()));
        }
        init();
        PrimeFaces.current().ajax().update("form:messages", "form:"+ID_DATA_TABLE);
    }
    public  Capasitacion getEntidad() {
        return entidad;
    }

    public  void setEntidad(Capasitacion entidad) {
        this.entidad = entidad;
    }

    public TipoDeCapasitacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeCapasitacion tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public Date getFechaDeInicio() {
        return fechaDeInicio;
    }

    public void setFechaDeInicio(Date fechaDeInicio) {
        this.fechaDeInicio = fechaDeInicio;
    }

    public Date getFechaDeFin() {
        return fechaDeFin;
    }

    public void setFechaDeFin(Date fechaDeFin) {
        this.fechaDeFin = fechaDeFin;
    }

    public  List<Capasitacion> getLista_de_entidades() {
        return lista_de_entidades;
    }

    public  void setLista_de_entidades(List<Capasitacion> lista_de_entidades) {
        this.lista_de_entidades = lista_de_entidades;
    }

}
