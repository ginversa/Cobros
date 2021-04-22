/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.Tipodescuento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Z420WK
 */
@Local
public interface TipodescuentoService {

    public List<Tipodescuento> findAll();

    public Tipodescuento findByIdtipodescuento(Tipodescuento obj);

    public Tipodescuento findByCodigo(Tipodescuento obj);
}
