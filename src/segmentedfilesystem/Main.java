package segmentedfilesystem;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        int port;
        InetAddress address = InetAddress.getByName("csci-4409.morris.umn.edu");
        byte[] buf = new byte[1028];
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 6014);
        //Starting the Conversation
        socket.send(packet);
        //ArrayList<DatagramPacket> File1; //= //new ArrayList<>();
        //ArrayList<DatagramPacket> File2 = new ArrayList<>();


        HashMap<Integer, ArrayList<DatagramPacket>> map = new HashMap<>();

        while(!isDone()) { //while the server is sending packets, make a new packet and recieve that packet.
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            //sortPacket(packet);
            if(buf[0] % 2  == 0){
                //header.add(packet); add to an array
            } else {
                addToMap(packet, map);
            }

        }



        socket.close();

    }

    public static void addToMap(DatagramPacket myPacket, HashMap<Integer, ArrayList<DatagramPacket>> map){
        byte[] dataByte = myPacket.getData();
        int fileID = dataByte[1];
        if(map.containsKey(fileID)) {
            map.get(fileID).add(myPacket);
        } else {
            ArrayList<DatagramPacket> myList = new ArrayList<>();
            myList.add(myPacket);
            map.put(fileID, myList);
        }

    }

    public static ArrayList<DatagramPacket> sortPacket(DatagramPacket p){
        byte[] pdata = p.getData();
        if(pdata[1] % 2 == 0){

        }
        return null;
    }

    public static boolean isDone(){
        return true;
    }

}
