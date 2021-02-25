/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.TblPromesa;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Z420WK
 */
@Local
public interface PromesaService {

    public List<TblPromesa> findAll();

    public TblPromesa findById(TblPromesa obj);

    public List<TblPromesa> findByOperacion(TblPromesa obj);

    public List<TblPromesa> findByTelefono(TblPromesa obj);

    public List<TblPromesa> findByFechaPago(TblPromesa obj);

    public List<TblPromesa> findByUsuarioingreso(TblPromesa obj);

    public List<TblPromesa> findByFechaingreso(TblPromesa obj);

    public List<TblPromesa> findByUsuariomodifico(TblPromesa obj);

    public List<TblPromesa> findByFechamodifico(TblPromesa obj);

    public List<TblPromesa> findByFechaPagoAndUsuarioIngreso(TblPromesa obj);

    public void insert(TblPromesa obj);

    public void update(TblPromesa obj);

    public void delete(TblPromesa obj);

    public TblPromesa findPromesaUltimoPago(Long idGestion,Long idLlamada);

}
