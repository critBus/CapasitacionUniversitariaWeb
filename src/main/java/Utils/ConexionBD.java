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

    public List<Profesor> obtenerListaDeProfesores()throws Exception{
        return restProfesor.findAll();
    }
    public List<Estudiante> obtenerListaDeEstudiantes()throws Exception{
        return restEstudiante.findAll();
    }
    public List<Universitario> obtenerListaDeUniversitario()throws Exception{
        return restUniversitario.findAll();
    }
    public Universitario obtenerUniversitario(Profesor e)throws Exception{
        return e.getIduniversitario();
    }
    public Universitario obtenerUniversitario(Estudiante e)throws Exception{
        return e.getIduniversitario();
    }
    public Universitario obtenerUniversitario(String nombre)throws Exception{
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
    public Universitario obtenerUniversitario(Universitario u)throws Exception{
        return obtenerUniversitario(u.getId());
    }
    public Estudiante obtenerEstudiante(Estudiante e)throws Exception{
        return obtenerEstudiante(e.getId());
    }
    public Universitario obtenerUniversitario(int id)throws Exception{
        return restUniversitario.findById(id);
    }
    public Estudiante obtenerEstudiante(int id)throws Exception{
        return restEstudiante.findById(id);
    }
    public Profesor obtenerProfesor(Profesor p)throws Exception{
        return obtenerProfesor(p.getId());
    }
    public Profesor obtenerProfesor(int id)throws Exception{
        return restProfesor.findById(id);
    }
    public Estudiante obtenerEstudiante(String nombre)throws Exception{
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
    public Profesor obtenerProfesor(String nombre)throws Exception{
        nombre=nombre.trim();
        List<Profesor> le=obtenerListaDeProfesores();
        for (Profesor e:
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
                                      String descripcion)throws Exception{
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
    public Estudiante copiarEstudiante(Estudiante es)throws Exception{
        Estudiante e=new Estudiante();

        e.setCurso(es.getCurso());
        e.setSemestre(es.getSemestre());
        e.setIduniversitario(copiarUniversitario(es.getIduniversitario()));
        e.setId(es.getId());
        return e;
    }
    public Profesor copiarProfesor(Profesor es)throws Exception{
        Profesor e=new Profesor();

        e.setEspecialidad(es.getEspecialidad());
        e.setIduniversitario(copiarUniversitario(es.getIduniversitario()));
        e.setId(es.getId());
        return e;
    }
    public Universitario copiarUniversitario(Universitario old)throws Exception{
        Universitario u=new Universitario();
        u.setId(old.getId());
        u.setNombre(old.getNombre());
        u.setFacultad(old.getFacultad());
        u.setDescripcion(old.getDescripcion());
        u.setCarrera(old.getCarrera());
        return u;
    }
    private Estudiante crearEstudiante(Universitario u
                                        ,int curso,
                                       int semestre)throws Exception{
        Estudiante e=new Estudiante();
        int id=obtenerUltimoID_Estudiante();
        e.setId(++id);
        e.setCurso(curso);
        e.setSemestre(semestre);
        e.setIduniversitario(u);
        if(restEstudiante.create(e)){
            e=obtenerEstudiante(u.getNombre());
            return e;
        }
        return null;
    }
    public Estudiante crearEstudiante(String nombre,
                                      String facultad,
                                      String carrera,
                                      String descripcion,
                                      int curso,
                                      int semestre)throws Exception{
        Universitario u=crearUniversitario(nombre,facultad,carrera,descripcion);
        if(u!=null){
            return crearEstudiante(u,curso,semestre);
        }
        return null;
    }
    public Profesor crearProfesor(String nombre,
                                      String facultad,
                                      String carrera,
                                      String descripcion,
                                    String especialidad)throws Exception{
        Universitario u=crearUniversitario(nombre,facultad,carrera,descripcion);
        if(u!=null){
            Profesor e=new Profesor();
            int id=obtenerUltimoID_Profesor();
            e.setId(++id);
            e.setEspecialidad(especialidad);
            e.setIduniversitario(u);
            if(restProfesor.create(e)){
                e=obtenerProfesor(nombre);
                return e;
            }
        }
        return null;
    }
    public Profesor crearProfesorEstudiante(String nombre,
                                  String facultad,
                                  String carrera,
                                  String descripcion,
                                  String especialidad,
                                            int curso,
                                            int semestre)throws Exception{
        Profesor p=crearProfesor(nombre,facultad,carrera,descripcion,especialidad);
        if(p!=null){
            Estudiante e= crearEstudiante(obtenerUniversitario(p),curso,semestre);
            if(e!=null){
                return p;
            }

        }
        return null;
    }
    public Universitario editar_Universitario(Universitario e)throws Exception{
        if(restUniversitario.update(e)){
            return obtenerUniversitario(e);
        }
        return null;
    }
    public Estudiante editar_Estudiante_ConSu_Universitario(Estudiante e)throws Exception{
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
    public Profesor editar_Profesor_ConSu_Universitario(Profesor e)throws Exception{
        Universitario u=obtenerUniversitario(e);
        if(editar_Universitario(u)!=null
        ){
            e.setIduniversitario(obtenerUniversitario(u));
            if(restProfesor.update(e)){
                return  obtenerProfesor(e);
            }
        }
        return null;
    }
    public boolean eliminar_Estudiante(Estudiante e)throws Exception{
        return restEstudiante.delete(e.getId());
    }
    public boolean eliminar_Profesor(Profesor e)throws Exception{
        return restProfesor.delete(e.getId());
    }

    public int obtenerUltimoID_Universitario()throws Exception{
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
    public int obtenerUltimoID_Estudiante()throws Exception{
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
    public int obtenerUltimoID_Profesor()throws Exception{
        int mayor=0;
        List<Profesor> l=obtenerListaDeProfesores();
        for (Profesor u:
                l) {
            if(u.getId()>mayor){
                mayor=u.getId();
            }
        }
        return mayor;
    }


}
