/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.util;

import com.inversa.cobros.controller.SubtipificacionController;
import com.inversa.cobros.model.Subtipificacion;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Z420WK
 */

@FacesConverter(value = "subtipificacionConverter")
public class SubtipificacionConverter implements Converter{
   
     @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String valorId) {
        ValueExpression vex = ctx.getApplication().getExpressionFactory().createValueExpression(ctx.getELContext(),"#{subtipificacionController}", SubtipificacionController.class);
        SubtipificacionController controller = (SubtipificacionController)vex.getValue(ctx.getELContext());
        
        Integer idInteger = Integer.valueOf("0");
        if (valorId != null && !valorId.trim().equals("")) {
            idInteger = Integer.valueOf(valorId);
        } 
        
        return controller.getSubtipificacion(idInteger);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object obj) {
        String idString = "";
        if (obj instanceof Subtipificacion) {
            Integer id = ((Subtipificacion) obj).getIdSubtipificacion();
            idString = String.valueOf(id);
        }

        return idString;
    }
    
}
