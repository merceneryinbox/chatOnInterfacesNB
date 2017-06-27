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

    private ChatProPostgresDBQueries talkToDb;
    private int isLogOk;
    private boolean returnState = false;

    @Override
    public boolean authoCheck(DialogPacket authPack) {
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
