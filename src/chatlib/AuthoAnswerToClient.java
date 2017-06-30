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

    private final String login;
    private final int session;
    private final long time;
    private DialogPacket authBackPacket;
    private final Socket socket;
    private boolean isOk = false;

    public AuthoAnswerToClient(Socket socket, String login, int session, long time) {
        this.socket = socket;
        this.login = login;
        this.session = session;
        this.time = time;
    }

    @Override
    public boolean putAndFrow() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            int code = new ChatProPostgresDBQueries().checkPermission(login);
            if (code == -1) {
                authBackPacket = new DialogPacket("quit", "quit", "quit", 0, 0);
            } else {
                authBackPacket = new DialogPacket("ok", "ok", "ok", session, time);
                isOk = true;
            }
            boolean isputAndFrowOk = new SendPacket(oos, authBackPacket).putAndFrow();
        } catch (Exception e) {
            return false;
        }
        return isOk;
    }
}
