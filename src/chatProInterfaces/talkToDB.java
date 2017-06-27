/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatProInterfaces;

/**
 *
 * @author mercenery
 */
public interface talkToDB {

    public void checkRegistration();

    /* pSCheckRequest = connection.prepareStatement(
	"select code from chatpro.users  where upper(login) = upper" + "(?)");*/

 /*pSControllUserIncom = connectionDB.prepareStatement(
	"select * from chatpro.aprovedsessions where upper(login) = upper (?)");*/
    public void registrate();

    /*psSRegistration = connection.prepareStatement(
	"insert into chatpro.users (login,pass,code) values (?,?,?) on conflict (login) do nothing;");*/
    public void sessionAssigne();

    /*pSSesionAprove = connection.prepareStatement(
	"insert into chatpro.aprovedsessions (login,timestampforsess) values(?,?) ;");*/
    public void saveChatStory();

    /*pSSaveFirstPackInUsers = connectionDB.prepareStatement(
	"insert into chatpro.sessionsstory (login,sessionid,messages,timeincome) "
				+ "values(?,?,'session start',?)");*/
 /*pSSaveStoryInSessions = connectionDB.prepareStatement(
	"insert into chatpro.sessionsstory (login, sessionid, messages, timeincome) values(?,?,?,?)");*/
    public void saveIllegalAttempt();

    /*psSIlligalAttempt = connectionDB.prepareStatement(
	"insert into chatpro.illigalattempt(login,pas,mes,ses,timeoftheattempt,ipadressofattempt) values (?,?,?,?,?,?)");*/
    public void bannU();

    public void disconnectBannedU();

    public void clearSessionTab();
}
