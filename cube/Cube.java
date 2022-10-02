package cube;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
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

    public Deque<String> inputQueue; // moves to be executed
    private Deque<String> movesStack; // state of the cube in moves

    public static final Pattern MOVE = Pattern.compile("((u|d|r|l|f|b)'?)", Pattern.CASE_INSENSITIVE);
    public static final Pattern INPUT = Pattern.compile("((u|d|r|l|f|b)'?)+", Pattern.CASE_INSENSITIVE);

    public Cube(List<String> initalInput) {
        inputQueue = new ArrayDeque<String>(initalInput);
        movesStack = new ArrayDeque<String>();
        exec();
    }

    public void exec() {
        for (String input : inputQueue) {
            doMove(input);
            movesStack.addLast(input);
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
        for (String move : movesStack) {
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

    /**
     * I caught a cold while writing this part of the code, the side facelet translations are ugly at best.
     * Forgive my lack of patience with java. - "If it ain't broke don't fix it" - T. Bert Lance
     * @param input
     */
    private void doMove(String input) {
        try {
            char[] temp;
            switch (input) {
                case "u":
                    state[4] = rrot(state[4]);
                    temp = getColumn(state[2], 0);
                    state[2] = setColumn(state[1], 0, state[2], 0);
                    state[1] = setColumn(state[0], 0, state[1], 0);
                    state[0] = setColumn(state[3], 0, state[0], 0);
                    state[3] = setColumn(temp, state[3], 0);
                    break;
                case "d":
                    state[5] = rrot(state[5]);
                    temp = getColumn(state[3], 2);
                    state[3] = setColumn(state[0], 2, state[3], 2);
                    state[0] = setColumn(state[1], 2, state[0], 2);
                    state[1] = setColumn(state[2], 2, state[1], 2);
                    state[2] = setColumn(temp, state[2], 2);
                    break;
                case "r":
                    state[0] = rrot(state[0]);
                    temp = state[3][0];
                    state[3][0] = state[4][2];
                    state[4][2] = state[1][2];
                    state[1][2] = state[5][2];
                    state[5][2] = temp;
                    break;
                case "l":
                    state[2] = rrot(state[2]);
                    temp = state[3][2];
                    state[3][2] = state[5][0];
                    state[5][0] = state[1][0];
                    state[1][0] = state[4][0];
                    state[4][0] = temp;
                    break;
                case "f":
                    state[1] = rrot(state[1]);
                    temp = getColumn(state[5], 0);
                    state[5] = setColumn(state[0][0], state[5], 0);
                    state[0][0] = getColumn(state[4], 2);
                    state[4] = setColumn(state[2][2], state[4], 2);
                    state[2][2] = temp;
                    break;
                case "b":
                    state[3] = rrot(state[3]);
                    temp = getColumn(state[4], 0);
                    state[4] = setColumn(state[0][2], state[4], 0);
                    state[0][2] = getColumn(state[5], 2);
                    state[5] = setColumn(state[2][0], state[5], 2);
                    state[2][0] = temp;
                    break;
                    
                case "u'":
                    state[4] = rot(state[4]);
                    for (int i = 0; i < 3; i++) {
                        temp = getColumn(state[2], 0);
                        state[2] = setColumn(state[1], 0, state[2], 0);
                        state[1] = setColumn(state[0], 0, state[1], 0);
                        state[0] = setColumn(state[3], 0, state[0], 0);
                        state[3] = setColumn(temp, state[3], 0);
                    }
                    break;
                case "d'":
                    state[5] = rot(state[5]);
                    for (int i = 0; i < 3; i++) {
                        temp = getColumn(state[3], 2);
                        state[3] = setColumn(state[0], 2, state[3], 2);
                        state[0] = setColumn(state[1], 2, state[0], 2);
                        state[1] = setColumn(state[2], 2, state[1], 2);
                        state[2] = setColumn(temp, state[2], 2);
                    }
                    break;
                case "r'":
                    state[0] = rot(state[0]);
                    for (int i = 0; i < 3; i++) {
                        temp = state[3][0];
                        state[3][0] = state[4][2];
                        state[4][2] = state[1][2];
                        state[1][2] = state[5][2];
                        state[5][2] = temp;
                    }
                    break;
                case "l'":
                    state[2] = rot(state[2]);   
                    for (int i = 0; i < 3; i++) {
                        temp = state[3][2];
                        state[3][2] = state[5][0];
                        state[5][0] = state[1][0];
                        state[1][0] = state[4][0];
                        state[4][0] = temp;
                    }
                    break;
                case "f'":
                    state[1] = rot(state[1]);
                    for (int i = 0; i < 3; i++) {
                        temp = getColumn(state[5], 0);
                        state[5] = setColumn(state[0][0], state[5], 0);
                        state[0][0] = getColumn(state[4], 2);
                        state[4] = setColumn(state[2][2], state[4], 2);
                        state[2][2] = temp;
                    }
                    break;
                case "b'":
                    state[3] = rot(state[3]);
                    for (int i = 0; i < 3; i++) {
                        temp = getColumn(state[4], 0);
                        state[4] = setColumn(state[0][2], state[4], 0);
                        state[0][2] = getColumn(state[5], 2);
                        state[5] = setColumn(state[2][0], state[5], 2);
                        state[2][0] = temp;
                    }
                    break;
                default:
                    throw new Exception("Invalid move.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // I made this by accident when making rot(matrix) and decided to keep it
    private static char[][] transpose(char[][] matrix) {
        final int N = matrix.length;
        final int M = matrix[0].length;
        char[][] retMat = new char[M][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                retMat[j][i] = matrix[i][j];
            }
        }
        return retMat;
    }

    /**
     * 
     * @param matrix
     * @return rotation of matrix
     */
    private static char[][] rot(char[][] matrix) {
        final int N = matrix.length;
        final int M = matrix[0].length;
        char[][] retMat = new char[M][N];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                retMat[j][(N-1)-i] = matrix[i][j];
            }
        }
        return retMat;
    }

    /**
     * 
     * @param matrix
     * @return reverse rotation of matrix
     */
    private static char[][] rrot(char[][] matrix) {
        final int N = matrix.length;
        final int M = matrix[0].length;
        char[][] retMat = new char[M][N];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                retMat[(M-1)-j][i] = matrix[i][j];
            }
        }
        return retMat;
    }

    private static char[] getColumn(char[][] matrix, int Idx) {
        final int N = matrix.length;
        final int M = matrix[0].length;
        char[] retColVec = new char[M];

        for (int i = 0; i < M; i++) {
            retColVec[i] = matrix[Idx][i];
        }

        return retColVec;
    }

    private static char[][] setColumn(char[][] src, int srcIdx, char[][] dest, int destIdx) {
        final int srcRows = src.length;
        final int srcCols = src[0].length;
        final int destRows = dest.length;
        final int destCols = dest[0].length;

        for (int i = 0; i < srcCols; i++) {
            dest[destIdx][i] = src[srcIdx][i];
        }

        return dest;
    }

    private static char[][] setColumn(char[] src, char[][] dest, int destIdx) {
        final int srcLen = src.length;
        final int destRows = dest.length;
        final int destCols = dest[0].length;

        for (int i = 0; i < srcLen; i++) {
            dest[destIdx][i] = src[i];
        }

        return dest;
    }
}
