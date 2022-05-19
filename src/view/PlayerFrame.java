package view;

import controller.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerFrame extends JFrame{


        private String name;
        private String password;
        ChessGameFrame chessGameFrame;
        private Player player;
        private int width=300;
        private int height=400;

    public PlayerFrame() {
        setTitle("Welcome To ChessWorld"); //设置标题
        setSize(300,400);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        //my design

        setLayout(null);
        addPayer();

    }


    private void addPayer() {
        JButton button = new JButton("");
        button.setLocation(width, height/ 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog(this, " Path Form：");
        });
    }

}


