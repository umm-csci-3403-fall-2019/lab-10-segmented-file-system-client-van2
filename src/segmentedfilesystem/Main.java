package segmentedfilesystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        int port;
        InetAddress address = InetAddress.getByName("csci-4409.morris.umn.edu");
        byte[] buf = new byte[1028];
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 6014);
        ArrayList<DatagramPacket> header = new ArrayList<DatagramPacket>();
        ArrayList<DatagramPacket> datap = new ArrayList<DatagramPacket>();
                //Starting the Conversation
        socket.send(packet);

        

        while(!isDone()) { //while the server is sending packets, make a new packet and recieve that packet.
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            if(buf[0] % 2  == 0){
                header.add(packet);
            } else {
                datap.add(packet);
            }

        }



        socket.close();

    }

    public static boolean isDone(){
        return true;
    }

}
