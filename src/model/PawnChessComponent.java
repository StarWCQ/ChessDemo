package model;

import controller.Step;
import view.ChessGameFrame;
import view.Chessboard;
import view.Chessboard.*;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的兵
 */
public class PawnChessComponent extends ChessComponent {
    /**
     * 黑兵和白兵的图片，static使得其可以被所有兵对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;


    /**
     * 兵棋子对象自身的图片，是上面两种中的一种
     */
    private Image pawnImage;

    /**
     * 读取加载兵棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定KingImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }

    /**
     * 兵棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 兵棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
//        System.out.printf("%d %d\n",
//                Step.qishirecord.get(Step.qishirecord.size()-3).getChessboardPoint().getX(),
//                Step.qishirecord.get(Step.qishirecord.size()-3).getChessboardPoint().getY());
        if(chessComponents[destination.getX()][destination.getY()].chessColor == chessColor)
            return false;

        if(chessColor == ChessColor.BLACK){
            if(source.getX()+1==destination.getX()&&source.getY()==destination.getY()
                    &&chessComponents[source.getX()+1][source.getY()].chessColor!=ChessColor.WHITE
                    &&chessComponents[source.getX()+1][source.getY()].chessColor!=ChessColor.BLACK) {
                return true;//走一格
            }
//            if(Step.qishirecord.size() < 2)
//                return false;
            else if((source.getY()==destination.getY()&&destination.getX()==3&&source.getX()==1)
                    &&chessComponents[3][source.getY()].chessColor!=ChessColor.WHITE
                    &&chessComponents[2][source.getY()].chessColor!=ChessColor.WHITE
                    &&chessComponents[2][source.getY()].chessColor!=ChessColor.BLACK
                    &&chessComponents[3][source.getY()].chessColor!=ChessColor.BLACK){
                return true;//走两格
            }
            else if((source.getY()<7&&source.getY()>0)&&(chessComponents[source.getX()+1][source.getY()-1].chessColor == ChessColor.WHITE)&&
                    (source.getX()+1==destination.getX())&&(source.getY()-destination.getY())==1){
                return true;//非边路黑棋的左下吃
            }
            else if((source.getY()<7&&source.getY()>0)&&(chessComponents[source.getX()+1][source.getY()+1].chessColor == ChessColor.WHITE)&&
                    (source.getX()+1==destination.getX())&&(source.getY()-destination.getY())==-1){
                return true;//非边路黑棋的右下吃
            }
            else if(source.getY()==0&&(chessComponents[source.getX()+1][source.getY()+1].chessColor == ChessColor.WHITE)&&
                    (source.getX()+1==destination.getX())&&(source.getY()-destination.getY())==-1){
                return true;//左边界黑棋的右下吃
            }else if(source.getY()==7&&(chessComponents[source.getX()+1][source.getY()-1].chessColor == ChessColor.WHITE)&&
                    (source.getX()+1==destination.getX())&&(source.getY()-destination.getY())==1) {
                return true;//右边界黑棋的左下吃
            }else if(Step.qishirecord.size()>3&&source.getY()>=0&&source.getY()<=6&&source.getY()+1==destination.getY()&&source.getX()+1==destination.getX()&&
                    ChessGameFrame.chessboard.chessComponents[destination.getX() - 1][destination.getY()] instanceof PawnChessComponent
                    &&Step.qishirecord.get(Step.qishirecord.size()-2).getChessboardPoint().getX()== source.getX()+2
                    &&Step.qishirecord.get(Step.qishirecord.size()-2).getChessboardPoint().getY()== destination.getY()
                    ){
                ChessComponent.specialJudge[destination.getX()][destination.getY()] = 1;
                return true;//右下吃过路兵
            }
            else if (Step.qishirecord.size()>3&&source.getY()>=1&&source.getY()<=7&&source.getY()-1==destination.getY()&&source.getX()+1==destination.getX()&&
                    ChessGameFrame.chessboard.chessComponents[destination.getX() - 1][destination.getY()] instanceof PawnChessComponent
                    &&Step.qishirecord.get(Step.qishirecord.size()-2).getChessboardPoint().getX()== source.getX()+2
                    &&Step.qishirecord.get(Step.qishirecord.size()-2).getChessboardPoint().getY()== destination.getY()
                    ){
                ChessComponent.specialJudge[destination.getX()][destination.getY()] = 1;
                return true;//左下吃过路兵
            }
            return false;
        }
        else {
            if(source.getX()-1==destination.getX()&&source.getY()==destination.getY()//白棋
                &&chessComponents[source.getX()-1][source.getY()].chessColor!=ChessColor.BLACK
                &&chessComponents[source.getX()-1][source.getY()].chessColor!=ChessColor.WHITE) {
                 return true;//走一格
            }

            else if(source.getY()==destination.getY()&&destination.getX()==4&&source.getX()==6
                &&chessComponents[4][source.getY()].chessColor!=ChessColor.BLACK
                &&chessComponents[5][source.getY()].chessColor!=ChessColor.BLACK
                &&chessComponents[4][source.getY()].chessColor!=ChessColor.WHITE
                &&chessComponents[5][source.getY()].chessColor!=ChessColor.WHITE){
                return true;//走两格
            }
            else if((source.getY()<7&&source.getY()>0)&&(chessComponents[source.getX()-1][source.getY()+1].chessColor == ChessColor.BLACK)
                &&(source.getX()-1==destination.getX()&&(source.getY()-destination.getY())==-1)){
                return true;//非边路白棋的右上吃
            }
            else if ((source.getY()<7&&source.getY()>0)&&(chessComponents[source.getX()-1][source.getY()-1].chessColor == ChessColor.BLACK)
                &&(source.getX()-1==destination.getX()&&(source.getY()-destination.getY())==1)){
                return true;//非边路白棋的左上吃
            }else if(source.getY()==0&&(chessComponents[source.getX()-1][source.getY()+1].chessColor == ChessColor.BLACK)&&
                (source.getX()-1==destination.getX())&&(source.getY()-destination.getY())==-1){
                return true;//左边界白棋的右上吃
            }else if(source.getY()==7&&(chessComponents[source.getX()-1][source.getY()-1].chessColor == ChessColor.BLACK)&&
                (source.getX()-1==destination.getX())&&(source.getY()-destination.getY())==1) {
                return true;//右边界白棋的左上吃
            }else if(Step.qishirecord.size()>3&&source.getY()>=0&&source.getY()<=6&&source.getY()+1==destination.getY()&&source.getX()-1==destination.getX()&&
                ChessGameFrame.chessboard.chessComponents[destination.getX()+1][destination.getY()] instanceof PawnChessComponent
                &&Step.qishirecord.get(Step.qishirecord.size()-2).getChessboardPoint().getX()== source.getX()-2
                &&Step.qishirecord.get(Step.qishirecord.size()-2).getChessboardPoint().getY()== destination.getY()){
            ChessComponent.specialJudge[destination.getX()][destination.getY()] = 1;
            return true;//右上吃过路兵
            }else if(Step.qishirecord.size()>3&&source.getY()>=1&&source.getY()<=7&&source.getY()-1==destination.getY()&&source.getX()-1==destination.getX()&&
                ChessGameFrame.chessboard.chessComponents[destination.getX()+1][destination.getY()] instanceof PawnChessComponent
                &&Step.qishirecord.get(Step.qishirecord.size()-2).getChessboardPoint().getX()== source.getX()-2
                &&Step.qishirecord.get(Step.qishirecord.size()-2).getChessboardPoint().getY()== destination.getY()){
            ChessComponent.specialJudge[destination.getX()][destination.getY()] = 1;
            return true;//左上吃过路兵
            }
            return false;
        }
    }


    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(queenImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
            showCanMove();
        }
    }
}
