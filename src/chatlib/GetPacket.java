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
public class GetPacket implements DialogPackReader {

    private static ObjectInputStream ois;
    private static DialogPacket packet;

    public GetPacket(ObjectInputStream ois) {
        GetPacket.ois = ois;
    }

    @Override
    public DialogPacket lookingForPacket() {
        try {
            packet = (DialogPacket) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
            }
        }
        return packet;
    }
}
