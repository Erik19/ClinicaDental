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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    @lombok.Setter
    @lombok.Getter
    private Agenda agenda;
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

    private int idPx;
    private int idDr;
    
    @lombok.Getter
    @lombok.Setter
    private int idClinica;
    private String rol;

    public ManagedBeanAgenda() {
        daoAgenda = new DaoAgenda();
        masterDao = new MasterDao();
        agenda = new Agenda();
        doctor = new Doctor();
        px = new Paciente();
        
        httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        appContext = httpServletRequest.getContextPath();
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        acceso = (Acceso) httpSession.getAttribute("acceso");
        rol = (String) httpSession.getAttribute("rol");
    }

    @PostConstruct
    public void init() {
        listAgenda = daoAgenda.listaAgenda(idPx, idDr, idClinica);
        this.loadEvents();

    }

    public void loadEvents() {
        lazyEventModel = new LazyScheduleModel() {
            @Override
            public void loadEvents(Date start, Date end) {
                listAgenda.forEach(e -> {
                    addEvent(new DefaultScheduleEvent(e.getIdEvento().getEvento(),
                            e.getFechaInicio(), e.getFechaFin()));
                });
            }
        };
    }

    public void loadData() {
        try {
            listDoctor = masterDao.listObject(doctor, "idClinica.id", idClinica);
            listEvento = masterDao.listObject(evento, "idClinica.id", idClinica);
            listPacientes = masterDao.listObject(px, "idClinica.id", idClinica);
        } catch (Exception e) {
        }
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar.getTime();
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
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
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
