/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import javax.persistence.*;
//import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

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
    @Column(name = "descripcion")
    private String descripcion;
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "iduniversitario")
//    private Estudiante estudiante;
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "iduniversitario")
//    private Profesor profesor;

    public Universitario() {
    }

    public Universitario(Integer id) {
        this.id = id;
    }

    public Universitario(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    public Estudiante getEstudiante() {
//        return estudiante;
//    }
//
//    public void setEstudiante(Estudiante estudiante) {
//        this.estudiante = estudiante;
//    }
//
//    public Profesor getProfesor() {
//        return profesor;
//    }
//
//    public void setProfesor(Profesor profesor) {
//        this.profesor = profesor;
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

    @Override
    public String toString() {
        return "entidades.Universitario[ id=" + id + " ]";
    }
    
}
