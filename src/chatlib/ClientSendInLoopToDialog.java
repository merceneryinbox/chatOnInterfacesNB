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
public class ClientSendInLoopToDialog implements DialogPacketSender {

    private ObjectOutputStream oos;
    private DialogPacket mesPacket;

    public ClientSendInLoopToDialog(ObjectOutputStream oos, DialogPacket mesPacket) {
        this.oos = oos;
        this.mesPacket = mesPacket;
    }

    @Override
    public boolean putAndFrow() {
        try {
            oos.writeObject(mesPacket);
            oos.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
