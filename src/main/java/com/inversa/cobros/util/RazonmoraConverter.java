/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.util;

import com.inversa.cobros.controller.RazonmoraController;
import com.inversa.cobros.model.Razonmora;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Z420WK
 */

@FacesConverter(value = "razonmoraConverter")
public class RazonmoraConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String valorId) {
        ValueExpression vex = fc.getApplication().getExpressionFactory().createValueExpression(fc.getELContext(),"#{razonmoraController}", RazonmoraController.class);
        RazonmoraController controller = (RazonmoraController)vex.getValue(fc.getELContext());
        
        Integer idInteger = Integer.valueOf("0");
        if (valorId != null && !valorId.trim().equals("")) {
            idInteger = Integer.valueOf(valorId);
        } 
        
        return controller.getRazonmora(idInteger);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        String idString = "";
        if (obj instanceof Razonmora) {
            Integer id = ((Razonmora) obj).getIdrazonmora();
            idString = String.valueOf(id);
        }

        return idString;
    }
    
}
