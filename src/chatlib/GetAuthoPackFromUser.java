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
public class GetAuthoPackFromUser implements DialogPackReader {

    ObjectInputStream ois;
    DialogPacket authPack;

    public GetAuthoPackFromUser(ObjectInputStream ois) {
        this.ois = ois;
    }

    @Override
    public DialogPacket lookingForPacket() {
        try {
            authPack = (DialogPacket) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return authPack;
    }
}
