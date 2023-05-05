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
public class AdminProfesores_bean {
    private  List<Profesor> lista_de_entidades = new ArrayList<Profesor>();

    private  String nombre = "";
    private   String facultad = "";
    private   String carrera = "";

    private   String descripcion = "";
    private   int curso=1;
    private   int semestre=1;
    
    public   String especialidad= "";
    public   boolean esEstudiante=false;

    public static  String ID_DLG_ADD="addDialog2";
    public static  String ID_DLG_EDIT="editDialog";
    public static  String ID_DATA_TABLE="dt-entidad";

    private   String facultad_estudiante = "";
    private   String carrera_estudiante = "";

    private  Profesor entidad;
//    private static Profesor entidad_editar;

    private static ConexionBD bd=new ConexionBD();
    public void init(){
        try{
            lista_de_entidades.clear();
            lista_de_entidades =bd.obtenerListaDeProfesores();
        }catch (Exception ex){
            responderException(ex);
        }



    }
    private void responderException(Exception ex){
        System.out.println("error en Profesor");
        ex.printStackTrace();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                , "Errores en el servidor", ex.getMessage()));
        PrimeFaces.current().ajax().update("form:messages");//, "form:"+ID_DATA_TABLE
    }
    public void clean_variables(){

        nombre = "";
        facultad = "";
        carrera = "";

        descripcion = "";
        curso=1;
        semestre=1;

        esEstudiante=false;
        especialidad="";
        facultad_estudiante="";
        carrera_estudiante="";
    }

    public void create(){
        try{
            nombre=nombre.trim();
            Profesor finded=bd.obtenerProfesor(nombre);
            if(finded!=null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                        , "Ya existe una persona en la universidad con este nombre", ""));
                PrimeFaces.current().ajax().update("form:messages");
                return;
            }
            facultad=facultad.trim();
            carrera=carrera.trim();
            if(descripcion!=null){
                descripcion=descripcion.trim();
            }
            especialidad=especialidad.trim();
            facultad_estudiante=facultad_estudiante.trim();
            carrera_estudiante=carrera_estudiante.trim();

            Profesor e=null;
            if(esEstudiante){
                e=bd.crearProfesorEstudiante(nombre,facultad,carrera,descripcion
                        ,especialidad,curso,semestre,facultad_estudiante,carrera_estudiante);
            }else{
                e=bd.crearProfesor(nombre,facultad,carrera,descripcion,especialidad);
            }

            if(e!=null){


                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO
                        , "Profesor Adicionado", ""));

                init();

                PrimeFaces.current().executeScript("PF('"+ID_DLG_ADD+"').hide()");
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                        , "Existen Errores en el formulario", ""));
            }
        }catch (Exception ex){
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                    , "Errores en el servidor", ex.getMessage()));
        }

        PrimeFaces.current().ajax().update("form:messages", "form:"+ID_DATA_TABLE);


    }
    public void copyedit(Profesor e) {
        try{
            if(e!=null){

                entidad=bd.copiarProfesor(e);
                esEstudiante=esProfesorEstudiante(e);
                if(esEstudiante){
                    Estudiante es=bd.obtenerEstudiante(e);
                    curso=es.getCurso();
                    semestre=es.getSemestre();
                    facultad_estudiante=es.getFacultad();
                    carrera_estudiante=es.getCarrera();
                }
            }
        }catch (Exception ex){
            responderException(ex);
        }
    }


    public boolean verSiEsEstudiante(){
        return esEstudiante;
    }
    public String stiloSiEsEstudiante(){
        return verSiEsEstudiante()?"":"display: none;";
    }
    public boolean esProfesorEstudiante(Profesor p){
        try{
            return bd.esEstudiante(p);
        }catch (Exception ex){
            responderException(ex);
            return false;
        }
    }
    public String getTextoEnTablaEsEstudiante(Profesor p){
        return esProfesorEstudiante(p)?"estudiante":"no";
    }
    public String getEstiloEnTablaEsEstudiante(Profesor p){
        return esProfesorEstudiante(p)?"green":"red";
    }

    public void edit(){
        try{
            Universitario u=bd.obtenerUniversitario(entidad);
            u.setNombre(u.getNombre().trim());
//            u.setCarrera(u.getCarrera().trim());
//            u.setFacultad(u.getFacultad().trim());
            if(u.getDescripcion()!=null){
                u.setDescripcion(u.getDescripcion().trim());
            }

            Profesor finded=bd.obtenerProfesor(entidad);
            if(finded == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                        , "Existen errores al editar el Profesor", "Profesor inexistente"));
            }
            else{
                if((!bd.obtenerUniversitario(finded).getNombre().equals(u.getNombre()))
                        &&bd.obtenerProfesor(u.getNombre())!=null
                ){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                            , "Ya existe una persona en la universidad con este nombre", ""));
                    PrimeFaces.current().ajax().update("form:messages");
                    return;
                }
                entidad.setEspecialidad(entidad.getEspecialidad().trim());
                entidad.setFacultad(entidad.getFacultad().trim());
                entidad.setCarrera(entidad.getCarrera().trim());
                facultad_estudiante=facultad_estudiante.trim();
                carrera_estudiante=carrera_estudiante.trim();
                if(bd.editar_ProfesorEstudiante_ConSu_Universitario(entidad,esEstudiante
                        ,curso,semestre,facultad_estudiante,carrera_estudiante)==null){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                            , "Existen errores al editar el Profesor", "Errores en el formulario"));


                }
                else{

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO
                            , "Profesor Actualizado", ""));

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

    public String getFacultad_estudiante() {
        return facultad_estudiante;
    }

    public void setFacultad_estudiante(String facultad_estudiante) {
        this.facultad_estudiante = facultad_estudiante;
    }

    public String getCarrera_estudiante() {
        return carrera_estudiante;
    }

    public void setCarrera_estudiante(String carrera_estudiante) {
        this.carrera_estudiante = carrera_estudiante;
    }

    public void delete(){
        try{
            if(entidad==null)
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                        , "Existen errores al eliminar el Profesor", "Profesor inexistente"));
            else {

                if (bd.eliminar_Profesor_Cascade(entidad)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO
                            , "Profesor Eliminado", ""));
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR
                            , "Errores en el servidor", "No se pudo eliminar el Profesor"));
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
    public  Profesor getEntidad() {
        return entidad;
    }

    public  void setEntidad(Profesor entidad) {
        this.entidad = entidad;
    }

    public  String getNombre() {
        return nombre;
    }

    public  void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public  String getFacultad() {
        return facultad;
    }

    public  void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public  String getCarrera() {
        return carrera;
    }

    public  void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public  String getDescripcion() {
        return descripcion;
    }

    public  void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public  List<Profesor> getLista_de_entidades() {
        return lista_de_entidades;
    }

    public  void setLista_de_entidades(List<Profesor> lista_de_entidades) {
        this.lista_de_entidades = lista_de_entidades;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isEsEstudiante() {
        return esEstudiante;
    }

    public void setEsEstudiante(boolean esEstudiante) {
        this.esEstudiante = esEstudiante;
    }
}
