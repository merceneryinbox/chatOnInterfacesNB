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
public class PermitOrNot implements AuthoPackChecker {

    private static DialogPacket authPack;
    private static ChatProPostgresDBQueries talkToDb;
    private static int isLogOk;
    private static boolean returnState = false;

    public PermitOrNot(ChatProPostgresDBQueries talkToDb, int isLogOk, DialogPacket authPack) {
        PermitOrNot.talkToDb = talkToDb;
        PermitOrNot.isLogOk = isLogOk;
        PermitOrNot.authPack = authPack;
    }

    @Override
    public boolean authoCheck() {
        String login = authPack.log;
        String password = authPack.pass;
        isLogOk = talkToDb.checkPermission(login);
        if (isLogOk == 0) {
            talkToDb.registrate(login, password, isLogOk);
            returnState = true;
        }
        return returnState;
    }

}
