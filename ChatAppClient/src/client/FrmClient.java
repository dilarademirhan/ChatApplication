/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import message.Message;
import javax.swing.DefaultListModel;

/**
 *
 * @author dilara
 */
public class FrmClient extends javax.swing.JFrame {
    
    public static DefaultListModel roomsModel;
    public static DefaultListModel usersModel;
    public static DefaultListModel clientMessagesModel;
    cClient myClient = null;

    /**
     * Creates new form FrmClient
     */
    public FrmClient() {
        initComponents();
        error.setVisible(false);
        frmLogin.setVisible(true);
        lbl_roomName.setVisible(false);
        txt_roomName.setVisible(false);
        room_error.setVisible(false);
        clientMessagesModel = new DefaultListModel();
        usersModel = new DefaultListModel();
        roomsModel = new DefaultListModel();
        lst_rooms.setModel(roomsModel);
        lst_users.setModel(usersModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frmLogin = new javax.swing.JFrame();
        btn_start = new javax.swing.JButton();
        txt_isim = new javax.swing.JTextField();
        error = new javax.swing.JLabel();
        lbl_info = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lst_users = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        lst_rooms = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_dm = new javax.swing.JButton();
        btn_enter = new javax.swing.JButton();
        btn_createRoom = new javax.swing.JButton();
        lbl_roomName = new javax.swing.JLabel();
        txt_roomName = new javax.swing.JTextField();
        room_error = new javax.swing.JLabel();
        lbl_welcome = new javax.swing.JLabel();

        frmLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        frmLogin.setMinimumSize(new java.awt.Dimension(400, 400));
        frmLogin.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_start.setText("Start");
        btn_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_startActionPerformed(evt);
            }
        });
        frmLogin.getContentPane().add(btn_start, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 210, -1));
        frmLogin.getContentPane().add(txt_isim, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 210, -1));

        error.setText("error");
        error.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                errorPropertyChange(evt);
            }
        });
        frmLogin.getContentPane().add(error, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 193, -1, -1));

        lbl_info.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_info.setText("Enter a username and start chatting");
        frmLogin.getContentPane().add(lbl_info, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setPreferredSize(new java.awt.Dimension(50, 150));

        lst_users.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lst_users.setMaximumSize(new java.awt.Dimension(50, 150));
        lst_users.setMinimumSize(new java.awt.Dimension(50, 150));
        lst_users.setPreferredSize(new java.awt.Dimension(50, 150));
        jScrollPane3.setViewportView(lst_users);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 100, 161));

        jScrollPane4.setPreferredSize(new java.awt.Dimension(50, 150));

        lst_rooms.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lst_rooms.setMaximumSize(new java.awt.Dimension(50, 150));
        lst_rooms.setMinimumSize(new java.awt.Dimension(50, 150));
        lst_rooms.setPreferredSize(new java.awt.Dimension(50, 150));
        jScrollPane4.setViewportView(lst_rooms);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 100, 161));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Online Users");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Rooms");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        btn_dm.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btn_dm.setText("Send Direct Message");
        btn_dm.setMaximumSize(new java.awt.Dimension(157, 27));
        btn_dm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dmActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dm, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, -1, -1));

        btn_enter.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btn_enter.setText("Enter The Room");
        btn_enter.setMaximumSize(new java.awt.Dimension(157, 27));
        btn_enter.setMinimumSize(new java.awt.Dimension(157, 27));
        btn_enter.setPreferredSize(new java.awt.Dimension(157, 27));
        btn_enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_enterActionPerformed(evt);
            }
        });
        getContentPane().add(btn_enter, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));

        btn_createRoom.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btn_createRoom.setText("Create a New Room");
        btn_createRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_createRoomActionPerformed(evt);
            }
        });
        getContentPane().add(btn_createRoom, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, -1, -1));

        lbl_roomName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lbl_roomName.setText("Room Name:");
        getContentPane().add(lbl_roomName, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, 20));

        txt_roomName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        getContentPane().add(txt_roomName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 310, 75, -1));

        room_error.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        room_error.setText("jLabel3");
        getContentPane().add(room_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        lbl_welcome.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_welcome.setText("welcome");
        getContentPane().add(lbl_welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startActionPerformed
        
        if (txt_isim.getText().equals("")) {
            error.setText("Please enter a username.");
            error.setVisible(true);
        } else if (txt_isim.getText().contains(" ")) {
            error.setText("Usernames cannot contain space. Please try another one");
            error.setVisible(true);
        } else {
            if (myClient == null || myClient.error.equals("This username is already in use. Please try another one")) {
                error.setVisible(false);
                myClient = new cClient("18.117.228.116", 5000, txt_isim.getText()); //127.0.0.1
                myClient.name = txt_isim.getText();
                Message msg = new Message(Message.Message_Type.Name);
                msg.content = myClient.name;
                myClient.SendMessage(msg);
                
                if (!usersModel.contains(myClient.name)) {
                    usersModel.addElement(myClient.name);
                }
                myClient.Start();
                lbl_welcome.setText("Welcome " + myClient.name);
            } else if (!myClient.isConnected) {
                myClient.Start();
            }
        }
    }//GEN-LAST:event_btn_startActionPerformed

    private void btn_dmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dmActionPerformed
        
        String sendTo = lst_users.getSelectedValue();
        String myName = myClient.name;
        if (sendTo.equals(myClient.name)) {
        } else {
            Message msg = new Message(Message.Message_Type.DChat);
            msg.additional = sendTo;
            msg.content = myName;
            myClient.SendMessage(msg);
            boolean exist = false;
            for (FrmDM d : myClient.dmScreens) {
                if (d.lbl_thisclient_name.equals(myClient.name)) {
                    exist = true;
                    d.setVisible(true);
                }
            }
            if (!exist) {
                FrmDM dmScreen = new FrmDM();
                myClient.dmScreens.add(dmScreen);
                dmScreen.setVisible(true);
                dmScreen.lbl_thisclient_name.setText(myClient.name);
                dmScreen.lbl_chatWith.setText(sendTo);
            }
        }
    }//GEN-LAST:event_btn_dmActionPerformed

    private void btn_createRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_createRoomActionPerformed
        txt_roomName.setVisible(true);
        lbl_roomName.setVisible(true);
        
        if (txt_roomName.getText().equals("")) {
            room_error.setText("Please enter a room name.");
            room_error.setVisible(true);
        } else if (txt_roomName.getText().contains(" ")) {
            room_error.setText("Room names can not contain space. Please try another one");
            room_error.setVisible(true);
        } else if (roomsModel.contains(txt_roomName.getText())) {
            room_error.setText("This room name already exists. Try entering a different name");
            room_error.setVisible(true);
        } else {
            Room r = new Room(txt_roomName.getText());
            Message msg = new Message(Message.Message_Type.Room);
            msg.content = txt_roomName.getText();
            myClient.SendMessage(msg);
            room_error.setVisible(false);
            txt_roomName.setVisible(false);
            txt_roomName.setText("");
            lbl_roomName.setVisible(false);
        }

    }//GEN-LAST:event_btn_createRoomActionPerformed

    private void btn_enterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_enterActionPerformed
        
        String roomName = lst_rooms.getSelectedValue();
        for (Room r : Room.Rooms) {
            if (r.name.equals(roomName)) {
                if (!r.clientsOfRoom.contains(myClient)) {
                    r.clientsOfRoom.add(myClient);
                }
                r.f.lbl_roomName.setText(roomName);
                r.f.txt_thisclient_name.setText(myClient.name);
                r.f.setVisible(true);
                Message m = new Message(Message.Message_Type.enterRoom);
                m.content = roomName;
                myClient.SendMessage(m);
            }
        }

    }//GEN-LAST:event_btn_enterActionPerformed

    private void errorPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_errorPropertyChange
        if (error.getText().equals("client added")) {
            frmLogin.setVisible(false);
            this.setVisible(true);
        }
    }//GEN-LAST:event_errorPropertyChange

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmClient().setVisible(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_createRoom;
    private javax.swing.JButton btn_dm;
    private javax.swing.JButton btn_enter;
    private javax.swing.JButton btn_start;
    public static javax.swing.JLabel error;
    public static javax.swing.JFrame frmLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbl_info;
    private javax.swing.JLabel lbl_roomName;
    private javax.swing.JLabel lbl_welcome;
    public static javax.swing.JList<String> lst_rooms;
    private javax.swing.JList<String> lst_users;
    private javax.swing.JLabel room_error;
    private javax.swing.JTextField txt_isim;
    private javax.swing.JTextField txt_roomName;
    // End of variables declaration//GEN-END:variables
}
