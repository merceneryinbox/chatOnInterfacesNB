/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatProInterfaces;

import java.sql.ResultSet;

/**
 *
 * @author mercenery
 */
public interface DialogPackReader {

    public void lookingForPacket();

    public void extrackPack();

    public ResultSet handlValues();
}
