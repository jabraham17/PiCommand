package server;

import util.CMDColors;
import util.CMDColors.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.*;

public class Server extends Thread {

    private int port;

    public Server() {
        //use default port
        this("4444");
    }

    public Server(String port) {

        //if no port, return error
        if(port == null || port.isEmpty() || !port.matches("\\d*")) {
            System.err.println("The port given is not a number or was not provided");
            System.exit(1);
        }
        this.port = Integer.parseInt(port);

        this.setDaemon(false);
    }

    @Override
    public void run() {

        // init all resources that must be closed here
        try (ServerSocket socket = new ServerSocket(port)) {

            //loop until receive a quit
            boolean done = false;
            while (!done) {
                ConnectionInstance connectionInstance = new ConnectionInstance(socket.accept());
                connectionInstance.start();
            }
        }
        catch (IOException e) {
            System.err.println("An error occured: " + e.getMessage());
            System.exit(1);
        }
    }
}
