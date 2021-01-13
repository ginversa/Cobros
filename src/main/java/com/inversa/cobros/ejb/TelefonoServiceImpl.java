/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.TelefonoDao;
import com.inversa.cobros.model.TblTelefono;
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
public class TelefonoServiceImpl implements TelefonoService, TelefonoServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private TelefonoDao dao;

    @Override
    public List<TblTelefono> findAll() {
        return dao.findAll();
    }

    @Override
    public TblTelefono findById(TblTelefono obj) {
        return dao.findById(obj);
    }

    @Override
    public TblTelefono findByTelefono(TblTelefono obj) {
        return dao.findByTelefono(obj);
    }

    @Override
    public List<TblTelefono> findByRanking(TblTelefono obj) {
        return dao.findByRanking(obj);
    }

    @Override
    public List<TblTelefono> findByUsuarioingreso(TblTelefono obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblTelefono> findByFechaingreso(TblTelefono obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblTelefono> findByUsuariomodifico(TblTelefono obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblTelefono> findByFechamodifico(TblTelefono obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(TblTelefono obj) {
         try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblTelefono obj) {
         try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblTelefono obj) {
         try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }   

    @Override
    public List<TblTelefono> findByContactoEstado(Integer idContacto,String estado) {
        return dao.findByContactoEstado(idContacto,estado);
    }

}
