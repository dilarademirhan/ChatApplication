/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import static client.cClient.clients;
import java.io.*;
import java.io.FileOutputStream;
import message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dilara
 */
public class cClient {

    public Socket socket;
    public ObjectInputStream cInput;
    public ObjectOutputStream cOutput;
    public boolean isConnected;
    public String serverIP;
    public int serverPort;
    public ClientListenThread listenThread;
    public static int idCount;
    int id;
    String name;
    public static ArrayList<cClient> clients = new ArrayList<>();
    public static ArrayList<FrmDM> dmScreens = new ArrayList<>();
    String error = "";

    public cClient(String serverIp, int serverPort, String name) { // namei bennnn
        try {
            this.id = idCount;
            idCount++;
            this.name = name;
            this.isConnected = false;
            this.serverIP = serverIp;
            this.serverPort = serverPort;
            this.socket = new Socket(this.serverIP, this.serverPort);
            this.cOutput = new ObjectOutputStream(this.socket.getOutputStream());
            this.cInput = new ObjectInputStream(this.socket.getInputStream());

        } catch (IOException ex) {
            Logger.getLogger(cClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SendMessage(Message msg) {
        if (this.socket.isConnected()) {
            try {
                cOutput.writeObject(msg);
            } catch (IOException ex) {
                Logger.getLogger(cClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void SendMessage(Message msg, cClient c) {
        if (this.socket.isConnected()) {
            try {
                c.cOutput.writeObject(msg);
            } catch (IOException ex) {
                Logger.getLogger(cClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static byte[] writeFile(File file) {
        byte[] bytes = new byte[4096 * 1024];
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrmDM.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            in.read(bytes, 0, bytes.length);
        } catch (IOException ex) {
            Logger.getLogger(FrmDM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bytes;
    }

    public static void RemoveClient(cClient client) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).name.equals(client.name)) {
                clients.get(i).Close();
                clients.remove(i);
                break;
            }
        }
        UpdateClientList();
    }

    public static void UpdateClientList() {
        FrmClient.usersModel.removeAllElements();
        for (cClient c : cClient.clients) {
            FrmClient.usersModel.addElement(c.name);
            System.out.println(c.name + " eklendi");
        }
    }

    public void Start() {
        this.listenThread = new ClientListenThread(this);
        this.isConnected = true;
        this.listenThread.start();
    }

    public void Close() {
        try {
            this.isConnected = false;
            this.socket.close();
            this.cInput.close();
            this.cOutput.close();
        } catch (IOException ex) {
            Logger.getLogger(cClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class ClientListenThread extends Thread {

    private cClient client;

    public ClientListenThread(cClient client) {
        this.client = client;

    }

    @Override
    public void run() {
        try {
            while (this.client.isConnected) {
                try {
                    Message msg = (Message) this.client.cInput.readObject(); // blocking method | waiting message
                    switch (msg.type) {
                        case Name:
                            String name = msg.content.toString();
                            boolean clientExist = false;
                            for (cClient c : cClient.clients) {
                                if (c.name.equals(name)) {
                                    clientExist = true;
                                }
                            }
                            if (!FrmClient.usersModel.contains(name)) {
                                if (!clientExist) {
                                    cClient.clients.add(this.client);
                                }
                                FrmClient.usersModel.addElement(name);
                            }
                            Set<cClient> set = new HashSet<>(clients);
                            clients.clear();
                            clients.addAll(set);
                            FrmClient.error.setText("client added");
                            FrmClient.error.setVisible(false);
                            break;
                        case Error:
                            this.client.error = "This username is already in use. Please try another one";
                            FrmClient.error.setText(this.client.error);
                            FrmClient.error.setVisible(true);
                            this.client = null;
                            break;
                        case DChat:
                            boolean exist = false;
                            for (FrmDM d : this.client.dmScreens) {
                                if (d.lbl_thisclient_name.getText().equals(this.client.name) && d.lbl_chatWith.getText().equals(msg.content.toString())) {
                                    exist = true;
                                }
                            }
                            if (!exist) {
                                FrmDM dmScreen = new FrmDM();
                                this.client.dmScreens.add(dmScreen);
                                dmScreen.setVisible(true);
                                dmScreen.lbl_thisclient_name.setText(this.client.name);
                                dmScreen.lbl_chatWith.setText(msg.content.toString());
                            }
                            break;
                        case Dm:
                            for (FrmDM d : this.client.dmScreens) {
                                if (d.lbl_chatWith.getText().equals(msg.additional.split(" ")[1]) && d.lbl_thisclient_name.getText().equals(msg.additional.split(" ")[0])) {
                                    d.messagesModel.addElement(msg.additional.split(" ")[1] + ": " + msg.content.toString());
                                }
                            }
                            break;
                        case Room:
                            boolean isExist = false;
                            for (Room r : Room.Rooms) {
                                if (r.name.equals(msg.content.toString())) {
                                    isExist = true;
                                }
                            }
                            if (!isExist) {
                                Room r = new Room(msg.content.toString());
                            }
                            break;
                        case RMessage:
                            for (Room room : Room.Rooms) {
                                if (room.name.equals(msg.additional.split(" ")[0])) {
                                    if (this.client.name.equals(msg.additional.split(" ")[1])) {
                                        String s = "<html><b>" + msg.additional.split(" ")[1] + ": " + msg.content.toString() + "</b></html>";
                                        room.f.clientMessagesModel.addElement(s);
                                    } else {
                                        room.f.clientMessagesModel.addElement(msg.additional.split(" ")[1] + ": " + msg.content.toString());
                                    }

                                }
                            }
                            break;
                        case File:
                            if (msg.additional.split(" ")[0].equals("dm")) {
                                byte[] bytes = (byte[]) msg.content;
                                FileOutputStream fr = new FileOutputStream(msg.additional.split(" ")[3]);
                                fr.write(bytes, 0, bytes.length);
                                for (FrmDM d : this.client.dmScreens) {
                                    if (d.lbl_chatWith.getText().equals(msg.additional.split(" ")[2]) && d.lbl_thisclient_name.getText().equals(msg.additional.split(" ")[1])) {
                                        d.messagesModel.addElement(msg.additional.split(" ")[2] + ": " + msg.additional.split(" ")[3]);
                                    }
                                }
                            } else if (msg.additional.split(" ")[0].equals("room")) {
                                for (Room room : Room.Rooms) {
                                    if (room.name.equals(msg.additional.split(" ")[1])) {
                                        if (!this.client.name.equals(msg.additional.split(" ")[2])) {
                                            byte[] bytes = (byte[]) msg.content;
                                            FileOutputStream fr = new FileOutputStream(msg.additional.split(" ")[3]);
                                            fr.write(bytes, 0, bytes.length);
                                            room.f.clientMessagesModel.addElement(msg.additional.split(" ")[2] + ": " + msg.additional.split(" ")[3]);
                                        }

                                    }
                                }
                            }
                            break;
                        case Remove:

                            for (cClient c : cClient.clients) {
                                if (c.name.equals(msg.content)) {
                                    cClient.clients.remove(c);
                                }
                            }
                            FrmClient.usersModel.removeAllElements();
                            /* for (cClient c : cClient.clients) {
                                FrmClient.usersModel.addElement(c.name);
                            }*/
                            break;
                    }

                } catch (IOException ex) {
                    this.client.Close();
                    Logger.getLogger(ClientListenThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    this.client.Close();
                    Logger.getLogger(ClientListenThread.class.getName()).log(Level.SEVERE, null, ex);

                }

            }
        } catch (NullPointerException e) {
        }
    }

}
