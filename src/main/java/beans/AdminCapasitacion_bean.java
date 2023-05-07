package beans;

import Entity.*;
import Utils.*;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// es ViewScoped para poder tener varios del mismo tipo sin problemas, este tipo solo debe de
//durar mientras la pagina es visible en el navegador
@ManagedBean
@ViewScoped
public class AdminCapasitacion_bean {
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

    private  Capasitacion entidad;

    private DualListModel<Estudiante> dualListModel_estudiantes=new DualListModel<Estudiante>(new ArrayList<Estudiante>(),new ArrayList<Estudiante>());
    private DualListModel<Profesor> dualListModel_profesores=new DualListModel<Profesor>(new ArrayList<Profesor>(),new ArrayList<Profesor>());

    private static ConexionBD bd=new ConexionBD();
    public void init(){
        try{
            lista_de_entidades.clear();
            lista_de_entidades =bd.obtenerListaDeCapasitacion(tipo);
        }catch (Exception ex){
            responderException(ex);
        }



    }
    private List<Estudiante> convertirListaEstudiantes(List l) throws Exception{
        List<Estudiante> le=new ArrayList<>();
       // try{

        for (Object o :
                l) {
         if(o instanceof Estudiante){
             le.add((Estudiante)o);
         } else{
             System.out.println("e o="+o);
             Estudiante e=bd.obtenerEstudiante(o.toString());
             if(e!=null){
                 le.add(e);
             }
         }
        }
//    }catch (Exception ex){
//        responderException(ex);
//    }
        return le;
    }
    private List<Profesor> convertirListaProfesores(List l) throws Exception{
        List<Profesor> le=new ArrayList<>();
        // try{

        for (Object o :
                l) {
            if(o instanceof Profesor){
                le.add((Profesor)o);
            } else{
                System.out.println("e o="+o);
                Profesor e=bd.obtenerProfesor(o.toString());
                if(e!=null){
                    le.add(e);
                }
            }
        }
//    }catch (Exception ex){
//        responderException(ex);
//    }
        return le;
    }

    private List<Estudiante> obtenerListaDeEstudiantesSeleccionados()throws Exception{
        return convertirListaEstudiantes(dualListModel_estudiantes.getTarget());
    }
    private List<Profesor> obtenerListaDeProfesoresSeleccionados()throws Exception{
        return convertirListaProfesores(dualListModel_profesores.getTarget());
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
try{
        titulo = "";
         tema = "";
        edicion = "";

        programa = "";

        fechaDeFin=new Date();
        fechaDeInicio=DateUtils.subtractHoursFromDate(fechaDeFin,2);

        dualListModel_estudiantes=new DualListModel<Estudiante>(bd.obtenerListaDeEstudiantes(),new ArrayList<Estudiante>());
        dualListModel_profesores=new DualListModel<Profesor>(bd.obtenerListaDeProfesores(),new ArrayList<Profesor>());
    }catch (Exception ex){
        responderException(ex);
    }
        //System.out.println("se mando a limpiar las variables");
    }
//    public void onTransfer_Profesor(TransferEvent event) {
//        StringBuilder builder = new StringBuilder();
//        for(Object item : event.getItems()) {
//            builder.append(((Profesor) item).getIduniversitario().getNombre()).append("<br />");
//        }
//
//        FacesMessage msg = new FacesMessage();
//        msg.setSeverity(FacesMessage.SEVERITY_INFO);
//        msg.setSummary("Items Transferred");
//        msg.setDetail(builder.toString());
//
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
//    public void onTransfer_Estudiante(TransferEvent event) {
//        StringBuilder builder = new StringBuilder();
//        for(Object item : event.getItems()) {
//            builder.append(((Estudiante) item).getIduniversitario().getNombre()).append("<br />");
//        }
//
//        FacesMessage msg = new FacesMessage();
//        msg.setSeverity(FacesMessage.SEVERITY_INFO);
//        msg.setSummary("Items Transferred2");
//        msg.setDetail(builder.toString());
//
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
//    public void onSelect_Profesor(SelectEvent<Profesor> event) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().getIduniversitario().getNombre()));
//    }
//    public void onSelect_Estudiante(SelectEvent<Estudiante> event) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().getIduniversitario().getNombre()));
//    }
//
//    public void onUnselect_Profesor(UnselectEvent<Profesor> event) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().getIduniversitario().getNombre()));
//    }
//    public void onUnselect_Estudiante(UnselectEvent<Estudiante> event) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().getIduniversitario().getNombre()));
//    }
//    public void onReorder() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
//    }
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
            if(!Validaciones_bean.validar_Capasitacion(
                    obtenerListaDeProfesoresSeleccionados()
                    ,obtenerListaDeEstudiantesSeleccionados()
                    ,fechaDeInicio,fechaDeFin)){
                PrimeFaces.current().ajax().update("form:messages");
                return;
            }
            tema=tema.trim();
            edicion=edicion.trim();
            programa=programa.trim();


            Capasitacion e=bd.crearCapasitacion(titulo,tema,edicion
                    ,programa,fechaDeInicio,fechaDeFin,tipo
                    ,obtenerListaDeProfesoresSeleccionados()
                    ,obtenerListaDeEstudiantesSeleccionados()
            );
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
                dualListModel_profesores=new DualListModel<Profesor>(
                        bd.obtenerListaDeProfesoresQueNoEstan(entidad)
                        ,bd.obtenerListaDeProfesores(entidad)
                );
                dualListModel_estudiantes=new DualListModel<Estudiante>(
                        bd.obtenerListaDeEstudianteQueNoEstan(entidad)
                        ,bd.obtenerListaDeEstudiante(entidad)
                );
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
            if(!Validaciones_bean.validar_Capasitacion(
                    obtenerListaDeProfesoresSeleccionados()
                    ,obtenerListaDeEstudiantesSeleccionados()
                    ,entidad.getFechaInicio(),entidad.getFechaFin())){
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


                if(bd.editar_Capasitacion(entidad
                        ,dualListModel_profesores.getTarget()
                        ,dualListModel_estudiantes.getTarget()
                )==null){
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

    public DualListModel<Estudiante> getDualListModel_estudiantes() {
        return dualListModel_estudiantes;
    }

    public void setDualListModel_estudiantes(DualListModel<Estudiante> dualListModel_estudiantes) {
        this.dualListModel_estudiantes = dualListModel_estudiantes;
    }

    public DualListModel<Profesor> getDualListModel_profesores() {
        return dualListModel_profesores;
    }

    public void setDualListModel_profesores(DualListModel<Profesor> dualListModel_profesores) {
        this.dualListModel_profesores = dualListModel_profesores;
    }
}
