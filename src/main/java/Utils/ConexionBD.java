package Utils;
import Entity.*;
import Rest.*;

import java.util.List;

public class ConexionBD {
    private RestAuthorities restAuthorities=new RestAuthorities();
    private RestCapasitacion restCapasitacion=new RestCapasitacion();
    private RestCapasitacionEstudiante restCapasitacionEstudiante=new RestCapasitacionEstudiante();
    private RestCapasitacionProfesor restCapasitacionProfesor=new RestCapasitacionProfesor();
    private RestEstudiante restEstudiante=new RestEstudiante();
    private RestProfesor restProfesor=new RestProfesor();
    private RestUniversitario restUniversitario=new RestUniversitario();
    private RestUsers restUsers=new RestUsers();

    public List<Estudiante> obtenerListaDeEstudiantes(){
        return restEstudiante.findAll();
    }
    public List<Universitario> obtenerListaDeUniversitario(){
        return restUniversitario.findAll();
    }
    public Universitario obtenerUniversitario(Estudiante e){
        return e.getIduniversitario();
    }
    public Universitario obtenerUniversitario(String nombre){
        nombre=nombre.trim();
        List<Universitario> le=obtenerListaDeUniversitario();
        for (Universitario e:
                le) {

            if(e.getNombre().equals(nombre)){
                return e;
            }
        }
        return null;
    }
    public Universitario obtenerUniversitario(Universitario u){
        return obtenerUniversitario(u.getId());
    }
    public Estudiante obtenerEstudiante(Estudiante e){
        return obtenerEstudiante(e.getId());
    }
    public Universitario obtenerUniversitario(int id){
        return restUniversitario.findById(id);
    }
    public Estudiante obtenerEstudiante(int id){
        return restEstudiante.findById(id);
    }
    public Estudiante obtenerEstudiante(String nombre){
        nombre=nombre.trim();
        List<Estudiante> le=obtenerListaDeEstudiantes();
        for (Estudiante e:
             le) {
            Universitario u=obtenerUniversitario(e);
            if(u.getNombre().equals(nombre)){
                return e;
            }
        }
        return null;
    }
    public Universitario crearUniversitario(String nombre,
                                      String facultad,
                                      String carrera,
                                      String descripcion){
        Universitario u=new Universitario();
        int id=obtenerUltimoID_Universitario();
        u.setId(++id);
        u.setCarrera(carrera);
        u.setDescripcion(descripcion);
        u.setFacultad(facultad);
        u.setNombre(nombre);
        if(restUniversitario.create(u)){
            u=obtenerUniversitario(nombre);
            return u;
        }
        return null;

    }
    public Estudiante copiarEstudiante(Estudiante es){
        Estudiante e=new Estudiante();

        e.setCurso(es.getCurso());
        e.setSemestre(es.getSemestre());
        e.setIduniversitario(copiarUniversitario(es.getIduniversitario()));
        e.setId(es.getId());
        return e;
    }
    public Universitario copiarUniversitario(Universitario old){
        Universitario u=new Universitario();
        u.setId(old.getId());
        u.setNombre(old.getNombre());
        u.setFacultad(old.getFacultad());
        u.setDescripcion(old.getFacultad());
        u.setCarrera(old.getCarrera());
        return u;
    }
    public Estudiante crearEstudiante(String nombre,
                                      String facultad,
                                      String carrera,
                                      String descripcion,
                                      int curso,
                                      int semestre){
        Universitario u=crearUniversitario(nombre,facultad,carrera,descripcion);
        if(u!=null){
            Estudiante e=new Estudiante();
            int id=obtenerUltimoID_Estudiante();
            e.setId(++id);
            e.setCurso(curso);
            e.setSemestre(semestre);
            e.setIduniversitario(u);
            if(restEstudiante.create(e)){
                e=obtenerEstudiante(nombre);
                return e;
            }
        }
        return null;
    }
    public Universitario editar_Universitario(Universitario e){
        if(restUniversitario.update(e)){
            return obtenerUniversitario(e);
        }
        return null;
    }
    public Estudiante editar_Estudiante_ConSu_Universitario(Estudiante e){
        Universitario u=obtenerUniversitario(e);
        if(editar_Universitario(u)!=null
        ){
            e.setIduniversitario(obtenerUniversitario(u));
            if(restEstudiante.update(e)){
                return  obtenerEstudiante(e);
            }
        }
        return null;
    }

    public boolean eliminar_Estudiante(Estudiante e){
        return restEstudiante.delete(e.getId());
    }

    public int obtenerUltimoID_Universitario(){
        int mayor=0;
        List<Universitario> l=obtenerListaDeUniversitario();
        for (Universitario u:
             l) {
            if(u.getId()>mayor){
                mayor=u.getId();
            }
        }
        return mayor;
    }
    public int obtenerUltimoID_Estudiante(){
        int mayor=0;
        List<Estudiante> l=obtenerListaDeEstudiantes();
        for (Estudiante u:
                l) {
            if(u.getId()>mayor){
                mayor=u.getId();
            }
        }
        return mayor;
    }


}
