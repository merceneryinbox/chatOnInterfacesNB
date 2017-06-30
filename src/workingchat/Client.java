
import chatlib.ClientCheckAuthoPac;
import chatlib.ClientSendAuthoPackToAS;
import chatlib.ClientToAuthChatServerConnect;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import markerIface.DialogPacket;

/**
 * Created by mercenery on 20.06.2017.
 */
public class Client {

    private Socket socketDialog;

    private DialogPacket dialogPackFromAuthorization;
    private DialogPacket dialogPacketInSessionFromServer;
    private DialogPacket dialogPacketInSessionFromUser;
    private DialogPacket authPacket;
    private DialogPacket firstPackToDialog;

    private ObjectOutputStream ooDialog;
    private ObjectInputStream oiDialog;

    private String message;
    private String reply;
    private int sessionIdInClient;
    private int sessionFromRunAuthorization;
    private long timeStampFromRunAuthorization;

    public Client() {
    }

    public static void main(String[] args) {
        System.out.println("Client starts");
        while (untilAuthorize() != 0) {
            System.out.println("while loop for authorization starts until it successfully ended");
            untilAuthorize();
        }
        System.out.println("Main try without resources block in Client starts");
        try {
            System.out.println("Creating BufferedReader, socketDialog, ooDialog, oiDialog");
            BufferedReader talk = new BufferedReader(new InputStreamReader(System.in));
            socketDialog = new Socket("localhost", 55555);
            ooDialog = new ObjectOutputStream(socketDialog.getOutputStream());
            oiDialog = new ObjectInputStream(socketDialog.getInputStream());
            System.out.println("Creating firstPackToDialog for the first message to RunDialog");
            firstPackToDialog = new DialogPacket(loginInClient, pasInClient,
                    "----------------------------------------------------",
                    sessionFromRunAuthorization, timeStampFromRunAuthorization);
            ooDialog.writeObject(firstPackToDialog);
            ooDialog.flush();
            System.out.println("First message in DialogPacket send");

            //принимаю ответ на первый пакет и вывожу на консоль
            System.out.println("Starts waits for RunDialog reply my first packet");

            dialogPacketInSessionFromServer = (DialogPacket) oiDialog.readObject();
            reply = dialogPacketInSessionFromServer.message;
            System.out.println("Reply for first packet received");
            System.out.println("Server replyed" + reply);

            System.out.println("Checking for keywor - quit");
            if (reply.equalsIgnoreCase("quit")) {
                System.out.println("KeyWord - quit received, closing my resources");
                ooDialog.close();
                oiDialog.close();
                socketDialog.close();
            }
            //запускаю основной цикл общения
            System.out.println("KeyWord - quit not received, closing my resources");
            System.out.println("Closing KeyWord not found, main while loop for talking with RunDialog starts");
            while (!socketDialog.isClosed()) {

                System.out.print("input your message : ");
                message = talk.readLine();
                System.out.println("Saving my message into variable - message, creating DialogPacket in Client");
                dialogPacketInSessionFromUser = new DialogPacket(loginInClient, pasInClient, message, sessionIdInClient,
                        new Date().getTime());
                ooDialog.writeObject(dialogPacketInSessionFromUser);
                ooDialog.flush();
                System.out.println("DialogPacket in main while loop in Client send");

                System.out.println("Checking for - quit keyword");
                if (message.equalsIgnoreCase("quit")) {
                    System.out.println("Keyword - quit found, close resources");
                    System.out.println("Looking up for last send from RunDialog message if exists");
                    dialogPacketInSessionFromServer = (DialogPacket) oiDialog.readObject();
                    reply = dialogPacketInSessionFromServer.message;
                    System.out.println("Printing last echoreply");
                    System.out.println("Server replyed - " + reply);
                    System.out.println("Closing resources, breaking main while loop");
                    ooDialog.close();
                    oiDialog.close();
                    socketDialog.close();
                    break;
                }
                System.out.println("Listen to channel, waits for reply packed");
                dialogPacketInSessionFromServer = (DialogPacket) oiDialog.readObject();
                reply = dialogPacketInSessionFromServer.message;

                System.out.println("Server packet found checking for - quit in message");
                if (reply.equalsIgnoreCase("quit")) {
                    System.out.println("Keyword - quit found, close resources, breaking main while loop");
                    System.out.println("Server replyed" + reply);
                    ooDialog.close();
                    oiDialog.close();
                    socketDialog.close();
                    break;
                }

                System.out.println("Keyword - quit not found");
                System.out.println(reply);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ooDialog.close();
                oiDialog.close();
                socketDialog.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int authoriseMe() {
        System.out.println("authorizeMe() method and its try-with-resources in client starts");

        // открываем сокет для проверки авторизации
        Socket socketAuth = new ClientToAuthChatServerConnect().connectToServer();

        BufferedReader autorizeReader = new BufferedReader(new InputStreamReader(System.in));
        String loginInClient;
        String pasInClient;

        // запрашиваем логин и пароль на ввод от пользователя
        System.out.println("Login:");
        try {
            loginInClient = autorizeReader.readLine();
            System.out.println("Pass:");
            pasInClient = autorizeReader.readLine();
            boolean putAndFrow = new ClientSendAuthoPackToAS(socketAuth, loginInClient).putAndFrow();
            boolean isAuthOk = new ClientCheckAuthoPac(socketAuth).authoCheck();
        } catch (IOException e) {
        }

        dialogPackFromAuthorization = (DialogPacket) oisAutor.readObject();
        // проверяем не забанен ли я (если забанен - тогда в логине будет слово - quit и клиент закрывается
        String requestAproveLog = dialogPackFromAuthorization.log;
        sessionFromRunAuthorization = dialogPackFromAuthorization.sessionId;
        timeStampFromRunAuthorization = dialogPackFromAuthorization.timeStampFromDiPa;

        System.out.println(
                "Reply packet from RunAuthorization received, checking allowing session start");
        if (requestAproveLog.equalsIgnoreCase(
                "quit")) {
            System.out.println("Session refused by RunAuthorization");
            System.out.println("you are banned");
            System.out.println("сообщение от сервера авторизации - " + dialogPackFromAuthorization.message);
            return - 1;
        }

        // во всех остальных случаях если я не забанен продолжаю работу - шлю первый диалоговый пакет диалоговому
        // серверу предварительно вставив в него номер сессии из пакета от сервера авторизации
        System.out.println(
                "Session allowed returns 0");
        sessionFromRunAuthorization = dialogPackFromAuthorization.sessionId;
        timeStampFromRunAuthorization = dialogPackFromAuthorization.timeStampFromDiPa;

        return 0;

    }

    private static int untilAuthorize() {
        return authoriseMe();
    }
}
