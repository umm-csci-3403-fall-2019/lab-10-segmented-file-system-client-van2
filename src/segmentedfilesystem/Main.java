package segmentedfilesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    private static ArrayList<Integer> count = new ArrayList<>();
    private static int size;
    private static ArrayList<Integer> myFileIDS = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int port;
        InetAddress address = InetAddress.getByName("csci-4409.morris.umn.edu");
        byte[] buf = new byte[1028];
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 6014);
        //Starting the Conversation
        socket.send(packet);
        ArrayList<DatagramPacket> files = new ArrayList<>();


        HashMap<Integer, ArrayList<DatagramPacket>> map = new HashMap<>();

        while(!isDone()) { //while the server is sending packets, make a new packet and recieve that packet.
            System.out.println("what");
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            addToMap(packet, map);
        }
        writeToFile(map);
        socket.close();
    }

    public static void addToMap(DatagramPacket myPacket, HashMap<Integer, ArrayList<DatagramPacket>> map){
        byte[] dataByte = myPacket.getData();
        int fileID = dataByte[1];
        if (!myFileIDS.contains(fileID)) {
            myFileIDS.add(fileID);
        }
        if(map.containsKey(fileID)) {
            if(dataByte[0] % 2 == 0) {
                System.out.println("Found Header Packet");
                map.get(fileID).add(0, myPacket);
            } else if (dataByte[0] % 4 == 3) {
                int counter = dataByte[2];
                count.add(counter);
                map.get(fileID).add(1, myPacket);
            } else {
                map.get(fileID).add(myPacket);
            }
        } else {
            ArrayList<DatagramPacket> myList = new ArrayList<>();
            myList.add(myPacket);
            map.put(fileID, myList);
        }

        if (dataByte[0] % 4 == 3) {
            int counter = dataByte[2];
            count.add(counter);
        }

       lastPackets();



    }

    public static boolean isDone(){
        if (count.size() == 3) {
            if (count.get(0) + count.get(1) + count.get(2) == size) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

//    public static boolean findLastPacket(HashMap<Integer, ArrayList<DatagramPacket>> map){
//        for()
//        return true;
//    }

    public static void lastPackets(){
        if (count.size() == 3) {
            size = count.get(0) + count.get(1) + count.get(2);
        }
    }

    public static void writeToFile(HashMap<Integer, ArrayList<DatagramPacket>> map) throws IOException {
        System.out.println("We here");
        for (int i = 0; i < 3; i++) {
            ArrayList<DatagramPacket> theFile = map.get(myFileIDS.get(i));
            int myKey = myFileIDS.get(i);
            DatagramPacket header = theFile.get(0);
            byte[] myData = header.getData();
            System.out.println(" status of header " + myData[0]);
            String myFileName = new String(myData, 2, myData.length-2);
            System.out.println(myFileName);
            File file = new File(myFileName);
            OutputStream outputStream = new FileOutputStream(file);
            int counter = 0;
            for (int t = 1; t < theFile.size(); t++) {
                for (int k = 1; k < theFile.size(); k++){
                    if (map.get(myKey).get(k).getData()[2] == counter){
                        outputStream.write(map.get(myKey).get(k).getData(),4, map.get(myKey).get(k).getData().length-4);
                        counter++;
                        System.out.println("it added Packet");
                    }
                }
                //outputStream.write();
            }
            outputStream.flush();
        }

    }

}
