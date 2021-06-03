/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.FiltrocolaDao;
import com.inversa.cobros.model.TblCartera;
import com.inversa.cobros.model.TblCola;
import com.inversa.cobros.model.TblFiltrocola;
import com.inversa.cobros.model.TblGestion;
import com.inversa.findme.ejb.FindmeService;
import com.inversa.findme.ejb.LaboralHistoricoService;
import com.inversa.findme.model.HistorialLaboral;
import com.inversa.findme.model.LaboralHistorico;
import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class FiltrocolaServiceImpl implements FiltrocolaService, FiltrocolaServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private FiltrocolaDao dao;

    @Inject
    private ColaService ejbColaLocal;

    @Inject
    private LaboralHistoricoService ejbLaboralHistoricoLocal;

    @Inject
    private FindmeService ejbFindmeLocal;

    @Override
    public List<TblFiltrocola> findAll() {
        return dao.findAll();
    }

    @Override
    public TblFiltrocola findByIdFiltrocola(TblFiltrocola obj) {
        return dao.findByIdFiltrocola(obj);
    }

    @Override
    public void insert(TblFiltrocola obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblFiltrocola obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblFiltrocola obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public List<TblFiltrocola> findByCodigoCartera(TblFiltrocola obj) {
        return dao.findByCodigoCartera(obj);
    }

    @Override
    public void generarCola(TblFiltrocola filtrocola, String usuarioIngreso, Date fechaIngreso) {
        TblCartera cartera = new TblCartera();
        cartera.setCodigoCartera(filtrocola.getCodigoCartera());
        cartera.setCodigoGestor(null);
        
        filtrocola = this.findByIdFiltrocola(filtrocola);
        
        // Filtrar segun gestion.
        List<TblCola> carteraFiltrada = findByCodigoGestorANDCodigoCarteraANDFiltro(filtrocola);

        // Filtrar segun findme
        carteraFiltrada = this.filtrarCarteraSegunFindme(filtrocola, carteraFiltrada);

        if (carteraFiltrada != null) {
            List<TblCola> colaList = new ArrayList<>();            
            for (TblCola tblCola : carteraFiltrada) {
                TblCola cola = new TblCola();                
                cola.setIdFiltrocola(filtrocola);                
                cola.setCodigoCartera(tblCola.getCodigoCartera());
                cola.setCodigoGestor(null);
                cola.setIdentificacion(tblCola.getIdentificacion());
                cola.setSaldoColones(tblCola.getSaldoColones());
                cola.setSaldoDolares(tblCola.getSaldoDolares());
                cola.setEstado(tblCola.getEstado());                
                cola.setUsuarioingreso(usuarioIngreso);
                cola.setFechaingreso(fechaIngreso);
                cola.setUsuariomodifico(usuarioIngreso);
                cola.setFechamodifico(fechaIngreso);
                colaList.add(cola);
            }
            
            try {
                if (!colaList.isEmpty() && colaList.size() > 0) {                    
                    filtrocola.setTblColaList(colaList);
                    this.update(filtrocola);
                }
                
            } catch (Exception e) {
                contexto.setRollbackOnly();// hace rollback...
                e.printStackTrace(System.out);// imprime en consola el error
            }
        }
    }

    @Override
    public List<TblCola> findByCodigoGestorANDCodigoCarteraANDFiltro(TblFiltrocola filtro) {
        String codigo_cartera = filtro.getCodigoCartera();
        String codigo_gestor = null;
        String codigoTipificacion = null;
        String codigoSubTipificacion = null;
        String codigoRazonMora = null;
        Long uno = null;
        Long dos = null;
        Long mesUP = null;
        Long mesPP = null;

        //TblFiltrocola filtro = new TblFiltrocola();
        filtro.setCodigoCartera(codigo_cartera);
        //List<TblFiltrocola> filtroList = this.findByCodigoCartera(filtro);
        //if (filtroList != null && !filtroList.isEmpty() && filtroList.size() > 0) {
        //filtro = filtroList.get(0);
        if (filtro.getIdTipificacion() != null) {
            codigoTipificacion = filtro.getIdTipificacion().getCodigo();
        }

        if (filtro.getIdSubtipificacion() != null) {
            codigoSubTipificacion = filtro.getIdSubtipificacion().getCodigo();
        }

        if (filtro.getIdrazonmora() != null) {
            codigoRazonMora = filtro.getIdrazonmora().getCodigo();
        }

        String dias_singestion = filtro.getDiasSingestion();
        if (dias_singestion != null && !dias_singestion.trim().equals("")) {
            String[] dias_Array = dias_singestion.split("a");
            if (dias_Array.length > 1) {
                uno = Long.valueOf(dias_Array[0]);
                dos = Long.valueOf(dias_Array[1]);
            } else {
                if (dias_Array[0].trim().equals("15+")) {
                    String quince = dias_Array[0].substring(0, dias_Array[0].length() - 1);
                    if (quince != null && !quince.trim().equals("")) {
                        uno = Long.valueOf(quince);
                        dos = null;
                    }
                }
            }
        }

        String ultimo_mes = filtro.getUltimopagoMes();
        if (ultimo_mes != null && !ultimo_mes.trim().equals("")) {
            mesUP = Long.valueOf(ultimo_mes);
        }

        String ultimaPromesa_mes = filtro.getUltimapromesaMes();
        if (ultimaPromesa_mes != null && !ultimaPromesa_mes.trim().equals("")) {
            mesPP = Long.valueOf(ultimaPromesa_mes);
        }
        //}

        return dao.findByCodigoGestorANDCodigoCarteraANDFiltro(filtro.getIdFiltrocola(), codigo_cartera, codigo_gestor, codigoTipificacion, codigoSubTipificacion, codigoRazonMora, uno, dos, mesUP, mesPP);
    }

    @Override
    public void deleteCola(TblFiltrocola filtrocola) {
        TblCola cola = new TblCola();
        cola.setIdFiltrocola(filtrocola);
        this.ejbColaLocal.deleteByIdFiltro(cola);
    }

    /**
     *
     * @param filtrocola
     * @param carteraFiltrada
     * @return
     */
    @Override
    public List<TblCola> filtrarCarteraSegunFindme(TblFiltrocola filtrocola, List<TblCola> carteraFiltrada) {
        List<TblCola> listaFiltrada = new ArrayList<>();
        Integer si_Poseetrabajo = filtrocola.getPoseetrabajo();
        Integer si_Rangobalance = filtrocola.getRangobalance();
        String si_Tiposalario = filtrocola.getTiposalario();
        BigDecimal cantidad = filtrocola.getCantidad();

        boolean isFiltroPoseetrabajo = si_Poseetrabajo != null;
        boolean isFiltroRangobalance = si_Rangobalance != null;
        boolean isFiltroTiposalario = si_Tiposalario != null && !si_Tiposalario.trim().equals("");

        for (TblCola tblCola : carteraFiltrada) {
            boolean addPoseetrabajo = false;
            boolean addRangobalance = false;
            boolean addTiposalario = false;

            if (isFiltroPoseetrabajo) {
                LaboralHistorico poseeTrabajo = this.ejbLaboralHistoricoLocal.getPoseeTrabajo(tblCola.getIdentificacion());
                if (poseeTrabajo != null && si_Poseetrabajo == 1) {
                    addPoseetrabajo = true;
                }

                if (poseeTrabajo == null && si_Poseetrabajo == 0) {
                    addPoseetrabajo = true;
                }

            } else {
                addPoseetrabajo = true;
            }

            if (isFiltroRangobalance) {
                LaboralHistorico rangoBalance = this.ejbLaboralHistoricoLocal.getRangoBalance(tblCola.getIdentificacion(), cantidad, si_Rangobalance);
                if (rangoBalance != null) {
                    addRangobalance = true;
                } else {
                    addRangobalance = false;
                }
            } else {
                addRangobalance = true;
            }

            if (isFiltroTiposalario) {
                List<HistorialLaboral> historialLaboralList = this.ejbFindmeLocal.analisis_laboral(tblCola.getIdentificacion());
                if (historialLaboralList != null && !historialLaboralList.isEmpty() && historialLaboralList.size() > 0) {
                    HistorialLaboral historialLaboral = historialLaboralList.get(0);
                    if (historialLaboral.getTipo_salario().equals(si_Tiposalario)) {
                        addTiposalario = true;
                    } else {
                        addTiposalario = false;
                    }
                } else {
                    addTiposalario = false;
                }
            } else {
                addTiposalario = true;
            }

            if (addPoseetrabajo && addRangobalance && addTiposalario) {
                listaFiltrada.add(tblCola);
            }
        }

        return listaFiltrada;
    }

}
