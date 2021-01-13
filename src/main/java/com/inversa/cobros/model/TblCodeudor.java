/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "tbl_codeudor")
@NamedQueries({
    @NamedQuery(name = "TblCodeudor.findAll", query = "SELECT t FROM TblCodeudor t"),
    @NamedQuery(name = "TblCodeudor.findByIdCodeudor", query = "SELECT t FROM TblCodeudor t WHERE t.idCodeudor = :idCodeudor"),
    @NamedQuery(name = "TblCodeudor.findByCodigoCartera", query = "SELECT t FROM TblCodeudor t WHERE t.codigoCartera = :codigoCartera"),
    @NamedQuery(name = "TblCodeudor.findByDocumentoDeudor", query = "SELECT t FROM TblCodeudor t WHERE t.documentoDeudor = :documentoDeudor"),
    @NamedQuery(name = "TblCodeudor.findByDocumento", query = "SELECT t FROM TblCodeudor t WHERE t.documento = :documento"),
    @NamedQuery(name = "TblCodeudor.findByNombre", query = "SELECT t FROM TblCodeudor t WHERE t.nombre = :nombre")})
public class TblCodeudor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_codeudor")
    private Integer idCodeudor;
    @Size(max = 5)
    @Column(name = "codigo_cartera")
    private String codigoCartera;
    @Size(max = 50)
    @Column(name = "documento_deudor")
    private String documentoDeudor;
    @Size(max = 250)
    @Column(name = "documento")
    private String documento;
    @Size(max = 250)
    @Column(name = "nombre")
    private String nombre;

    public TblCodeudor() {
    }

    public TblCodeudor(Integer idCodeudor) {
        this.idCodeudor = idCodeudor;
    }

    public Integer getIdCodeudor() {
        return idCodeudor;
    }

    public void setIdCodeudor(Integer idCodeudor) {
        this.idCodeudor = idCodeudor;
    }

    public String getCodigoCartera() {
        return codigoCartera;
    }

    public void setCodigoCartera(String codigoCartera) {
        this.codigoCartera = codigoCartera;
    }

    public String getDocumentoDeudor() {
        return documentoDeudor;
    }

    public void setDocumentoDeudor(String documentoDeudor) {
        this.documentoDeudor = documentoDeudor;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCodeudor != null ? idCodeudor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblCodeudor)) {
            return false;
        }
        TblCodeudor other = (TblCodeudor) object;
        if ((this.idCodeudor == null && other.idCodeudor != null) || (this.idCodeudor != null && !this.idCodeudor.equals(other.idCodeudor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblCodeudor[ idCodeudor=" + idCodeudor + " ]";
    }
    
}
