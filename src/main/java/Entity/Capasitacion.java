/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import javax.persistence.*;
//import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rene2
 */
@Entity
@Table(name = "capasitacion")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Capasitacion.findAll", query = "SELECT c FROM Capasitacion c")
    , @NamedQuery(name = "Capasitacion.findById", query = "SELECT c FROM Capasitacion c WHERE c.id = :id")
    , @NamedQuery(name = "Capasitacion.findByTitulo", query = "SELECT c FROM Capasitacion c WHERE c.titulo = :titulo")
    , @NamedQuery(name = "Capasitacion.findByTema", query = "SELECT c FROM Capasitacion c WHERE c.tema = :tema")
    , @NamedQuery(name = "Capasitacion.findByEdicion", query = "SELECT c FROM Capasitacion c WHERE c.edicion = :edicion")
    , @NamedQuery(name = "Capasitacion.findByPrograma", query = "SELECT c FROM Capasitacion c WHERE c.programa = :programa")
    , @NamedQuery(name = "Capasitacion.findByFechaInicio", query = "SELECT c FROM Capasitacion c WHERE c.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Capasitacion.findByFechaFin", query = "SELECT c FROM Capasitacion c WHERE c.fechaFin = :fechaFin")
    , @NamedQuery(name = "Capasitacion.findByTipo", query = "SELECT c FROM Capasitacion c WHERE c.tipo = :tipo")})
public class Capasitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "tema")
    private String tema;
    @Basic(optional = false)
    @Column(name = "edicion")
    private String edicion;
    @Basic(optional = false)
    @Column(name = "programa")
    private String programa;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcapasitacion")
//    private List<CapasitacionEstudiante> capasitacionEstudianteList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcapasitacion")
//    private List<CapasitacionProfesor> capasitacionProfesorList;

    public Capasitacion() {
    }

    public Capasitacion(Integer id) {
        this.id = id;
    }

    public Capasitacion(Integer id, String titulo, String tema, String edicion, String programa, Date fechaInicio, Date fechaFin, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.tema = tema;
        this.edicion = edicion;
        this.programa = programa;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

//    @XmlTransient
//    public List<CapasitacionEstudiante> getCapasitacionEstudianteList() {
//        return capasitacionEstudianteList;
//    }
//
//    public void setCapasitacionEstudianteList(List<CapasitacionEstudiante> capasitacionEstudianteList) {
//        this.capasitacionEstudianteList = capasitacionEstudianteList;
//    }
//
//    @XmlTransient
//    public List<CapasitacionProfesor> getCapasitacionProfesorList() {
//        return capasitacionProfesorList;
//    }
//
//    public void setCapasitacionProfesorList(List<CapasitacionProfesor> capasitacionProfesorList) {
//        this.capasitacionProfesorList = capasitacionProfesorList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Capasitacion)) {
            return false;
        }
        Capasitacion other = (Capasitacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Capasitacion[ id=" + id + " ]";
    }
    
}
