/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.Estadopromesa;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface EstadopromesaDao {

    public List<Estadopromesa> findAll();

    public Estadopromesa findByIdestadopromesa(Estadopromesa obj);

    public Estadopromesa findByCodigo(Estadopromesa obj);
}
