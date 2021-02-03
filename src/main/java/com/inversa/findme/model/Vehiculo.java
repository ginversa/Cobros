/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Z420WK
 */

@Entity
@Table(name="Vehiculo")
public class Vehiculo implements Serializable{
    
    @Id
    private Integer id;
    
    @Column(name="cedula")
    private String cedula;
    
    @Column(name="placa")
    private String placa;
    
    @Column(name="ano_fabricacion")
    private Integer ano_fabricacion;
    
    @Column(name="marca")
    private String marca;
    
    @Column(name="estilo")
    private String estilo;
    
    @Column(name="tipo")
    private String tipo;
    
    @Column(name="valor_fiscal")
    private BigDecimal valor_fiscal;
    
    @Column(name="moneda")
    private String moneda;
    
    @Column(name="pais")
    private String pais;
    
    @Column(name="antiguedad")
    private Integer antiguedad;
    
    @Column(name="fecha_del_dato")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_del_dato;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getAno_fabricacion() {
        return ano_fabricacion;
    }

    public void setAno_fabricacion(Integer ano_fabricacion) {
        this.ano_fabricacion = ano_fabricacion;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor_fiscal() {
        return valor_fiscal;
    }

    public void setValor_fiscal(BigDecimal valor_fiscal) {
        this.valor_fiscal = valor_fiscal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Integer antiguedad) {
        this.antiguedad = antiguedad;
    }

    public Date getFecha_del_dato() {
        return fecha_del_dato;
    }

    public void setFecha_del_dato(Date fecha_del_dato) {
        this.fecha_del_dato = fecha_del_dato;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    

    @Override
    public String toString() {
        return "Vehiculo{" + "id=" + id + ", cedula=" + cedula + ", placa=" + placa + ", ano_fabricacion=" + ano_fabricacion + ", estilo=" + estilo + ", tipo=" + tipo + ", valor_fiscal=" + valor_fiscal + ", pais=" + pais + ", antiguedad=" + antiguedad + ", fecha_del_dato=" + fecha_del_dato + '}';
    }

}
