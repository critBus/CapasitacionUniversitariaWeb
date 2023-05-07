package Utils;
import Entity.*;
import Rest.*;

import java.util.ArrayList;
import java.util.Date;
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

    private List<CapasitacionEstudiante> obtenerListaDeCapasitacionEstudiante(Capasitacion c)throws Exception{
        List<CapasitacionEstudiante> lce=obtenerListaDeCapasitacionEstudiante();
        List<CapasitacionEstudiante> l=new ArrayList<>();
        for (CapasitacionEstudiante ce :
                lce) {
            if (ce.getIdcapasitacion().getId().equals(c.getId())) {
                l.add(ce);
            }
        }
        return l;
    }
    private List<CapasitacionProfesor> obtenerListaDeCapasitacionProfesor(Capasitacion c)throws Exception{
        List<CapasitacionProfesor> lce=obtenerListaDeCapasitacionProfesor();
        List<CapasitacionProfesor> l=new ArrayList<>();
        for (CapasitacionProfesor ce :
                lce) {
            if (ce.getIdcapasitacion().getId().equals(c.getId())) {
                l.add(ce);
            }
        }
        return l;
    }

    private List<CapasitacionEstudiante> obtenerListaDeCapasitacionEstudiante()throws Exception{
        return restCapasitacionEstudiante.findAll();
    }
    private List<CapasitacionProfesor> obtenerListaDeCapasitacionProfesor()throws Exception{
        return restCapasitacionProfesor.findAll();
    }
    private List<Capasitacion> obtenerListaDeCapasitacion()throws Exception{
        return restCapasitacion.findAll();
    }
    public List<Capasitacion> obtenerListaDeCapasitacion(TipoDeCapasitacion tipo)throws Exception{
        List<Capasitacion> lc=restCapasitacion.findAll();

        List<Capasitacion> lr=new ArrayList<>();
        for (Capasitacion c :
                lc) {
            if(c.getTipo().equals(tipo.toString())){
                lr.add(c);
            }
        }
        return lr;
    }
    public List<Profesor> obtenerListaDeProfesoresQueNoEstan(Capasitacion c)throws Exception{
        List<Profesor> lpe=obtenerListaDeProfesores(c);
        List<Profesor> lp=obtenerListaDeProfesores();

        for (Profesor p_esta :
                lpe) {
            for (Profesor p:
                    lp) {
                if(p.getId().equals(p_esta.getId())){
                    lp.remove(p);
                    break;
                }
            }
        }
        return lp;
    }
    public List<Estudiante> obtenerListaDeEstudianteQueNoEstan(Capasitacion c)throws Exception{
        List<Estudiante> lpe=obtenerListaDeEstudiante(c);
        List<Estudiante> lp=obtenerListaDeEstudiantes();

        for (Estudiante p_esta :
                lpe) {
            for (Estudiante p:
                    lp) {
                if(p.getId().equals(p_esta.getId())){
                    lp.remove(p);
                    break;
                }
            }
        }
        return lp;
    }
    public List<Profesor> obtenerListaDeProfesores(Capasitacion c)throws Exception{
        List<Profesor> lp=new ArrayList<>();
        List<CapasitacionProfesor> lcp=obtenerListaDeCapasitacionProfesor(c);
        for (CapasitacionProfesor cp:
             lcp){
            lp.add(cp.getIdprofesor());
        }
        return lp;
    }
    public List<Estudiante> obtenerListaDeEstudiante(Capasitacion c)throws Exception{
        List<Estudiante> lp=new ArrayList<>();
        List<CapasitacionEstudiante> lcp=obtenerListaDeCapasitacionEstudiante(c);
        for (CapasitacionEstudiante cp:
                lcp){
            lp.add(cp.getIdestudiante());
        }
        return lp;
    }
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
    public Capasitacion obtenerCapasitacion(int id)throws Exception{
        return restCapasitacion.findById(id);
    }

    public CapasitacionProfesor obtenerCapasitacionProfesor(Capasitacion c,Profesor p)throws Exception{
        List<CapasitacionProfesor> lc=obtenerListaDeCapasitacionProfesor(c);
        for (CapasitacionProfesor cp :
                lc) {
            if (cp.getIdprofesor().getId().equals(p.getId())) {
                return cp;
            }
        }
        return null;
    }
    public CapasitacionEstudiante obtenerCapasitacionEstudiante(Capasitacion c,Estudiante p)throws Exception{
        List<CapasitacionEstudiante> lc=obtenerListaDeCapasitacionEstudiante(c);
        for (CapasitacionEstudiante cp :
                lc) {
            if (cp.getIdestudiante().getId().equals(p.getId())) {
                return cp;
            }
        }
        return null;
    }
    public CapasitacionProfesor obtenerCapasitacionProfesor(CapasitacionProfesor c)throws Exception{
        return obtenerCapasitacionProfesor(c.getId());
    }
    public CapasitacionProfesor obtenerCapasitacionProfesor(int id)throws Exception{
        return restCapasitacionProfesor.findById(id);
    }
    public CapasitacionEstudiante obtenerCapasitacionEstudiante(CapasitacionEstudiante c)throws Exception{
        return obtenerCapasitacionEstudiante(c.getId());
    }
    public CapasitacionEstudiante obtenerCapasitacionEstudiante(int id)throws Exception{
        return restCapasitacionEstudiante.findById(id);
    }
    public Capasitacion obtenerCapasitacion(Capasitacion c)throws Exception{
        return obtenerCapasitacion(c.getId());
    }
    public Capasitacion obtenerCapasitacion(TipoDeCapasitacion tipo,String nombre)throws Exception{
        nombre=nombre.trim();
        List<Capasitacion> le=obtenerListaDeCapasitacion(tipo);
        for (Capasitacion e:
                le) {

            if(e.getTitulo().equals(nombre)){
                return e;
            }
        }
        return null;
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

                                      String descripcion)throws Exception{
        Universitario u=new Universitario();
        int id=obtenerUltimoID_Universitario();
        u.setId(++id);
        //u.setCarrera(carrera);
        u.setDescripcion(descripcion);
        //u.setFacultad(facultad);
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
        e.setCarrera(es.getCarrera());
        e.setFacultad(es.getFacultad());
        e.setId(es.getId());
        return e;
    }
    public Profesor copiarProfesor(Profesor es)throws Exception{
        Profesor e=new Profesor();

        e.setEspecialidad(es.getEspecialidad());
        e.setIduniversitario(copiarUniversitario(es.getIduniversitario()));
        e.setCarrera(es.getCarrera());
        e.setFacultad(es.getFacultad());
        e.setId(es.getId());
        return e;
    }
    public Universitario copiarUniversitario(Universitario old)throws Exception{
        Universitario u=new Universitario();
        u.setId(old.getId());
        u.setNombre(old.getNombre());
        //u.setFacultad(old.getFacultad());
        u.setDescripcion(old.getDescripcion());
        //u.setCarrera(old.getCarrera());
        return u;
    }
    public Capasitacion copiarCapasitacion(Capasitacion old)throws Exception{

        Capasitacion u=new Capasitacion();

        u.setId(old.getId());
        u.setTipo(old.getTipo());
        u.setTitulo(old.getTitulo());
        u.setTema(old.getTema());
        u.setPrograma(old.getPrograma());
        u.setFechaInicio(old.getFechaInicio());
        u.setFechaFin(old.getFechaFin());
        u.setEdicion(old.getEdicion());

        return u;
    }
    private Estudiante crearEstudiante(Universitario u,
                                       String facultad,
                                       String carrera,

                                        int curso,
                                       int semestre)throws Exception{
        Estudiante e=new Estudiante();
        int id=obtenerUltimoID_Estudiante();
        e.setId(++id);
        e.setCarrera(carrera);
        e.setCurso(curso);
        e.setSemestre(semestre);
        e.setFacultad(facultad);
        e.setIduniversitario(u);
        e=restEstudiante.createAndGet(e);
        if(e!=null){
            //e=obtenerEstudiante(u.getNombre());
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
        Universitario u=crearUniversitario(nombre,descripcion);
        if(u!=null){
            return crearEstudiante(u,facultad,carrera,curso,semestre);
        }
        return null;
    }
    public Profesor crearProfesor(String nombre,
                                      String facultad,
                                      String carrera,
                                      String descripcion,
                                    String especialidad)throws Exception{
        Universitario u=crearUniversitario(nombre,descripcion);
        if(u!=null){
            Profesor e=new Profesor();
            int id=obtenerUltimoID_Profesor();
            e.setId(++id);
            e.setEspecialidad(especialidad);
            e.setCarrera(carrera);
            e.setFacultad(facultad);
            e.setIduniversitario(u);
            e=restProfesor.createAndGet(e);
            if(e!=null){
                //e=obtenerProfesor(nombre);
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
                                            int semestre,
                                            String facultad_Estudiante,
                                            String carrera_Estudiante
    )throws Exception{
        Profesor p=crearProfesor(nombre,facultad,carrera,descripcion,especialidad);
        if(p!=null){
            Estudiante e= crearEstudiante(obtenerUniversitario(p),facultad_Estudiante,carrera_Estudiante,curso,semestre);
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
    public Capasitacion editar_Capasitacion(
            Capasitacion e
            ,List<Profesor> profesores
            ,List<Estudiante> estudiantes)throws Exception{

        if(restCapasitacion.update(e)){
            e=obtenerCapasitacion(e);
            if(e!=null){
                List<Profesor> lpe=obtenerListaDeProfesores(e);
                List<Profesor> lpe_QueSiEstan=new ArrayList<>();
                for (Profesor p_Anterior:
                     lpe) {
                    boolean estaProfesorActual=false;
                    for (Profesor p_Actual:
                            profesores) {
                        if(p_Actual.getId().equals(p_Anterior.getId())){
                            estaProfesorActual=true;
                            break;
                        }
                    }
                    if(!estaProfesorActual){
                        CapasitacionProfesor cp=obtenerCapasitacionProfesor(e,p_Anterior);
                        if(cp==null||!eliminar_CapasitacionProfesor(cp)){
                            return null;
                        }
                    }else{
                        lpe_QueSiEstan.add(p_Anterior);
                    }
                }
                for (Profesor p_Actual :
                        profesores) {
                    boolean estaProfesorActual=false;
                    for (Profesor p_Anterior:
                            lpe_QueSiEstan){
                        if(p_Actual.getId().equals(p_Anterior.getId())){
                            estaProfesorActual=true;
                            break;
                        }
                    }
                    if(!estaProfesorActual){
                        if(crearCapasitacionProfesor(e,p_Actual)==null){
                            return null;
                        }
                    }
                }


                List<Estudiante> lpe2=obtenerListaDeEstudiante(e);
                List<Estudiante> lpe2_QueSiEstan=new ArrayList<>();
                for (Estudiante p_Anterior:
                        lpe2) {
                    boolean estaActual=false;
                    for (Estudiante p_Actual:
                            estudiantes) {
                        if(p_Actual.getId().equals(p_Anterior.getId())){
                            estaActual=true;
                            break;
                        }
                    }
                    if(!estaActual){
                        CapasitacionEstudiante cp=obtenerCapasitacionEstudiante(e,p_Anterior);
                        if(cp==null||!eliminar_CapasitacionEstudiante(cp)){
                            return null;
                        }
                    }else{
                        lpe2_QueSiEstan.add(p_Anterior);
                    }
                }
                for (Estudiante p_Actual :
                        estudiantes) {
                    boolean estaActual=false;
                    for (Estudiante p_Anterior:
                            lpe2_QueSiEstan){
                        if(p_Actual.getId().equals(p_Anterior.getId())){
                            estaActual=true;
                            break;
                        }
                    }
                    if(!estaActual){
                        if(crearCapasitacionEstudiante(e,p_Actual)==null){
                            return null;
                        }
                    }
                }


                return e;
            }
        }
        return null;
    }
    public Estudiante editar_Estudiante_ConSu_Universitario(Estudiante e)throws Exception{
        Universitario u=obtenerUniversitario(e);
        if(editar_Universitario(u)!=null
        ){
            e.setIduniversitario(obtenerUniversitario(u));
            return editar_Estudiante(e);
//            if(restEstudiante.update(e)){
//                return  obtenerEstudiante(e);
//            }
        }
        return null;
    }
    //es privado pq no lo utilizo en otro lado fuera de esta clase
    private Estudiante editar_Estudiante(Estudiante e)throws Exception{
        if(restEstudiante.update(e)){
            return  obtenerEstudiante(e);
        }
        return null;
    }
    //es privado pq no lo utilizo en otro lado fuera de esta clase
    private Profesor editar_Profesor_ConSu_Universitario(Profesor e)throws Exception{
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

    public Profesor editar_ProfesorEstudiante_ConSu_Universitario(Profesor p
            ,boolean esEstudiante,int curso,int semestre,String facultad_Estudiante,
                                                                  String carrera_Estudiante)throws Exception{


        p=editar_Profesor_ConSu_Universitario(p);
        if(p==null){
            return null;
        }
        Universitario u=obtenerUniversitario(p);
        if(u==null){
            return null;
        }
        boolean elProfesorEsAnteriormenteEstudiante=esEstudiante(p);
        if(esEstudiante){
            if(elProfesorEsAnteriormenteEstudiante){
                Estudiante e=obtenerEstudiante(p);
                if(e!=null){
                    e.setFacultad(facultad_Estudiante);
                    e.setCarrera(carrera_Estudiante);
                    e.setCurso(curso);
                    e.setSemestre(semestre);
                    e=editar_Estudiante(e);
                    if(e!=null){
                        return p;
                    }
                }
            }else{
                Estudiante e=crearEstudiante(u,facultad_Estudiante,carrera_Estudiante,curso,semestre);
                if(e!=null){
                    return p;
                }
            }

        }else if(elProfesorEsAnteriormenteEstudiante){
            Estudiante e=obtenerEstudiante(p);
            if(e!=null&&eliminar_Estudiante(e)){
                return p;

            }
        }else{
            return p;
        }

        return null;
    }
    public boolean eliminar_Estudiante_YDatosDeUniversitarioSinProfesor(Estudiante e)throws Exception{
        boolean esProfesor=esProfesor(e);
        Universitario u=obtenerUniversitario(e);
        if(u==null){
            return false;
        }
        if(eliminar_Estudiante(e)&&!esProfesor){
            return eliminar_Universitario(u);
        }
        return false;
    }
    private boolean eliminar_Estudiante(Estudiante e)throws Exception{
        return restEstudiante.delete(e.getId());
    }
    private boolean eliminar_Universitario(Universitario e)throws Exception{
        return restUniversitario.delete(e.getId());
    }
    private boolean eliminar_Profesor(Profesor e)throws Exception{
        return restProfesor.delete(e.getId());
    }
    private boolean eliminar_CapasitacionEstudiante(CapasitacionEstudiante e)throws Exception{
        return restCapasitacionEstudiante.delete(e.getId());
    }
    private boolean eliminar_CapasitacionProfesor(CapasitacionProfesor e)throws Exception{
        return restCapasitacionProfesor.delete(e.getId());
    }
    public boolean eliminar_Profesor_Cascade(Profesor p)throws Exception{

        if(esEstudiante(p)){
            Estudiante e=obtenerEstudiante(p);
            eliminar_Estudiante(e);
        }
        Universitario u=obtenerUniversitario(p);
        if(u==null){
            return false;
        }
        if(eliminar_Profesor(p)){return eliminar_Universitario(u);}
        return false;
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
    public int obtenerUltimoID_Capasitacion()throws Exception{
        int mayor=0;
        List<Capasitacion> l=obtenerListaDeCapasitacion();
        for (Capasitacion u:
                l) {
            if(u.getId()>mayor){
                mayor=u.getId();
            }
        }
        return mayor;
    }

    public int obtenerUltimoID_CapasitacionProfesor()throws Exception{
        int mayor=0;
        List<CapasitacionProfesor> l=obtenerListaDeCapasitacionProfesor();
        for (CapasitacionProfesor u:
                l) {
            if(u.getId()>mayor){
                mayor=u.getId();
            }
        }
        return mayor;
    }
    public int obtenerUltimoID_CapasitacionEstudiante()throws Exception{
        int mayor=0;
        List<CapasitacionEstudiante> l=obtenerListaDeCapasitacionEstudiante();
        for (CapasitacionEstudiante u:
                l) {
            if(u.getId()>mayor){
                mayor=u.getId();
            }
        }
        return mayor;
    }

    public boolean esEstudiante(Profesor p)throws Exception{
        return obtenerEstudiante(p)!=null;
    }
    public boolean esProfesor(Estudiante p)throws Exception{
        return obtenerProfesor(p)!=null;
    }
    public Estudiante obtenerEstudiante(Profesor p)throws Exception{
        Universitario u=obtenerUniversitario(p);
        if(u!=null){
            return obtenerEstudiante(u.getNombre());
        }
        return null;

    }
    public Profesor obtenerProfesor(Estudiante p)throws Exception{
        Universitario u=obtenerUniversitario(p);
        if(u!=null){
            return obtenerProfesor(u.getNombre());
        }
        return null;

    }
    public CapasitacionProfesor crearCapasitacionProfesor(Capasitacion c,Profesor p)throws Exception{
        CapasitacionProfesor cp=new CapasitacionProfesor();
        int id=obtenerUltimoID_CapasitacionProfesor();
        cp.setId(++id);
        cp.setIdcapasitacion(c);
        cp.setIdprofesor(p);

        cp=restCapasitacionProfesor.createAndGet(cp);
        if(cp!=null){
            //cp=obtenerCapasitacionProfesor(id);
            return cp;
        }
        return null;
    }
    public CapasitacionEstudiante crearCapasitacionEstudiante(Capasitacion c,Estudiante p)throws Exception{
        CapasitacionEstudiante cp=new CapasitacionEstudiante();
        int id=obtenerUltimoID_CapasitacionEstudiante();
        cp.setId(++id);
        cp.setIdcapasitacion(c);
        cp.setIdestudiante(p);
        cp=restCapasitacionEstudiante.createAndGet(cp);
        if(cp!=null){
            //cp=obtenerCapasitacionEstudiante(id);
            return cp;
        }
        return null;
    }
    public Capasitacion crearCapasitacion(String titulo,
                                           String tema,
                                           String edicion,
                                           String programa,
                                           Date fechaDeInicio,
                                           Date fechaDeFin,
                                           TipoDeCapasitacion tipo
                                          ,List<Profesor> profesores
                                          ,List<Estudiante> estudiantes
    )throws Exception{
        Capasitacion u=new Capasitacion();
        int id=obtenerUltimoID_Capasitacion();
        u.setId(++id);
        u.setEdicion(edicion);
        u.setFechaFin(fechaDeFin);
        u.setFechaInicio(fechaDeInicio);
        u.setPrograma(programa);
        u.setTema(tema);
        u.setTitulo(titulo);
        u.setTipo(tipo.toString());

        u=restCapasitacion.createAndGet(u);
        id=u.getId();
        if(u!=null){
            u=obtenerCapasitacion(tipo,titulo);
            if(u!=null){
                for (Profesor p:
                        profesores) {
                    if(crearCapasitacionProfesor(u,p)==null){
                        return null;
                    }
                }
                for (Estudiante p:
                        estudiantes) {
                    if(crearCapasitacionEstudiante(u,p)==null){
                        return null;
                    }
                }
                return u;
            }


        }

        return null;

    }
    public boolean eliminar_Capasitacion_Cascade(Capasitacion e)throws Exception{
        List<CapasitacionEstudiante> lce=obtenerListaDeCapasitacionEstudiante(e);
        for (CapasitacionEstudiante ce :
                lce) {
            if(!eliminar_CapasitacionEstudiante(ce)){
                return false;
            }

        }
        List<CapasitacionProfesor> lcp=obtenerListaDeCapasitacionProfesor(e);
        for (CapasitacionProfesor cp :
                lcp) {
            if(!eliminar_CapasitacionProfesor(cp)){
                return false;
            }
        }
        return eliminar_Capasitacion(e);

    }
    private boolean eliminar_Capasitacion(Capasitacion e)throws Exception{
        return restCapasitacion.delete(e.getId());
    }

}
