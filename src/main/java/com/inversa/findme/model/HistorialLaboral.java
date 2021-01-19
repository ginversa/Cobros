/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Z420WK
 */

@Entity
@Table(name = "HistorialLaboral")
public class HistorialLaboral implements Serializable{
    
    @Id
    private Integer id;
    
    @Column(name="cedula")
    private String cedula;
    
    @Column(name="cedula_patrono")
    private String cedula_patrono;
    
    @Column(name="nombre")
    private String nombre;
    
    @Column(name="ultimo_salario")
    private String ultimo_salario;
    
    @Column(name="ultimo_periodo")
    private String ultimo_periodo;
    
    @Column(name="meses")
    private String meses;
    
    @Column(name="promedio")
    private String promedio;
    
    @Column(name="tipo_salario")
    private String tipo_salario;
    
    @Column(name="estatus")
    private String estatus;

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

    public String getCedula_patrono() {
        return cedula_patrono;
    }

    public void setCedula_patrono(String cedula_patrono) {
        this.cedula_patrono = cedula_patrono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUltimo_salario() {
        return ultimo_salario;
    }

    public void setUltimo_salario(String ultimo_salario) {
        this.ultimo_salario = ultimo_salario;
    }

    public String getUltimo_periodo() {
        return ultimo_periodo;
    }

    public void setUltimo_periodo(String ultimo_periodo) {
        this.ultimo_periodo = ultimo_periodo;
    }

    public String getMeses() {
        return meses;
    }

    public void setMeses(String meses) {
        this.meses = meses;
    }

    public String getPromedio() {
        return promedio;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }

    public String getTipo_salario() {
        return tipo_salario;
    }

    public void setTipo_salario(String tipo_salario) {
        this.tipo_salario = tipo_salario;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "HistorialLaboral{" + "id=" + id + ", cedula=" + cedula + ", cedula_patrono=" + cedula_patrono + ", nombre=" + nombre + ", ultimo_salario=" + ultimo_salario + ", ultimo_periodo=" + ultimo_periodo + ", meses=" + meses + ", promedio=" + promedio + ", tipo_salario=" + tipo_salario + ", estatus=" + estatus + '}';
    }
    
}
