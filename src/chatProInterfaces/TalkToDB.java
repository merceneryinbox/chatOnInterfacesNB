/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatProInterfaces;

import java.net.Socket;
import markerIface.DialogPacket;

/**
 *
 * @author mercenery
 */
public interface TalkToDB {

    public int checkPermission(String login);

    /* pSCheckRequest = connection.prepareStatement(
	"select code from chatpro.users  where upper(login) = upper" + "(?)");*/
    public boolean registrate(String login, String pass, int code);

    /*psSRegistration = connection.prepareStatement(
	"insert into chatpro.users (login,pass,code) values (?,?,?) on conflict (login) do nothing;");*/
    public boolean sessionAssigne(String login, int sessionID, long timestamp);

    /*pSSesionAprove = connection.prepareStatement(
	"insert into chatpro.aprovedsessions (login,timestampforsess) values(?,?) ;");*/
    public boolean saveFirstPackToDB(DialogPacket dialogPacket);

    /*
    pSSaveFirstPackInUsers = connectionDB.prepareStatement(
				"insert into chatpro.sessionsstory (login,sessionid,messages,timeincome) "
				+ "values(?,?,'session start',?)");
     */
    public boolean saveChatStory(DialogPacket dialogPacket);

    /*pSSaveFirstPackInUsers = connectionDB.prepareStatement(
	"insert into chatpro.sessionsstory (login,sessionid,messages,timeincome) "
				+ "values(?,?,'session start',?)");*/
 /*pSSaveStoryInSessions = connectionDB.prepareStatement(
	"insert into chatpro.sessionsstory (login, sessionid, messages, timeincome) values(?,?,?,?)");*/
    public boolean saveIllegalAttempt(Socket socket, DialogPacket dpIllegalA);

    /*psSIlligalAttempt = connectionDB.prepareStatement(
	"insert into chatpro.illigalattempt(login,pas,mes,ses,timeoftheattempt,ipadressofattempt) values (?,?,?,?,?,?)");*/
    public boolean bannU(String login);

    public boolean clearUSessionTab(String login);
}
