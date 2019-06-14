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
@Table(name = "acceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acceso.findAll", query = "SELECT a FROM Acceso a")})
public class Acceso implements Serializable {

    @JoinColumn(name = "ID_TIPO_ACCESO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoAcceso idTipoAcceso;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
   
    @Basic(optional = false)
    @Column(name = "ULTIMO_ACCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoAcceso;
    @Basic(optional = false)
    @Column(name = "INTENTOS")
    private int intentos;
    @Basic(optional = false)
    @Column(name = "ESTATUS")
    private int estatus;
  
    public Acceso() {
    }

    public Acceso(Integer id) {
        this.id = id;
    }

    public Acceso(Integer id, String usuario, String password, Date ultimoAcceso, int intentos, int estatus) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.ultimoAcceso = ultimoAcceso;
        this.intentos = intentos;
        this.estatus = estatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Date ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
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
        if (!(object instanceof Acceso)) {
            return false;
        }
        Acceso other = (Acceso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dental.beans.Acceso[ id=" + id + " ]";
    }

    public TipoAcceso getIdTipoAcceso() {
        return idTipoAcceso;
    }

    public void setIdTipoAcceso(TipoAcceso idTipoAcceso) {
        this.idTipoAcceso = idTipoAcceso;
    }
    
}
