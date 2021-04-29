/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblSaldo;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface SaldoDao {

    public List<TblSaldo> findAll();

    public TblSaldo findByIdSaldo(TblSaldo obj);

    public void insert(TblSaldo obj);

    public void update(TblSaldo obj);

    public void delete(TblSaldo obj);
}
