/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workingchat;

import chatlib.AuthoAnswerToClient;
import chatlib.ChatProPostgresDBQueries;
import chatlib.GetAuthoPackFromUser;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.Date;
import markerIface.DialogPacket;

/**
 * Created by mercenery on 16.06.2017.
 */
public class RunAuthorization implements Runnable {

    private final Socket socketClient;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private DialogPacket authPacket;
    private int code;
    private boolean isUOkInSes;
    private final long timeStampDef;
    private String loginFromPacket;
    private String pasFromPacket;
    private int backCodeFromDB;
    private int backCode;
    private final int sessionIDd;
    private Connection connectionToDB;

    public RunAuthorization(Socket socket) {
        System.out.println(
                "Start constructor of RunAuthorization Runnable thread. Stamping time and generate session" + " number");
        timeStampDef = new Date().getTime();
        sessionIDd = (int) (Math.random() * 1000000);
        this.socketClient = socket;
    }

    /**
     * @see Thread#run()
     */
    @Override
    public void run() {
        authPacket = (new GetAuthoPackFromUser(ois).lookingForPacket());
        loginFromPacket = authPacket.log;
        pasFromPacket = authPacket.pass;
        isUOkInSes = new AuthoAnswerToClient(socketClient, authPacket).putAndFrow();
        if (isUOkInSes) {
            boolean clearPreviousSess = new ChatProPostgresDBQueries().clearUSessionTab(loginFromPacket);
            if (clearPreviousSess) {
                boolean isRegistrationOk = new ChatProPostgresDBQueries().registrate(loginFromPacket, pasFromPacket, 0);
                if (isRegistrationOk) {
                    boolean isSesAsigneOk = new ChatProPostgresDBQueries().sessionAssigne(loginFromPacket, sessionIDd, timeStampDef);
                    if (isSesAsigneOk) {
                        System.out.println("Authorization Ok!");
                    }
                } else {
                    System.out.println("Registration problems.");
                }
            } else {
                System.out.println("Previouse session clearing problems.");
            }
        } else {
            System.out.println("Permission Code checking in 'users' problem");
        }
    }
}
