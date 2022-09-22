package cube;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cube {
    private char[][][] state = {
    {
        {'r', 'r', 'r'},
        {'r', 'r', 'r'},
        {'r', 'r', 'r'}
    },{
        {'b', 'b', 'b'},
        {'b', 'b', 'b'},
        {'b', 'b', 'b'}
    },{
        {'o', 'o', 'o'},
        {'o', 'o', 'o'},
        {'o', 'o', 'o'}
    },{
        {'g', 'g', 'g'},
        {'g', 'g', 'g'},
        {'g', 'g', 'g'}
    },{
        {'y', 'y', 'y'},
        {'y', 'y', 'y'},
        {'y', 'y', 'y'}
    },{
        {'w', 'w', 'w'},
        {'w', 'w', 'w'},
        {'w', 'w', 'w'}
    }};

    private Deque<String> inputQueue; // moves to be executed
    private Deque<String> cubeState; // state of the cube in moves

    public static final Pattern MOVE = Pattern.compile("((u|d|r|l|f|b)'?)", Pattern.CASE_INSENSITIVE);
    public static final Pattern INPUT = Pattern.compile("((u|d|r|l|f|b)'?)+", Pattern.CASE_INSENSITIVE);


    public Cube(List<String> initalInput) {
        inputQueue = new ArrayDeque<String>(initalInput);
        cubeState = new ArrayDeque<String>();
        exec();
    }

    public void exec() {
        for (String input : inputQueue) {
            cubeState.addLast(input);
        }
        inputQueue.clear();
    }

    public String toString() {
        StringBuilder reprBuilder = new StringBuilder();
        for (char[][] face : state) {
            for (char[] row : face) {
                reprBuilder.append(
                    String.format("%c|%c|%c\n", row[0], row[1], row[2])
                );
            }
            reprBuilder.append('\n');
        }
        return reprBuilder.toString().trim();
    }

    public String solutionString() {
        Deque<String> tempStack = new ArrayDeque<String>();
        for (String move : cubeState) {
            tempStack.push(move);
        }
        StringBuilder retString = new StringBuilder();
        while (true) {
            if (tempStack.isEmpty()) {
                return retString.toString();
            }
            retString.append(tempStack.pop());
        }
    }

    private static void validMove(String input) {
        try {
            switch (input.toLowerCase()) {
                case "u":
                case "d":
                case "r":
                case "l":
                case "f":
                case "b":
                case "u'":
                case "d'":
                case "r'":
                case "l'":
                case "f'":
                case "b'":
                    break;
                default:
                    throw new Exception("Invalid move.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
}
