/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dental.mbeans;

import com.dental.beans.Acceso;
import com.dental.beans.Evento;
import com.dental.dao.MasterDao;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
public class ManagedBeanEventos implements Serializable {

    //Contexto
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    private String appContext;

    private Acceso acceso;
    @lombok.Getter
    @lombok.Setter
    private Evento evento;
    @lombok.Getter
    @lombok.Setter
    private String estatus;
    @lombok.Getter
    @lombok.Setter
    private List<Evento> listaEventos;
    @lombok.Getter
    @lombok.Setter
    private int idClinica;

    private MasterDao masterDao;

    public ManagedBeanEventos() {
        masterDao = new MasterDao();
        evento = new Evento();
        httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        appContext = httpServletRequest.getContextPath();
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        acceso = (Acceso) httpSession.getAttribute("acceso");
        idClinica = acceso.getIdTipoAcceso().getIdPerfil().getIdClinica().getId();
        this.loadEvents();
    }

    private void loadEvents() {
        try {
            listaEventos = masterDao.listObject(evento, "idClinica.id", idClinica);
        } catch (Exception ex) {
            Logger.getLogger(ManagedBeanEventos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addEvent() {

        this.evento.setEstatus(estatus.equals("true"));
        masterDao.saveOrUpdate(evento);
        loadEvents();
    }

}
