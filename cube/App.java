package cube;

import java.util.Scanner;

/**
 * Main Application for The Cube
 */
public class App {
    static String helpMsg = """
            Usage: Cube [Input] [Options...]

            Input for Cube: 

                Any argument of a series of moves:
                    u, d, r, l, f, b, u', d', r', l', f', or b'
            
            Options for Cube:

                --help, -h
                    Display help information
                
                --print, -p
                    Outputs the state of the cube given it's input
            """;
    
    public Cube cube;
    
    public static void main(String[] args) {
        Config config = new Config(args);
        App app = new App();

        if (config.help == true) {
            System.out.println(App.helpMsg);
            return;
        }

        app.cube = new Cube(config.initalInput);
        if (config.print == true) {
            app.print();
        } else {
            app.run();
        }
    }

    public void run() {
        Boolean done = false;
        Scanner keyboard = new Scanner(System.in);
        while (true) {
            System.out.printf("%s\n\n%s",
            cube.toString(),
            cube.solutionString());
            String input = keyboard.nextLine();
            if (input == "quit") {
                return;
            }
        }
        
    }

    public void print() {
        System.out.println(cube.toString());
    }
}
