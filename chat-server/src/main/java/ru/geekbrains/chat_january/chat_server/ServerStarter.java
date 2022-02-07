package ru.geekbrains.chat_january.chat_server;

import ru.geekbrains.chat_january.chat_server.auth.InMemoryAuthService;
import ru.geekbrains.chat_january.chat_server.server.Server;

public class ServerStarter {

    public static void main(String[] args) {

        Server server = new Server(new InMemoryAuthService());
        server.start();
    }
}
