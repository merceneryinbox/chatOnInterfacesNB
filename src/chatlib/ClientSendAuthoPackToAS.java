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
public class ClientSendAuthoPackToAS implements DialogPacketSender {

    private DialogPacket authPacket;
    private final Socket socket;
    private final String login;

    /**
     *
     * @param socket
     * @param login
     */
    public ClientSendAuthoPackToAS(Socket socket, String login) {
        this.socket = socket;
        this.login = login;
    }

    @Override
    public boolean putAndFrow() {
        authPacket = new DialogPacket(login, "0", "0", 0, 0);

        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            oos.writeObject(authPacket);
            oos.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
