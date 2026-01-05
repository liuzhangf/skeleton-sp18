package byog.Core;
import java.awt.Font;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.util.Random;

import java.io.Serializable;
import byog.TileEngine.TETile;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    public static final int WIDTH = 65;
    public static final int HEIGHT = 65;
    public static final int TEXT_BOX_HEIGHT = 10; // 上方文本框高度（像素，可自定义）
    public static final int TOTAL_WINDOW_HEIGHT = HEIGHT + TEXT_BOX_HEIGHT;
    private int posx, posy;
    private Random RANDOM;
    TETile[][] tiles;

    public void playWithKeyboard() {
        GenerateWorld worldGenerator = new GenerateWorld(0);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, TOTAL_WINDOW_HEIGHT);
        RANDOM = new Random(0);
        drawMainMenu();
        boolean keyPressed = false;
        while(( captureMovementInput() == 5 || captureMovementInput() == 6 || captureMovementInput() == -1) && !keyPressed) {

            if (captureMovementInput() == 5){
                TETile[][] tiless = worldGenerator.generateTiles();
                keyPressed = true;
                SetStart(tiless);
                ter.renderFrame(tiless);
                int move = captureMovementInput();
                while (move != 0){
                    int tag = RenderThePicture(move, tiless);
                    drawFame (tag, tiless);
                    move = captureMovementInput();
                }
            }

            else if (captureMovementInput() == 6){
                if(this.tiles == null){
                    StdDraw.clear(StdDraw.BLACK);
                    Font font0 = new Font("Monaco", Font.BOLD, 80);
                    StdDraw.setFont(font0);
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.text(WIDTH * 0.5, HEIGHT * 0.8 , "No tiles found");
                    StdDraw.show();
                    StdDraw.pause(2000);
                    drawMainMenu();
                }

                else {
                    keyPressed = false;
                    ter.renderFrame(this.tiles);
                    int move = captureMovementInput();
                    while (move != 0){
                        int tag = RenderThePicture(move, this.tiles);
                        drawFame (tag, this.tiles);
                        move = captureMovementInput();
                    }
                }
            }
            else {
                System.exit(0);
            }
        }
    }

    private void saveGame () {

    }

    public void SetStart(TETile[][] world) {
        boolean flag = true;
        while(flag){
            for(int x = 0; x < WIDTH; x++) {
                for(int y = 0; y < HEIGHT; y++) {
                    if(world[x][y] == Tileset.FLOOR && flag) {
                        if(randomInt(0,1000) <= 1){
                            world[x][y] = Tileset.PLAYER;
                            flag = false;
                            posx = x;
                            posy = y;
                        }
                    }
                }
            }
        }
    }

    private void drawMainMenu(){
        StdDraw.clear(StdDraw.BLACK);
        Font font0 = new Font("Monaco", Font.BOLD, 80);
        StdDraw.setFont(font0);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.8 , "CS61B: THE GAME");

        Font font1 = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font1);
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.55 , "NEW GAME (N)");
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.5, "LOAD GAME (L)");
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.45 , "QUIT GAME (Q)");
        StdDraw.show();
    }

    private int randomInt(int min, int max) {
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }
        return min + RANDOM.nextInt(max - min + 1);
    }

    private void drawFame(int moveResult, TETile[][] tiles){
        ter.renderFrame(tiles);
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        String tip = "";
        if (moveResult == 0) {
            tip = "You Touch the Wall!!!";
        } else if (moveResult == 2) {
            tip = "You Move One Step!!!";
        } else {
            StdDraw.clear(StdDraw.BLACK);
            Font font0 = new Font("Monaco", Font.BOLD, 80);
            StdDraw.setFont(font0);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(WIDTH * 0.5, HEIGHT * 0.6 , "You Win");
            StdDraw.show();
        }

        double textX = 10;
        double textY = HEIGHT + TEXT_BOX_HEIGHT/2.0 - 4;
        StdDraw.text(textX, textY, tip);
        StdDraw.show();
        StdDraw.rectangle(WIDTH/2.0, HEIGHT + TEXT_BOX_HEIGHT/2.0 - 4, WIDTH/2.0 - 2, TEXT_BOX_HEIGHT/2.0 - 4);
        StdDraw.pause(10);

    }

    private int RenderThePicture(int flag, TETile[][] tiles){

        if(flag == -1){
            this.tiles = tiles;
            System.exit(0);
        }
        else if (flag == 1){
            if(tiles[posx][posy + 1] == Tileset.WALL){
                return 0;
            }

            else if (tiles[posx][posy + 1] == Tileset.UNLOCKED_DOOR){
                return 1;
            }

            else {
                tiles[posx][posy + 1] = Tileset.PLAYER;
                tiles[posx][posy] = Tileset.FLOOR;
                posy = posy + 1;
            }
        }

        else if (flag == 2){
            if(tiles[posx][posy - 1] == Tileset.WALL){
                return 0;
            }

            else if (tiles[posx][posy - 1] == Tileset.UNLOCKED_DOOR){
                return 1;
            }

            else {
                tiles[posx][posy - 1] = Tileset.PLAYER;
                tiles[posx][posy] = Tileset.FLOOR;
                posy = posy - 1;
            }
        }

        else if (flag == 3){
            if(tiles[posx - 1][posy] == Tileset.WALL){
                return 0;
            }

            else if (tiles[posx - 1][posy] == Tileset.UNLOCKED_DOOR){
                return 1;
            }

            else {
                tiles[posx - 1][posy] = Tileset.PLAYER;
                tiles[posx][posy] = Tileset.FLOOR;
                posx = posx - 1;
            }
        }

        else {
            if(tiles[posx + 1][posy] == Tileset.WALL){
                return 0;
            }

            else if (tiles[posx + 1][posy] == Tileset.UNLOCKED_DOOR){
                return 1;
            }

            else {
                tiles[posx + 1][posy] = Tileset.PLAYER;
                tiles[posx][posy] = Tileset.FLOOR;
                posx = posx + 1;
            }
        }
        return 2;
    }

    public int captureMovementInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                switch (key) {
                    case 'q':
                        return -1;
                    case 'w':
                        return 1;
                    case 's':
                        return 2;
                    case 'a':
                        return 3;
                    case 'd':
                        return 4;
                    case 'n':
                        return 5;
                    case 'l':
                        return 6;
                    default:
                        break;
                }
            }
            StdDraw.pause(10);
        }
    }

    public TETile[][] playWithInputString(String s) {
        long seed = parseSeedFromInput(s);
        System.out.println("seed: " + seed);
        GenerateWorld worldGenerator = new GenerateWorld(seed);
        return worldGenerator.generateTiles();
    }

    private long parseSeedFromInput(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        int nIndex = input.indexOf('n');
        int sIndex = input.indexOf('s');
        if (nIndex == -1 || sIndex == -1 || sIndex <= nIndex + 1) {
            return 0;
        }
        String seedStr = input.substring(nIndex + 1, sIndex);
        try {
            return Long.parseLong(seedStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}