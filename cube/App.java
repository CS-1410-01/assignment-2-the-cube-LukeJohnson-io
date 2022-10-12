package cube;

import java.util.Scanner;
import java.util.regex.Matcher;

// Feedback: 
//  A very impressive implementation, extremely readable and would be easy to contribute to. Not necessarilly easy for some new developers to
//  understand so it might be wise to comment slightly more if you plan on sharing with class mates but on the whole excellent. I would
//  note one addition might be to check on each move whether or not the cube is solved and maybe emptying out your solutions stack just so
//  you dont run into outputs like this: Solution: r'r'r'r'u'u'u'u'
//  But that was not part of the breif and is more of a suggestion. 
//
// Great Job!

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
        try (Scanner keyboard = new Scanner(System.in)) { //ensures you don't run into resource leaks, not necessary but good practice.
            while (true) {
                System.out.printf("%s\n\nSolution: %s\n\n>>> ",
                    cube.toString(),
                    cube.solutionString());
                String input = keyboard.nextLine();

                if (input.equals("quit")) {
                    return;
                }

                if (!Cube.INPUT.matcher(input).matches()) {
                    System.out.println("Invalid Input, try u, d, r, l, f, b, u', d', r', l', f', b', or quit");
                    continue;
                }
                
                Matcher moveMatcher = Cube.MOVE.matcher(input);
                while (moveMatcher.find()) {
                    cube.inputQueue.addLast(moveMatcher.group());
                    cube.exec();
                }
            }
            // clean up
        }
    }

    public void print() {
        System.out.printf("%s\n\n%s\n>>> ",
                cube.toString(),
                cube.solutionString());
    }
}
