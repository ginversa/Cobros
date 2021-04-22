/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Arreglopago;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface ArreglopagoDao {

    public List<Arreglopago> findAll();

    public Arreglopago findByIdarreglopago(Arreglopago obj);

    public Arreglopago findByCodigo(Arreglopago obj);
}
