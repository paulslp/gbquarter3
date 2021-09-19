package gb.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import static gb.server.MainServer.LOGGER;

public class Server {
    private Vector<ClientHandler> clients;

    public Server() {
        try {
            SQLHandler.connect();
            ServerSocket serverSocket = new ServerSocket(8189);
            clients = new Vector<>();
            while (true) {
                LOGGER.info("Ждем подключения клиента");
                Socket socket = serverSocket.accept();
                ClientHandler c = new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            LOGGER.severe("Ошибка подключения клиента: " + e.getMessage());
        } finally {
            SQLHandler.disconnect();
        }
    }

    public void subscribe(ClientHandler client) {
        LOGGER.info("Добавление клиента " + client.getNickname());
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        LOGGER.info("Удаление клиента " + client.getNickname());
        clients.remove(client);
    }

    public void broadcastMsg(String msg) {
        LOGGER.config("Отправка сообщения " + msg + " клиентам");
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }

    public void sendPrivateMsg(String nickNameReceiver, String nickNameSender, String msg) {
        for (ClientHandler c : clients) {
            if (c.getNickname().equals(nickNameReceiver)) {
                LOGGER.severe("Пользователь " + nickNameSender + " отправил пользователю " +
                        nickNameReceiver + " сообщение: " + msg);
                c.sendMsg(nickNameSender + ": " + msg);
            }
        }
    }
}
