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
public interface talkToDB {

    public boolean checkRegistration(String code);

    /* pSCheckRequest = connection.prepareStatement(
	"select code from chatpro.users  where upper(login) = upper" + "(?)");*/
    public void registrate(String login, String pass, int code);

    /*psSRegistration = connection.prepareStatement(
	"insert into chatpro.users (login,pass,code) values (?,?,?) on conflict (login) do nothing;");*/
    public void sessionAssigne(String login, int sessionID, long timestamp);

    /*pSSesionAprove = connection.prepareStatement(
	"insert into chatpro.aprovedsessions (login,timestampforsess) values(?,?) ;");*/
    public void saveFirstPackToDB(DialogPacket dialogPacket);

    /*
    pSSaveFirstPackInUsers = connectionDB.prepareStatement(
				"insert into chatpro.sessionsstory (login,sessionid,messages,timeincome) "
				+ "values(?,?,'session start',?)");
     */
    public void saveChatStory(DialogPacket dialogPacket);

    /*pSSaveFirstPackInUsers = connectionDB.prepareStatement(
	"insert into chatpro.sessionsstory (login,sessionid,messages,timeincome) "
				+ "values(?,?,'session start',?)");*/
 /*pSSaveStoryInSessions = connectionDB.prepareStatement(
	"insert into chatpro.sessionsstory (login, sessionid, messages, timeincome) values(?,?,?,?)");*/
    public void saveIllegalAttempt(Socket socket, DialogPacket dpIllegalA);

    /*psSIlligalAttempt = connectionDB.prepareStatement(
	"insert into chatpro.illigalattempt(login,pas,mes,ses,timeoftheattempt,ipadressofattempt) values (?,?,?,?,?,?)");*/
    public void bannU(String login);

    public void clearUSessionTab(String login);
}
