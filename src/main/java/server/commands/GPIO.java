package server.commands;

import com.pi4j.io.gpio.*;

public class GPIO implements Command {

    @Override
    public String name() {
        return "GPIO";
    }

    @Override
    public String description() {
        return "Interface with gpio ports";
    }

    @Override
    public String invocation() {
        return "gpio high|low|flip <gpio pin number>";
    }

    public String subCmdOptions() {
        return "high|low|flip";
    }


    //precondition: must pass one argument which is and int representing pin number
    @Override
    public Object execute(Object... args) {
        if(args.length != 2 || args[0] == null || args[1] == null || !(args[0] instanceof String) || !(args[1] instanceof Integer)) {
            throw new IllegalArgumentException("Must pass two arguments, one of type 'String' and one of type 'int'");
        }
        String action = (String) args[0];
        //can now safely cast
        int pinNum = ((Integer) args[1]).intValue();


        GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));
        //make gpio controller
        GpioController gpio = GpioFactory.getInstance();
        //make the pin
        GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiBcmPin.getPinByAddress(pinNum));

        if(action.equals("high")) {
            pin.high();
        }
        else if(action.equals("low")) {
            pin.low();
        }
        else if(action.equals("flip")) {
            pin.toggle();
        }
        else {
            throw new IllegalArgumentException("Action provided was not valid");
        }

        String output = String.format("BCM Pin %d is now %s", pinNum, pin.getState().getName());

        gpio.shutdown();
        gpio.unprovisionPin(pin);

        return output;
    }
}
