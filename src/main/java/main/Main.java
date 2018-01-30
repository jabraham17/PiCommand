package main;

import client.Client;
import org.apache.commons.cli.*;
import server.Server;

public class Main {

    //entry point for the app
    public static void main(String[] args) throws InterruptedException {


        Options options = new Options();
        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("displays the all options")
                .hasArg(false)
                .required(false)
                .build());

        options.addOption(Option.builder("s")
                .longOpt("server")
                .desc("starts the application as a server\nnote: the '-n' option has no effect on this")
                .hasArg(false)
                .required(false)
                .build());

        options.addOption(Option.builder("c")
                .longOpt("client")
                .desc("starts the application as a client")
                .hasArg(false)
                .required(false)
                .build());

        options.addOption(Option.builder("l")
                .longOpt("local")
                .desc("starts the application as both a client and server to run locally\nnote: the '-n' option has no effect on this")
                .hasArg(false)
                .required(false)
                .build());

        options.addOption(Option.builder("n")
                .longOpt("host")
                .desc("hostname of the computer that is hosting the server, only used for client mode\nnote: if unspecified will use 'localhost'")
                .hasArg(true)
                .required(false)
                .build());

        options.addOption(Option.builder("p")
                .longOpt("port")
                .desc("port to connect the server and client over, if not specified will use '4444'\nnote: the same port must be used for both the server and client")
                .hasArg(true)
                .required(false)
                .build());


        try
        {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmdLine = parser.parse(options, args);

            //nothing was parsed
            if (cmdLine == null) {
                System.err.println("Unknown option. Try using '-h'");
                System.exit(1);
            }
            //if no options, print err
            if (cmdLine.getOptions().length == 0) {
                System.err.println("Must supply option(s). Try using '-h'");
                System.exit(1);
            }

            //get port and host
            String host = cmdLine.getOptionValue("n","localhost");
            String port = cmdLine.getOptionValue("p", null);


            //if help
            if (cmdLine.hasOption("h")) {

                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("Pi Command", options);

                System.exit(0);
            }
            else if(cmdLine.hasOption("server"))
            {
                startServer(port);
            }
            else if(cmdLine.hasOption("client"))
            {
                startClient(host, port);
            }
            else if(cmdLine.hasOption("local")) {
                startServer(port);
                Thread.sleep(2000);
                startClient(host, port);
            }
        }
        catch (ParseException e) {
            System.err.println(e.getMessage());
            System.err.println("Try '-h' for details on how to use this");
            System.exit(1);
        }
    }

    //returns bool for success
    public static Server startServer(String port) {

        Server server;
        //use default port
        if (port == null) {
            server = new Server();
        }
        else {
            server = new Server(port);
        }
        server.start();

        return server;
    }
    public static Client startClient(String hostname, String port) {

        Client client;
        //use default port
        if (port == null) {
            client = new Client(hostname);
        }
        else {
            client = new Client(hostname, port);
        }
        client.start();

        return client;
    }
}
