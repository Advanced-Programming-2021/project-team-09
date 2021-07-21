package controller;

import model.Client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class ClientHandler {
    private static Socket socket;
    public static final Runnable clientRunnable = () -> handleClient(socket);
    private static final ArrayList<Client> clients = new ArrayList<>();

    public static void getClients() {
        try {
            ServerSocket serverSocket = new ServerSocket(8569);
            while (true) {
                socket = serverSocket.accept();
                new Thread(clientRunnable).start();
            }
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    public static void handleClient(Socket socket) {
        Client client = new Client(socket.getRemoteSocketAddress().toString(), UUID.randomUUID());
        removeOrAddClient(client, true);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(client.getUuid());
            objectOutputStream.flush();
            while (true) {
                String string = (String) objectInputStream.readObject();
                String[] strings = string.split("~");
                if (string.startsWith("login~doesUsernameExists~")){
                    objectOutputStream.writeObject(String.valueOf(LoginMenuController.doesUsernameExists(strings[2])));
                    objectOutputStream.flush();
                } else if (string.startsWith("login~doesNicknameExists~")) {
                    objectOutputStream.writeObject(String.valueOf(LoginMenuController.doesNicknameExists(strings[2])));
                    objectOutputStream.flush();
                } else if (string.startsWith("login~isPasswordCorrect~")) {
                    objectOutputStream.writeObject(String.valueOf(LoginMenuController.isPasswordCorrect(strings[2], strings[3])));
                    objectOutputStream.flush();
                } else if (string.startsWith("login~createUser~")) {
                    objectOutputStream.writeObject(LoginMenuController.createUser(strings[2], strings[3], strings[4]));
                    objectOutputStream.flush();
                } else if (string.startsWith(""));

            }
        } catch (IOException | ClassNotFoundException e) {
            if (e instanceof EOFException) {
                removeOrAddClient(client, false);
                return;
            }
            e.printStackTrace();
        }
    }

    private synchronized static void removeOrAddClient(Client client, boolean wantToAdd) {
        if (wantToAdd && client != null) {
            clients.add(client);
        } else {
            for (int i = 0; i < clients.size(); i++) {
                if (client == clients.get(i)) {
                    clients.remove(i);
                    return;
                }
            }
        }
    }
}
