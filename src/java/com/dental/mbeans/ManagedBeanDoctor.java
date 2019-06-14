/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dental.mbeans;

import com.dental.beans.Doctor;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Erik
 */
@ManagedBean
@ViewScoped
public class ManagedBeanDoctor {

    @lombok.Getter
    @lombok.Setter
    private Doctor doctor;
    
    public ManagedBeanDoctor() {
    }
    
}
