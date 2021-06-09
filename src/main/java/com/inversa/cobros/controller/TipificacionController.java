/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.constante.comun.ConstanteComun;
import com.inversa.cobros.controller.cola.ColaGestionController;
import com.inversa.cobros.ejb.TipificacionService;
import com.inversa.cobros.model.Subtipificacion;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblResultadogestion;
import com.inversa.cobros.model.TblResultadotercero;
import com.inversa.cobros.model.Tipificacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class TipificacionController implements Serializable {

    @Inject
    private TipificacionService ejbLocal;

    private List<Tipificacion> tipificacionList;

    private List<Subtipificacion> subtipificacionList;

    private List<TblResultadogestion> resultadogestionList;

    private List<TblResultadotercero> resultadoterceroList;

    private boolean isDisabledPromesa;

    @Inject
    private CarteraGestionController carteraGestionController;

    @Inject
    private UpdateGestionController updateGestionController;

    @Inject
    private ColaGestionController colaGestionController;

    @PostConstruct
    public void init() {

        this.tipificacionList = ejbLocal.findAll();

        this.isDisabledPromesa = true;// desabilita el boton de promesas...
    }

    /**
     *
     * @param id
     * @return
     */
    public Tipificacion getTipificacion(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Tipificacion obj : tipificacionList) {
            if (id.equals(obj.getIdTipificacion())) {
                return obj;
            }
        }
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Subtipificacion> getSubtipificacionList(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Tipificacion obj : tipificacionList) {
            if (id.equals(obj.getIdTipificacion())) {
                return obj.getSubtipificacionList();
            }
        }
        return null;
    }

    public List<Tipificacion> getTipificacionList() {
        return tipificacionList;
    }

    public void setTipificacionList(List<Tipificacion> tipificacionList) {
        this.tipificacionList = tipificacionList;
    }

    public List<Subtipificacion> getSubtipificacionList() {
        return subtipificacionList;
    }

    public void setSubtipificacionList(List<Subtipificacion> subtipificacionList) {
        this.subtipificacionList = subtipificacionList;
    }

    /**
     *
     * @param selectedLlamada
     */
    public void onTipificacionChange(TblLlamada selectedLlamada) {
        if (selectedLlamada != null) {
            Tipificacion selectedTipificacion = selectedLlamada.getIdTipificacion();
            if (selectedTipificacion != null) {
                this.subtipificacionList = selectedTipificacion.getSubtipificacionList();
                this.resultadogestionList = selectedTipificacion.getTblResultadogestionList();
                this.resultadoterceroList = selectedTipificacion.getTblResultadoterceroList();

                if (selectedTipificacion.getCodigo().equals("PRP")) {// promesa de pago o Recordatorio de pago
                    this.setIsDisabledPromesa(false);
                } else {
                    this.setIsDisabledPromesa(true);
                }

            } else {
                this.subtipificacionList = new ArrayList<Subtipificacion>();
                this.setIsDisabledPromesa(true);
            }

            this.carteraGestionController.setSubtipificacionNullonLlamada(selectedLlamada);
            this.carteraGestionController.setResultadoGestionNullonLlamada(selectedLlamada);
            this.carteraGestionController.setResultadoTerceroNullonLlamada(selectedLlamada);
        }
    }

    /**
     *
     * @param selectedLlamada
     */
    public void onTipificacionChange_cola(TblLlamada selectedLlamada) {
        if (selectedLlamada != null) {
            Tipificacion selectedTipificacion = selectedLlamada.getIdTipificacion();
            if (selectedTipificacion != null) {
                this.subtipificacionList = selectedTipificacion.getSubtipificacionList();
                this.resultadogestionList = selectedTipificacion.getTblResultadogestionList();
                this.resultadoterceroList = selectedTipificacion.getTblResultadoterceroList();

                if (selectedTipificacion.getCodigo().equals("PRP")) {// promesa de pago o Recordatorio de pago
                    this.setIsDisabledPromesa(false);
                } else {
                    this.setIsDisabledPromesa(true);
                }

            } else {
                this.subtipificacionList = new ArrayList<Subtipificacion>();
                this.setIsDisabledPromesa(true);
            }

            this.colaGestionController.setSubtipificacionNullonLlamada(selectedLlamada);
            this.colaGestionController.setResultadoGestionNullonLlamada(selectedLlamada);
            this.colaGestionController.setResultadoTerceroNullonLlamada(selectedLlamada);
        }
    }

    /**
     *
     * @param selectedLlamada
     */
    public void onSub_TipificacionChange_cola(TblLlamada selectedLlamada) {
        if (selectedLlamada != null) {
            Tipificacion selectedTipificacion = selectedLlamada.getIdTipificacion();
            Subtipificacion selectedSubTipificacion = selectedLlamada.getIdSubtipificacion();

            if (selectedTipificacion != null) {
                if (selectedSubTipificacion != null) {
                    List<TblResultadogestion> resultadoList = selectedSubTipificacion.getTblResultadogestionList();
                    if (resultadoList != null && !resultadoList.isEmpty() && resultadoList.size() > 0) {
                        this.resultadogestionList = resultadoList;
                    }
                }

                String codigo = selectedTipificacion.getCodigo();
                if (codigo != null && !codigo.trim().equals("") && selectedTipificacion.getCodigo().equals("PRP")) {
                    this.setIsDisabledPromesa(false);
                } else {
                    this.setIsDisabledPromesa(true);
                }

            } else {
                this.subtipificacionList = new ArrayList<Subtipificacion>();
                this.setIsDisabledPromesa(true);
            }

            this.colaGestionController.setResultadoGestionNullonLlamada(selectedLlamada);
            this.colaGestionController.setResultadoTerceroNullonLlamada(selectedLlamada);
        }
    }

    /**
     * 
     * @param selectedLlamada 
     */
    public void onResultadogestionChange_cola(TblLlamada selectedLlamada) {
        if (selectedLlamada != null) {
            TblResultadogestion selectedResultadogestion = selectedLlamada.getIdResultadogestion();
            if (selectedResultadogestion != null) {
                List<TblResultadotercero> respuestaList = selectedResultadogestion.getTblResultadoterceroList();
                if (respuestaList != null && !respuestaList.isEmpty() && respuestaList.size() > 0) {
                    this.resultadoterceroList = respuestaList;
                }

            } else {
                this.resultadoterceroList = new ArrayList<TblResultadotercero>();
            }

            this.colaGestionController.setResultadoTerceroNullonLlamada(selectedLlamada);
        }
    }

    /**
     *
     * @param selectedLlamada
     */
    public void onTipificacionChange_UG(TblLlamada selectedLlamada) {
        if (selectedLlamada != null) {
            Tipificacion selectedTipificacion = selectedLlamada.getIdTipificacion();
            if (selectedTipificacion != null) {
                this.subtipificacionList = selectedTipificacion.getSubtipificacionList();
                this.resultadogestionList = selectedTipificacion.getTblResultadogestionList();
                this.resultadoterceroList = selectedTipificacion.getTblResultadoterceroList();

                if (selectedTipificacion.getCodigo().equals("PRP") || selectedTipificacion.getCodigo().equals("REP")) {
                    this.setIsDisabledPromesa(false);
                } else {
                    this.setIsDisabledPromesa(true);
                }

            } else {
                this.subtipificacionList = new ArrayList<Subtipificacion>();
                this.setIsDisabledPromesa(true);
            }

            this.updateGestionController.setSubtipificacionNullonLlamada(selectedLlamada);
            this.updateGestionController.setResultadoGestionNullonLlamada(selectedLlamada);
            this.updateGestionController.setResultadoTerceroNullonLlamada(selectedLlamada);
        }
    }

    /**
     *
     * @param selectedLlamada
     */
    public void onSub_TipificacionChange(TblLlamada selectedLlamada) {
        if (selectedLlamada != null) {
            Tipificacion selectedTipificacion = selectedLlamada.getIdTipificacion();
            Subtipificacion selectedSubTipificacion = selectedLlamada.getIdSubtipificacion();

            if (selectedTipificacion != null) {
                if (selectedSubTipificacion != null) {
                    List<TblResultadogestion> resultadoList = selectedSubTipificacion.getTblResultadogestionList();
                    if (resultadoList != null && !resultadoList.isEmpty() && resultadoList.size() > 0) {
                        this.resultadogestionList = resultadoList;
                    }
                }

                if (selectedTipificacion.getCodigo().equals("PRP")) {
                    this.setIsDisabledPromesa(false);
                } else {
                    this.setIsDisabledPromesa(true);
                }

            } else {
                this.subtipificacionList = new ArrayList<Subtipificacion>();
                this.setIsDisabledPromesa(true);
            }

            this.carteraGestionController.setResultadoGestionNullonLlamada(selectedLlamada);
            this.carteraGestionController.setResultadoTerceroNullonLlamada(selectedLlamada);
        }
    }

    /**
     *
     * @param selectedLlamada
     */
    public void onSub_TipificacionChange_UG(TblLlamada selectedLlamada) {
        if (selectedLlamada != null) {
            Tipificacion selectedTipificacion = selectedLlamada.getIdTipificacion();
            Subtipificacion selectedSubTipificacion = selectedLlamada.getIdSubtipificacion();

            if (selectedTipificacion != null) {
                if (selectedSubTipificacion != null) {
                    List<TblResultadogestion> resultadoList = selectedSubTipificacion.getTblResultadogestionList();
                    if (resultadoList != null && !resultadoList.isEmpty() && resultadoList.size() > 0) {
                        this.resultadogestionList = resultadoList;
                    }
                }

                if (selectedTipificacion.getCodigo().equals("PRP") || selectedTipificacion.getCodigo().equals("REP")) {
                    this.setIsDisabledPromesa(false);
                } else {
                    this.setIsDisabledPromesa(true);
                }

            } else {
                this.subtipificacionList = new ArrayList<Subtipificacion>();
                this.setIsDisabledPromesa(true);
            }

            this.updateGestionController.setResultadoGestionNullonLlamada(selectedLlamada);
            this.updateGestionController.setResultadoTerceroNullonLlamada(selectedLlamada);
        }
    }

    /**
     *
     * @param selectedResultadogestion
     */
    public void onResultadogestionChange(TblLlamada selectedLlamada) {
        if (selectedLlamada != null) {
            TblResultadogestion selectedResultadogestion = selectedLlamada.getIdResultadogestion();
            if (selectedResultadogestion != null) {
                List<TblResultadotercero> respuestaList = selectedResultadogestion.getTblResultadoterceroList();
                if (respuestaList != null && !respuestaList.isEmpty() && respuestaList.size() > 0) {
                    this.resultadoterceroList = respuestaList;
                }

            } else {
                this.resultadoterceroList = new ArrayList<TblResultadotercero>();
            }

            this.carteraGestionController.setResultadoTerceroNullonLlamada(selectedLlamada);
        }
    }

    /**
     *
     * @param selectedLlamada
     */
    public void onResultadogestionChange_UG(TblLlamada selectedLlamada) {
        if (selectedLlamada != null) {
            TblResultadogestion selectedResultadogestion = selectedLlamada.getIdResultadogestion();
            if (selectedResultadogestion != null) {
                List<TblResultadotercero> respuestaList = selectedResultadogestion.getTblResultadoterceroList();
                if (respuestaList != null && !respuestaList.isEmpty() && respuestaList.size() > 0) {
                    this.resultadoterceroList = respuestaList;
                }

            } else {
                this.resultadoterceroList = new ArrayList<TblResultadotercero>();
            }

            this.updateGestionController.setResultadoTerceroNullonLlamada(selectedLlamada);
        }
    }

    public boolean isIsDisabledPromesa() {
        return isDisabledPromesa;
    }

    public void setIsDisabledPromesa(boolean isDisabledPromesa) {
        this.isDisabledPromesa = isDisabledPromesa;
    }

    public List<TblResultadogestion> getResultadogestionList() {
        return resultadogestionList;
    }

    public void setResultadogestionList(List<TblResultadogestion> resultadogestionList) {
        this.resultadogestionList = resultadogestionList;
    }

    public List<TblResultadotercero> getResultadoterceroList() {
        return resultadoterceroList;
    }

    public void setResultadoterceroList(List<TblResultadotercero> resultadoterceroList) {
        this.resultadoterceroList = resultadoterceroList;
    }

}
