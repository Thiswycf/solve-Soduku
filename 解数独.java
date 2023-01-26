import java.util.*;

/*
 * 项目：解数独
 * 时间：2023年1月23日20:00:34
 * 状态：成功
 * 
 * 输入：空用小数点代替的、一定有解的9×9初始模板
 * 期望输出：题目的一个解
 */

class Solution {
    private char[][] board;
    private boolean[][] init, // 初始占用
            row, // 行互斥
            col, // 列互斥
            map; // 九宫格

    private int getIndex(int x, int y) {// 得到单个九宫格的下标
        if (x < 3) {
            if (y < 3)
                return 0;
            if (y < 6)
                return 1;
            return 2;
        }
        if (x < 6) {
            if (y < 3)
                return 3;
            if (y < 6)
                return 4;
            return 5;
        }
        if (y < 3)
            return 6;
        if (y < 6)
            return 7;
        return 8;
    }

    private void opMap(int x, int y, int i, boolean sign) {// 设置单个九宫格为sign
        map[getIndex(x, y)][i] = sign;
    }

    private boolean ok(int x, int y, int fill) {
        return !row[x][fill] && !col[y][fill] && !map[getIndex(x, y)][fill];
    }

    private boolean dfs(int index, int fill) {
        if (index == 81)
            return true;
        int x = index / 9, y = index % 9;
        index++;
        if (init[x][y])
            return dfs(index, fill);// 初始就有，直接跳过
        if (!ok(x, y, fill))
            return false;

        row[x][fill] = true;
        col[y][fill] = true;
        opMap(x, y, fill, true);
        board[x][y] = (char) ('0' + fill);// 不影响接下来的dfs，可以不还原
        for (int i = 1; i < 10; i++) {
            if (dfs(index, i))
                return true;
        }

        row[x][fill] = false;// 剪枝
        col[y][fill] = false;
        opMap(x, y, fill, false);
        return false;
    }

    public void solveSudoku(char[][] board) {
        row = new boolean[9][10];
        col = new boolean[9][10];
        map = new boolean[9][10];
        init = new boolean[9][9];
        this.board = board;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.')
                    init[i][j] = true;
            }
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (!init[x][y])
                    continue;
                int i = board[x][y] - '0';
                row[x][i] = true;
                col[y][i] = true;
                opMap(x, y, i, true);
            }
        }
        for (int i = 1; i < 10; i++) {
            if (dfs(0, i))
                return;
        }
    }
}

public class 解数独 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // while(true) {
            System.out.println();
            String[] ss={
            "8........",
            "..36.....",
            ".7..9.2..",
            ".5...7...",
            "....457..",
            "...1...3.",
            "..1....68",
            "..85...1.",
            ".9....4.."};
        
            char[][] board = new char[9][]; // 空用小数点代替
        
            // for (int i = 0; i < 9; i++) // 直接输入
            //     board[i] = sc.next().toCharArray();
        
            for(int i=0;i<9;i++) board[i]=ss[i].toCharArray();
        
            new Solution().solveSudoku(board);
            for (int i = 0; i < 9; i++) System.out.println(new String(board[i]));
        // }

        // sc.close();
    }
}