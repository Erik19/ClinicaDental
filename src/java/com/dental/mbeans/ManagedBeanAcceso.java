/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dental.mbeans;

import com.dental.beans.Acceso;
import com.dental.dao.DaoAcceso;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
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
public class ManagedBeanAcceso implements Serializable {

    //Contexto
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    private boolean cambiarClave;//Para indicar que se tiene que cambiar la clave por una solicitud de recuperacion
    private String appContext;

    @lombok.Getter
    @lombok.Setter
    private String usuario;
    @lombok.Getter
    @lombok.Setter
    private String password;
    private DaoAcceso daoAcceso;
    
     private boolean logged;
     private Acceso acceso; 
    

    public ManagedBeanAcceso() {
        httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        appContext = httpServletRequest.getContextPath();
        daoAcceso = new DaoAcceso();
    }

    public void login() {
        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        acceso = daoAcceso.login(this.usuario);
       
         if (acceso != null) {
            if (daoAcceso.comparaPassword(usuario, password, acceso.getPassword())) {
                this.logged = true;
                    HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                    httpSession.setAttribute("acceso", acceso);
                    httpSession.setAttribute("usernick", this.acceso.getUsuario());//Nombre Usuario
                    httpSession.setAttribute("logeado", this.logged);//Bandera
                    httpSession.setAttribute("rol", acceso.getIdTipoAcceso().getIdRol().getRol());//rol
                    httpSession.setAttribute("perfil", (acceso.getIdTipoAcceso().getIdPerfil().getPerfil() == null) ?
                            0 : acceso.getIdTipoAcceso().getIdPerfil().getPerfil());//En caso de que el perfil sea nulo evitamos que el usuario pueda hacer cosas en la aplicacion
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(httpServletRequest.getContextPath() + "/secciones/inicio.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(ManagedBeanAcceso.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Password incorrecta", null));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "El usuario no existe", null));
        }
      
    }
    
    public void logout() {
        //Invalidate the session and remove the atributes
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        httpSession.removeAttribute("acceso");
        httpSession.removeAttribute("rol");
        httpSession.removeAttribute("usernick");
        httpSession.removeAttribute("logeado");
        httpSession.removeAttribute("perfil");
      
        httpSession.invalidate();
       
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(appContext);
          
        } catch (IOException ex) {
            Logger.getLogger(ManagedBeanAcceso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
