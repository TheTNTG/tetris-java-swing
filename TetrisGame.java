package simple.tetris;

import java.util.Random;

public class TetrisGame implements java.io.Serializable {
    private Grid<Character> board;
    private Pieces currentPiece;
    private int score;
    private boolean isGameOver;
    private Pieces nextPiece;

    /**
     * Constructor: Initializes the board and the piece queue.
     */
    public TetrisGame() {
        board = new Grid<>(10, 20);
        score = 0;
        isGameOver = false;
        
        // 1. Generate the very first nextPiece so the queue isn't empty
        nextPiece = createRandomPiece();
        // 2. Move that piece into the current slot and generate the next preview
        spawnPiece();
    }

    /**
     * Factory Method: Creates a new piece at the default preview coordinates.
     */
    private Pieces createRandomPiece() {
        Random rand = new Random();
        int pick = rand.nextInt(7);
        // Start them at 0,0 for the preview panel; spawnPiece() will center them.
        switch (pick) {
            case 0: return new IPiece(0, 0);
            case 1: return new JPiece(0, 0);
            case 2: return new LPiece(0, 0);
            case 3: return new OPiece(0, 0);
            case 4: return new SPiece(0, 0);
            case 5: return new TPiece(0, 0);
            case 6: return new ZPiece(0, 0);
            default: return new TPiece(0, 0);
        }
    }

    /**
     * Transitions the nextPiece to currentPiece and centers it.
     */
    public Pieces spawnPiece() {
        // Move the queued piece to the active slot
        currentPiece = nextPiece;
        
        // Center the new active piece on the board (x=4, y=0)
        if (currentPiece != null) {
            currentPiece.movePiece(4, 0);
        }

        // Generate the NEW piece for the preview box
        nextPiece = createRandomPiece();

        // Check if the newly spawned piece immediately hits a block
        if (chkCollision(0, 0)) {
            isGameOver = true;
        }

        return currentPiece;
    }

    private boolean chkCollision(int dx, int dy) {
        if (currentPiece == null) return false;
        
        int[][] shape = currentPiece.getShape();
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) {
                    int targetX = currentPiece.getX() + col + dx;
                    int targetY = currentPiece.getY() + row + dy;

                    // Bound checks
                    if (targetX < 0 || targetX >= 10 || targetY >= 20) return true;
                    
                    // Board check (if within Y bounds)
                    if (targetY >= 0 && board.getT(targetX, targetY) != null) return true;
                }
            }
        }
        return false;
    }

    public void update() {
        if (isGameOver) return;

        if (chkCollision(0, 1)) {
            lockPiece();
            clearLines();
            spawnPiece();
        } else {
            currentPiece.movePiece(0, 1);
        }
    }

    public void lockPiece() {
        int[][] shape = currentPiece.getShape();
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == 1) {
                    int realX = currentPiece.getX() + col;
                    int realY = currentPiece.getY() + row;
                    if (realX >= 0 && realX < 10 && realY >= 0 && realY < 20) {
                        board.setT(realX, realY, '#');
                    }
                }
            }
        }
    }

    public void clearLines() {
        int linesThisTurn = 0;
        for (int y = 19; y >= 0; y--) {
            boolean isFull = true;
            for (int x = 0; x < 10; x++) {
                if (board.getT(x, y) == null) {
                    isFull = false;
                    break;
                }
            }

            if (isFull) {
                linesThisTurn++;
                // Shift down
                for (int shiftY = y; shiftY > 0; shiftY--) {
                    for (int x = 0; x < 10; x++) {
                        board.setT(x, shiftY, board.getT(x, shiftY - 1));
                    }
                }
                // Clear top
                for (int x = 0; x < 10; x++) board.setT(x, 0, null);
                y++; // Re-check the same Y index
            }
        }
        
        if (linesThisTurn > 0) {
            this.score += currentPiece.getClearBonus(linesThisTurn);
        }
    }

    public void attemptRotate() {
        int[][] oldShape = currentPiece.getShape();
        currentPiece.rotate();
        if (chkCollision(0, 0)) {
            currentPiece.setShape(oldShape);
        }
    }

    public void hardDrop() {
        while (!chkCollision(0, 1)) {
            currentPiece.movePiece(0, 1);
        }
        lockPiece();
        clearLines();
        spawnPiece();
    }

    public int getGhostY() {
        if (currentPiece == null) return 0;
        int ghostY = currentPiece.getY();
        while (!chkCollision(0, (ghostY - currentPiece.getY()) + 1)) {
            ghostY++;
        }
        return ghostY;
    }
    
    public void attemptMove(int dx, int dy) {
    // This uses your existing chkCollision logic
    if (!chkCollision(dx, dy)) {
        currentPiece.movePiece(dx, dy);
    }
}

    
    private boolean isPaused = false;

public void togglePause() {
    isPaused = !isPaused;
}

public boolean isPaused() {
    return isPaused;
}
    // Getters
    public Pieces getNextPiece() { return nextPiece; }
    public int getScore() { return score; }
    public boolean getGameState() { return isGameOver; }
    public Pieces getCurrentPiece() { return currentPiece; }
    public Grid getBoard() { return board; }
}