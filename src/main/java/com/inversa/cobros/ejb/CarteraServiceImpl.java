/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.CarteraDao;
import com.inversa.cobros.model.TblCartera;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Z420WK
 */
@Stateless
public class CarteraServiceImpl implements CarteraService, CarteraServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private CarteraDao dao;

    @Override
    public List<TblCartera> findAll() {
        return dao.findAll();
    }

    @Override
    public TblCartera findById(TblCartera obj) {
        return dao.findById(obj);
    }

    @Override
    public List<TblCartera> findByCodigoCartera(TblCartera obj) {
        return dao.findByCodigoCartera(obj);
    }

    @Override
    public List<TblCartera> findByCodigoGestor(TblCartera obj) {
        return dao.findByCodigoGestor(obj);
    }

    @Override
    public List<TblCartera> findByNumeroClienteCif(TblCartera obj) {
        return dao.findByNumeroClienteCif(obj);
    }

    @Override
    public List<TblCartera> findByIdentificacion(TblCartera obj) {
        return dao.findByIdentificacion(obj);
    }

    @Override
    public TblCartera findByNumeroCuenta(TblCartera obj) {
        return dao.findByNumeroCuenta(obj);
    }

    @Override
    public List<TblCartera> findByNumeroTarjeta(TblCartera obj) {
        return dao.findByNumeroTarjeta(obj);
    }
    
    @Override
    public TblCartera findByNumeroCuentaANDIdentificacion(TblCartera obj){
        return dao.findByNumeroCuentaANDIdentificacion(obj);
    }

    @Override
    public List<TblCartera> findByTipoProducto(TblCartera obj) {
        return dao.findByTipoProducto(obj);
    }

    @Override
    public List<TblCartera> findByBucket(TblCartera obj) {
        return dao.findByBucket(obj);
    }

    @Override
    public List<TblCartera> findByDiasMora(TblCartera obj) {
        return dao.findByDiasMora(obj);
    }

    @Override
    public List<TblCartera> findByPlacement(TblCartera obj) {
        return dao.findByPlacement(obj);
    }

    @Override
    public List<TblCartera> findByFechaAsignacion(TblCartera obj) {
        return dao.findByFechaAsignacion(obj);
    }

    @Override
    public List<TblCartera> findByEstado(TblCartera obj) {
        return dao.findByEstado(obj);
    }

    @Override
    public List<TblCartera> findByUsuarioIngreso(TblCartera obj) {
        return dao.findByUsuarioIngreso(obj);
    }

    @Override
    public void insert(TblCartera obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblCartera obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblCartera obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override               
    public List<TblCartera> findByCarteraGestorIdentificacion(TblCartera objCartera) {
        return dao.findByCarteraGestorIdentificacion(objCartera);
    }

    @Override
    public List<TblCartera> findByCodigoGestorANDCodigoCartera(TblCartera objCartera) {
        return dao.findByCodigoGestorANDCodigoCartera(objCartera);
    }

    @Override
    public List<TblCartera> findByCarteraGestorIdentificacionNotExistsGestion(TblCartera objCartera) {                
        return dao.findByCarteraGestorIdentificacionNotExistsGestion(objCartera);
    }

}
