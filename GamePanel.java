package simple.tetris;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private TetrisGame game;
    private final int TILE_SIZE = 30; // Size of each square

    public GamePanel(TetrisGame game) {
        this.game = game;
        this.setPreferredSize(new Dimension(300, 600)); 
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 1. IMPROVE QUALITY
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 2. DRAW INDIE GRADIENT BACKGROUND
        GradientPaint gp = new GradientPaint(0, 0, new Color(44, 62, 80), 0, getHeight(), new Color(20, 24, 35));
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // 3. DRAW SUBTLE GRID
        g2d.setColor(new Color(255, 255, 255, 15)); 
        for (int i = 0; i <= 10; i++) g2d.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, getHeight());
        for (int i = 0; i <= 20; i++) g2d.drawLine(0, i * TILE_SIZE, getWidth(), i * TILE_SIZE);

        if (game == null) return;

        // 4. DRAW GHOST PIECE (Landing Hint)
        Pieces p = game.getCurrentPiece();
        if (p != null) {
            drawGhostPiece(g2d, p);
        }

        // 5. DRAW LOCKED BLOCKS (The Board)
        Color lockedGray = new Color(100, 110, 120);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 20; y++) {
                if (game.getBoard().getT(x, y) != null) {
                    drawProfessionalBlock(g2d, x * TILE_SIZE, y * TILE_SIZE, lockedGray);
                }
            }
        }

        // 6. DRAW ACTIVE FALLING PIECE
        if (p != null) {
            int[][] shape = p.getShape();
            for (int r = 0; r < shape.length; r++) {
                for (int c = 0; c < shape[r].length; c++) {
                    if (shape[r][c] == 1) {
                        int drawX = (p.getX() + c) * TILE_SIZE;
                        int drawY = (p.getY() + r) * TILE_SIZE;
                        drawProfessionalBlock(g2d, drawX, drawY, p.getPieceColor());
                    }
                }
            }
        }
        
        // Add this at the very bottom of paintComponent in GamePanel.java
        if (game.isPaused() || game.getGameState()) {
            // 1. Draw a "Blur" overlay (Transparent Black)
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // 2. Prepare Text
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 30));
            FontMetrics fm = g2d.getFontMetrics();

            String msg = game.getGameState() ? "GAME OVER" : "PAUSED";

            // 3. Center the text
            int msgX = (getWidth() - fm.stringWidth(msg)) / 2;
            int msgY = getHeight() / 2;

            // Draw a subtle shadow for the text
            g2d.setColor(new Color(0, 0, 0, 200));
            g2d.drawString(msg, msgX + 2, msgY + 2);

            // Draw main text
            g2d.setColor(game.getGameState() ? Color.RED : Color.YELLOW);
            g2d.drawString(msg, msgX, msgY);

            // 4. Draw "Press P to Resume" or "Score"
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 14));
            String subMsg = game.getGameState() ? "Final Score: " + game.getScore() : "Press 'P' to Resume";
            int subX = (getWidth() - g2d.getFontMetrics().stringWidth(subMsg)) / 2;
            g2d.setColor(Color.WHITE);
            g2d.drawString(subMsg, subX, msgY + 40);
        }
    }

    // Helper to draw the "Ghost" neon outline
    private void drawGhostPiece(Graphics2D g2d, Pieces p) {
        int ghostY = game.getGhostY();
        int[][] shape = p.getShape();
        g2d.setColor(new Color(255, 255, 255, 40)); // Faint neon white
        g2d.setStroke(new BasicStroke(2));
        
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] == 1) {
                    int x = (p.getX() + c) * TILE_SIZE;
                    int y = (ghostY + r) * TILE_SIZE;
                    g2d.drawRoundRect(x + 3, y + 3, TILE_SIZE - 6, TILE_SIZE - 6, 8, 8);
                }
            }
        }
    }

    // Helper method to make blocks look "Modern/Indie"
    private void drawProfessionalBlock(Graphics2D g2d, int x, int y, Color color) {
        // Main block body
        g2d.setColor(color);
        g2d.fillRoundRect(x + 1, y + 1, TILE_SIZE - 2, TILE_SIZE - 2, 8, 8);

        // Glassy Shine (Top half)
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.fillRoundRect(x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE / 2 - 4, 5, 5);
        
        // Subtle dark border
        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.drawRoundRect(x + 1, y + 1, TILE_SIZE - 2, TILE_SIZE - 2, 8, 8);
    }
}