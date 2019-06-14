/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dental.beans;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Erik
 */
@Entity
@Table(name = "direccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direccion.findAll", query = "SELECT d FROM Direccion d")})
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CALLE")
    private String calle;
    @Column(name = "NO_INT")
    private String noInt;
    @Column(name = "NO_EXT")
    private String noExt;
    @Column(name = "COLONIA")
    private String colonia;
    @Column(name = "CP")
    private String cp;
    @Column(name = "MUNICIPIO")
    private String municipio;
    @Column(name = "OTRAS_REFERENCIAS")
    private String otrasReferencias;

    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID")
    @ManyToOne
    private Estado idEstado;
    @JoinColumn(name = "ID_PAIS", referencedColumnName = "ID")
    @ManyToOne
    private Pais idPais;

    public Direccion() {
    }

    public Direccion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getOtrasReferencias() {
        return otrasReferencias;
    }

    public void setOtrasReferencias(String otrasReferencias) {
        this.otrasReferencias = otrasReferencias;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
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
        if (!(object instanceof Direccion)) {
            return false;
        }
        Direccion other = (Direccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dental.beans.Direccion[ id=" + id + " ]";
    }

    /**
     * @return the noInt
     */
    public String getNoInt() {
        return noInt;
    }

    /**
     * @param noInt the noInt to set
     */
    public void setNoInt(String noInt) {
        this.noInt = noInt;
    }

    /**
     * @return the noExt
     */
    public String getNoExt() {
        return noExt;
    }

    /**
     * @param noExt the noExt to set
     */
    public void setNoExt(String noExt) {
        this.noExt = noExt;
    }

    public String getCompleteAddres() {

        String add = ("".equals(this.calle) ? "" : this.calle + ", ")
                + ("".equals(this.colonia) ? "" : this.colonia + ", ")
                + ("".equals(this.noExt) ? "" : this.noExt + ", ")
                + ("".equals(this.noInt) ? "" : this.noInt + ", ")
                + ("".equals(this.municipio) ? "" : this.municipio + ", ")
                + ("".equals(this.cp) ? "" : this.cp);

        return add;
    }

}
