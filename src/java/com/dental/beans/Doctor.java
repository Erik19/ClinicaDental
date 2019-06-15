/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dental.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Erik
 */
@Entity
@Table(name = "doctor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Doctor.findAll", query = "SELECT d FROM Doctor d")})
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CEDULA")
    private String cedula;
    @Column(name = "TITULO")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "ESTATUS")
    private boolean estatus;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Persona idPersona;
    @Lob
    @Column(name = "TITULO_PDF")
    private byte[] tituloPdf;
    @Basic(optional = false)
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @JoinColumn(name = "ID_CLINICA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Clinica idClinica;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "OTRO_CONTACTO")
    private String otroContacto;

    public Doctor() {
    }

    public Doctor(Integer id) {
        this.id = id;
    }

    public Doctor(Integer id, String cedula, boolean estatus) {
        this.id = id;
        this.cedula = cedula;
        this.estatus = estatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public byte[] getTituloPdf() {
        return tituloPdf;
    }

    public void setTituloPdf(byte[] tituloPdf) {
        this.tituloPdf = tituloPdf;
    }

    public boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Doctor)) {
            return false;
        }
        Doctor other = (Doctor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dental.beans.Doctor[ id=" + id + " ]";
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the idClinica
     */
    public Clinica getIdClinica() {
        return idClinica;
    }

    /**
     * @param idClinica the idClinica to set
     */
    public void setIdClinica(Clinica idClinica) {
        this.idClinica = idClinica;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the otroContacto
     */
    public String getOtroContacto() {
        return otroContacto;
    }

    /**
     * @param otroContacto the otroContacto to set
     */
    public void setOtroContacto(String otroContacto) {
        this.otroContacto = otroContacto;
    }

}
