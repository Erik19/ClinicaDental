/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dental.dao;

import com.dental.beans.Acceso;
import com.util.HibernateUtil;
import com.util.security.Protection;
import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Erik
 */
public class DaoAcceso implements Serializable {

    public Acceso login(String user) {
        Acceso acceso = null;
        Session ss = HibernateUtil.getSession();
        try {
            acceso = (Acceso) ss.createCriteria(Acceso.class).
                    add(Restrictions.eq("usuario", user))
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            HibernateUtil.close(ss);
        }
        return acceso;
    }
    
    public boolean comparaPassword(String usuario, String passEnviada, String passBd) {

        boolean res;
        try {
            BASE64Encoder var8 = new BASE64Encoder();
            byte[] var9 = Protection.makeDigest(usuario, passEnviada);
            String var10 = var8.encode(var9);
            res = passBd.equals(var10);
            System.out.println("pass: " + var10);
        } catch (Exception ex) {
            System.out.println("NO ES POSIBLE COMPARAR CONTRASEÑA");
            res = false;
        }

        return res;
    }
}
