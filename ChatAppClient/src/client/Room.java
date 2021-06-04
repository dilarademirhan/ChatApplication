/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;

/**
 *
 * @author dilara
 */
public class Room {

    String name;
    ArrayList<cClient> clientsOfRoom = new ArrayList();
    public static ArrayList<Room> Rooms = new ArrayList();
    FrmRoom f = new FrmRoom();

    public Room(String name) {
        this.name = name;
        Rooms.add(this);
        FrmClient.roomsModel.addElement(this.name);
    }

}
