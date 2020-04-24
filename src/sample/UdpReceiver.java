package sample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;//a
import java.net.*; //a

public class UdpReceiver implements Runnable {
    private int inPort = 7000; // port we're receiving messages from
    private int portBroadcast = 4000; // port we're sending messages to

    private DatagramSocket socket;
    private boolean receiveMessages = true;
    private Controller messageHandler;

    public UdpReceiver (Controller controller){

        this.messageHandler = controller;
        setupSocket();
    }
    private void setupSocket(){

        try {

            socket = new DatagramSocket(inPort);

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageBack(){
        do {
            Message msg = receivePacket();
            sendMessage("Making the pixel " + msg.getMessage());
        } while (receiveMessages);
    }

    public Message receivePacket(){
        byte[] inBuf = new byte[256];
        DatagramPacket packet = new DatagramPacket(inBuf, inBuf.length);
        Message message = null;
        try {

            socket.receive(packet);
            message = new Message(packet.getData(), packet.getLength());
            System.out.println(message);
            messageHandler.handleMessage(message);
            packet.getSocketAddress();

            if (receiveMessages) messageHandler.receivePacket(message);
            return message;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void sendMessage(String string){
        try {
            sendMessage(string.getBytes(), InetAddress.getByName("255.255.255.255"));
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(byte[] bytes, InetAddress Name){
        DatagramPacket packet = null;
        try {
            packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("255.255.255.255"), portBroadcast);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        sendMessageBack();
    }

    public boolean isReceiveMessages() {
        return receiveMessages;
    }

    public void setReceiveMessages(boolean receiveMessages) {
        this.receiveMessages = receiveMessages;
    }
}