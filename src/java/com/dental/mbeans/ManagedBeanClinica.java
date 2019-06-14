/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dental.mbeans;

import com.dental.beans.Acceso;
import com.dental.beans.Clinica;
import com.dental.dao.MasterDao;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Erik
 */
@ManagedBean
@ViewScoped
public class ManagedBeanClinica implements Serializable{

    //Contexto
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    private String appContext;
    
    private Clinica clinica;
    private Acceso acceso;
    private MasterDao masterDao;
    @lombok.Getter
    @lombok.Setter
    public List<Clinica> listClinica; 
    
    public ManagedBeanClinica() {
        masterDao = new MasterDao();
        httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        appContext = httpServletRequest.getContextPath();
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        acceso = (Acceso) httpSession.getAttribute("acceso");
        clinica = new Clinica();
        this.loadClinicas();
    }
    
    private void loadClinicas(){
        try {
            listClinica = masterDao.listAdminObject(clinica);
        } catch (Exception ex) {
            Logger.getLogger(ManagedBeanClinica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addClinica(){
        
    }
    
}
