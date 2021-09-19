package gb.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static gb.server.MainServer.LOGGER;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickname;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();
                        // /auth login1 password1
                        String[] subStrings = str.split(" ");
                        if (subStrings.length >= 3 && subStrings[0].equals("/auth")) {
                            LOGGER.info("Авторизационные данные получены...");
                            String nickFromDB = SQLHandler.getNickByLoginAndPassword(subStrings[1], subStrings[2]);
                            if (nickFromDB != null) {
                                sendMsg("/authok");
                                LOGGER.info("Клиент " + nickFromDB + " подписывается на сообщения сервера");
                                server.subscribe(this);
                                nickname = nickFromDB;
                                break;
                            }
                        }
                    }

                    while (true) {
                        String str = in.readUTF();
                        LOGGER.info("Сообщение от клиента: " + str);
                        if (str.equals("/end")) {
                            LOGGER.info("Клиент " + nickname + " инициировал свое удаление из чата");
                            break;
                        }
                        String[] subStrings = str.split(" ");
                        if (subStrings.length >= 3 && subStrings[0].equals("/w")) {
                            if (SQLHandler.isExistsNick(subStrings[1])) {
                                StringBuilder msg = new StringBuilder();
                                for (int i = 2; i < subStrings.length; i++) {
                                    msg.append(subStrings[i]);
                                }
                                server.sendPrivateMsg(subStrings[1], nickname, msg.toString().trim());
                            }
                        } else {
                            server.broadcastMsg(nickname + ": " + str);
                        }
                    }
                } catch (IOException e) {
                    LOGGER.severe("Ошибка обработки сообщения клиента " + nickname);
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                }
            }).start();
        } catch (IOException e) {
            LOGGER.severe("Ошибка при создании входящего/исходящего потоков данных " + e.getMessage());
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            LOGGER.severe("Ошибка записи сообщения в исходящий поток " + e.getMessage());
            try {
                out.close();
            } catch (IOException ioException) {
                LOGGER.severe("Ошибка при закрытии исходящего потока " + e.getMessage());
            }
        }
    }
}
