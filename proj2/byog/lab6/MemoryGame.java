package byog.lab6;
import edu.princeton.cs.introcs.StdDraw;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        /*
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }
        int seed = Integer.parseInt(args[0]);

        //rand =  new Random(seed);
         */
        int seed = 123;
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        rand = new Random(seed);
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        this.gameOver = false;
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return sb.toString();
    }


    public void drawFrame(String s, boolean status, String guli) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(StdDraw.BLACK);
        Font font0 = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font0);
        StdDraw.setPenColor(StdDraw.WHITE);

        StringBuilder sb = new StringBuilder();
        sb.append("Round:");
        sb.append(round);
        StdDraw.text(3, height - 1, sb.toString());
        if (status) {
            StdDraw.text(Math.round(width * 0.4), height -1, "Watch");
        }
        else {
            StdDraw.text(Math.round(width * 0.4), height -1, "Type");
        }



        sb=  new StringBuilder();
        sb.append(ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
        if (status == true) {
            guli = sb.toString();
        }

        StdDraw.text(Math.round(width * 0.82), height -1, guli);

        double centerX = width / 2.0;
        double centerY = height / 2.0;
        Font font = new Font("Monaco", Font.BOLD, 70);
        StdDraw.setFont(font);
        StdDraw.text(centerX, centerY, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        for(int i = 0; i < letters.length(); i++) {
            drawFrame(letters.charAt(i) + "", true, "");
            StdDraw.pause(1000);
            if (i != letters.length() - 1) {
                drawFrame("", true, "");
                StdDraw.pause(500);
            }
        }
        //TODO: Display each character in letters, making sure to blank the screen between letters
    }

    public String solicitNCharsInput(int n) {

        StringBuilder sb = new StringBuilder();
        while (sb.length() < n - 1) {
            if (StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                sb.append(c);
            }
            drawFrame(sb.toString(), false, "You're a Star");
        }
        drawFrame(sb.toString(), false, "You're a Star");
        return sb.toString();
    }

    public void startGame() {
        int cnt = 1;
        while (!gameOver) {
            String s = generateRandomString(cnt++);
            System.out.println(s);
            flashSequence(s);
            String s2 = solicitNCharsInput(cnt);
            System.out.println(s2.length());
            if(s2.equals(s)){
                gameOver = false;
            }
            else {
                gameOver = true;
            }
        }
        System.out.println("haha ");

        StdDraw.clear(StdDraw.BLACK);
        Font font0 = new Font("Monaco", Font.BOLD, 80);
        StdDraw.setFont(font0);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(width * 0.5, height * 0.6 , "Game Over");
        StdDraw.show();
    }
}