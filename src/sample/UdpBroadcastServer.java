
package sample;

import java.io.IOException;
import java.net.*;

public class UdpBroadcastServer implements Runnable {

    private boolean broadcast = true; // this boolean is gonna tell the class whether to broadcast or not
    private DatagramSocket socket; //in order to do udp programming you need two kind of objects basically: socket and packets.
    // Sockets can send and receive and packets
    // Packets because those are the ones being sent and received
    private int portBroadcast = 4000; //this is the port we're gonna broadcast on
    private String message ="Echo server listens on port 7000, answers on port 7007";

    private boolean isBroadcast() {
        return broadcast;
    }

    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    //now we need to make a method that tries  to call the other message every 3 seconds or so
    public void broadcastLoop() { //this will work on a loop, wait 3 secs, send msg, wait 3 secs, send message
        while (true) {
            try {
                Thread.sleep(5000); //if u want this to wait for 3 seconds u can make this thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (broadcast) broadcastMessage(message);

        }
    }

    //I need a method that actually broadcasts the message
    private void broadcastMessage(String message){ //* from this line
        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);

            byte[] buffer = message.getBytes(); //create a byte array. You can not send a msg as a string you need to convert it into a byte array
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), portBroadcast); //creating a packet
            //now that we created a socket we're gonna send it:
            socket.send(packet);
            socket.close(); //since we made a new socket, we should close it when we're done //*to this line, this will send our msg on the broadcasting network

        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() { //this method is what starts the thread!!!!
        broadcastLoop();

    }
}