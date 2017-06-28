/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.DialogPacketSender;
import java.io.ObjectOutputStream;
import java.net.Socket;
import markerIface.DialogPacket;

/**
 *
 * @author mercenery
 */
public class AuthoAnswerToClient implements DialogPacketSender {

    private DialogPacket incomPacket;
    private String login;
    private DialogPacket authBackPacket;
    private Socket socket;
    private boolean isOk = false;

    public AuthoAnswerToClient(Socket socket, DialogPacket incomPacket) {
        this.socket = socket;
        this.incomPacket = incomPacket;
    }

    @Override
    public boolean putAndFrow() {
        login = incomPacket.log;
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            int code = new ChatProPostgresDBQueries().checkPermission(login);
            if (code == -1) {
                authBackPacket = new DialogPacket("quit", "quit", "quit", 0, 0);
            } else {
                authBackPacket = new DialogPacket("ok", "ok", "ok", 0, 0);
                isOk = true;
            }
            oos.writeObject(authBackPacket);
        } catch (Exception e) {
            return false;
        }
        return isOk;
    }
}
