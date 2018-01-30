package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionInstance extends Thread
{

    private Socket socket;
    public ConnectionInstance(Socket socket)
    {
        this.socket = socket;
        this.setDaemon(false);
    }

    @Override
    public void run() {

        // init all resources that must be closed here
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            //loop until receive a quit
            boolean done = false;
            while (!done) {
                //what will be returned
                String output;

                //read the sent data
                String input = in.readLine();

                //process
                if(input.equals("exit")) {
                    done = true;
                    output = "Closed connection succesfully";
                }
                //uknown
                else {
                    output = "Unknown output";
                }

                //send the response
                out.println(output);
            }

            //when done close socket
            socket.close();
        }
        catch (IOException e) {
            System.err.println("An error occured: " + e.getMessage());
            System.exit(1);
        }
    }
}
