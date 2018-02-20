package server;

import server.commands.GPIO;

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
                else if(input.contains("gpio")) {
                    GPIO pinControl = new GPIO();
                    String[] s = input.split(" ");
                    //check if supplied help command
                    if(s.length == 2 && s[1].equals("help")) {
                        output = String.format("%s\n%s\n%s", pinControl.name(), pinControl.description(), pinControl.invocation());
                    }
                    //check if valud control sequence was specified
                    else if(s.length != 3 || !pinControl.subCmdOptions().contains(s[1]) || !s[2].matches("\\d+")) {
                        output = "Invalid Options: Try 'gpio help'";
                    }
                    else {
                        output = (String) pinControl.execute(s[1], Integer.parseInt(s[2]));
                    }
                }
                //uknown
                else {
                    output = "Unknown output";
                }

                //TODO: use proto buffers so output is propeprly wirtten
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
