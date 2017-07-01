
import chatlib.ClientCheckAuthoPac;
import chatlib.ClientSendAuthoPackToAS;
import chatlib.ClientToAuthChatServerConnect;
import chatlib.ClientToDialogChatServerConnect;
import chatlib.SendPacket;
import java.io.*;
import java.net.Socket;
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

        // получаем сокет для проверки авторизации
        Socket socketAuth = new ClientToAuthChatServerConnect().connectToServer();
        String loginInClient;
        String pasInClient;

        // запрашиваем логин и пароль на ввод от пользователя
        try (BufferedReader autorizeReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Login:");
            loginInClient = autorizeReader.readLine();
            System.out.println("Pass:");
            pasInClient = autorizeReader.readLine();
            boolean putAndFrow = new ClientSendAuthoPackToAS(socketAuth, loginInClient).putAndFrow();
            ClientCheckAuthoPac clientCheckAuthoPac = new ClientCheckAuthoPac(socketAuth);
            boolean isAuthOk = clientCheckAuthoPac.authoCheck();
            if (!isAuthOk) {
                System.err.println("Connection refused during authorization.");
                System.exit(-1);
            }

            Socket socketInLoop = new ClientToDialogChatServerConnect().connectToServer();
            boolean firstBPInDialog = new SendPacket(socketInLoop, clientCheckAuthoPac.getAuthPack()).putAndFrow();
            if (firstBPInDialog) {
                int session = clientCheckAuthoPac.getAuthPack().sessionId;
                long time = clientCheckAuthoPac.getAuthPack().timeStampFromDiPa;
                while (!socketInLoop.isClosed()) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                        System.out.println("Input your message: ");
                        String message = br.readLine();
                        DialogPacket mesPack = new DialogPacket(loginInClient, pasInClient, message, session, time);
                        boolean sendInLoopOk = new SendPacket(socketInLoop, mesPack).putAndFrow();
                        if (sendInLoopOk) {
                        }
                    } catch (Exception e) {
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}
