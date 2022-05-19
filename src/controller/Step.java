package controller;

import model.ChessColor;
import model.ChessComponent;

import java.util.ArrayList;

public class Step {
    private int sid;
    private ChessComponent chessboardPoint;
    protected ChessColor chessColor;
    private static int stepCnt = 1;
    public Step(ChessComponent chessboardPoint1, ChessColor color){
        this.chessboardPoint=chessboardPoint1;
        this.chessColor = color;
        this.sid = stepCnt++;
    }

    public int getSid() {
        return this.sid;
    }

    public ChessComponent getChessboardPoint() {
        return chessboardPoint;
    }

    public void setChessboardPoint(ChessComponent chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public void setChessColor(ChessColor chessColor) {
        this.chessColor = chessColor;
    }
    public static int getStepCnt(){
        return stepCnt;
    }
    public static void initializeStep(){
        stepCnt = 1;
    }
    public static ArrayList<ChessComponent> qishirecord =new ArrayList<>();//记录每一步棋的起始位置
    public static ArrayList<ChessComponent> zhongzhirecord =new ArrayList<>();//记录每一步棋的终止位置



}