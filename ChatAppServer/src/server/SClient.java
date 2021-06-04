/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dilara
 */
public class SClient {

    public static int idCount;
    public Server server;
    public int id;
    public Socket socket;
    public ObjectOutputStream cOutput;
    public ObjectInputStream sInput;
    public ClientListenThread listenThread;
    public boolean isConnected;
    String name;

    public SClient(Socket socket, Server server, String name) {
        try {
            this.name = name;
            this.server = server;
            this.id = idCount;
            idCount++;
            this.socket = socket;
            this.cOutput = new ObjectOutputStream(this.socket.getOutputStream());
            this.sInput = new ObjectInputStream(this.socket.getInputStream());
            this.listenThread = new ClientListenThread(this);
            this.isConnected = false;
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public SClient(Socket socket, Server server) {
        try {
            this.server = server;
            this.id = idCount;
            idCount++;
            this.socket = socket;
            this.cOutput = new ObjectOutputStream(this.socket.getOutputStream());
            this.sInput = new ObjectInputStream(this.socket.getInputStream());
            this.listenThread = new ClientListenThread(this);
            this.isConnected = false;
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Close() {
        try {
            this.isConnected = false;
            this.socket.close();
            this.sInput.close();
            this.cOutput.close();
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void SendMessage(Message msg) {
        if (this.socket.isConnected()) {
            try {
                cOutput.writeObject(msg);
            } catch (IOException ex) {
                Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void Listen() {

        this.isConnected = true;
        this.listenThread.start();
    }

}

class ClientListenThread extends Thread {

    private SClient client;

    public ClientListenThread(SClient client) {
        this.client = client;
    }

    public void run() {

        while (this.client.isConnected) {
            try {
                Message msg = (Message) client.sInput.readObject();

                switch (msg.type) {
                    case Name:
                        String receivedName = msg.content.toString();
                        boolean exist = false;
                        for (SClient c : Server.clients) {
                            if (c.name == null) {
                            } else if (c.name.equals(receivedName)) {
                                exist = true;
                            }
                        }
                        if (!exist) {
                            this.client.name = receivedName;
                        } else {
                            Message m = new Message(Message.Message_Type.Error);
                            Server.SendSelectedClientMessage(m, this.client.id);
                            Server.clients.remove(this.client);
                        }
                        Set<SClient> set = new HashSet<>(Server.clients);
                        Server.clients.clear();
                        Server.clients.addAll(set);
                        for (SClient c : Server.clients) {
                            Message m = new Message(Message.Message_Type.Name);
                            m.content = c.name;
                            Server.SendBroadcastMessage(m);
                        }
                        for (Room r : Room.Rooms) {
                            Message m = new Message(Message.Message_Type.Room);
                            m.content = r.name;
                            Server.SendBroadcastMessage(m);
                        }
                        break;

                    case DChat:
                        String name = msg.additional;
                        Server.SendSelectedClientMessage(msg, name);
                        break;
                    case Dm:
                        String chatWith = msg.additional.split(" ")[0];
                        Server.SendSelectedClientMessage(msg, chatWith);
                        break;
                    case Room:
                        Room r = new Room(msg.content.toString());
                        Message m = new Message(Message.Message_Type.Room);
                        m.content = r.name;
                        Server.SendBroadcastMessage(m);
                        break;

                    case enterRoom:
                        String roomName = msg.content.toString();
                        for (Room room : Room.Rooms) {
                            if (room.name.equals(roomName)) {
                                for (SClient c : Server.clients) {
                                    if (!room.clientsOfRoom.contains(c)) {
                                        room.clientsOfRoom.add(c);
                                    }
                                }
                            }
                        }
                        break;

                    case RMessage:
                        String room_name = msg.additional.split(" ")[0];
                        for (Room Room : Room.Rooms) {
                            if (Room.name.equals(room_name)) {
                                for (SClient s : Room.clientsOfRoom) {
                                    Server.SendSelectedClientMessage(msg, s.name);
                                }
                            }
                        }
                        break;

                    case File:
                        if (msg.additional.split(" ")[0].equals("dm")) {
                            for (SClient c : Server.clients) {
                                if (c.name.equals(msg.additional.split(" ")[1])) {
                                    Server.SendSelectedClientMessage(msg, c.name);
                                }
                            }
                        } else if (msg.additional.split(" ")[0].equals("room")) {
                            for (Room Room : Room.Rooms) {
                                if (Room.name.equals(msg.additional.split(" ")[1])) {
                                    for (SClient s : Room.clientsOfRoom) {
                                        Server.SendSelectedClientMessage(msg, s.name);
                                    }

                                }
                            }
                        }
                        break;

                }
            } catch (SocketException e) {
                this.client.server.RemoveClient(client);
            } catch (IOException ex) {
                this.client.server.RemoveClient(client);
                Logger.getLogger(ClientListenThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                this.client.server.RemoveClient(client);
                Logger.getLogger(ClientListenThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
