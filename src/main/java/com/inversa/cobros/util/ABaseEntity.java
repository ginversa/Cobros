/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.util;

import java.io.Serializable;

/**
 *
 * @author Z420WK
 */
public abstract class ABaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public abstract Long getIdentifier();
    
}
