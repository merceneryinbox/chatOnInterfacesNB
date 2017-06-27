/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.DialogPacketSender;
import java.io.IOException;
import java.io.ObjectOutputStream;
import markerIface.DialogPacket;

/**
 *
 * @author mercenery
 */
public class AuthoPackBackToUser implements DialogPacketSender {

    private static ObjectOutputStream oos;
    private static int sessionID;
    private static long timeStampDef;
    private static DialogPacket AuthPacketBack;
    private static String connectOrNotString;

    public AuthoPackBackToUser(ObjectOutputStream oos, String connectOrNotString, int sessionID, long timeStampDef) {
        AuthoPackBackToUser.oos = oos;
        AuthoPackBackToUser.connectOrNotString = connectOrNotString;
        AuthoPackBackToUser.sessionID = sessionID;
        AuthoPackBackToUser.timeStampDef = timeStampDef;
    }

    @Override
    public boolean putAndFrowToCl() {
        AuthPacketBack = new DialogPacket(connectOrNotString, connectOrNotString, connectOrNotString, sessionID, timeStampDef);
        try {
            oos.writeObject(AuthPacketBack);
            oos.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean putAndFrowToAuthServ() {
        return false;
    }
}
