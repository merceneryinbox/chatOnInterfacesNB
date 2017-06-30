/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatlib;

import chatProInterfaces.TalkToDB;
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
public class ChatProPostgresDBQueries implements TalkToDB {

    private Connection connection;
    private PreparedStatement pSCheckRequest;
    private PreparedStatement psCheckSesRegForRunDBS;
    private PreparedStatement psSRegistration;
    private PreparedStatement pSSesionAprove;
    private PreparedStatement pSSaveFirstPackInUsers;
    private PreparedStatement pSSaveStoryInSessions;
    private PreparedStatement psSIlligalAttempt;
    private PreparedStatement psBanU;
    private CallableStatement psClearSessionTab;
    private ResultSet resultSetCheck;
    private int coded;
    private boolean checkSesDBS;

    public ChatProPostgresDBQueries() {
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
            psCheckSesRegForRunDBS = connection.prepareStatement("select timestampforsess from chatpro.aprovedsessions  where upper(login) = upper(?)");
            psClearSessionTab = connection.prepareCall("delete fromapprovedsessions where upper(login) = upper(?);");

        } catch (SQLException e) {
        }
    }

    @Override
    public int checkPermission(String login) {
        try {
            pSCheckRequest.setString(1, login);
            resultSetCheck = pSCheckRequest.executeQuery();
            resultSetCheck.next();
            coded = resultSetCheck.getInt("code");
        } catch (SQLException e) {
        }
        return coded;
    }

    @Override
    public boolean registrate(String login, String pass, int code) {
        try {
            psSRegistration.setString(1, login);
            psSRegistration.setString(2, pass);
            psSRegistration.setInt(3, code);
            psSRegistration.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean sessionAssigne(String login, int sessionID, long timestamp) {
        try {
            pSSesionAprove.setString(1, login);
            pSSesionAprove.setInt(2, sessionID);
            pSSesionAprove.setLong(3, timestamp);
            pSSesionAprove.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean saveFirstPackToDB(DialogPacket dpFirst) {
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
            return false;
        }
        return true;
    }

    @Override
    public boolean saveChatStory(DialogPacket dpStory) {
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
            return false;
        }
        return true;

    }

    @Override
    public boolean saveIllegalAttempt(Socket socket, DialogPacket dpIllegalA) {
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
            return false;
        }
        return true;
    }

    @Override
    public boolean bannU(String login) {
        try {
            psBanU.setString(1, login);
            psBanU.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean clearUSessionTab(String login) {
        try {
            psClearSessionTab.setString(1, login);
            psClearSessionTab.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean CheckSesAproveForDBS(String login, long timestamp) {
        try {
            psCheckSesRegForRunDBS.setString(1, login);
            resultSetCheck = psCheckSesRegForRunDBS.executeQuery();
            resultSetCheck.next();
            long tmp = resultSetCheck.getLong("timestampforsess");
            checkSesDBS = timestamp - tmp <= 60000;
            this.clearUSessionTab(login); // очистка таблицы сессий после проверки
            // в RunDialog, чтобы при следующем обращении не произошло чтение из старой записи
        } catch (SQLException e) {
        }
        return checkSesDBS;
    }
}
