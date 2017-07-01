/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.DialogPackReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import markerIface.DialogPacket;

/**
 *
 * @author mercenery
 */
public class GetPacket implements DialogPackReader {

    private final Socket socket;
    private final ObjectInputStream ois;
    private DialogPacket packet;

    public GetPacket(Socket socket) throws IOException {
        this.socket = socket;
        ois = new ObjectInputStream(socket.getInputStream());

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
                socket.close();
            } catch (IOException e) {
            }
        }
        return packet;
    }
}
