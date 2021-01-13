/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Z420WK
 */

@FacesConverter("phoneConverter")
public class PhoneConverter implements Converter{
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        String phoneNumber = (String) modelValue;
        //StringBuilder formattedPhoneNumber = new StringBuilder();

        String number = phoneNumber.replaceFirst("(\\d{4})(\\d+)", "$2-$2");

        return number.toString();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        // Conversion is not necessary for now. However, if you ever intend to use 
        // it on input components, you probably want to implement it here.
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
}
