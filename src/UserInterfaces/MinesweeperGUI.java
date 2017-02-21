package UserInterfaces;

import MinesweeperLogic.Game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Timer;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static UserInterfaces.Constants.*;

public class MinesweeperGUI extends JFrame {
    PaintCell[][] field;
    Game game;

    public MinesweeperGUI(Game game) {
        JLabel minesLeftLabel=new JLabel("Mines  left: "+game.GetMinesLeft());
        this.game=game;
        field = new PaintCell[game.GetRowNum()][game.GetColNum()];
        for (int x = 0; x < game.GetRowNum(); x++)
            for (int y = 0; y < game.GetColNum(); y++)
               field[y][x] = new PaintCell();
  
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, game.GetRowNum() * BLOCK_SIZE + FIELD_DX, game.GetColNum() * BLOCK_SIZE + FIELD_DY);
        setResizable(false);
        setLocationRelativeTo(null);
        final TimerLabel timeLabel = new TimerLabel();
        final Canvas canvas = new Canvas();
        canvas.setBackground(Color.white);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!game.IsFinished())
                {    
                    if (!timeLabel.timerStarted)
                    {
                        timeLabel.StartTimer();
                        minesLeftLabel.setVisible(true);  
                    }
                    super.mouseReleased(e);
                    int x = e.getX()/BLOCK_SIZE;
                    int y = e.getY()/BLOCK_SIZE;
                    if (!game.IsFinished())
                    {
                        if (e.getButton() == MOUSE_BUTTON_LEFT)
                            if (!game.GetCell(x, y).IsOpened() && !game.GetCell(x, y).IsMineMark())  {
                                game.CheckCell(x, y);
                        }
                        if (e.getButton() == MOUSE_BUTTON_RIGHT) 
                            game.SetMineFlag(x, y);
                        
                        minesLeftLabel.setText("Mines left: "+game.GetMinesLeft());
                        minesLeftLabel.repaint();
                        canvas.repaint();
                        if (game.IsFinished())
                        {
                            timeLabel.StopTimer(); 
                            String mess;                    
                            if (game.IsWon())
                                mess="You won!";
                            else
                                mess="You lose!";
                                
                            Object[] options = {"Yes",
                                                "No"};
                            if (JOptionPane.YES_OPTION ==JOptionPane.showOptionDialog(null, mess+"\n Would you like to play again?",
                                                             "Game over!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                                              null,options,options[0]))
                            {
                                dispose();
                                new MinesweeperGUI(new Game(game.GetRowNum(),game.GetColNum(),game.GetMineNum()));
                            }
                            else
                                System.exit(0);    
                        }
                    }
                }
            }
        });
        add(BorderLayout.CENTER, canvas);
        JPanel p = new JPanel(new BorderLayout());

        p.add(timeLabel,BorderLayout.WEST);
        minesLeftLabel.setVisible(false);
        p.add(minesLeftLabel,BorderLayout.EAST);
        add(p,BorderLayout.SOUTH);
        setVisible(true);
    }

    class TimerLabel extends JLabel {
        private boolean timerStarted;
        
        TimerLabel(){
            timerStarted=false;
        }
        
        Timer timer = new Timer();
           
        TimerTask timerTask = new TimerTask() {
            volatile int time;
            
            Runnable refresher = new Runnable() {
                @Override
                public void run() {
                    TimerLabel.this.setText(String.format("%02d:%02d", time / 60, time % 60));
                }
            };
            
            @Override
            public void run() {
                time++;
                SwingUtilities.invokeLater(refresher);
            }
        };

        void StartTimer(){ 
            timerStarted=true;
            timer.scheduleAtFixedRate(timerTask, 0, 1000); 
        }
        
        void StopTimer(){ 
            timerStarted=false;
            timer.cancel(); 
        }
        
        public boolean IsTimerStarted(){
            return(timerStarted);
        }
    }

    class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int x = 0; x < game.GetRowNum(); x++)
                for (int y = 0; y < game.GetColNum(); y++)
                    field[y][x].paint(g, x, y, game);
        }
    }
}