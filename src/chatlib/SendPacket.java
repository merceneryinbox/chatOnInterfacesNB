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
public class SendPacket implements DialogPacketSender {

    private final ObjectOutputStream oos;
    private final DialogPacket mesPacket;

    public SendPacket(ObjectOutputStream oos, DialogPacket mesPacket) {
        this.oos = oos;
        this.mesPacket = mesPacket;
    }

    @Override
    public boolean putAndFrow() {
        try {
            oos.writeObject(mesPacket);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            return false;
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
            }
        }
        return true;
    }
}
