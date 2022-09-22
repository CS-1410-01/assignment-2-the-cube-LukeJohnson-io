package cube;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Handles environment config for App
 */
public class Config {
    public boolean help;
    public boolean print;
    public List<String> initalInput = new ArrayList<String>();

    public Config(String[] args) {
        help = false;
        print = false;
        for (String arg : args) {
            if (arg.startsWith("--")) {
                setLongOption(arg);
            } else if (arg.startsWith("-")) {
                setShortOption(arg);
            } else if (Cube.INPUT.matcher(arg).matches()) {
                initializeInput(arg);
            } else {
                invalidArg(arg);
            }
        }
    }

    private void invalidArg(String arg) {
        System.out.printf("Invalid command line option: {}\n", arg);
        help = true;
    }

    private void setLongOption(String arg) {
        switch (arg.substring(2).toLowerCase()) {
            case "help":
                help = true;
                break;
            case "print":
                print = true;
                break;
            default:
                invalidArg(arg);
        }
    }

    private void setShortOption(String arg) {
        switch (arg.toLowerCase().charAt(1)) {
            case 'h':
                help = true;
                break;
            case 'p':
                print = true;
                break;
            default:
                invalidArg(arg);
        }
    }

    private void initializeInput(String arg) {
        Matcher moveMatcher = Cube.MOVE.matcher(arg);
        while (moveMatcher.find()) {
            initalInput.add(moveMatcher.group());
        }
    }
}
