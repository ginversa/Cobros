/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.LlamadaDao;
import com.inversa.cobros.model.TblLlamada;
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
public class LlamadaServiceImpl implements LlamadaService, LlamadaServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private LlamadaDao dao;

    @Override
    public List<TblLlamada> findAll() {
        return dao.findAll();
    }

    @Override
    public TblLlamada findById(TblLlamada obj) {
        return dao.findById(obj);
    }

    @Override
    public TblLlamada findByCallLogId(TblLlamada obj) {
        return dao.findByCallLogId(obj);
    }

    @Override
    public List<TblLlamada> findByDateIni(TblLlamada obj) {
        return dao.findByDateIni(obj);
    }

    @Override
    public List<TblLlamada> findByDateEnd(TblLlamada obj) {
        return dao.findByDateEnd(obj);
    }

    @Override
    public List<TblLlamada> findByCallFromNumber(TblLlamada obj) {
        return dao.findByCallFromNumber(obj);
    }

    @Override
    public List<TblLlamada> findByCallToNumber(TblLlamada obj) {
        return dao.findByCallToNumber(obj);
    }

    @Override
    public List<TblLlamada> findByDialstatus(TblLlamada obj) {
        return dao.findByDialstatus(obj);
    }

    @Override
    public List<TblLlamada> findByEstado(TblLlamada obj) {
        return dao.findByEstado(obj);
    }

    @Override
    public List<TblLlamada> findByUsuarioingreso(TblLlamada obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<TblLlamada> findByFechaingreso(TblLlamada obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<TblLlamada> findByUsuariomodifico(TblLlamada obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<TblLlamada> findByFechamodifico(TblLlamada obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public void insert(TblLlamada obj) {
        try{
            dao.insert(obj);
            
        }catch (Throwable t){
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        } 
    }

    @Override
    public void update(TblLlamada obj) {
        try{
            dao.update(obj);
            
        }catch (Throwable t){
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        } 
    }

    @Override
    public void delete(TblLlamada obj) {
        try{
            dao.delete(obj);
            
        }catch (Throwable t){
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        } 
    }

}
