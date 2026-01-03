package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

/*
    第一个坐标（x）= 左右方向 → 越大越靠右；
    第二个坐标（y）= 上下方向 → 越大越靠上；
*/

public class GenerateWorld {

    private static final int WIDTH = 65;
    private static final int HEIGHT = 65;
    private final Random RANDOM;
    private int Max_num;
    private ArrayList<Room> roomList;

    public GenerateWorld(long  x) {
        RANDOM = new Random(x);
        roomList = new ArrayList<>();
        Max_num = (int) Math.round(0.4 * WIDTH * HEIGHT);
    }

    public TETile[][] generateTiles() {

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        GenerateFull(world);
        Collections.sort(roomList);
        GeneratePath(world, roomList);
        drawWall(world);
        return world;
    }

    private int randomInt(int min, int max) {
        if (min > max) { // 防止min>max报错
            int temp = min;
            min = max;
            max = temp;
        }
        return min + RANDOM.nextInt(max - min + 1);
    }

    private void GeneratePath(TETile[][] tiles, ArrayList<Room> roomList) {
        for (int i = 1; i < roomList.size(); i++) {

            /*
            从左到右看,竖条的形状有重叠
            */

            if (roomList.get(i - 1).generatex + roomList.get(i - 1).generatewidth - 1 >= roomList.get(i).generatex) {
                if (roomList.get(i - 1).generatey + roomList.get(i - 1).generationheight - 1 < roomList.get(i).generatey){
                    for (int j = roomList.get(i - 1).generatey + roomList.get(i - 1).generationheight - 1; j < roomList.get(i).generatey; j++){
                        tiles[roomList.get(i).generatex][j] = Tileset.FLOOR;
                    }
                }
                else if (roomList.get(i).generatey + roomList.get(i).generationheight - 1 < roomList.get(i - 1).generatey){
                    for (int j = roomList.get(i).generatey + roomList.get(i).generationheight - 1; j < roomList.get(i - 1).generatey; j++){
                        tiles[roomList.get(i).generatex][j] = Tileset.FLOOR;
                    }
                }
            }

            else {

                if (roomList.get(i - 1).generatey + roomList.get(i - 1).generationheight - 1 >= roomList.get(i).generatey){
                    for (int j = roomList.get(i - 1).generatex + roomList.get(i - 1).generatewidth - 1; j < roomList.get(i).generatex; j++){
                        tiles[j][roomList.get(i).generatey] = Tileset.FLOOR;
                    }
                }

                else if (roomList.get(i).generatey + roomList.get(i).generationheight - 1 >= roomList.get(i - 1).generatey){
                    for (int j = roomList.get(i - 1).generatex + roomList.get(i - 1).generatewidth - 1; j < roomList.get(i).generatey; j++){
                        tiles[j][roomList.get(i - 1).generatey] = Tileset.FLOOR;
                    }
                }

                else {
                    if (roomList.get(i - 1).generatey + roomList.get(i - 1).generationheight - 1 < roomList.get(i).generatey){
                        int max = roomList.get(i).generatex - 1;
                        int min = roomList.get(i - 1).generatex + roomList.get(i - 1).generatewidth;
                        int column = RANDOM.nextInt(max - min - 1) + min + 1;
                        for (int j = min; j <= column; j++){
                            tiles[j][roomList.get(i - 1).generatex] = Tileset.FLOOR;
                        }
                        for (int j = max; j >= column; j--){
                            tiles[j][roomList.get(i).generatex] = Tileset.FLOOR;
                        }
                        for (int j = roomList.get(i - 1).generatey + roomList.get(i - 1).generationheight; j <= roomList.get(i).generatey; j++){
                            tiles[column][j] = Tileset.FLOOR;
                        }
                    }

                    else {
                        int max = roomList.get(i).generatex - 1;
                        int min = roomList.get(i - 1).generatex + roomList.get(i - 1).generatewidth;
                        int column = RANDOM.nextInt(max - min - 1) + min + 1;
                        for (int j = min; j <= column; j++){
                            tiles[j][roomList.get(i - 1).generatey] = Tileset.FLOOR;
                        }
                        for (int j = max; j >= column; j--){
                            tiles[j][roomList.get(i).generatey] = Tileset.FLOOR;
                        }
                        for (int j = roomList.get(i).generatey + roomList.get(i).generationheight; j <= roomList.get(i - 1).generatey; j++){
                            tiles[column][j] = Tileset.FLOOR;
                        }
                    }
                }
            }
        }
    }

    public static class Room implements Comparable<Room> {
        int generatewidth, generationheight, generatex, generatey;

        public Room(int generatewidth, int generationheight, int generatex, int generatey) {
            this.generatewidth = generatewidth;
            this.generationheight = generationheight;
            this.generatex = generatex;
            this.generatey = generatey;
        }
        @Override
        public int compareTo(Room o) {
            return generatex - o.generatex;
        }
    }

    private void GenerateFull(TETile[][] tiles){
        int cnt = 0;
        while(cnt < Max_num){
            int generatewidth = RANDOM.nextInt(17) + 3;
            int generationheight = RANDOM.nextInt(17) + 3;
            int generatex = randomInt((int) Math.round(WIDTH * 0.04), (int) Math.round((WIDTH - generatewidth) * 0.96));
            int generatey = randomInt((int) Math.round(HEIGHT * 0.04), (int) Math.round((HEIGHT - generationheight) * 0.96));

            if (judge(tiles, generatex, generatey, generatewidth, generationheight)){
                System.out.println(cnt);
                cnt += generatewidth * generationheight;
                draw(tiles, generatex, generatey, generatewidth, generationheight);
                roomList.add(new Room(generatewidth, generationheight, generatex, generatey));
            }
        }
    }

    boolean judge(TETile[][] tiles, int x, int y, int w, int h){
        if (x < 0 || y < 0 || x + w > WIDTH || y + h > HEIGHT) {
            return false;
        }        for (int i = 0; i < w; i += 1) {
            for (int j = 0; j < h; j += 1) {
                if (tiles[x + i][y + j] != Tileset.NOTHING) return false;
            }
        }
        return true;
    }

    private void draw(TETile[][] tiles, int x, int y, int w, int h){
        for (int i = 0; i < w; i += 1) {
            for (int j = 0; j < h; j += 1) {
                tiles[x + i][y + j] = Tileset.FLOOR;
            }
        }
    }

    private void drawWall(TETile[][] tiles) {
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                if (tiles[i][j] == Tileset.NOTHING) {
                    boolean hasFloor = false;
                    if (j + 1 < HEIGHT && tiles[i][j + 1] == Tileset.FLOOR) {
                        hasFloor = true;
                    }
                    else if (j - 1 >= 0 && tiles[i][j - 1] == Tileset.FLOOR) {
                        hasFloor = true;
                    }
                    else if (i + 1 < WIDTH && tiles[i + 1][j] == Tileset.FLOOR) {
                        hasFloor = true;
                    }
                    else if (i - 1 >= 0 && tiles[i - 1][j] == Tileset.FLOOR) {
                        hasFloor = true;
                    }
                    if (hasFloor) {
                        tiles[i][j] = Tileset.WALL;
                    }
                }
            }
        }
    }
/*
    public static void main(String[] args) {
        new GenerateWorld(10);
    }
*/

}
