/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.AuthoPackChecker;
import markerIface.DialogPacket;

/**
 *
 * @author mercenery
 */
public class ClientCheckAuthoPac implements AuthoPackChecker {

    private static DialogPacket authPack;
    private static String codeWord;

    public ClientCheckAuthoPac(DialogPacket authPack) {
        ClientCheckAuthoPac.authPack = authPack;
    }

    @Override
    public boolean authoCheck() {
        codeWord = authPack.log;
        if (codeWord.equalsIgnoreCase("quit")) {
            return false;
        }
        return true;
    }

}
