package byog.Core;

import java.io.Serializable;
import byog.TileEngine.TETile;
import java.util.Random;

public class GameState implements Serializable {
    public TETile[][] tiles;
    public int posx;
    public int posy;
    public Random random;
    public GameState(TETile[][] tiles, int posx, int posy, Random random) {
        this.tiles = tiles;
        this.posx = posx;
        this.posy = posy;
        this.random = random;
    }
}