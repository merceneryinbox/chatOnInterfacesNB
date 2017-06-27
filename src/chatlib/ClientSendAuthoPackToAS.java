/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.DialogPacketSender;
import java.io.ObjectOutputStream;
import markerIface.DialogPacket;

/**
 *
 * @author mercenery
 */
public class ClientSendAuthoPackToAS implements DialogPacketSender {

    private static DialogPacket authPacket;
    private static ObjectOutputStream oos;
    private static String login;

    public ClientSendAuthoPackToAS(ObjectOutputStream oos, String login) {
        this.oos = oos;
        this.login = login;
    }

    @Override
    public boolean putAndFrow() {
        authPacket = new DialogPacket(login, "0", "0", 0, 0);
        try {
            oos.writeObject(authPacket);
            oos.flush();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
