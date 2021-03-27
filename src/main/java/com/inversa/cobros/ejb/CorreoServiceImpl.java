/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.CorreoDao;
import com.inversa.cobros.model.TblCorreo;
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
public class CorreoServiceImpl implements CorreoService, CorreoServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private CorreoDao dao;

    @Override
    public List<TblCorreo> findAll() {
        return dao.findAll();
    }

    @Override
    public TblCorreo findById(TblCorreo obj) {
        return dao.findById(obj);
    }

    @Override
    public TblCorreo findByCorreo(TblCorreo obj) {
        return dao.findByCorreo(obj);
    }

    @Override
    public List<TblCorreo> findByRanking(TblCorreo obj) {
        return dao.findByRanking(obj);
    }

    @Override
    public List<TblCorreo> findByUsuarioingreso(TblCorreo obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblCorreo> findByFechaingreso(TblCorreo obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblCorreo> findByUsuariomodifico(TblCorreo obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblCorreo> findByFechamodifico(TblCorreo obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   @Override
    public void insert(TblCorreo obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblCorreo obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblCorreo obj) {
        try{
            dao.delete(obj);
            
        }catch (Throwable t){
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }        
    }

    @Override
    public List<TblCorreo> findByContactoEstado(Integer idContacto, String estado) {
        return dao.findByContactoEstado(idContacto, estado);
    }

}
