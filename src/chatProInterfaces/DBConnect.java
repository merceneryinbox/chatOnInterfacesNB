/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatProInterfaces;

import java.sql.Connection;

/**
 *
 * @author mercenery
 */
public interface DBConnect {

    public void driverLoading();

    public Connection connectionEstablish();
}
