package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;


/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void fillWithRandomTiles(TETile[][] tiles, int x, int y, int size) {
        int height = tiles[0].length;
        int width = tiles.length;
        int yy = y - size - 1;
        TETile randomTerrain = randomTile();
        if (size % 2 == 0) {
            for (int i = 0; i < 2 * size; i += 1) {             // i 表示画几行
                if (i < size) {
                    for (int j = 0; j < size + i * 2; j += 1) { // j表示每行画几列
                        tiles[yy + size + j - i - 1][x + i] = randomTerrain;
                    }
                } else {
                    for (int j = 0; j < size + (i - size) * 2; j += 1) {
                        tiles[yy + size + j - (i - size) - 1][x + 2 * size - 1 - (i - size)] = randomTerrain;
                    }
                }
            }
        }
        else {
            for (int i = 0; i < 2 * size; i += 1) {             // i 表示画几行
                if (i < size) {
                    for (int j = 0; j < size + i * 2; j += 1) { // j表示每行画几列
                        tiles[y + size + j - i - 1][x + i] = randomTerrain;
                    }
                } else {
                    for (int j = 0; j < size + (i - size) * 2; j += 1) {
                        tiles[y + size + j - (i - size) - 1][x + 2 * size - 1 - (i - size)] = randomTerrain;
                    }
                }
            }
        }
    }

    public static void GenerateTesselation(TETile[][] tiles, int x, int y, int size){
        fillWithRandomTiles(tiles, x - 2 * size + 1, y - 1, size);
        fillWithRandomTiles(tiles, x - 4 * size + 1, y - 1, size);
        fillWithRandomTiles(tiles, x - 6 * size + 1, y - 1, size);
        fillWithRandomTiles(tiles, x - 8 * size + 1, y - 1, size);
        fillWithRandomTiles(tiles, x - 10 * size + 1, y - 1, size);

        fillWithRandomTiles(tiles, x - 3 * size + 1, y - 1 - (2 * size - 1), size);
        fillWithRandomTiles(tiles, x - 5 * size + 1, y - 1 - (2 * size - 1), size);
        fillWithRandomTiles(tiles, x - 7 * size + 1, y - 1 - (2 * size - 1), size);
        fillWithRandomTiles(tiles, x - 9 * size + 1, y - 1 - (2 * size - 1), size);
        fillWithRandomTiles(tiles, x - 3 * size + 1, y - 1 + (2 * size - 1), size);
        fillWithRandomTiles(tiles, x - 5 * size + 1, y - 1 + (2 * size - 1), size);
        fillWithRandomTiles(tiles, x - 7 * size + 1, y - 1 + (2 * size - 1), size);
        fillWithRandomTiles(tiles, x - 9 * size + 1, y - 1 + (2 * size - 1), size);

        fillWithRandomTiles(tiles, x - 4 * size + 1, y + 1 - (4 * size ), size);
        fillWithRandomTiles(tiles, x - 6 * size + 1, y + 1 - (4 * size ), size);
        fillWithRandomTiles(tiles, x - 8 * size + 1, y + 1 - (4 * size ), size);
        fillWithRandomTiles(tiles, x - 4 * size + 1, y - 3 + (4 * size ), size);
        fillWithRandomTiles(tiles, x - 6 * size + 1, y - 3 + (4 * size ), size);
        fillWithRandomTiles(tiles, x - 8 * size + 1, y - 3 + (4 * size ), size);

    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.MOUNTAIN;
            default: return Tileset.SAND;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                randomTiles[i][j] = Tileset.NOTHING;
            }
        }
        GenerateTesselation(randomTiles,55, 50, 3);

        ter.renderFrame(randomTiles);
    }
}
