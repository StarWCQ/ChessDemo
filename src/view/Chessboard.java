package view;


import controller.Step;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//import static model.ChessComponent.BACKGROUND_COLORS;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }

    public ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    //my design
    private JLabel statusLabel;

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }



    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
//        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

      //  initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initialGame();
//        initRookOnBoard(0, 0, ChessColor.BLACK);
//        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
//        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
//        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
//        initQueenOnBoard(0,4,ChessColor.BLACK);
//        initQueenOnBoard(CHESSBOARD_SIZE-1,4,ChessColor.WHITE);
//        initKingOnBoard(0,3,ChessColor.BLACK);
//        initKingOnBoard(CHESSBOARD_SIZE-1,3,ChessColor.WHITE);
//        initBishopOnBoard(0,2,ChessColor.BLACK);
//        initBishopOnBoard(0,5,ChessColor.BLACK);
//        initBishopOnBoard(CHESSBOARD_SIZE-1,2,ChessColor.WHITE);
//        initBishopOnBoard(CHESSBOARD_SIZE-1,5,ChessColor.WHITE);
//        initKnightOnBoard(0,1,ChessColor.BLACK);
//        initKnightOnBoard(0,6,ChessColor.BLACK);
//        initKnightOnBoard(CHESSBOARD_SIZE-1,1,ChessColor.WHITE);
//        initKnightOnBoard(CHESSBOARD_SIZE-1,6,ChessColor.WHITE);
//        for(int i=0;i<8;i++){
//        initPawnOnBoard(1,i,ChessColor.BLACK);
//        initPawnOnBoard(CHESSBOARD_SIZE-2,i,ChessColor.WHITE);}
    }


    //w,write
    //棋盘上有很多棋子，当棋盘做repaint操作的时候，
    public void initialGame(){
        initiateEmptyChessboard();
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initQueenOnBoard(0,4,ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE-1,4,ChessColor.WHITE);
        initKingOnBoard(0,3,ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE-1,3,ChessColor.WHITE);
        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,5,ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE-1,2,ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE-1,5,ChessColor.WHITE);
        initKnightOnBoard(0,1,ChessColor.BLACK);
        initKnightOnBoard(0,6,ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE-1,1,ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE-1,6,ChessColor.WHITE);
        for(int i=0;i<8;i++){
            initPawnOnBoard(1,i,ChessColor.BLACK);
            initPawnOnBoard(CHESSBOARD_SIZE-2,i,ChessColor.WHITE);}
        repaint();
    }

//    //w,write
//    public void removeChessboard(){
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (chessComponents[i][j]!=null){
//                    remove(this.chessComponents[i][j]);
//                }
//            }
//
//        }
//    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    //w棋子添加到棋盘的操作
    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }
      //w吃子
    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if(getCurrentColor()==ChessColor.BLACK){
            statusLabel.setText("Current: WHITE");
        }else {statusLabel.setText("Current: BLACK");}

        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        //在图片展示的时候，执行了交换操作
        chess1.repaint();
        chess2.repaint();
    }
//    public void determineCurrentColor(ChessColor a){
//        statusLabel.setText("Current: "+a.toString());
//
//    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {

        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        repaint();
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }



    //绘制每个棋盘
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        //my write
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(chessData.get(i).charAt(j)=='R') {initRookOnBoard(i,j,ChessColor.BLACK);}
                if(chessData.get(i).charAt(j)=='N') {initKnightOnBoard(i,j,ChessColor.BLACK) ;}
                if(chessData.get(i).charAt(j)=='B') {initBishopOnBoard(i,j,ChessColor.BLACK);}
                if(chessData.get(i).charAt(j)=='P') {initPawnOnBoard(i,j,ChessColor.BLACK);}
                if(chessData.get(i).charAt(j)=='K') {initKingOnBoard(i,j,ChessColor.BLACK);}
                if(chessData.get(i).charAt(j)=='Q') {initQueenOnBoard(i,j,ChessColor.BLACK);}
                if(chessData.get(i).charAt(j)=='r') {initRookOnBoard(i,j,ChessColor.WHITE);}
                if(chessData.get(i).charAt(j)=='n')  {initKnightOnBoard(i,j,ChessColor.WHITE) ;}
                if(chessData.get(i).charAt(j)=='b') {initBishopOnBoard(i,j,ChessColor.WHITE);}
                if(chessData.get(i).charAt(j)=='p') {initPawnOnBoard(i,j,ChessColor.WHITE);}
                if(chessData.get(i).charAt(j)=='k') {initKingOnBoard(i,j,ChessColor.WHITE);}
                if(chessData.get(i).charAt(j)=='q'){initQueenOnBoard(i,j,ChessColor.WHITE);}
            }
        }
        if(chessData.get(8).charAt(0)=='w'){
            currentColor=ChessColor.WHITE;
        }else {currentColor=ChessColor.BLACK;}
        repaint();

        chessData.forEach(System.out::println);
    }
    public boolean checkLoadGame(List<String>chessData){
        boolean currentP=true;

        boolean chess=true;
        if(chessData.size()!=9){
            return false;
        }else{
            if (chessData.get(8).charAt(0)!='w'&&chessData.get(8).charAt(0)!='b'){currentP=false;}
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessData.get(i).charAt(j)!='K'&&chessData.get(i).charAt(j)!='Q'
                            &&chessData.get(i).charAt(j)!='B'&&chessData.get(i).charAt(j)!='P'
                            &&chessData.get(i).charAt(j)!='N'&&chessData.get(i).charAt(j)!='R'
                            &&chessData.get(i).charAt(j)!='k'&&chessData.get(i).charAt(j)!='q'
                            &&chessData.get(i).charAt(j)!='b'&&chessData.get(i).charAt(j)!='p'
                            &&chessData.get(i).charAt(j)!='n'&&chessData.get(i).charAt(j)!='r'
                            &&chessData.get(i).charAt(j)!='_'){
                        chess=false;
                        break;
                    }
                }

            }
        }
        if (chess&&currentP){return  false;
        }else {return true;}
    }
    public String saveGame(){
        StringBuilder out=new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j]instanceof RookChessComponent){
                    if (chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                        out.append('R');
                    }else {out.append('r');}
                }
                if (chessComponents[i][j]instanceof BishopChessComponent){
                    if (chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                        out.append('B');
                    }else {out.append('b');}
                }
                if (chessComponents[i][j]instanceof KnightChessComponent){
                    if (chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                        out.append('N');
                    }else {out.append('n');}
                }
                if (chessComponents[i][j]instanceof KingChessComponent){
                    if (chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                        out.append('K');
                    }else {out.append('k');}
                }
                if (chessComponents[i][j]instanceof QueenChessComponent){
                    if (chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                        out.append('Q');
                    }else {out.append('q');}
                }
                if (chessComponents[i][j]instanceof PawnChessComponent){
                    if (chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                        out.append('P');
                    }else {out.append('p');}
                }if (chessComponents[i][j]instanceof EmptySlotComponent){
                    out.append('_');

                }
            }
            out.append("\n");
        }
        if(currentColor==ChessColor.WHITE){out.append('w');}else {out.append('b');}
        return out.toString();
    }//

    //add queen
    private void initQueenOnBoard(int row, int col, ChessColor color) {//add queen
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initBishopOnBoard(int row, int col, ChessColor color) {//add bishop
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initKingOnBoard(int row, int col, ChessColor color) {//add bishop
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initKnightOnBoard(int row, int col, ChessColor color) {//add bishop
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initPawnOnBoard(int row, int col, ChessColor color) {//add bishop
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void clearChess(int x,int y,ChessComponent chessComponent){
//        System.out.printf("%d %d\n",x,y);
        remove(chessComponents[x][y]);
        chessComponents[x][y] = new EmptySlotComponent(new ChessboardPoint(x,y),
                calculatePoint(x,y),
                chessComponent.getClickController(),
                chessComponent.getsize());
        putChessOnBoard(chessComponents[x][y]);
        chessComponent.gridColor = (x + y) % 2 == 0 ? Color.WHITE : Color.BLACK;
//        System.out.println(chessComponents[x][y].getChessColor());
        chessComponents[x][y].repaint();
    }


}



