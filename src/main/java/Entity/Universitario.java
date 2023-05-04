/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rene2
 */
@Entity
@Table(name = "universitario")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Universitario.findAll", query = "SELECT u FROM Universitario u")
    , @NamedQuery(name = "Universitario.findById", query = "SELECT u FROM Universitario u WHERE u.id = :id")
    , @NamedQuery(name = "Universitario.findByNombre", query = "SELECT u FROM Universitario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Universitario.findByFacultad", query = "SELECT u FROM Universitario u WHERE u.facultad = :facultad")
    , @NamedQuery(name = "Universitario.findByCarrera", query = "SELECT u FROM Universitario u WHERE u.carrera = :carrera")
    , @NamedQuery(name = "Universitario.findByDescripcion", query = "SELECT u FROM Universitario u WHERE u.descripcion = :descripcion")})
public class Universitario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "facultad")
    private String facultad;
    @Basic(optional = false)
    @Column(name = "carrera")
    private String carrera;
    @Column(name = "descripcion")
    private String descripcion;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iduniversitario")
//    private List<Estudiante> estudianteList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iduniversitario")
//    private List<Profesor> profesorList;

    public Universitario() {
    }

    public Universitario(Integer id) {
        this.id = id;
    }

    public Universitario(Integer id, String nombre, String facultad, String carrera) {
        this.id = id;
        this.nombre = nombre;
        this.facultad = facultad;
        this.carrera = carrera;
    }

    public Universitario(String nombre, String facultad, String carrera, String descripcion) {
        this.nombre = nombre;
        this.facultad = facultad;
        this.carrera = carrera;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    @XmlTransient
//    public List<Estudiante> getEstudianteList() {
//        return estudianteList;
//    }
//
//    public void setEstudianteList(List<Estudiante> estudianteList) {
//        this.estudianteList = estudianteList;
//    }
//
//    @XmlTransient
//    public List<Profesor> getProfesorList() {
//        return profesorList;
//    }
//
//    public void setProfesorList(List<Profesor> profesorList) {
//        this.profesorList = profesorList;
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
        if (!(object instanceof Universitario)) {
            return false;
        }
        Universitario other = (Universitario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "entidades.Universitario[ id=" + id + " ]";
//    }


    @Override
    public String toString() {
        return "Universitario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", facultad='" + facultad + '\'' +
                ", carrera='" + carrera + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
