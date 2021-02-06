/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name = "tbl_central")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblCentral.findAll", query = "SELECT t FROM TblCentral t"),
    @NamedQuery(name = "TblCentral.findById", query = "SELECT t FROM TblCentral t WHERE t.id = :id"),
    @NamedQuery(name = "TblCentral.findByNombreCentral", query = "SELECT t FROM TblCentral t WHERE t.nombreCentral = :nombreCentral"),
    @NamedQuery(name = "TblCentral.findByProtocolo", query = "SELECT t FROM TblCentral t WHERE t.protocolo = :protocolo"),
    @NamedQuery(name = "TblCentral.findByIpCentral", query = "SELECT t FROM TblCentral t WHERE t.ipCentral = :ipCentral"),
    @NamedQuery(name = "TblCentral.findByDirectorio", query = "SELECT t FROM TblCentral t WHERE t.directorio = :directorio")})
public class TblCentral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "nombre_central")
    private String nombreCentral;
    @Size(max = 10)
    @Column(name = "protocolo")
    private String protocolo;
    @Size(max = 30)
    @Column(name = "ip_central")
    private String ipCentral;
    @Size(max = 30)
    @Column(name = "directorio")
    private String directorio;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblCentral", fetch = FetchType.EAGER)
    private List<TblPrefijoSalida> tblPrefijoSalidaList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCentral", fetch = FetchType.EAGER)
    private List<TblUrlLlamada> tblUrlLlamadaList;

    public TblCentral() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCentral() {
        return nombreCentral;
    }

    public void setNombreCentral(String nombreCentral) {
        this.nombreCentral = nombreCentral;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getIpCentral() {
        return ipCentral;
    }

    public void setIpCentral(String ipCentral) {
        this.ipCentral = ipCentral;
    }

    public String getDirectorio() {
        return directorio;
    }

    public void setDirectorio(String directorio) {
        this.directorio = directorio;
    }

    @XmlTransient
    public List<TblPrefijoSalida> getTblPrefijoSalidaList() {
        return tblPrefijoSalidaList;
    }

    public void setTblPrefijoSalidaList(List<TblPrefijoSalida> tblPrefijoSalidaList) {
        this.tblPrefijoSalidaList = tblPrefijoSalidaList;
    }

    @XmlTransient
    public List<TblUrlLlamada> getTblUrlLlamadaList() {
        return tblUrlLlamadaList;
    }

    public void setTblUrlLlamadaList(List<TblUrlLlamada> tblUrlLlamadaList) {
        this.tblUrlLlamadaList = tblUrlLlamadaList;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TblCentral other = (TblCentral) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "TblCentral{" + "id=" + id + ", nombreCentral=" + nombreCentral + ", protocolo=" + protocolo + ", ipCentral=" + ipCentral + ", directorio=" + directorio + ", tblPrefijoSalidaList=" + tblPrefijoSalidaList + ", tblUrlLlamadaList=" + tblUrlLlamadaList + '}';
    }
    
}
