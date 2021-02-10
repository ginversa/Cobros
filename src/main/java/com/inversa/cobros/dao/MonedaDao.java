/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Moneda;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface MonedaDao {
 
    public List<Moneda> findAll();
    
    public Moneda findByIdMoneda(Moneda obj);
    
    public Moneda findByCodigo(Moneda obj);
    
}
