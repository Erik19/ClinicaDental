/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dental.dao;

import com.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Erik
 */
public class MasterDao implements Serializable {

    public boolean saveOrUpdate(Object o) {
        boolean res = true;
        Session ss = HibernateUtil.getSession();
        Transaction tt = ss.beginTransaction();
        try {
            ss.saveOrUpdate(o);
            tt.commit();
        } catch (Exception e) {
            tt.rollback();
            e.printStackTrace();
            res = false;
        } finally {
            HibernateUtil.close(ss);
        }
        return res;
    }

    public boolean delete(Object o) {
        boolean res = true;
        Session ss = HibernateUtil.getSession();
        Transaction tt = ss.beginTransaction();
        try {
            ss.delete(o);
            tt.commit();
        } catch (Exception e) {
            tt.rollback();
            e.printStackTrace();
            res = false;
        } finally {
            HibernateUtil.close(ss);
        }
        return res;
    }

    public List listObject(Object o, String property, int id) throws Exception {

        List<Object> aux = new ArrayList<>();
        Session ss = HibernateUtil.getSession();
        try {
            aux = ss.createCriteria(o.getClass()).
                    add(Restrictions.eq(property, id)).
                    list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close(ss);
        }

        return this.converToClass(aux, o);
    }
    
    public List listAdminObject(Object o) throws Exception {

        List<Object> aux = new ArrayList<>();
        Session ss = HibernateUtil.getSession();
        try {
            aux = ss.createCriteria(o.getClass()).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close(ss);
        }

        return this.converToClass(aux, o);
    }

    private List converToClass(List<Object> lst, Object o) throws Exception {

        return lst.stream().map(i -> o.getClass().cast(i)).
                collect(Collectors.toList());
    }
}
