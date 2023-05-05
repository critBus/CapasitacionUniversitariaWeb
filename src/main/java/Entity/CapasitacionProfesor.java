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
@Table(name = "capasitacion_profesor")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CapasitacionProfesor.findAll", query = "SELECT c FROM CapasitacionProfesor c")
    , @NamedQuery(name = "CapasitacionProfesor.findById", query = "SELECT c FROM CapasitacionProfesor c WHERE c.id = :id")})
public class CapasitacionProfesor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idcapasitacion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Capasitacion idcapasitacion;
    @JoinColumn(name = "idprofesor", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Profesor idprofesor;

    public CapasitacionProfesor() {
    }

    public CapasitacionProfesor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Capasitacion getIdcapasitacion() {
        return idcapasitacion;
    }

    public void setIdcapasitacion(Capasitacion idcapasitacion) {
        this.idcapasitacion = idcapasitacion;
    }

    public Profesor getIdprofesor() {
        return idprofesor;
    }

    public void setIdprofesor(Profesor idprofesor) {
        this.idprofesor = idprofesor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CapasitacionProfesor)) {
            return false;
        }
        CapasitacionProfesor other = (CapasitacionProfesor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CapasitacionProfesor[ id=" + id + " ]";
    }
    
}
