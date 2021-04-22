/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.util;

import com.inversa.cobros.controller.ArreglopagoController;
import com.inversa.cobros.model.Arreglopago;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Z420WK
 */
@FacesConverter(value = "arreglopagoConverter")
public class ArreglopagoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ValueExpression vex = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), "#{arreglopagoController}", ArreglopagoController.class);
        ArreglopagoController controller = (ArreglopagoController) vex.getValue(context.getELContext());

        Integer idInteger = Integer.valueOf("0");
        if (value != null && !value.trim().equals("")) {
            idInteger = Integer.valueOf(value);
        }

        return controller.getArreglopago(idInteger);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        String idString = "";
        if (obj instanceof Arreglopago) {
            Integer id = ((Arreglopago) obj).getIdarreglopago();
            idString = String.valueOf(id);
        }

        return idString;
    }

}
