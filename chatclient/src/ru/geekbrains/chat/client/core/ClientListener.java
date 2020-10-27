package ru.geekbrains.chat.client.core;

import ru.geekbrains.network.SocketThread;

public interface ClientListener {
    void onClientReady();

    void onDisconnect();

    void onMessage(String message);

    void onClientException(SocketThread thread, Exception exception);
}
