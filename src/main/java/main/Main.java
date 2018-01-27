package main;

import org.apache.commons.cli.*;

public class Main {

    //entry point for the app
    public static void main(String[] args) {


        Options options = new Options();
        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("displays the all options")
                .hasArg(false)
                .required(false)
                .build());

        options.addOption(Option.builder("s")
                .longOpt("server")
                .desc("starts the application as a server")
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
                .desc("starts the application as both a client and server to run locally")
                .hasArg(false)
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

            //if help
            if (cmdLine.hasOption("h")) {
                System.out.println("Help");
                for(Option opt : options.getOptions()) {
                    System.out.printf("%s[%s]\t\t%s\n", opt.getOpt(), opt.getLongOpt(), opt.getDescription());
                }
                System.exit(0);
            }
            else if(cmdLine.hasOption("server"))
            {
                startServer();
            }
            else if(cmdLine.hasOption("client"))
            {
                startClient();
            }
            else if(cmdLine.hasOption("local")) {
                startServer();
                startClient();
            }
        }
        catch (ParseException e) {
            System.err.println(e.getMessage());
            System.err.println("Try '-h' for details on how to use this");
            System.exit(1);
        }
    }

    //returns bool for success
    public static boolean startServer() {
    System.out.println("HI");
        return true;
    }
    public static boolean startClient() {

        return true;
    }
}
