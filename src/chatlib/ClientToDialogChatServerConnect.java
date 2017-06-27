/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.ClientConnect;
import java.net.Socket;

/**
 *
 * @author mercenery
 */
public class ClientToDialogChatServerConnect implements ClientConnect {

    Socket dialogSock;

    @Override
    public Socket connectToServer() {
        try {
            dialogSock = new Socket("localhost", 55555);
        } catch (Exception e) {
        }
        return dialogSock;
    }

}
