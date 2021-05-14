/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.util;

import com.inversa.cobros.controller.cola.ListarFiltroController;
import com.inversa.cobros.model.TblFiltrocola;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Z420WK
 */
@FacesConverter(value = "filtrocolaConverter")
public class FiltrocolaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String valorId) {
        ValueExpression vex = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), "#{listarFiltroController}", ListarFiltroController.class);
        ListarFiltroController controller = (ListarFiltroController) vex.getValue(context.getELContext());

        Integer idInteger = Integer.valueOf("0");
        if (valorId != null && !valorId.trim().equals("")) {
            idInteger = Integer.valueOf(valorId);
        }

        return controller.getFiltrocola(idInteger);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        String idString = "";
        if (obj instanceof TblFiltrocola) {
            Long id = ((TblFiltrocola) obj).getIdFiltrocola();
            idString = String.valueOf(id);
        }

        return idString;
    }

}
