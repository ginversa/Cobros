/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.CarteraService;
import com.inversa.cobros.ejb.PagosHistorialService;
import com.inversa.cobros.model.TblCartera;
import com.inversa.cobros.model.TblClienteUsuario;
import com.inversa.cobros.model.TblPagosHistorial;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class PagosHistorialController implements Serializable {

    @Inject
    private PagosHistorialService ejbLocal;

    @Inject
    private CarteraService ejbCarteraService;

    private List<TblPagosHistorial> pagoLst;
    private TblPagosHistorial pago;

    private BigDecimal saldoCRC;
    private BigDecimal interesesCRC;
    private BigDecimal saldoUSD;
    private BigDecimal interesesUSD;
    
    private TblUsuario usuario;
    private String codigoCartera = null;

    @PostConstruct
    public void init() {
        this.saldoCRC = BigDecimal.ZERO;
        this.interesesCRC = BigDecimal.ZERO;
        this.saldoUSD = BigDecimal.ZERO;
        this.interesesUSD = BigDecimal.ZERO;
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");        
        this.codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo_cartera");
    }

    public List<TblPagosHistorial> getPagoLst() {
        return pagoLst;
    }

    public void setPagoLst(List<TblPagosHistorial> pagoLst) {
        this.pagoLst = pagoLst;
    }

    public TblPagosHistorial getPago() {
        return pago;
    }

    public void setPago(TblPagosHistorial pago) {
        this.pago = pago;
    }

    public BigDecimal getSaldoCRC() {
        return saldoCRC;
    }

    public void setSaldoCRC(BigDecimal saldoCRC) {
        this.saldoCRC = saldoCRC;
    }

    public BigDecimal getInteresesCRC() {
        return interesesCRC;
    }

    public void setInteresesCRC(BigDecimal interesesCRC) {
        this.interesesCRC = interesesCRC;
    }

    public BigDecimal getSaldoUSD() {
        return saldoUSD;
    }

    public void setSaldoUSD(BigDecimal saldoUSD) {
        this.saldoUSD = saldoUSD;
    }

    public BigDecimal getInteresesUSD() {
        return interesesUSD;
    }

    public void setInteresesUSD(BigDecimal interesesUSD) {
        this.interesesUSD = interesesUSD;
    }

    /**
     *
     * @param cartera
     * @param operacion
     * @param identificacion
     */
    public void cargarPagos(String cartera, String operacion, String identificacion) {
        TblPagosHistorial obj = new TblPagosHistorial();
        obj.setNumeroCuenta(operacion);
        obj.setCodigoCartera(this.codigoCartera);
        this.pagoLst = this.ejbLocal.findByNumeroCuenta(obj);
    }

    /**
     *
     * @param operacion
     */
    public void onOperacionChange(String operacion) {

        if (operacion != null && !operacion.trim().equals("")) {
            TblCartera cartera = new TblCartera();
            cartera.setNumeroCuenta(operacion);
            List<TblCartera> carteraList = this.ejbCarteraService.findByNumeroCuenta(cartera);
            if (carteraList != null && !carteraList.isEmpty() && carteraList.size() > 0) {
                cartera = carteraList.get(0);
                this.saldoCRC = cartera.getSaldoColones();
                this.interesesCRC = cartera.getInteresesColones();
                this.saldoUSD = cartera.getSaldoDolares();
                this.interesesUSD = cartera.getInteresesDolares();
            }

            TblPagosHistorial obj = new TblPagosHistorial();
            obj.setNumeroCuenta(operacion);
            obj.setCodigoCartera(this.codigoCartera);
            this.pagoLst = this.ejbLocal.findByNumeroCuenta(obj);

        } //if
    }

}
