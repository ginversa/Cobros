/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Z420WK
 */
    @Entity
@Table(name = "tbl_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblUsuario.findAll", query = "SELECT t FROM TblUsuario t"),
    @NamedQuery(name = "TblUsuario.findByIdPersona", query = "SELECT t FROM TblUsuario t WHERE t.idPersona = :idPersona"),
    @NamedQuery(name = "TblUsuario.findByUsuario", query = "SELECT t FROM TblUsuario t WHERE t.usuario = :usuario"),
    @NamedQuery(name = "TblUsuario.findByClave", query = "SELECT t FROM TblUsuario t WHERE t.clave = :clave"),
    @NamedQuery(name = "TblUsuario.findByUsuarioAndClave", query = "SELECT t FROM TblUsuario t WHERE t.usuario = :usuario AND t.clave = :clave"),
    @NamedQuery(name = "TblUsuario.findByCodigoGestor", query = "SELECT t FROM TblUsuario t WHERE t.codigoGestor = :codigoGestor"),
    @NamedQuery(name = "TblUsuario.findByEstado", query = "SELECT t FROM TblUsuario t WHERE t.estado = :estado"),
    @NamedQuery(name = "TblUsuario.findByUsuarioingreso", query = "SELECT t FROM TblUsuario t WHERE t.usuarioingreso = :usuarioingreso"),
    @NamedQuery(name = "TblUsuario.findByFechaingreso", query = "SELECT t FROM TblUsuario t WHERE t.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "TblUsuario.findByUsuariomodifico", query = "SELECT t FROM TblUsuario t WHERE t.usuariomodifico = :usuariomodifico"),
    @NamedQuery(name = "TblUsuario.findByFechamodifico", query = "SELECT t FROM TblUsuario t WHERE t.fechamodifico = :fechamodifico"),
    @NamedQuery(name = "TblUsuario.findByExtEnsion", query = "SELECT t FROM TblUsuario t WHERE t.extEnsion = :extEnsion")})
public class TblUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_persona")
    private Integer idPersona;
    @Size(max = 50)
    @Column(name = "usuario")
    private String usuario;
    @Size(max = 50)
    @Column(name = "clave")
    private String clave;
    @Size(max = 5)
    @Column(name = "codigo_gestor")
    private String codigoGestor;
    @Size(max = 3)
    @Column(name = "estado")
    private String estado;
    @Size(max = 50)
    @Column(name = "usuarioingreso")
    private String usuarioingreso;
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaingreso;
    @Size(max = 50)
    @Column(name = "usuariomodifico")
    private String usuariomodifico;
    @Column(name = "fechamodifico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamodifico;
    @Size(max = 5)
    @Column(name = "ext_ension")
    private String extEnsion;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private TblPersona tblPersona;
    @JoinColumn(name = "id_rolusuario", referencedColumnName = "id_rolusuario")
    @ManyToOne(fetch = FetchType.EAGER)
    private TblRolusuario idRolusuario;
    @OneToMany(mappedBy = "idUsuariosupervisor", fetch = FetchType.EAGER)
    private List<TblUsuario> tblUsuarioList;
    @JoinColumn(name = "id_usuariosupervisor", referencedColumnName = "id_persona")
    @ManyToOne(fetch = FetchType.EAGER)
    private TblUsuario idUsuariosupervisor;
    @OneToMany(mappedBy = "idPersona", fetch = FetchType.EAGER)
    private List<TblClienteUsuario> tblClienteUsuarioList;

    public TblUsuario() {
    }

    public TblUsuario(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCodigoGestor() {
        return codigoGestor;
    }

    public void setCodigoGestor(String codigoGestor) {
        this.codigoGestor = codigoGestor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioingreso() {
        return usuarioingreso;
    }

    public void setUsuarioingreso(String usuarioingreso) {
        this.usuarioingreso = usuarioingreso;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getUsuariomodifico() {
        return usuariomodifico;
    }

    public void setUsuariomodifico(String usuariomodifico) {
        this.usuariomodifico = usuariomodifico;
    }

    public Date getFechamodifico() {
        return fechamodifico;
    }

    public void setFechamodifico(Date fechamodifico) {
        this.fechamodifico = fechamodifico;
    }

    public String getExtEnsion() {
        return extEnsion;
    }

    public void setExtEnsion(String extEnsion) {
        this.extEnsion = extEnsion;
    }

    public TblPersona getTblPersona() {
        return tblPersona;
    }

    public void setTblPersona(TblPersona tblPersona) {
        this.tblPersona = tblPersona;
    }

    public TblRolusuario getIdRolusuario() {
        return idRolusuario;
    }

    public void setIdRolusuario(TblRolusuario idRolusuario) {
        this.idRolusuario = idRolusuario;
    }

    @XmlTransient
    public List<TblUsuario> getTblUsuarioList() {
        return tblUsuarioList;
    }

    public void setTblUsuarioList(List<TblUsuario> tblUsuarioList) {
        this.tblUsuarioList = tblUsuarioList;
    }

    public TblUsuario getIdUsuariosupervisor() {
        return idUsuariosupervisor;
    }

    public void setIdUsuariosupervisor(TblUsuario idUsuariosupervisor) {
        this.idUsuariosupervisor = idUsuariosupervisor;
    }

    @XmlTransient
    public List<TblClienteUsuario> getTblClienteUsuarioList() {
        return tblClienteUsuarioList;
    }

    public void setTblClienteUsuarioList(List<TblClienteUsuario> tblClienteUsuarioList) {
        this.tblClienteUsuarioList = tblClienteUsuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblUsuario)) {
            return false;
        }
        TblUsuario other = (TblUsuario) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblUsuario[ idPersona=" + idPersona + " ]";
    }
    
}
