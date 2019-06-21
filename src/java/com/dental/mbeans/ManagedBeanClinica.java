/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dental.mbeans;

import com.dental.beans.Acceso;
import com.dental.beans.Clinica;
import com.dental.beans.Direccion;
import com.dental.beans.Estado;
import com.dental.beans.Pais;
import com.dental.dao.MasterDao;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Erik
 */
@ManagedBean
@ViewScoped
public final class ManagedBeanClinica implements Serializable {

    //Contexto
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    private String appContext;
    private HttpSession httpSession;
    @lombok.Getter
    @lombok.Setter
    private Clinica clinica;
    private Acceso acceso;
    private MasterDao masterDao;
    @lombok.Getter
    @lombok.Setter
    public List<Clinica> listClinica;
    @lombok.Getter
    @lombok.Setter
    private List<Pais> listPais;
    @lombok.Getter
    @lombok.Setter
    private List<Estado> listEstado;

    @lombok.Getter
    @lombok.Setter
    private Direccion direccion;
    @lombok.Getter
    @lombok.Setter
    private Estado estadoObj;
    @lombok.Getter
    @lombok.Setter
    private Pais paisObj;

    @lombok.Getter
    @lombok.Setter
    private Integer idPais;
    @lombok.Getter
    @lombok.Setter
    private Integer idEstado;

    @lombok.Getter
    @lombok.Setter
    private UploadedFile file;

    private boolean editar = false;
    @lombok.Getter
    @lombok.Setter
    private boolean active = true;

    public ManagedBeanClinica() {
        masterDao = new MasterDao();
        httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        appContext = httpServletRequest.getContextPath();
        httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        acceso = (Acceso) httpSession.getAttribute("acceso");
        clinica = httpSession.getAttribute("clinica") != null ? (Clinica) httpSession.getAttribute("clinica")
                : new Clinica();
        direccion = new Direccion();
        estadoObj = new Estado();
        paisObj = new Pais();
        this.loadClinicas();
        this.loadInfoDireccion();
        this.loadData();
    }

    private void loadData() {
        this.editar = httpSession.getAttribute("clinica") != null;
        idPais = httpSession.getAttribute("clinica") != null ? clinica.getIdDireccion().getIdPais().getId() : 0;
        idEstado = httpSession.getAttribute("clinica") != null ? clinica.getIdDireccion().getIdEstado().getId() : 0;
        if (idEstado > 0) {
            loadEstados();
        }
        httpSession.setAttribute("clinica", null);
    }

    private void loadClinicas() {
        try {
            //Clinica aux = new Clinica();
            listClinica = masterDao.listAdminObject(clinica);
        } catch (Exception ex) {
            Logger.getLogger(ManagedBeanClinica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirect() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(appContext + "/secciones/administracion/clinica/edit_clinica.xhtml");
        } catch (IOException e1) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A ocurrido un error al intentar redireccionar.", "Error"));
        }
    }

    public String onFlowProcess(FlowEvent event) {

        if (event.getNewStep().contains("confirm")) {
            Optional<Estado> opt = listEstado.stream().
                    filter(e -> e.getId().equals(idEstado)).findFirst();
            estadoObj = opt.get();
            Optional<Pais> optPais = listPais.stream().
                    filter(p -> p.getId().equals(idPais)).findFirst();
            paisObj = optPais.get();
        }
        return event.getNewStep();
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void loadInfoDireccion() {
        try {
            listPais = masterDao.listAdminObject(paisObj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadEstados() {
        try {
            listEstado = masterDao.listObject(estadoObj, "idPais", idPais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addClinica() {

        direccion.setIdEstado(estadoObj);
        direccion.setIdPais(paisObj);
        if (masterDao.saveOrUpdate(direccion)) {
            clinica.setIdDireccion(direccion);
           
            if (!editar) {
                clinica.setFechaRegistro(new Date());
                 clinica.setEstatus(active);
            }
            if (masterDao.saveOrUpdate(clinica)) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(appContext + "/secciones/administracion/clinica/edit_clinica.xhtml");
                } catch (IOException e1) {
                    FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A ocurrido un error al intentar redireccionar.", "Error"));
                }
            }
        }
    }

    public void editClinica(Clinica cl) {

        if (cl != null) {
            System.out.println("clin: " + cl.getNombre());

            try {
                httpSession.setAttribute("clinica", cl);
                FacesContext.getCurrentInstance().getExternalContext().redirect(appContext + "/secciones/administracion/clinica/edit_clinica.xhtml");
            } catch (IOException e1) {
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A ocurrido un error al intentar redireccionar.", "Error"));
            }
        } else {
            FacesMessage msg = new FacesMessage("Debe seleccionar una clínica.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void changeStatus(Clinica cl) {
        if (masterDao.saveOrUpdate(cl)) {
            FacesMessage msg = new FacesMessage("Estatus cambiado exitosamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }else{
            FacesMessage msg = new FacesMessage("Ocurrío un error al actualizar el estatus de la clínica.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
