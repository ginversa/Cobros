/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.reporte.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name="GestionesProductividadPorHora")
public class GestionesProductividadPorHora implements Serializable{
    
    @Id
    private String indicador;
    
    @Column(name="nombrecartera")
    private String nombrecartera;
    
    @Column(name="hora7am")
    private BigDecimal hora7am;
    
    @Column(name="hora8am")
    private BigDecimal hora8am;
    
    @Column(name="hora9am")
    private BigDecimal hora9am;
    
    @Column(name="hora10am")
    private BigDecimal hora10am;
    
    @Column(name="hora11am")
    private BigDecimal hora11am;
    
    @Column(name="hora12am")
    private BigDecimal hora12am;
    
    @Column(name="hora1am")
    private BigDecimal hora1am;
    
    @Column(name="hora2am")
    private BigDecimal hora2am;
    
    @Column(name="hora3am")
    private BigDecimal hora3am;
    
    @Column(name="hora4am")
    private BigDecimal hora4am;
    
    @Column(name="hora5am")
    private BigDecimal hora5am;
    
    @Column(name="hora6am")
    private BigDecimal hora6am;
    
    @Column(name="total")
    private BigDecimal total;

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getNombrecartera() {
        return nombrecartera;
    }

    public void setNombrecartera(String nombrecartera) {
        this.nombrecartera = nombrecartera;
    }

    public BigDecimal getHora7am() {
        return hora7am;
    }

    public void setHora7am(BigDecimal hora7am) {
        this.hora7am = hora7am;
    }

    public BigDecimal getHora8am() {
        return hora8am;
    }

    public void setHora8am(BigDecimal hora8am) {
        this.hora8am = hora8am;
    }

    public BigDecimal getHora9am() {
        return hora9am;
    }

    public void setHora9am(BigDecimal hora9am) {
        this.hora9am = hora9am;
    }

    public BigDecimal getHora10am() {
        return hora10am;
    }

    public void setHora10am(BigDecimal hora10am) {
        this.hora10am = hora10am;
    }

    public BigDecimal getHora11am() {
        return hora11am;
    }

    public void setHora11am(BigDecimal hora11am) {
        this.hora11am = hora11am;
    }

    public BigDecimal getHora12am() {
        return hora12am;
    }

    public void setHora12am(BigDecimal hora12am) {
        this.hora12am = hora12am;
    }

    public BigDecimal getHora1am() {
        return hora1am;
    }

    public void setHora1am(BigDecimal hora1am) {
        this.hora1am = hora1am;
    }

    public BigDecimal getHora2am() {
        return hora2am;
    }

    public void setHora2am(BigDecimal hora2am) {
        this.hora2am = hora2am;
    }

    public BigDecimal getHora3am() {
        return hora3am;
    }

    public void setHora3am(BigDecimal hora3am) {
        this.hora3am = hora3am;
    }

    public BigDecimal getHora4am() {
        return hora4am;
    }

    public void setHora4am(BigDecimal hora4am) {
        this.hora4am = hora4am;
    }

    public BigDecimal getHora5am() {
        return hora5am;
    }

    public void setHora5am(BigDecimal hora5am) {
        this.hora5am = hora5am;
    }

    public BigDecimal getHora6am() {
        return hora6am;
    }

    public void setHora6am(BigDecimal hora6am) {
        this.hora6am = hora6am;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.indicador);
        hash = 79 * hash + Objects.hashCode(this.nombrecartera);
        hash = 79 * hash + Objects.hashCode(this.hora7am);
        hash = 79 * hash + Objects.hashCode(this.hora8am);
        hash = 79 * hash + Objects.hashCode(this.hora9am);
        hash = 79 * hash + Objects.hashCode(this.hora10am);
        hash = 79 * hash + Objects.hashCode(this.hora11am);
        hash = 79 * hash + Objects.hashCode(this.hora12am);
        hash = 79 * hash + Objects.hashCode(this.hora1am);
        hash = 79 * hash + Objects.hashCode(this.hora2am);
        hash = 79 * hash + Objects.hashCode(this.hora3am);
        hash = 79 * hash + Objects.hashCode(this.hora4am);
        hash = 79 * hash + Objects.hashCode(this.hora5am);
        hash = 79 * hash + Objects.hashCode(this.hora6am);
        hash = 79 * hash + Objects.hashCode(this.total);
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
        final GestionesProductividadPorHora other = (GestionesProductividadPorHora) obj;
        if (!Objects.equals(this.indicador, other.indicador)) {
            return false;
        }
        if (!Objects.equals(this.nombrecartera, other.nombrecartera)) {
            return false;
        }
        if (!Objects.equals(this.hora7am, other.hora7am)) {
            return false;
        }
        if (!Objects.equals(this.hora8am, other.hora8am)) {
            return false;
        }
        if (!Objects.equals(this.hora9am, other.hora9am)) {
            return false;
        }
        if (!Objects.equals(this.hora10am, other.hora10am)) {
            return false;
        }
        if (!Objects.equals(this.hora11am, other.hora11am)) {
            return false;
        }
        if (!Objects.equals(this.hora12am, other.hora12am)) {
            return false;
        }
        if (!Objects.equals(this.hora1am, other.hora1am)) {
            return false;
        }
        if (!Objects.equals(this.hora2am, other.hora2am)) {
            return false;
        }
        if (!Objects.equals(this.hora3am, other.hora3am)) {
            return false;
        }
        if (!Objects.equals(this.hora4am, other.hora4am)) {
            return false;
        }
        if (!Objects.equals(this.hora5am, other.hora5am)) {
            return false;
        }
        if (!Objects.equals(this.hora6am, other.hora6am)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GestionesProductividadPorHora{" + "indicador=" + indicador + ", nombrecartera=" + nombrecartera + ", hora7am=" + hora7am + ", hora8am=" + hora8am + ", hora9am=" + hora9am + ", hora10am=" + hora10am + ", hora11am=" + hora11am + ", hora12am=" + hora12am + ", hora1am=" + hora1am + ", hora2am=" + hora2am + ", hora3am=" + hora3am + ", hora4am=" + hora4am + ", hora5am=" + hora5am + ", hora6am=" + hora6am + ", total=" + total + '}';
    }
    
}
