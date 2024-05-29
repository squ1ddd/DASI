/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import business.model.Establishment;

/**
 *
 * @author mlaurent1
 */
public class EstablishmentDAO {
    public void create(Establishment establishment) {
        JpaUtil.obtenirContextePersistance().persist(establishment);
    }

    public Establishment findByCode(String establishmentCode) {
        return JpaUtil.obtenirContextePersistance().find(Establishment.class, establishmentCode);
    }
}
