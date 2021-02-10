/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.Moneda;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Z420WK
 */
@Remote
public interface MonedaServiceRemote {

    public List<Moneda> findAll();

    public Moneda findByIdMoneda(Moneda obj);

    public Moneda findByCodigo(Moneda obj);

}
