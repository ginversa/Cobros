/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.reporte.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Z420WK
 */
@Entity
@Table(name="GestionProductividadPorAsesor")
public class GestionProductividadPorAsesor implements Serializable{
    
    @Id
    private String usuario;
    
    @Column(name="nombrecartera")
    private String nombrecartera;
    
    @Column(name="fechaGestion")
    private BigDecimal fechaGestion;
    
    @Column(name="agentes")
    private BigDecimal agentes;
    
    @Column(name="workload")
    private BigDecimal workload;
    
    @Column(name="contactoTitular")
    private BigDecimal rpc;
    
    @Column(name="por_contactoTitular")
    private BigDecimal por_RPC;
    
    @Column(name="promesadepago")
    private BigDecimal pdp;
    
    @Column(name="por_promesadepago")
    private BigDecimal por_PDP;
    
    @Column(name="valorworkload")
    private BigDecimal valorworkload;
    
    @Column(name="valor_promesadepago")
    private BigDecimal valor_pdp;
    
    @Column(name="valorpromediopromesa")
    private BigDecimal CCP;
    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombrecartera() {
        return nombrecartera;
    }

    public void setNombrecartera(String nombrecartera) {
        this.nombrecartera = nombrecartera;
    }

    public BigDecimal getFechaGestion() {
        return fechaGestion;
    }

    public void setFechaGestion(BigDecimal fechaGestion) {
        this.fechaGestion = fechaGestion;
    }

    public BigDecimal getAgentes() {
        return agentes;
    }

    public void setAgentes(BigDecimal agentes) {
        this.agentes = agentes;
    }

    public BigDecimal getWorkload() {
        return workload;
    }

    public void setWorkload(BigDecimal workload) {
        this.workload = workload;
    }

    public BigDecimal getRpc() {
        return rpc;
    }

    public void setRpc(BigDecimal rpc) {
        this.rpc = rpc;
    }

    public BigDecimal getPor_RPC() {
        return por_RPC;
    }

    public void setPor_RPC(BigDecimal por_RPC) {
        this.por_RPC = por_RPC;
    }

    public BigDecimal getPdp() {
        return pdp;
    }

    public void setPdp(BigDecimal pdp) {
        this.pdp = pdp;
    }

    public BigDecimal getPor_PDP() {
        return por_PDP;
    }

    public void setPor_PDP(BigDecimal por_PDP) {
        this.por_PDP = por_PDP;
    }

    public BigDecimal getValorworkload() {
        return valorworkload;
    }

    public void setValorworkload(BigDecimal valorworkload) {
        this.valorworkload = valorworkload;
    }

    public BigDecimal getValor_pdp() {
        return valor_pdp;
    }

    public void setValor_pdp(BigDecimal valor_pdp) {
        this.valor_pdp = valor_pdp;
    }

    public BigDecimal getCCP() {
        return CCP;
    }

    public void setCCP(BigDecimal CCP) {
        this.CCP = CCP;
    }

    @Override
    public String toString() {
        return "GestionesProductividadPorAsesor{" + "usuario=" + usuario + ", nombrecartera=" + nombrecartera + ", fechaGestion=" + fechaGestion + ", agentes=" + agentes + ", workload=" + workload + ", rpc=" + rpc + ", por_RPC=" + por_RPC + ", pdp=" + pdp + ", por_PDP=" + por_PDP + ", valorworkload=" + valorworkload + ", valor_pdp=" + valor_pdp + ", CCP=" + CCP + '}';
    }
    
}
