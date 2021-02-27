/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.CarteraService;
import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.model.TblCartera;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPromesa;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@RequestScoped
public class CarteraController implements Serializable {

    @Inject
    private CarteraService ejbLocal;

    @Inject
    private GestionService ejbGestionLocal;
    
    @Inject
    private PromesaService ejbPromesaLocal;

    private TblCartera cartera;
    private String observaciones;
    
    private List<TblCartera> carteraList;
    private TblUsuario usuario;
    private Calendar fechaHoy;

    @PostConstruct
    public void init() {
        
        this.fechaHoy = Calendar.getInstance();
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        String codigoGestor = this.usuario.getCodigoGestor();

        if(codigoGestor != null){
            this.cartera = new TblCartera();
            this.cartera.setCodigoGestor(codigoGestor);
            this.fechaHoy.set(Calendar.YEAR, 2020);
            this.fechaHoy.set(Calendar.MONTH, 11);
            this.fechaHoy.set(Calendar.DAY_OF_MONTH, 30);
            this.cartera.setFechaIngreso(this.fechaHoy.getTime());
            this.carteraList = this.ejbLocal.findByCodigoGestor(this.cartera);
            this.buscarGestion();
        }

    }

    public TblCartera getCartera() {
        return cartera;
    }

    public void setCartera(TblCartera cartera) {
        this.cartera = cartera;
    }

    public List<TblCartera> getCarteraList() {
        return carteraList;
    }

    public void setCarteraList(List<TblCartera> carteraList) {
        this.carteraList = carteraList;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }    

    /**
     *
     */
    private void buscarGestion() {

        if (this.carteraList != null && !this.carteraList.isEmpty()) {
            for (int index = 0; index < this.carteraList.size(); index++) {
                String codigoCartera = this.carteraList.get(index).getCodigoCartera();
                String identificacion = this.carteraList.get(index).getIdentificacion();
                String operacion = this.carteraList.get(index).getNumeroCuenta();

                TblGestion gestion = new TblGestion();
                gestion.setCodigoCartera(codigoCartera);
                gestion.setIdentificacion(identificacion);
                
                gestion = this.ejbGestionLocal.findByCodigoCarteraANDIdentificacion(gestion);
                if (gestion != null) {

                    Date fechaUltimaGestion = gestion.getFechaGestion();
                    String razonMora = "";
                    
                    TblPromesa ultimaPromesa = this.ejbPromesaLocal.findPromesaUltimoPago(gestion.getIdGestion());
                            
                    List<TblLlamada> llamadaList = gestion.getTblLlamadaList();
                    if (llamadaList != null && !llamadaList.isEmpty()) {
                        Date fechaingresoMax = null;
                        for (int indexLlamada = 0; indexLlamada < llamadaList.size(); indexLlamada++) {
                            TblLlamada llamada = llamadaList.get(indexLlamada);
                            Date fechaingreso = llamada.getFechaingreso();
                            if (fechaingresoMax == null) {
                                fechaingresoMax = fechaingreso;
                                if (llamada.getIdrazonmora() != null) {
                                    razonMora = llamada.getIdrazonmora().getDescripcion();
                                }
/*
                                List<TblPromesa> promesaList = llamada.getTblPromesaList();
                                if (promesaList != null && !promesaList.isEmpty()) {
                                    for (int indexPromesa = 0; indexPromesa < promesaList.size(); indexPromesa++) {
                                        TblPromesa promesa = promesaList.get(indexPromesa);
                                        Date fechaPago = promesa.getFechaPago();
                                        String operacionPromesa = promesa.getOperacion();
                                        if (fechaUltimaPromesa == null && operacion.equals(operacionPromesa)) {
                                            fechaUltimaPromesa = fechaPago;
                                            montoUltimaPromesa = promesa.getMtopago();
                                        } else if (fechaPago != null && fechaUltimaPromesa.before(fechaPago) && operacion.equals(operacionPromesa)) {
                                            fechaUltimaPromesa = fechaPago;
                                            montoUltimaPromesa = promesa.getMtopago();
                                        }
                                    }
                                }
*/

                            } else if (fechaingresoMax.before(fechaingreso)) {// fechaingresoMax is before fechaingreso. fecha es mayor
                                fechaingresoMax = fechaingreso;
                                if (llamada.getIdrazonmora() != null) {
                                    razonMora = llamada.getIdrazonmora().getDescripcion();
                                }
/*
                                List<TblPromesa> promesaList = llamada.getTblPromesaList();
                                if (promesaList != null && !promesaList.isEmpty()) {
                                    for (int indexPromesa = 0; indexPromesa < promesaList.size(); indexPromesa++) {
                                        TblPromesa promesa = promesaList.get(indexPromesa);
                                        Date fechaPago = promesa.getFechaPago();
                                        if (fechaUltimaPromesa == null) {
                                            fechaUltimaPromesa = fechaPago;
                                            montoUltimaPromesa = promesa.getMtopago();
                                        } else if (fechaUltimaPromesa.before(fechaPago)) {
                                            fechaUltimaPromesa = fechaPago;
                                            montoUltimaPromesa = promesa.getMtopago();
                                        }
                                    }
                                }
*/
                            }
                        }
                    }

                    this.carteraList.get(index).setFechaUltimaGestion(fechaUltimaGestion);
                    this.carteraList.get(index).setUltimaPromesa(ultimaPromesa);                    
                    this.carteraList.get(index).setRazonMora(razonMora);
                }// if Gestion
            }
        }
    }//buscarGestion

}
