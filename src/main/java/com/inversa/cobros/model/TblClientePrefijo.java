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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "tbl_cliente_prefijo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblClientePrefijo.findAll", query = "SELECT t FROM TblClientePrefijo t"),
    @NamedQuery(name = "TblClientePrefijo.findById", query = "SELECT t FROM TblClientePrefijo t WHERE t.id = :id")})
public class TblClientePrefijo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TblCliente idCliente;
    @JoinColumn(name = "id_salida", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TblPrefijoSalida idSalida;

    public TblClientePrefijo() {
    }

    public TblClientePrefijo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TblCliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(TblCliente idCliente) {
        this.idCliente = idCliente;
    }

    public TblPrefijoSalida getIdSalida() {
        return idSalida;
    }

    public void setIdSalida(TblPrefijoSalida idSalida) {
        this.idSalida = idSalida;
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
        if (!(object instanceof TblClientePrefijo)) {
            return false;
        }
        TblClientePrefijo other = (TblClientePrefijo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.inversa.cobros.model.TblClientePrefijo[ id=" + id + " ]";
    }
    
}
