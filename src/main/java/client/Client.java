package client;

import util.CMDColors;
import util.CMDColors.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {


    private String hostname;
    private int port;

    public Client(String hostname) {
        //use default port
        this(hostname, "4444");
    }

    public Client(String hostname, String port) {

        //if no port, return error
        if(port == null || port.isEmpty() || !port.matches("\\d*")) {
            System.err.println("The port given is not a number or was not provided");
            System.exit(1);
        }

        this.hostname = hostname;
        this.port = Integer.parseInt(port);

    }

    @Override
    public void run() {
        super.run();

        // init all resources that must be closed here
        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            //print diagnoastic info about connection
            System.out.println("Connected to server at " + hostname + " on port " + port);

            //loop until receive a quit
            boolean done = false;
            while (!done) {
                //print the prompt
                printPrompt();
                //get the text
                String input = stdIn.readLine();
                if(input.equals("exit") || input.equals("quit")) {
                    //if the result is exit control close connection
                    input = "exiting";
                }

                //send to server
                out.println(input);
                //read the response
                String result = in.readLine();

                // print the result
                printServerResponse(result);
            }
        }
        catch (IOException e) {
            System.err.println("An error occured: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void printPrompt() {
        System.out.print(CMDColors.getSequence(ForegroundColor.BRIGHT_CYAN) + "$" + CMDColors.getSequence() + " ");
    }

    public static void printServerResponse(String message) {
        System.out.println(CMDColors.getSequence(ForegroundColor.PURPLE) + "SERVER:" + CMDColors.getSequence() + " " + message);
    }
}
