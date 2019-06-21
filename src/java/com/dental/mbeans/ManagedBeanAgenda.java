/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dental.mbeans;

import com.dental.beans.Acceso;
import com.dental.beans.Agenda;
import com.dental.beans.Clinica;
import com.dental.beans.Doctor;
import com.dental.beans.Evento;
import com.dental.beans.Paciente;
import com.dental.dao.DaoAgenda;
import com.dental.dao.MasterDao;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Erik
 */
@ManagedBean
@ViewScoped
public class ManagedBeanAgenda implements Serializable {

    //Contexto
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    private String appContext;

    private ScheduleModel lazyEventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private DaoAgenda daoAgenda;
    @lombok.Getter
    @lombok.Setter
    private List<Agenda> listAgenda;
    @lombok.Getter
    @lombok.Setter
    private Agenda agendaObj;
    private Doctor doctor;
    private Paciente px;
    private Evento evento;
    private Clinica clinica;
    private Acceso acceso;

    @lombok.Getter
    @lombok.Setter
    private List<Evento> listEvento;
    @lombok.Getter
    @lombok.Setter
    private List<Paciente> listPacientes;
    @lombok.Getter
    @lombok.Setter
    private List<Doctor> listDoctor;

    private MasterDao masterDao;
    @lombok.Getter
    @lombok.Setter
    private int idPx;
    @lombok.Getter
    @lombok.Setter
    private int idDr;

    @lombok.Getter
    @lombok.Setter
    private int idClinica;
    private String rol;
    @lombok.Getter
    @lombok.Setter
    private int idEvento;
    @lombok.Getter
    @lombok.Setter
    private boolean admingral = false;

    public ManagedBeanAgenda() {
        daoAgenda = new DaoAgenda();
        masterDao = new MasterDao();
        agendaObj = new Agenda();
        doctor = new Doctor();
        evento = new Evento();
        px = new Paciente();

        httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        appContext = httpServletRequest.getContextPath();
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        acceso = (Acceso) httpSession.getAttribute("acceso");
        rol = (String) httpSession.getAttribute("rol");
        admingral = rol.equalsIgnoreCase("admingral");
        idClinica = acceso.getIdTipoAcceso().getIdPerfil().getIdClinica().getId();
        idDr = acceso.getIdTipoAcceso().getIdUsuario();

    }

    @PostConstruct
    public void init() {
        listAgenda = daoAgenda.listaAgenda(idDr, idClinica);
        this.loadEvents();
        this.loadData();
    }

    public void loadEvents() {
        lazyEventModel = new LazyScheduleModel() {
            @Override
            public void loadEvents(Date start, Date end) {
                listAgenda.forEach(e -> {
                    addEvent(new DefaultScheduleEvent(e.getIdEvento().getEvento(),
                            e.getFechaInicio(), e.getFechaFin(), e));
                });
            }
        };

    }

    public void loadData() {
        try {
            agendaObj.setFechaInicio(new Date());
            agendaObj.setFechaFin(new Date());
            listDoctor = masterDao.listObject(doctor, "idClinica.id", idClinica);
            listEvento = masterDao.listObject(evento, "idClinica.id", idClinica);
            listPacientes = masterDao.listObject(px, "idClinica.id", idClinica);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void saveEvent() {

        Optional<Paciente> auxPx = listPacientes.stream().
                filter(i -> i.getId().equals(idPx)).findAny();
        Optional<Doctor> auxDr = listDoctor.stream().
                filter(i -> i.getId().equals(idDr)).findAny();
        Optional<Evento> auxEv = listEvento.stream().
                filter(i -> i.getId().equals(idEvento)).findAny();

        agendaObj.setIdDr(auxDr.get());
        agendaObj.setIdEvento(auxEv.get());
        agendaObj.setIdPx(auxPx.get());
        agendaObj.setIdClinica(idClinica);
        agendaObj.setEstatus(1);

        if (masterDao.saveOrUpdate(agendaObj)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Paciente agendado exitosamente", null));
            lazyEventModel.clear();
            lazyEventModel.getEvents().clear();
            loadEvents();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Ocurrio un error al agendar al paciente", null));
        }

    }

    public void addEvent() {

        if (event.getId() == null) {
            lazyEventModel.addEvent(event);
        } else {
            lazyEventModel.updateEvent(event);
        }

        event = new DefaultScheduleEvent();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        agendaObj = new Agenda();
        event = (ScheduleEvent) selectEvent.getObject();
        agendaObj = (Agenda) event.getData();
        idDr = agendaObj.getIdDr().getId();
        idPx = agendaObj.getIdPx().getId();
        idEvento = agendaObj.getIdEvento().getId();
        System.out.println("evento: " + agendaObj.getIdEvento().getEvento());
        System.out.println("fecha: " + agendaObj.getFechaInicio());
    }

    public void onDateSelect(SelectEvent selectEvent) {
        agendaObj = new Agenda();
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        Date d = (Date) selectEvent.getObject();
        agendaObj.setFechaInicio(d);
        agendaObj.setFechaFin(d);
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event actualizado", "Días movido:" + event.getDayDelta());
        updateEvent(event.getScheduleEvent(), message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Minutos movido:" + event.getMinuteDelta());
        updateEvent(event.getScheduleEvent(), message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void updateEvent(ScheduleEvent scEvent, FacesMessage message) {
        agendaObj = new Agenda();
        agendaObj = (Agenda) scEvent.getData();
        agendaObj.setFechaInicio(scEvent.getStartDate());
        agendaObj.setFechaFin(scEvent.getEndDate());
        
        System.out.println("fecha fin: " + scEvent.getEndDate());
        masterDao.saveOrUpdate(agendaObj);

        addMessage(message);
    }
}
