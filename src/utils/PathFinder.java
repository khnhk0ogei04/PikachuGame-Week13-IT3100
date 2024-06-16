package utils;

public class PathFinder {
    private static final int CONST_VALUE = -1; // Default value for empty cells
    private int[][] matrix;
    private int[][] extendedMatrix;

    public PathFinder(int[][] matrix) {
        this.matrix = matrix;
        this.extendedMatrix = extendMatrix(matrix);
    }

    private int[][] extendMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] extendedMatrix = new int[rows + 2][cols + 2]; // Extend the matrix 2 rows, 2 cols with indexs 0, n + 1
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, extendedMatrix[i + 1], 1, cols);
        }
        for (int i = 0; i < rows + 2; i++) {
            for (int j = 0; j < cols + 2; j++) {
                if (i == 0 || i == rows + 1 || j == 0 || j == cols + 1) {
                    extendedMatrix[i][j] = CONST_VALUE;
                }
            }
        }
        return extendedMatrix;
    }
    // Case 1: 2 buttons are located in the same row or column
    private boolean checkLineX(int y1, int y2, int x) {
        int minCol = Math.min(y1, y2);
        int maxCol = Math.max(y1, y2);
        for (int y = minCol + 1; y < maxCol; y++) {
            if (extendedMatrix[x][y] != CONST_VALUE) {
                return false;
            }
        }
        return true;
    }

    private boolean checkLineY(int x1, int x2, int y) {
        int minRow = Math.min(x1, x2);
        int maxRow = Math.max(x1, x2);
        for (int x = minRow + 1; x < maxRow; x++) {
            if (extendedMatrix[x][y] != CONST_VALUE) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRectX(int x1, int y1, int x2, int y2) {
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);

        for (int y = minY; y <= maxY; y++) {
            // Check the horizontal line from x1 to x2 at the current y
            if ((y == y1 || extendedMatrix[x1][y] == CONST_VALUE) &&
                (y == y2 || extendedMatrix[x2][y] == CONST_VALUE) &&
                checkLineY(x1, x2, y) && checkLineX(y1, y, x1) && checkLineX(y, y2, x2)) {
                System.out.println("Valid path found for checkRectX between (" + x1 + ", " + y1 + ") and (" + x2 + ", " + y2 + ") via column " + y);
                return true;
            }
        }
        return false;
    }

    private boolean checkRectY(int x1, int y1, int x2, int y2) {
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);

        for (int x = minX; x <= maxX; x++) {
            // Check the vertical line from y1 to y2 at the current x
            if ((x == x1 || extendedMatrix[x][y1] == CONST_VALUE) &&
                (x == x2 || extendedMatrix[x][y2] == CONST_VALUE) &&
                checkLineX(y1, y2, x) && checkLineY(x1, x, y1) && checkLineY(x, x2, y2)) {
                System.out.println("Valid path found for checkRectY between (" + x1 + ", " + y1 + ") and (" + x2 + ", " + y2 + ") via row " + x);
                return true;
            }
        }
        return false;
    }



    private boolean checkMoreLineX(int x1, int y1, int x2, int y2, int type) {
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);

        int y = maxY + type;
        if (type == -1) {
            y = minY + type;
        }

        if ((extendedMatrix[x1][maxY] == CONST_VALUE || y1 == y2)
                && checkLineX(y1, y2, x1)) {
            while (y >= 0 && y < extendedMatrix[0].length && extendedMatrix[x1][y] == CONST_VALUE) {
                if (extendedMatrix[x2][y] != CONST_VALUE && checkLineY(x1, x2, y) && extendedMatrix[x2][y] == CONST_VALUE) {
                    System.out.println("checkMoreLineX: true for (" + x1 + ", " + y1 + ") -> (" + x1 + ", " + y + ") -> (" + x2 + ", " + y + ") -> (" + x2 + ", " + y2 + ")");
                    return true;
                }
                y += type;
            }
        }
        return false;
    }

    private boolean checkMoreLineY(int x1, int y1, int x2, int y2, int type) {
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);

        int x = maxX + type;
        if (type == -1) {
            x = minX + type;
        }

        if ((extendedMatrix[maxX][y1] == CONST_VALUE || x1 == x2)
                && checkLineY(x1, x2, y1)) {
            while (x >= 0 && x < extendedMatrix.length && extendedMatrix[x][y1] == CONST_VALUE) {
                if (extendedMatrix[x][y2] != CONST_VALUE && checkLineX(y1, y2, x) && extendedMatrix[x][y2] == CONST_VALUE) {
                    System.out.println("checkMoreLineY: true for (" + x1 + ", " + y1 + ") -> (" + x + ", " + y1 + ") -> (" + x + ", " + y2 + ") -> (" + x2 + ", " + y2 + ")");
                    return true;
                }
                x += type;
            }
        }
        return false;
    }



    private boolean checkTwoTurns(int x1, int y1, int x2, int y2) {
        for (int x = 0; x < extendedMatrix.length; x++) {
            if (extendedMatrix[x][y1] == CONST_VALUE && extendedMatrix[x][y2] == CONST_VALUE) {
                if (checkLineY(x1, x, y1) && checkLineX(y1, y2, x) && checkLineY(x, x2, y2)) {
                    System.out.println("checkTwoTurns: true for (" + x1 + ", " + y1 + ") -> (" + x + ", " + y1 + ") -> (" + x + ", " + y2 + ") -> (" + x2 + ", " + y2 + ")");
                    return true;
                }
            }
        }
        for (int y = 0; y < extendedMatrix[0].length; y++) {
            if (extendedMatrix[x1][y] == CONST_VALUE && extendedMatrix[x2][y] == CONST_VALUE) {
                if (checkLineX(y1, y, x1) && checkLineY(x1, x2, y) && checkLineX(y, y2, x2)) {
                    System.out.println("checkTwoTurns: true for (" + x1 + ", " + y1 + ") -> (" + x1 + ", " + y + ") -> (" + x2 + ", " + y + ") -> (" + x2 + ", " + y2 + ")");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPathAvailable(int x1, int y1, int x2, int y2) {
        x1 += 1;
        y1 += 1;
        x2 += 1;
        y2 += 1;

        if (Math.abs(x2 - x1) > 2 || Math.abs(y2 - y1) > 2) {
            if (isCellBlocked(x1, y1) || isCellBlocked(x2, y2)) {
                return false;
            }
        }

        if (extendedMatrix[x1][y1] != CONST_VALUE && extendedMatrix[x1][y1] == extendedMatrix[x2][y2] && !((x1 == x2) && (y1 == y2))) {
            if (x1 == x2 && checkLineX(y1, y2, x1)) {
                System.out.println("Direct line X: true for (" + x1 + ", " + y1 + ") -> (" + x2 + ", " + y2 + ")");
                return true;
            }
            if (y1 == y2 && checkLineY(x1, x2, y1)) {
                System.out.println("Direct line Y: true for (" + x1 + ", " + y1 + ") -> (" + x2 + ", " + y2 + ")");
                return true;
            }
            if (checkRectX(x1, y1, x2, y2)) {
                System.out.println("Rectangular X: true for (" + x1 + ", " + y1 + ") -> (" + x2 + ", " + y2 + ")");
                return true;
            }
            if (checkRectY(x1, y1, x2, y2)) {
                System.out.println("Rectangular Y: true for (" + x1 + ", " + y1 + ") -> (" + x2 + ", " + y2 + ")");
                return true;
            }
            if (checkMoreLineX(x1, y1, x2, y2, 1)) {
                System.out.println("More line X +1: true for (" + x1 + ", " + y1 + ") -> (" + x2 + ", " + y2 + ")");
                return true;
            }
            if (checkMoreLineX(x1, y1, x2, y2, -1)) {
                System.out.println("More line X -1: true for (" + x1 + ", " + y1 + ") -> (" + x2 + ", " + y2 + ")");
                return true;
            }
            if (checkMoreLineY(x1, y1, x2, y2, 1)) {
                System.out.println("More line Y +1: true for (" + x1 + ", " + y1 + ") -> (" + x2 + ", " + y2 + ")");
                return true;
            }
            if (checkMoreLineY(x1, y1, x2, y2, -1)) {
                System.out.println("More line Y -1: true for (" + x1 + ", " + y1 + ") -> (" + x2 + ", " + y2 + ")");
                return true;
            }
            if (checkTwoTurns(x1, y1, x2, y2)) {
                System.out.println("Two turns: true for (" + x1 + ", " + y1 + ") -> (" + x2 + ", " + y2 + ")");
                return true;
            }
        }

        return false;
    }
    private boolean isCellBlocked(int x, int y) {
    	return extendedMatrix[x-1][y] != CONST_VALUE && 
    	           extendedMatrix[x+1][y] != CONST_VALUE && 
    	           extendedMatrix[x][y-1] != CONST_VALUE && 
    	           extendedMatrix[x][y+1] != CONST_VALUE;   
    }

}
