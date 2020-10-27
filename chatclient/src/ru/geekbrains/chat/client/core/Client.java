package ru.geekbrains.chat.client.core;

import ru.geekbrains.common.Library;
import ru.geekbrains.network.SocketThread;
import ru.geekbrains.network.SocketThreadListener;

import java.io.IOException;
import java.net.Socket;

/*Так как в GUI накопилось логики и он стал загруженным, перенес
не UI фичи сюда
*/
public class Client implements SocketThreadListener {
    private SocketThread socketThread;
    private String serverIP;
    private int serverPort;
    private String login;
    private String password;
    private ClientListener listener;
    String[] users = {"user1", "user2", "user3", "user4", "user5",
            "user_with_an_exceptionally_long_name_in_this_chat"};

    public Client(ClientListener listener) {
        this.listener = listener;
    }

    public void connect(String ip, String port, String login, String password) throws IOException {
        this.serverIP = ip;
        this.serverPort = Integer.parseInt(port);
        this.login = login;
        this.password = password;
        Socket socket = new Socket(serverIP, serverPort);
        socketThread = new SocketThread("Client", this, socket);
    }

    public void sendMessage(String msg) {
        socketThread.sendMessage(msg);
    }

    public void disconnect() {
        socketThread.close();
    }

    public String[] getUsers() {
        return users;
    }

    /**
     * Socket thread listener methods
     */

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        listener.onMessage("Start");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        listener.onDisconnect();
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        listener.onClientReady();
        thread.sendMessage(Library.getAuthRequest(login, password));
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        listener.onMessage(msg);
    }

    @Override
    public void onSocketException(SocketThread thread, Exception exception) {
        listener.onClientException(thread, exception);
    }
}
