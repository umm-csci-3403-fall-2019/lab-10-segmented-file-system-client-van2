package segmentedfilesystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {
    
    public static void main(String[] args) throws IOException {
        int port;
        InetAddress address = InetAddress.getByName(args[0]);
        byte[] buf = new byte[1028];
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 6014);
        socket.send(packet);

        //while() { while the server is sending packets, make a new packet and recieve that packet.
            packet = new DatagramPacket(buf, buf.length);

        //}

        socket.close();

    }

}
