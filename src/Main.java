import view.ChessGameFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
            //Toolkit.getDefaultToolkit().getScreenSize().width, 获得屏幕的宽
            // Toolkit.getDefaultToolkit().getScreenSize().height获得屏幕的高
//            mainFrame.getMusic().play();
//            mainFrame.getMusic().setLoop(true);

            mainFrame.setVisible(true);
        });
    }
}
