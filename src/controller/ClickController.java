package controller;


import model.*;
import view.Chessboard;
import view.ChessboardPoint;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;
    //my design
    private JLabel statusLabel;

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }


    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        for(int i = 0;i < 8;i++){
            for(int j = 0;j < 8;j++){
                chessboard.chessComponents[i][j].Xcordinate = i;
                chessboard.chessComponents[i][j].Ycordinate = j;
            }
        }
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                ChessComponent tmp = new ChessComponent(chessComponent.getChessboardPoint(),
                        chessComponent.getLocation(),
                        chessComponent.getChessColor(), null
                        , 100
                ){
                    @Override
                    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
                        return false;
                    }
                    @Override
                    public void loadResource() throws IOException {
                    }
                };
                Step.qishirecord.add(tmp);//选中一个后存储，为移动做准备
                first = chessComponent;
                first.repaint();

            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                Step.qishirecord.remove(Step.qishirecord.size()-1);//取消选取时将它remove掉
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();

            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
//                chessboard.determineCurrentColor(chessboard.getCurrentColor());

                Step.zhongzhirecord.add(chessComponent);//成功移动后将终止位置加入
                first.setSelected(false);

                if(ChessComponent.specialJudge[chessComponent.Xcordinate][chessComponent.Ycordinate] == 1){
                    if(first.getChessColor() == ChessColor.BLACK){
                        chessboard.clearChess(first.Xcordinate,chessComponent.Ycordinate,chessComponent);
//                        System.out.printf("%d %d\n",first.Xcordinate,chessComponent.Ycordinate);
                    }
                    else if(first.getChessColor() == ChessColor.WHITE){
                        chessboard.clearChess(first.Xcordinate,chessComponent.Ycordinate,chessComponent);
                    }
                }
                first = null;
            }
            chessComponent.showCanMove();
        }
    }


    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());

    }








    public static ChessComponent findKing(ChessComponent[][] chessComponents){
        ChessComponent king=null;
        int i;
        if (Step.zhongzhirecord.size() > 2) {//实际上这个if应该一直为true
            for (i = Step.zhongzhirecord.size() - 1; i > 0; i--) {
                if (Step.zhongzhirecord.get(Step.zhongzhirecord.size() - 1) instanceof KingChessComponent) {
                    break;
                }
            }
            king=Step.zhongzhirecord.get(i);//king即为国王
        }return king;
    }

    public static ArrayList<ChessComponent> findCaller(ChessComponent[][] chessComponents){//找到将军的棋子caller，因为可能的Runto还没有被移动过去，所以这个方法的caller不是可能移动后的caller
        ArrayList callers=new ArrayList<>();
        ChessComponent king=findKing(chessComponents);
        for (int a=0;a<8;a++){
            for (int b=0;b<8;b++){
                if(chessComponents[a][b].getChessColor()==ChessColor.NONE){
                    continue;
                }else {
                    if(chessComponents[a][b].getChessColor()==king.getChessColor()){
                        continue;
                    }else{
                        if (chessComponents[a][b].canMoveTo(chessComponents,king.getChessboardPoint())){
                            callers.add(chessComponents[a][b]);
                        }else continue;
                    }
                }
            }
        }return callers;
    }




    public static int beCalled(ChessComponent[][] chessComponents){//判断有没有被将军的函数 0 代表没有被将军,1 代表被将军
       ChessComponent king=findKing(chessComponents);
           if(Step.zhongzhirecord.get(Step.zhongzhirecord.size()-1).canMoveTo(chessComponents,king.getChessboardPoint())) {
               return 1;
           }else return 0;
        }



    public static boolean stillBeCalled(ChessComponent[][] chessComponents,ChessboardPoint Runto,ChessColor kingColor){//传入点为王想逃跑的位置
        boolean s=true;
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if(chessComponents[i][j].getChessColor()==ChessColor.NONE){
                    continue;
                }else {
                    if(chessComponents[i][j].getChessColor()==kingColor){
                        continue;
                    }else{
                        if (chessComponents[i][j].canMoveTo(chessComponents,Runto)){
                        s=false;
                        }else continue;
                    }
                }
            }
        }return s;
    }



    public static boolean judgeRun(ChessComponent[][] chessComponents,ChessboardPoint chessboardPoint,ChessComponent chessComponent,ChessColor chessColor){
        //判断被将军后能不能移动到某格，传入的应该是可能的destination
        if(chessComponent.canMoveTo(chessComponents,chessboardPoint)&&stillBeCalled(chessComponents,chessboardPoint,chessColor)==true){
            return true;//可以移动到某格
        }else return false;
    }



    public static boolean canRun(ChessComponent[][] chessComponents) {//判断被将军后能不能移走国王，该方法应在被将军后被调用判断
        boolean s=false;
            ChessComponent king=findKing(chessComponents);
            //再遍历一遍棋盘，如果遍历到了不考虑将军的canMoveTo，就进行judgeRun方法
            for (int a=0;a<8;a++){
                for (int b=0;b<8;b++){
                    if (king.canMoveTo(chessComponents,new ChessboardPoint(a,b))){
                        if (judgeRun(chessComponents,new ChessboardPoint(a,b),king,king.getChessColor())){
                            s=true;
                        }
                    }
                }
            }
            return s;
    }



    public static boolean eat(ChessComponent[][] chessComponents){//判断能不能吃掉将军的棋子,该方法应在被将军后调用进行判断
        ChessComponent king=findKing(chessComponents);
        boolean s=false;
        if (findCaller(chessComponents).size()>1){
            return false;
        }else{
        ChessComponent caller=findCaller(chessComponents).get(0);
        for (int a=0;a<8;a++){//判断有没有棋子能吃掉caller
            for (int b=0;b<8;b++){
                if(chessComponents[a][b].getChessColor()==ChessColor.NONE){
                    continue;
                }else {
                    if(chessComponents[a][b].getChessColor()!=king.getChessColor()){
                        continue;
                    }else{
                        if (caller!=null&&chessComponents[a][b].canMoveTo(chessComponents,caller.getChessboardPoint())){
                            s=true;
                        }else continue;
                    }
                }
            }
        }
        }return s;
    }


    public static boolean resist(ChessComponent[][] chessComponents) {//判断能不能垫子来阻止将军
        ChessComponent king=findKing(chessComponents);
        if(findCaller(chessComponents).size()>1){
            return false;
        }else{//遍历一遍棋盘，判断是被直着将军还是被斜着将军，再依次判断能不能把棋子塞到里面
            ChessComponent caller=findCaller(chessComponents).get(0);
//            ArrayList<ChessboardPoint> effectivePoints=new ArrayList<>();//每种将军的可能都把可以被垫上的点存进数组
            if(caller.getXcordinate()==king.getXcordinate()){
                for (int i=0;i<8;i++){
                for (int j=0;j<0;j++){
                    if(chessComponents[i][j].getChessColor()==ChessColor.NONE){
                        continue;
                    }else if(chessComponents[i][j].getChessColor()!=king.getChessColor()){
                        continue;
                    }else{
                        for (int a=1;a<Math.max(caller.getXcordinate(), king.getXcordinate())-Math.min(caller.getXcordinate(), king.getXcordinate());a++){
                            if(chessComponents[i][j].canMoveTo(chessComponents,new ChessboardPoint(Math.min(caller.getXcordinate(), king.getXcordinate())+a,king.getYcordinate()))){
                                return true;
                            }else continue;
                        }
                    }
                }
            }
        }else if (caller.getYcordinate()==king.getYcordinate()){//被横着将军
                for (int i=0;i<8;i++){
                    for (int j=0;j<0;j++){
                        if(chessComponents[i][j].getChessColor()==ChessColor.NONE){
                            continue;
                        }else if(chessComponents[i][j].getChessColor()!=king.getChessColor()){
                            continue;
                        }else{
                            for (int a=1;a<Math.max(caller.getYcordinate(), king.getYcordinate())-Math.min(caller.getYcordinate(), king.getYcordinate());a++){
                                if(chessComponents[i][j].canMoveTo(chessComponents,new ChessboardPoint
                                        (king.getXcordinate(), Math.min(caller.getYcordinate(), king.getYcordinate())+a))){
                                    return true;
                                }else continue;
                            }
                        }
                    }
                }
            }else if((caller.getXcordinate()- king.getXcordinate())==-(caller.getYcordinate()- king.getYcordinate())){//被右上到左下斜着将军，这里似乎应该再考虑兵的问题，不过我先忽略掉
                for (int i=0;i<8;i++){
                    for (int j=0;j<8;j++){
                        if(chessComponents[i][j].getChessColor()==ChessColor.NONE){
                            continue;
                        }else if(chessComponents[i][j].getChessColor()!=king.getChessColor()){
                            continue;
                        }else {
                            for (int a=1;a<Math.max(caller.getYcordinate(), king.getYcordinate())-Math.min(caller.getYcordinate(), king.getYcordinate());a++){
                                if(chessComponents[i][j].canMoveTo(chessComponents,new ChessboardPoint
                                        (Math.min(caller.getXcordinate(), king.getXcordinate())+a,Math.max(caller.getYcordinate(), king.getYcordinate())-a))){
                                    return true;//遍历是向左下遍历
                                }else continue;

                            }
                        }
                    }
                }

            }else if ((caller.getXcordinate()- king.getXcordinate())==(caller.getYcordinate()- king.getYcordinate())){//被左上到右下斜着将军
                for (int i=0;i<8;i++){
                    for (int j=0;j<8;j++){
                        if(chessComponents[i][j].getChessColor()==ChessColor.NONE){
                            continue;
                        }else if(chessComponents[i][j].getChessColor()!=king.getChessColor()){
                            continue;
                        }else {
                            for (int a=1;a<Math.max(caller.getYcordinate(), king.getYcordinate())-Math.min(caller.getYcordinate(), king.getYcordinate());a++){
                                if(chessComponents[i][j].canMoveTo(chessComponents,new ChessboardPoint
                                        (Math.min(caller.getXcordinate(), king.getXcordinate())+a,Math.min(caller.getYcordinate(), king.getYcordinate())+a))){
                                    return true;//遍历是向右下遍历
                                }else continue;

                            }
                        }
                    }
                }
            }else return false;
        }
        return false;

    }

}









