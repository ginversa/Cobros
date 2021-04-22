/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.util;

import com.inversa.cobros.controller.MonedaController;
import com.inversa.cobros.model.Moneda;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Z420WK
 */
@FacesConverter(value = "monedaConverter")
public class MonedaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ValueExpression vex = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), "#{monedaController}", MonedaController.class);
        MonedaController controller = (MonedaController) vex.getValue(context.getELContext());

        Integer idInteger = Integer.valueOf("0");
        if (value != null && !value.trim().equals("")) {
            idInteger = Integer.valueOf(value);
        }

        return controller.getMoneda(idInteger);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        String idString = "";
        if (obj instanceof Moneda) {
            Integer id = ((Moneda) obj).getIdMoneda();
            idString = String.valueOf(id);
        }

        return idString;
    }

}
