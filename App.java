import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;                   // widht of frame
        int boardHeight = boardWidth;           //height of frame

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);                 //use to visible our frame
        frame.setSize(boardWidth, boardHeight);     //give height and width i.e 600px
        frame.setLocationRelativeTo(null);          //use to set our window at center
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}
