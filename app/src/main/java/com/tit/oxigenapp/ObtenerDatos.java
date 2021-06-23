package com.tit.oxigenapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ObtenerDatos {
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public int conectar(){
        int totalNum = 0;

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        System.out.println(btAdapter.getBondedDevices());
        BluetoothDevice hc05 = btAdapter.getRemoteDevice("00:19:09:03:74:52");
        System.out.println(hc05.getName());
        BluetoothSocket btsocket=null;

        try {
            btsocket = hc05.createRfcommSocketToServiceRecord(myUUID);
            System.out.println(btsocket);
            btsocket.connect();
            System.out.println(btsocket.isConnected());
        } catch (IOException e) {
            System.out.println("Catch 1");
        }

        try {
            OutputStream mmOutStream = null;
            if(btsocket.isConnected()){
                mmOutStream = btsocket.getOutputStream();
                mmOutStream.write(new String("1").getBytes());
            }
        } catch (IOException e) {
            System.out.println("Catch 2");
        }

        InputStream intputStream = null;

        try {
            int bytesAvailable = 0;
            byte[] packetBytes;
            int count = 0;
            do {
                intputStream = btsocket.getInputStream();
                //intputStream.skip(intputStream.available());
                bytesAvailable = intputStream.available();
                packetBytes = new byte[bytesAvailable];
                count++;
            } while (bytesAvailable == 0 && count < 500);
            String total = null;
            if (bytesAvailable > 0) {
                int i2 = 0;
                for(int i=0; i<bytesAvailable; i++) {
                    byte b = (byte) intputStream.read();
                    i2++;
                    //System.out.println((char) b);
                    if (i2 < 2) {
                        byte c = (byte) intputStream.read();
                        System.out.println("Esto es B: " + (char) b);
                        System.out.println("Esto es c: " + (char) c);
                        total = Character.toString((char) b) + Character.toString((char) c);
                        totalNum = Integer.parseInt(total);
                        System.out.println(totalNum);
                        return totalNum;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Catch 3");
        }

        try {
            btsocket.close();
            System.out.println(btsocket.isConnected());
        } catch (IOException e) {
            System.out.println("Catch 4");
        }
        return totalNum;
    }
}
