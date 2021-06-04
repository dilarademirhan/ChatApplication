/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;

/**
 *
 * @author dilara
 */
public class Room {

    String name;
    ArrayList<SClient> clientsOfRoom = new ArrayList();
    public static ArrayList<Room> Rooms = new ArrayList();

    public Room(String name) {
        this.name = name;
        Rooms.add(this);
    }

}
