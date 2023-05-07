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
@Table(name = "estudiante")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e")
    , @NamedQuery(name = "Estudiante.findById", query = "SELECT e FROM Estudiante e WHERE e.id = :id")
    , @NamedQuery(name = "Estudiante.findByFacultad", query = "SELECT e FROM Estudiante e WHERE e.facultad = :facultad")
    , @NamedQuery(name = "Estudiante.findByCarrera", query = "SELECT e FROM Estudiante e WHERE e.carrera = :carrera")
    , @NamedQuery(name = "Estudiante.findByCurso", query = "SELECT e FROM Estudiante e WHERE e.curso = :curso")
    , @NamedQuery(name = "Estudiante.findBySemestre", query = "SELECT e FROM Estudiante e WHERE e.semestre = :semestre")})
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "facultad")
    private String facultad;
    @Basic(optional = false)
    @Column(name = "carrera")
    private String carrera;
    @Basic(optional = false)
    @Column(name = "curso")
    private int curso;
    @Basic(optional = false)
    @Column(name = "semestre")
    private int semestre;
    @JoinColumn(name = "iduniversitario", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Universitario iduniversitario;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idestudiante")
//    private List<CapasitacionEstudiante> capasitacionEstudianteList;

    public Estudiante() {
    }

    public Estudiante(Integer id) {
        this.id = id;
    }

    public Estudiante(Integer id, String facultad, String carrera, int curso, int semestre) {
        this.id = id;
        this.facultad = facultad;
        this.carrera = carrera;
        this.curso = curso;
        this.semestre = semestre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Universitario getIduniversitario() {
        return iduniversitario;
    }

    public void setIduniversitario(Universitario iduniversitario) {
        this.iduniversitario = iduniversitario;
    }

//    @XmlTransient
//    public List<CapasitacionEstudiante> getCapasitacionEstudianteList() {
//        return capasitacionEstudianteList;
//    }
//
//    public void setCapasitacionEstudianteList(List<CapasitacionEstudiante> capasitacionEstudianteList) {
//        this.capasitacionEstudianteList = capasitacionEstudianteList;
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
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + id +
                ", facultad='" + facultad + '\'' +
                ", carrera='" + carrera + '\'' +
                ", curso=" + curso +
                ", semestre=" + semestre +
                ", iduniversitario=" + iduniversitario +
                '}';
    }
}
