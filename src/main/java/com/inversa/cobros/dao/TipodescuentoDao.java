/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Tipodescuento;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface TipodescuentoDao {

    public List<Tipodescuento> findAll();

    public Tipodescuento findByIdtipodescuento(Tipodescuento obj);

    public Tipodescuento findByCodigo(Tipodescuento obj);

}
