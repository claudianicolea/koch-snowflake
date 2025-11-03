import javax.swing.*;
import java.awt.*;

class KochCanvas extends JComponent {

    private final int padding;
    private int currentDepth = 1;
    private int maxDepth;

    public KochCanvas(int padding) {
        this.padding = padding;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    // acts like the 'main' function for a Graphics g
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // clears the background and prepares the component
        drawKochSnowflake(g);
    }

    // 'main' function to animate the koch snowflake
    public void animateKochSnowflake(int secondsDelay) {
        Timer t = new Timer(secondsDelay * 1000, e -> {
            currentDepth++;
            if (currentDepth > maxDepth) {
                currentDepth = 1; // reset to start over
            }
            repaint();
        });
        t.start();
    }

    // 'main' function to draw the koch snowflake
    public void drawKochSnowflake(Graphics g) {
        int height = getHeight() - getHeight() / 4; // height of the triangle, adjusted to fit 3/4 of the canvas
        int xStart = getWidth() / 2 - height / 2; // starting x-coordinate for the triangle, to center the drawing horizontally

        // draw triangle
        drawKochLine(g, currentDepth, xStart + padding, height - padding, xStart + height - padding, height - padding); // side 1: bottom left to bottom right
        drawKochLine(g, currentDepth, xStart + height - padding, height - padding, xStart + height / 2, padding); // side 2: bottom right to top
        drawKochLine(g, currentDepth, xStart + height / 2, padding, xStart + padding, height - padding); // side 3: top to bottom left
    }

    // recursive function to draw the koch snowflake
    public void drawKochLine(Graphics g, int depth, int xStart, int yStart, int xFinal, int yFinal) {
        // base case
        if (depth == 1) {
            g.setColor(Color.BLACK);
            g.drawLine(xStart, yStart, xFinal, yFinal);
            return;
        }
        int dX = xFinal - xStart, dY = yFinal - yStart;

        // coordinates for the first third of the line
        int x1 = xStart + dX / 3;
        int y1 = yStart + dY / 3;

        // coordinates for the middle peak
        int x2 = (int) ((double)(xStart + xFinal) / 2 + Math.sqrt(3) * (yStart - yFinal) / 6);
        int y2 = (int) ((double)(yStart + yFinal) / 2 + Math.sqrt(3) * (xFinal - xStart) / 6);

        // coordinates for the last third of the line
        int x3 = xStart + 2 * dX / 3;
        int y3 = yStart + 2 * dY / 3;

        // recursively call the following layers
        drawKochLine(g, depth - 1, xStart, yStart, x1, y1);
        drawKochLine(g, depth - 1, x1, y1, x2, y2);
        drawKochLine(g, depth - 1, x2, y2, x3, y3);
        drawKochLine(g, depth - 1, x3, y3, xFinal, yFinal);
    }
}

public class KochSnowflake {
    static void main() {
        JFrame window = new JFrame();
        int windowWidth = 1000, windowHeight = 1000;

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setBounds(0, 0, windowWidth, windowHeight);

        KochCanvas kochCanvas = new KochCanvas(25);
        kochCanvas.setMaxDepth(7);
        window.getContentPane().add(kochCanvas);

        window.setVisible(true);
        kochCanvas.animateKochSnowflake(1);
    }
}
