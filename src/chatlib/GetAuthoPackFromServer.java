/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.DialogPackReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import markerIface.DialogPacket;

/**
 *
 * @author mercenery
 */
public class GetAuthoPackFromServer implements DialogPackReader {

    private static ObjectInputStream ois;
    private static DialogPacket authBPacket;

    public GetAuthoPackFromServer(ObjectInputStream ois) {
        GetAuthoPackFromServer.ois = ois;
    }

    @Override
    public DialogPacket lookingForPacket() {
        try {
            authBPacket = (DialogPacket) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return authBPacket;
    }

}
