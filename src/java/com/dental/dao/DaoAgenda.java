/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dental.dao;

import com.dental.beans.Agenda;
import com.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Erik
 */
public class DaoAgenda implements Serializable{
    
    public List<Agenda> listaAgenda(int idDr, int idPx, int idClin){
        List<Agenda> res = new ArrayList<>();
        Session ss = HibernateUtil.getSession();
        try {
            Criteria cr = ss.createCriteria(Agenda.class);
            if(idDr != 0){
                cr.add(Restrictions.eq("idDr.id", idDr));
            }
            if(idPx != 0){
                cr.add(Restrictions.eq("idPx.id", idPx));
            }
            cr.add(Restrictions.eq("idPx.id", idPx));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            HibernateUtil.close(ss);
        }
        return res;
    }
    
   
}
