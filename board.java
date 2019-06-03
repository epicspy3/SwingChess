import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.util.ArrayList;
public class board {
    private JFrame frame;
    private JPanel board;
    private JPanel underBoard;
    private JButton[][] chessboard;
    private GridLayout boardLayout = new GridLayout(8,8,0,0);
    private rulesManager ruleChecker;
    private String toMove = "W";
    private JLabel showToMove;
    private JButton resetBoard;
    public board() {
        frame = new JFrame("Chess for cool kids");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(720,720));
        frame.setResizable(false);
        
        board = new JPanel();
        board.setLayout(boardLayout);
        
        underBoard = new JPanel();
        underBoard.setLayout(new BoxLayout(underBoard,BoxLayout.LINE_AXIS));
        
        chessboard = new JButton[8][8];
        initializeBoard(); //initialize chessboard GUI
        frame.add(board); //add to JFrame
        underBoard.add(showToMove); //add label that shows who's move it is
        
        ruleChecker = new rulesManager();
        
        resetBoard = new JButton("Restart Game"); //button to restart the game
        resetBoard.setPreferredSize(new Dimension(50,50));
        resetBoard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                initializeBoard();
            }
        });
        underBoard.add(resetBoard);
        frame.add(underBoard,BorderLayout.PAGE_END);
        frame.pack();
    }
    private void initializeBoard() {
        board.removeAll(); //remove everything from the jpanel so pieces can be readded (for the reset button)
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessboard[i][j] = new JButton();
                chessboard[i][j].setOpaque(true);
                if (i == 1) {
                    chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/BP.png")));
                    chessboard[i][j].setName("BP");
                }
                else if (i == 6) {
                    chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/WP.png")));
                    chessboard[i][j].setName("WP");
                }
                else if (i == 0) {
                    switch(j) {
                        case 0: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/BR.png")));
                                chessboard[i][j].setName("BR");
                                break;
                        case 1: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/BN.png")));
                                chessboard[i][j].setName("BN");
                                break;
                        case 2: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/BB.png")));
                                chessboard[i][j].setName("BB");
                                break;
                        case 3: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/BQ.png")));
                                chessboard[i][j].setName("BQ");
                                break;
                        case 4: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/BK.png")));
                                chessboard[i][j].setName("BK");
                                break;
                        case 5: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/BB.png")));
                                chessboard[i][j].setName("BB");
                                break;
                        case 6: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/BN.png")));
                                chessboard[i][j].setName("BN");
                                break;
                        case 7: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/BR.png")));
                                chessboard[i][j].setName("BR");
                                break;
                    }
                }
                else if (i == 7) {
                    switch(j) {
                        case 0: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/WR.png")));
                                chessboard[i][j].setName("WR");
                                break;
                        case 1: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/WN.png")));
                                chessboard[i][j].setName("WN");
                                break;
                        case 2: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/WB.png")));
                                chessboard[i][j].setName("WB");
                                break;
                        case 3: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/WQ.png")));
                                chessboard[i][j].setName("WQ");
                                break;
                        case 4: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/WK.png")));
                                chessboard[i][j].setName("WK");
                                break;
                        case 5: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/WB.png")));
                                chessboard[i][j].setName("WB");
                                break;
                        case 6: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/WN.png")));
                                chessboard[i][j].setName("WN");
                                break;
                        case 7: chessboard[i][j].setIcon(new ImageIcon(getClass().getResource("/images/WR.png")));
                                chessboard[i][j].setName("WR");
                                break;
                    }
                }
                else chessboard[i][j].setName("nothing");
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.add(chessboard[i][j]);
            }
        }
        setBackgroundColors();
        showToMove = new JLabel("White to move");
        showToMove.setFont(showToMove.getFont().deriveFont(20f)); //set font size to 20
        setUpClicking(); //add clicking functionality to each button in GUI
        frame.repaint();
        frame.revalidate();
    }
    public void setBackgroundColors() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    chessboard[i][j].setBackground(new Color(253,253,253));
                }
                else {
                    chessboard[i][j].setBackground(new Color(130,161,170));
                }
            }
            frame.pack();
        }
    }
    private void setUpClicking() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton component1 = chessboard[i][j];
                component1.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        int[] highlightPos = getHighlighted();
                        int hX = highlightPos[0];
                        int hY = highlightPos[1];
                        int x = -1;
                        int y = -1;
                        for (int a = 0; a < 8; a++) {
                            for (int b = 0; b < 8; b++) if (e.getSource().equals(chessboard[a][b])) {
                                x = a;
                                y = b;
                            }
                        }
                        setBackgroundColors();
                        if (hX == -1 && hY == -1) component1.setBackground(new Color(139,247,136)); 
                        //sets background if piece is clicked
                        if (!(hX == -1) || !(hY == -1)) {
                            if (!chessboard[hX][hY].getName().equals("nothing")) {
                                if (ruleChecker.isLegalMove(hX,hY,x,y,chessboard) && 
                                    chessboard[hX][hY].getName().substring(0,1).equals(toMove)) {
                                    makeMove(hX,hY,x,y);
                                }
                                else {
                                    System.out.println("wrong"+hX+hY+x+y);
                                }
                            }
                        }  
                    }
                });
            }
        }
    }
    public void makeMove(int oldX, int oldY, int newX, int newY) {
        String newName = chessboard[oldX][oldY].getName();
        Icon newIcon = chessboard[oldX][oldY].getIcon();
        chessboard[oldX][oldY].setIcon(null);
        chessboard[oldX][oldY].setName("nothing");
        chessboard[newX][newY].setName(newName);
        chessboard[newX][newY].setIcon(newIcon);
        setBackgroundColors();
        changeToMove();
        frame.repaint();
        frame.revalidate();
    }
    public int[] getHighlighted() {
        int[] returnArr = {-1,-1};
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (chessboard[row][col].getBackground().equals(new Color(139,247,136))) {
                    returnArr[0] = row;
                    returnArr[1] = col;
                }
            }
        }
        return returnArr;
    }
    public void changeToMove() {
        if (toMove.equals("W")) {
            showToMove.setText("Black to move");
            toMove = "B";
        }
        else {
            showToMove.setText("White to move");
            toMove = "W";
        }
    }
    public void showBoard() {frame.setVisible(true);}
    public JButton[][] getBoard() {return chessboard;}
    public static void main(String[] args) {
        board x = new board();
        x.showBoard();
    }
}