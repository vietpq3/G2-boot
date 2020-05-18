package utils;

import constant.XOConstant;

public class XOUtils {
	public static void resetBoard(String[][] board) {
		for (int i = 0; i < XOConstant.SIZE; i++) {
			for (int j = 0; j < XOConstant.SIZE; j++) {
				board[i][j] = null;
			}
		}
	}
}
