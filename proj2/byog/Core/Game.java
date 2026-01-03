package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public TETile[][] playWithInputString(String s) {
        // 1. 解析输入字符串，提取种子
        long seed = parseSeedFromInput(s);
        // 2. 生成世界瓦片数组
        GenerateWorld worldGenerator = new GenerateWorld(seed);
        return worldGenerator.generateTiles();
    }

    /**
     * 解析输入字符串的种子：
     * - 格式要求：以N开头，S结尾，中间为数字（支持正负，如"N-123S"）；
     * - 异常处理：非法格式默认返回0（可根据需求调整）。
     */
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
