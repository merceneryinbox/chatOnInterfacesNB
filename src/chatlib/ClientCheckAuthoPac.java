/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.AuthoPackChecker;
import java.io.ObjectInputStream;
import java.net.Socket;
import markerIface.DialogPacket;

/**
 *
 * @author mercenery
 */
public class ClientCheckAuthoPac implements AuthoPackChecker {

    private final Socket socket;
    private DialogPacket authPack;
    private String codeWord;
    private boolean result;

    public ClientCheckAuthoPac(Socket socket) {
        this.socket = socket;
    }

    @Override
    public boolean authoCheck() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            authPack = (DialogPacket) ois.readObject();
            codeWord = authPack.log;
            result = !codeWord.equalsIgnoreCase("quit");

        } catch (Exception e) {
        }
        return result;
    }
}
