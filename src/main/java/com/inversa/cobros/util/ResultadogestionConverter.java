/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.util;

import com.inversa.cobros.controller.ResultadogestionController;
import com.inversa.cobros.model.TblResultadogestion;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Z420WK
 */

@FacesConverter(value = "resultadogestionConverter")
public class ResultadogestionConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String valorId) {

        ValueExpression vex = ctx.getApplication().getExpressionFactory().createValueExpression(ctx.getELContext(), "#{resultadogestionController}", ResultadogestionController.class);
        ResultadogestionController controller = (ResultadogestionController) vex.getValue(ctx.getELContext());

        Integer idInteger = Integer.valueOf("0");
        if (valorId != null && !valorId.trim().equals("")) {
            idInteger = Integer.valueOf(valorId);
        }

        return controller.getResultadogestion(idInteger);

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object obj) {
        String idString = "";
        if (obj instanceof TblResultadogestion) {
            Integer id = ((TblResultadogestion) obj).getIdResultadogestion();
            idString = String.valueOf(id);
        }

        return idString;
    }

}
