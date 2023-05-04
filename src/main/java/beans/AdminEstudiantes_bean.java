package beans;

import Entity.*;
import Utils.*;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class AdminEstudiantes_bean {

    private static List<Estudiante> lista_de_entidades = new ArrayList<Estudiante>();

    private static String nombre = "";
    private static String facultad = "";
    private static String carrera = "";

    private static String descripcion = "";
    private static int curso=1;
    private static int semestre=1;

    public  String ID_DLG_ADD="addDialog";
    public  String ID_DLG_EDIT="editDialog";
    public  String ID_DATA_TABLE="dt-entidad";

    private static Estudiante entidad;
//    private static Estudiante entidad_editar;

    private static ConexionBD bd=new ConexionBD();
    public void init(){
        lista_de_entidades.clear();
        lista_de_entidades =bd.obtenerListaDeEstudiantes();
    }

    public void clean_variables(){

        nombre = "";
        facultad = "";
        carrera = "";

        descripcion = "";
        curso=1;
        semestre=1;
    }

    public void create(){
        try{
        Estudiante finded=bd.obtenerEstudiante(nombre);
        if(finded!=null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                    , "Ya existe una persona en la universidad con este nombre", ""));
            PrimeFaces.current().ajax().update("form:messages");
            return;
        }
        Estudiante e=bd.crearEstudiante(nombre,facultad,carrera,descripcion,curso,semestre);
        if(e!=null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO
                    , "Estudiante Adicionado", ""));

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
    public void copyedit(Estudiante e) {
        if(e!=null){
            //entidad_editar=bd.copiarEstudiante(e);
            entidad=bd.copiarEstudiante(e);
        }
     }
//     public void alApretarEnCancelarEditar(){
//         entidad_editar=bd.copiarEstudiante(entidad);
//     }
    public void edit(){
        try{
            Universitario u=bd.obtenerUniversitario(entidad);
            Estudiante finded=bd.obtenerEstudiante(entidad);
            if(finded == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                        , "Existen errores al editar el Estudiante", "Estudiante inexistente"));
            }
            else{
                if((!bd.obtenerUniversitario(finded).getNombre().equals(u.getNombre()))
                        &&bd.obtenerEstudiante(u.getNombre())!=null
                ){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                            , "Ya existe una persona en la universidad con este nombre", ""));
                    PrimeFaces.current().ajax().update("form:messages");
                    return;
                }

                if(bd.editar_Estudiante_ConSu_Universitario(entidad)==null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                            , "Existen errores al editar el Estudiante", "Errores en el formulario"));


                }
                else{

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO
                            , "Estudiante Actualizado", ""));

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
                    , "Existen errores al eliminar el Estudiante", "Estudiante inexistente"));
        else {

            if (bd.eliminar_Estudiante(entidad)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO
                        , "Estudiante Eliminado", ""));
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
    public  Estudiante getEntidad() {
        return entidad;
    }

    public  void setEntidad(Estudiante entidad) {
        AdminEstudiantes_bean.entidad = entidad;
    }

    public  String getNombre() {
        return nombre;
    }

    public  void setNombre(String nombre) {
        AdminEstudiantes_bean.nombre = nombre;
    }

    public  String getFacultad() {
        return facultad;
    }

    public  void setFacultad(String facultad) {
        AdminEstudiantes_bean.facultad = facultad;
    }

    public  String getCarrera() {
        return carrera;
    }

    public  void setCarrera(String carrera) {
        AdminEstudiantes_bean.carrera = carrera;
    }

    public  String getDescripcion() {
        return descripcion;
    }

    public  void setDescripcion(String descripcion) {
        AdminEstudiantes_bean.descripcion = descripcion;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public  List<Estudiante> getLista_de_entidades() {
        return lista_de_entidades;
    }

    public  void setLista_de_entidades(List<Estudiante> lista_de_entidades) {
        this.lista_de_entidades = lista_de_entidades;
    }

//    public  Estudiante getEntidad_editar() {
//        return entidad_editar;
//    }
//
//    public  void setEntidad_editar(Estudiante entidad_editar) {
//        this.entidad_editar = entidad_editar;
//    }
}
