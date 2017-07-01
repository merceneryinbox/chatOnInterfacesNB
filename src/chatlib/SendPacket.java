/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.DialogPacketSender;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import markerIface.DialogPacket;

/**
 *
 * @author mercenery
 */
public class SendPacket implements DialogPacketSender {

    private final Socket socket;
    private final ObjectOutputStream oos;
    private final DialogPacket mesPacket;

    public SendPacket(Socket socket, DialogPacket mesPacket) throws IOException {
        this.socket = socket;
        this.mesPacket = mesPacket;
        oos = new ObjectOutputStream(socket.getOutputStream());
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
