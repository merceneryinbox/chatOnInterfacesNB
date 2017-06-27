/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.ClientConnect;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mercenery
 */
public class ClientToAuthChatServerConnect implements ClientConnect {

    private static Socket socketAuth;

    public Socket connectToServer() {
        try {
            socketAuth = new Socket("localhost", 12345);
        } catch (IOException ex) {
            Logger.getLogger(ClientToAuthChatServerConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return socketAuth;
    }
}
