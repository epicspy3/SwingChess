import javax.swing.*;
public class rulesManager {
    private JButton[][] board;
    private JButton[][] testBoard;
    public rulesManager() {
        board = new JButton[8][8];
        testBoard = new JButton[8][8];
    }
    public boolean isLegalMove(int xOld, int yOld, int xNew, int yNew, JButton[][] boardState) {
        for (int i = 0; i < 8; i++) for (int j = 0; j < 8; j++) {
            board[i][j] = boardState[i][j];
            testBoard[i][j] = boardState[i][j];
        }
        if (board[xOld][yOld].getName().substring(0,1).equals(board[xNew][yNew].getName().substring(0,1))) return false;
        // ^ check if colors match
        switch(testBoard[xOld][yOld].getName().substring(1,2)) {
            case "P": return pawnIsLegalMove(xOld,yOld,xNew,yNew);
            case "N": return knightIsLegalMove(xOld,yOld,xNew,yNew);
            case "B": return bishopIsLegalMove(xOld,yOld,xNew,yNew);
            case "R": return rookIsLegalMove(xOld,yOld,xNew,yNew);
            case "Q": return rookIsLegalMove(xOld,yOld,xNew,yNew) || bishopIsLegalMove(xOld,yOld,xNew,yNew);
            case "K": return kingIsLegalMove(xOld,yOld,xNew,yNew);
            default: return false;
        }
    }
    private boolean pawnIsLegalMove(int xOld, int yOld, int xNew, int yNew) {
        String pieceColor = board[xOld][yOld].getName().substring(0,1);
        if (Math.abs(xOld-xNew) == 2) {
            if (pieceColor.equals("W") && xOld == 6) {
                if ((board[xOld-1][yOld].getIcon()) == null && (board[xOld-2][yOld].getIcon() == null))
                    return true;
                }
            else if (pieceColor.equals("B") && xOld == 1) {
                if ((board[xOld+1][yOld].getIcon()) == null && (board[xOld+2][yOld].getIcon() == null))
                    return true;
            }
            else return false;
        }
        if (Math.abs(xOld-xNew) == 1) {
            if (board[xNew][yNew].getIcon() == null && yOld == yNew) return true;
            else if (Math.abs(yOld-yNew) == 1 && !(board[xNew][yNew].getIcon() == null)) return true;
            else return false;
        }
        //create exception for en passant!!! through saving a list of moves made
        return false;
    }
    private boolean knightIsLegalMove(int xOld, int yOld, int xNew, int yNew) {
        if (Math.abs(xOld-xNew) == 2 && Math.abs(yOld-yNew) == 1) return true;
        if (Math.abs(yOld-yNew) == 2 && Math.abs(xOld-xNew) == 1) return true;
        return false;
    }
    private boolean bishopIsLegalMove(int xOld, int yOld, int xNew, int yNew) {
        if (!(Math.abs(xOld-xNew) == Math.abs(yOld-yNew)) || xOld == xNew || yOld == yNew) return false; //check if diagonal
        if (xOld < xNew) {
            if (yOld < yNew) {
                for (int i = 1; i < Math.abs(xNew-xOld); i++) if (!(board[xOld+i][yOld+i].getIcon() == null)) return false;
            }
            else {
                for (int i = 1; i < Math.abs(xNew-xOld); i++) if (!(board[xOld+i][yOld-i].getIcon() == null)) return false;
            }
        }
        else {
            if (yOld < yNew) {
                for (int i = 1; i < Math.abs(xNew-xOld); i++) if (!(board[xOld-i][yOld+i].getIcon() == null)) return false;
            }
            else {
                for (int i = 1; i < Math.abs(xNew-xOld); i++) if (!(board[xOld-i][yOld-i].getIcon() == null)) return false;
            }
        }
        return true;
    }
    private boolean rookIsLegalMove(int xOld, int yOld, int xNew, int yNew) {
        String pieceColor = board[xOld][yOld].getName().substring(0,1);
        if (!(xOld == xNew || yOld == yNew)) return false; //to work on later
        if (xOld == xNew) {
            if (yOld < yNew) for (int i = yOld+1; i < yNew; i++) if (!(board[xOld][yOld+i].getIcon() == null)) return false;
            else for (int j = yNew+1; j < yOld; j++) if (!(board[xOld][yNew+i].getIcon() == null)) return false;
        }
        else {
            if (xOld < xNew) for (int i = xOld+1; i < xNew; i++) if (!(board[xOld+i][yOld].getIcon() == null)) return false;
            else for (int j = xNew+1; j < xOld; j++) if (!(board[xNew+i][yOld].getIcon() == null)) return false;
        }
        return true;
    }
    private boolean kingIsLegalMove(int xOld, int yOld, int xNew, int yNew) {
        String pieceColor = board[xOld][yOld].getName().substring(0,1);
        boolean check = isInCheck(pieceColor,testBoard);
        if (!(Math.abs(xOld-xNew) == 1 || Math.abs(yOld-yNew) == 1)) {
            return false;
        }
        if (!check) {
            return true;
        }
        else {
            makeTempMove(xOld,yOld,xNew,yNew,testBoard);
            if (!isInCheck(pieceColor,testBoard)) {
                return true;
            }
        }
        //add functionality for checks
        return false;
    }
    private boolean isInCheck(String playerColor, JButton[][] boardState1) {
        int x = -1;
        int y = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((playerColor + "K").equals(boardState1[i][j].getName())) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(board[i][j].getName().substring(1,2).equals("K"))) {
                    if (isLegalMove(i,j,x,y,boardState1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private void makeTempMove(int xOld, int yOld, int xNew, int yNew, JButton[][] boardState2) {
        String newName = boardState2[xOld][yOld].getName();
        Icon newIcon = boardState2[xOld][yOld].getIcon();
        boardState2[xOld][yOld].setIcon(null);
        boardState2[xOld][yOld].setName("nothing");
        boardState2[xNew][yNew].setName(newName);
        boardState2[xNew][yNew].setIcon(newIcon);
    }
}