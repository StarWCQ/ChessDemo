package view;

import controller.GameController;
import model.ChessColor;
import musicplay.MusicPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    public static Chessboard chessboard;
    //my design
    JLabel statusLabel;
    //ImageIcon background;
//    MusicPlayer music=new MusicPlayer("./music.wav");
//
//    public MusicPlayer getMusic() {
//        return music;
//    }

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        //my design

        setLayout(null);

        addBackground();
        addLabel();//先创建label，然后在Chessboard 里面赋值
        addChessboard();
        //addLabel();
        addRestartButton();
        addLoadButton();
        addSaveButton();


    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        chessboard.setStatusLabel(statusLabel);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
        repaint();
    }

    /**
     * 在游戏面板中添加标签
     * !!!自己写实现表述黑方还是白方行棋
     */
    private void addLabel() {
        statusLabel = new JLabel("Current: WHITE");
        statusLabel.setLocation(HEIGTH, HEIGTH / 8);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
        repaint();
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */



//    private void addRestartButton() {
//        JButton button = new JButton("Restart Game");
//        button.addActionListener((e) ->JOptionPane.showMessageDialog(this, "Do you determine to restart"));
//        gameController.initialGame();
//        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//    }
    private void addRestartButton() {
        JButton button = new JButton("Restart Game");

        button.addActionListener(e ->{
                //JOptionPane.showMessageDialog(this, "Do you determine to restart")
            //restart the game
            int result=JOptionPane.showConfirmDialog(button,"Are you sure you want to restart the game?");
                if (result==0){gameController.initialGame();}
        }
        );

        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            gameController.loadGameFromFile(path);
        });
    }

    private void addSaveButton() {
        //?需要在这里调用方法将棋盘储存成字符串
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog(this, " Path Form：");
            gameController.saveGame(path);
        });
    }

//    public void addBackground(){
//        ImagePanel imagePanel=new ImagePanel("./images/background.png");
//        imagePanel.paintComponent();
//    }
    public void addBackground() {


//        //String path = "C:\\User\\王常青\\Desktop\\期末\\background.png";
//        String path = "C:/Users/王常青/Desktop/期末/ChessDemo/images/background.png";
////背景图片
//         ImageIcon background = new ImageIcon(path) ;
////把背景图片显示在--个标签里面
//        JLabel label = new JLabel(background) ;
////把标签的大小位置设置为图片刚好填充整个面板
//        label. setBounds(  0, 0 ,this. getWidth(),this . getHeight());
////把内容窗格转化为JPaneL，否则不能用方法setOpaque()来使内容窗格透明
//        JPanel imagePanel = (JPanel) this. getContentPane();
//        imagePanel. setOpaque(false);
////把背景图片添加到分层窗格的最底层作为背景
//        this. getLayeredPane () . add(label);

//        ImageIcon background = new ImageIcon("./images/background.png");
//        //将背景图进行压缩，一般如果你想显示一整张图片，就得把大小设置跟窗口一样
//        Image image = background.getImage();
//        Image smallImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST);
//        ImageIcon backgrounds = new ImageIcon(smallImage);
//
//        //将图片添加到JLabel标签
//        JLabel jlabel = new JLabel(backgrounds);
//        //设置标签的大小
//        jlabel.setBounds(0,0, getWidth(),getHeight() );
//        //将图片添加到窗口
//        add(jlabel);

    }


}





