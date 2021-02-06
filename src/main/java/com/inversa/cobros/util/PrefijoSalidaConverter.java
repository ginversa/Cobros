/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.util;

import com.inversa.cobros.controller.PrefijoSalidaController;
import com.inversa.cobros.model.TblPrefijoSalida;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Z420WK
 */
@FacesConverter(value = "prefijoSalidaConverter")
public class PrefijoSalidaConverter implements Converter{
    
     @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String valorId) {
        ValueExpression vex = ctx.getApplication().getExpressionFactory().createValueExpression(ctx.getELContext(), "#{prefijoSalidaController}", PrefijoSalidaController.class);
        PrefijoSalidaController controller = (PrefijoSalidaController) vex.getValue(ctx.getELContext());

        Integer idInteger = Integer.valueOf("0");
        if (valorId != null && !valorId.trim().equals("")) {
            idInteger = Integer.valueOf(valorId);
        }

        return controller.getPrefijoSalida(idInteger);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object obj) {
        String idString = "";
        if (obj instanceof TblPrefijoSalida) {
            Integer id = ((TblPrefijoSalida) obj).getId();
            idString = String.valueOf(id);
        }

        return idString;
    }
    
}
