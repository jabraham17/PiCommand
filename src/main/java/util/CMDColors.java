package util;

//command line colors and customization
public class CMDColors {

    //escape sequence in oct
    public static final String ESCAPE = "\033[";
    //value ender
    public static final String END = "m";
    //serpeator for mutlipe values
    public static final String SEPERATPOR = ";";


    //for all of the enums here that have codes to implement
    public interface ControlCode {
        public String getValue();
    }

    //reste codes
    public enum Reset implements ControlCode {
        ALL("0");

        //constrcutor to hold value
        Reset(String value) {
            this.value = value;
        }
        //the value of the enum
        public String value;

        @Override
        public String getValue() {
            return value;
        }
    }

    //all foreground colors
    public enum ForegroundColor implements ControlCode {

        BLACK("30"),
        RED("31"),
        GREEN("32"),
        YELLOW("33"),
        BLUE("34"),
        PURPLE("35"),
        CYAN("36"),
        WHITE("37"),
        BRIGHT_BLACK("90"),
        BRIGHT_RED("91"),
        BRIGHT_GREEN("92"),
        BRIGHT_YELLOW("93"),
        BRIGHT_BLUE("94"),
        BRIGHT_PURPLE("95"),
        BRIGHT_CYAN("96"),
        BRIGHT_WHITE("97"),
        DIM_BLACK("30;2"),
        DIM_RED("31;2"),
        DIM_GREEN("32;2"),
        DIM_YELLOW("33;2"),
        DIM_BLUE("34;2"),
        DIM_PURPLE("35;2"),
        DIM_CYAN("36;2"),
        DIM_WHITE("37;2");


        //constrcutor to hold value
        ForegroundColor(String value) {
            this.value = value;
        }
        //the value of the enum
        public String value;

        @Override
        public String getValue() {
            return value;
        }
    }

    //all background colors
    public enum BackgroundColor implements ControlCode {

        BLACK("40"),
        RED("41"),
        GREEN("42"),
        YELLOW("43"),
        BLUE("44"),
        PURPLE("45"),
        CYAN("46"),
        WHITE("47"),
        BRIGHT_BLACK("100"),
        BRIGHT_RED("101"),
        BRIGHT_GREEN("102"),
        BRIGHT_YELLOW("103"),
        BRIGHT_BLUE("104"),
        BRIGHT_PURPLE("105"),
        BRIGHT_CYAN("106"),
        BRIGHT_WHITE("107"),
        DIM_BLACK("40;2"),
        DIM_RED("41;2"),
        DIM_GREEN("42;2"),
        DIM_YELLOW("43;2"),
        DIM_BLUE("44;2"),
        DIM_PURPLE("45;2"),
        DIM_CYAN("46;2"),
        DIM_WHITE("47;2");

        //constrcutor to hold value
        BackgroundColor(String value) {
            this.value = value;
        }
        //the value of the enum
        public String value;

        @Override
        public String getValue() {
            return value;
        }
    }

    //different text decorators
    public enum Decorator implements ControlCode {
        //diferent names for same thing
        BOLD("1;2"),
        ITALLIC("3"),
        UNDERSCORE("4"),
        BLINK("5"),
        INVERT("7"),
        HIDDEN("8"),
        STRIKETHROUGH("9"),
        DEFAULT("10"),
        OVERSCORE("53");

        //constrcutor to hold value
        Decorator(String value) {
            this.value = value;
        }
        //the value of the enum
        public String value;

        @Override
        public String getValue() {
            return value;
        }
    }

    //get a control sequence to be used
    public static String getSequence(ControlCode... codes) {
        //if no codes specified, return a reset sequence
        if(codes.length == 0) {
            return ESCAPE + Reset.ALL.getValue() + END;
        }

        //start the sequence with an escape character and the first elemnt
        String sequence = ESCAPE + codes[0].getValue();
        //loop througb reming elements and place a serpeator befoew each one
        for(int i = 1; i < codes.length; i++) {
            ControlCode code = codes[i];
            sequence += SEPERATPOR + code.getValue();
        }
        sequence += END;
        return sequence;
    }

}