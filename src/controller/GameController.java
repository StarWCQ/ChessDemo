package controller;

import view.Chessboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initialGame() {
        chessboard.initialGame();
    }

    public void saveGame( String path) {
        try {

            File writeName = new File(path); // 相对路径，如果没有则要建立一个新的output.txt文件
            if (!writeName.exists()) {
                writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            }
            FileWriter writer = new FileWriter(writeName);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(chessboard.saveGame());
            out.flush(); // 把缓存区内容压入文件
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
    //

    public boolean checkLoadGame(String path){
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            return chessboard.checkLoadGame(chessData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
