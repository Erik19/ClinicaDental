/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dental.mbeans;

import com.dental.beans.Acceso;
import com.dental.beans.Clinica;
import com.dental.beans.Direccion;
import com.dental.beans.Doctor;
import com.dental.beans.Estado;
import com.dental.beans.Pais;
import com.dental.beans.Persona;
import com.dental.dao.MasterDao;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;
import sun.misc.IOUtils;

/**
 *
 * @author Erik
 */
@ManagedBean
@ViewScoped
public class ManagedBeanDoctor {

    //Contexto
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    private String appContext;

    @lombok.Getter
    @lombok.Setter
    private int idClinica;
    private String rol;
    @lombok.Getter
    @lombok.Setter
    private boolean admingral = false;

    @lombok.Getter
    @lombok.Setter
    private Doctor doctor;
    @lombok.Getter
    @lombok.Setter
    private Persona persona;
    @lombok.Getter
    @lombok.Setter
    private Estado estadoObj;
    @lombok.Getter
    @lombok.Setter
    private Pais paisObj;
    @lombok.Getter
    @lombok.Setter
    private Direccion direccion;
    @lombok.Getter
    @lombok.Setter
    private List<Clinica> listClinica;
    @lombok.Getter
    @lombok.Setter
    private List<Pais> listPais;
    @lombok.Getter
    @lombok.Setter
    private List<Estado> listEstado;

    @lombok.Getter
    @lombok.Setter
    private List<Doctor> listDoctor;

    @lombok.Getter
    @lombok.Setter
    private int idEstado;
    @lombok.Getter
    @lombok.Setter
    private int idPais;

    private MasterDao masterDao;
    private Acceso acceso;
    private Clinica clinicaSelect;
    
    @lombok.Getter
    @lombok.Setter
    private UploadedFile file;

    public ManagedBeanDoctor() {
        masterDao = new MasterDao();
        persona = new Persona();
        doctor = new Doctor();
        estadoObj = new Estado();
        paisObj = new Pais();
        direccion = new Direccion();
        clinicaSelect = new Clinica();
        httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        appContext = httpServletRequest.getContextPath();
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        acceso = (Acceso) httpSession.getAttribute("acceso");
        rol = (String) httpSession.getAttribute("rol");
        idClinica = acceso.getIdTipoAcceso().getIdPerfil().getIdClinica().getId();

        if (rol.equalsIgnoreCase("admingral")) {
            loadClinicasAdmin();
            admingral = true;
        } else {
            loadDoctor();
            admingral = false;
        }

        loadInfoDireccion();
    }

    public void loadClinicasAdmin() {
        try {
            listClinica = masterDao.listAdminObject(clinicaSelect);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadDoctor() {
        try {
            listDoctor = masterDao.listObject(doctor, "idClinica.id", idClinica);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
            System.out.println("id: " + idPais);
            listEstado = masterDao.listObject(estadoObj, "idPais", idPais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void redirect() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(appContext + "/secciones/administracion/doctores/edit_doctor.xhtml");
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
    
    public void upload() {
        if(file != null) {
            doctor.setTituloPdf(file.getContents());
        }
    }

    public void saveOrUpdate() {
        if (rol.equalsIgnoreCase("Admingral")) {
            Optional<Clinica> optClin = listClinica.stream().
                    filter(e -> e.getId().equals(idClinica)).findFirst();
            clinicaSelect = optClin.get();
        } else {
            clinicaSelect = acceso.getIdTipoAcceso().getIdPerfil().getIdClinica();
        }

        doctor.setIdClinica(clinicaSelect);
        direccion.setIdEstado(estadoObj);
        direccion.setIdPais(paisObj);
        if (masterDao.saveOrUpdate(direccion)) {
            persona.setIdDireccion(direccion);
            if (masterDao.saveOrUpdate(persona)) {
                doctor.setIdPersona(persona);
                doctor.setFechaRegistro(new Date());
                masterDao.saveOrUpdate(doctor);

                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(appContext + "/secciones/administracion/pacientes/edit_paciente.xhtml");
                } catch (IOException e1) {
                    FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A ocurrido un error al intentar redireccionar.", "Error"));
                }
            }
        }
    }

}
