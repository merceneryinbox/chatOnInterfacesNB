/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.talkToDB;
import java.net.Socket;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import markerIface.DialogPacket;

/**
 *
 * @author mercenery
 */
public class chatProPostgresDBQueries implements talkToDB {

    private static Connection connection;
    private static PreparedStatement pSCheckRequest;
    private static PreparedStatement psSRegistration;
    private static PreparedStatement pSSesionAprove;
    private static PreparedStatement pSSaveFirstPackInUsers;
    private static PreparedStatement pSSaveStoryInSessions;
    private static PreparedStatement psSIlligalAttempt;
    private static PreparedStatement psBanU;
    private static CallableStatement psClearSession;
    private static ResultSet resultSetCheck;
    private static boolean coded;

    public chatProPostgresDBQueries() {
        try {
            pSCheckRequest = connection.prepareStatement(
                    "select code from chatpro.users  where upper(login) = upper" + "(?)");
            psSRegistration = connection.prepareStatement(
                    "insert into chatpro.users (login,pass,code) values (?,?,?) on conflict (login) do nothing;");
            pSSesionAprove = connection.prepareStatement(
                    "insert into chatpro.aprovedsessions (login, sessionID, timestampforsess) values(?,?,?) ;");
            pSSaveFirstPackInUsers = connection.prepareStatement(
                    "insert into chatpro.sessionsstory (login,sessionid,messages,timeincome) "
                    + "values(?,?,'session start',?)");
            pSSaveStoryInSessions = connection.prepareStatement(
                    "insert into chatpro.sessionsstory (login, sessionid, messages, timeincome) values(?,?,?,?)");
            psBanU = connection.prepareStatement("update users set code = -1 where upper(login) = upper(?);");
            psClearSession = connection.prepareCall("delete fromapprovedsessions where upper(login) = upper(?);");

        } catch (SQLException e) {
        }
    }

    @Override
    public boolean checkRegistration(String code) {
        try {
            pSCheckRequest.setString(1, code);
            resultSetCheck = pSCheckRequest.executeQuery();
            resultSetCheck.next();
            coded = !resultSetCheck.getString("code").equalsIgnoreCase(null);
        } catch (SQLException e) {
        }

        return coded;
    }

    @Override
    public void registrate(String login, String pass, int code) {
        try {
            psSRegistration.setString(1, login);
            psSRegistration.setString(2, pass);
            psSRegistration.setInt(3, code);
            psSRegistration.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void sessionAssigne(String login, int sessionID, long timestamp) {
        try {
            pSSesionAprove.setString(1, login);
            pSSesionAprove.setInt(2, sessionID);
            pSSesionAprove.setLong(3, timestamp);
            pSSesionAprove.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void saveFirstPackToDB(DialogPacket dpFirst) {
        String logFromUser = dpFirst.log;
        int sessionidFromUser = dpFirst.sessionId;
        long timeStampServerDialog = dpFirst.timeStampFromDiPa;

        try {
            pSSaveFirstPackInUsers.setString(1, logFromUser);
            pSSaveFirstPackInUsers.setInt(2, sessionidFromUser);
            pSSaveFirstPackInUsers.setLong(3, timeStampServerDialog);
            pSSaveFirstPackInUsers.executeUpdate();
            pSSaveFirstPackInUsers.close();
        } catch (SQLException e) {
        }
    }

    @Override
    public void saveChatStory(DialogPacket dpStory) {
        String login = dpStory.log;
        int sessionID = dpStory.sessionId;
        String messinPack = dpStory.message;
        long timeMessInPack = dpStory.timeStampFromDiPa;

        try {
            pSSaveStoryInSessions.setString(1, login);
            pSSaveStoryInSessions.setInt(2, sessionID);
            pSSaveStoryInSessions.setString(3, messinPack);
            pSSaveStoryInSessions.setLong(4, timeMessInPack);

            pSSaveStoryInSessions.executeUpdate();
        } catch (SQLException e) {
        }

    }

    @Override
    public void saveIllegalAttempt(Socket socket, DialogPacket dpIllegalA) {
        String logFromUser = dpIllegalA.log;
        String pasFromUser = dpIllegalA.pass;
        int sessionidFromUser = dpIllegalA.sessionId;
        long timeStampServerDialog = dpIllegalA.timeStampFromDiPa;
        String ipadress = socket.getInetAddress().toString();

        try {
            psSIlligalAttempt.setString(1, logFromUser);
            psSIlligalAttempt.setString(2, pasFromUser);
            psSIlligalAttempt.setString(3, "session illegal you are disconnected");
            psSIlligalAttempt.setInt(4, sessionidFromUser);
            psSIlligalAttempt.setLong(5, timeStampServerDialog);
            psSIlligalAttempt.setString(6, ipadress);
            psSIlligalAttempt.executeUpdate();

        } catch (SQLException e) {
        }
    }

    @Override
    public void bannU(String login) {
        try {
            psBanU.setString(1, login);
            psBanU.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void clearUSessionTab(String login) {
        try {
            psClearSession.setString(1, login);
            psClearSession.executeUpdate();
        } catch (SQLException e) {
        }
    }
}
