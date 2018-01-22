import com.martiansoftware.jsap.*;

public class Main {
    public static void main(String[] args){
        JSAP jsap = new JSAP();
        JSAPResult jsapResult;
        try {
            fillJsapParser(jsap);
        }
        catch (JSAPException e){
            System.out.println("There was a problem with JSAP parser.");
            return;
        }
        try {
            jsapResult = jsap.parse(args);
        }
        catch (Exception e){
            System.out.println("Couldn't parse given options.");
            return;
        }
        AirlyConsole airlyConsole = new AirlyConsole();
        airlyConsole.execute(jsapResult);
    }

    private static void fillJsapParser(JSAP jsap) throws JSAPException{
        FlaggedOption apiKeyOption = new FlaggedOption("api-key")
                .setStringParser(JSAP.STRING_PARSER)
                .setShortFlag(JSAP.NO_SHORTFLAG)
                .setLongFlag("api-key");

        apiKeyOption.setHelp("Program will use given api key to authenticate. " +
                "If no api key was specified, program will try to use env viable API_KEY.");

        FlaggedOption sensorIdOption = new FlaggedOption("sensor-id")
                .setStringParser(JSAP.INTEGER_PARSER)
                .setShortFlag(JSAP.NO_SHORTFLAG)
                .setLongFlag("sensor-id");

        sensorIdOption.setHelp("Shows measurements from the sensor of given id.");

        FlaggedOption latitudeOption = new FlaggedOption("latitude")
                .setStringParser(JSAP.DOUBLE_PARSER)
                .setShortFlag(JSAP.NO_SHORTFLAG)
                .setLongFlag("latitude");

        latitudeOption.setHelp("Shows measurements from the nearest sensor for given latitude. Requires longitude.");

        FlaggedOption longitudeOption = new FlaggedOption("longitude")
                .setStringParser(JSAP.DOUBLE_PARSER)
                .setShortFlag(JSAP.NO_SHORTFLAG)
                .setLongFlag("longitude");

        longitudeOption.setHelp("Shows measurements from the nearest sensor for given longitude. Requires latitude.");

        Switch historySwitch = new Switch("history")
                .setShortFlag(JSAP.NO_SHORTFLAG)
                .setLongFlag("history");

        historySwitch.setHelp("Changes program mode to display historic measurements instead of current one.");

        jsap.registerParameter(apiKeyOption);
        jsap.registerParameter(sensorIdOption);
        jsap.registerParameter(latitudeOption);
        jsap.registerParameter(longitudeOption);
        jsap.registerParameter(historySwitch);
    }
}
