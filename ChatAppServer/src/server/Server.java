/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import message.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dilara
 */
public class Server {

    public ServerSocket socket;
    public ListenThread listenThread;
    public int port;
    public static ArrayList<SClient> clients = new ArrayList<SClient>();

    public Server(int port) {
        try {

            this.port = port;
            this.socket = new ServerSocket(port);

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Listen() {
        this.listenThread = new ListenThread(this);
        this.listenThread.start();
    }

    public static void SendBroadcastMessage(Message msg) {
        for (SClient client : clients) {
            client.SendMessage(msg);
        }
    }

    public static void SendSelectedClientMessage(Message msg, String sendTo) {
        for (SClient client : clients) {
            if (sendTo.equals(client.name)) {
                client.SendMessage(msg);
                break;
            }
        }
    }

    public static void SendSelectedClientMessage(Message msg, int id) {
        for (SClient client : clients) {
            if (id == client.id) {
                client.SendMessage(msg);
                break;
            }
        }
    }

    public void RemoveClient(SClient client) {
        for (int i = 0; i < Server.clients.size(); i++) {
            if (Server.clients.get(i).id == client.id) {
                Server.clients.get(i).Close();
                Server.clients.remove(i);
                Message msg = new Message(Message.Message_Type.Remove);
                msg.content = client.name;
                SendBroadcastMessage(msg);
                break;
            }
        }
        for (SClient c : clients) {
            Message m = new Message(Message.Message_Type.Name);
            m.content = c.name;
            Server.SendBroadcastMessage(m);
        }
    }
    
    public static void main(String[] args) {
        Server myserver = new Server(5000);
        myserver.Listen();
    }

}

class ListenThread extends Thread {

    private Server server;

    public ListenThread(Server server) {
        this.server = server;
    }

    @Override
    public void run() {

        while (!this.server.socket.isClosed()) {
            try {
                Socket nSocket = this.server.socket.accept();//blocking method | soket döndürür ip + port

                SClient nClient = new SClient(nSocket, this.server);
                nClient.Listen();

                Server.clients.add(nClient);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
