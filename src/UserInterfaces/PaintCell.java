package UserInterfaces;

import MinesweeperLogic.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import static UserInterfaces.Constants.*;

class PaintCell{ 
        void paintBomb(Graphics g, int x, int y, Color color) {
            g.setColor(color);
            g.setFont(new Font("", Font.BOLD, BLOCK_SIZE));
            g.fillRect(x*BLOCK_SIZE + 7, y*BLOCK_SIZE + 10, 18, 10);
            g.fillRect(x*BLOCK_SIZE + 11, y*BLOCK_SIZE + 6, 10, 18);
            g.fillRect(x*BLOCK_SIZE + 9, y*BLOCK_SIZE + 8, 14, 14);
            g.setColor(Color.white);
            g.fillRect(x*BLOCK_SIZE + 11, y*BLOCK_SIZE + 10, 4, 4);  
        }
        
        void paintFlag(Graphics g, int x, int y) {
            g.setColor(Color.BLACK);
            g.fillRect(x*BLOCK_SIZE + 7, y*BLOCK_SIZE + 5, 2, 20);
            g.setColor(Color.RED);
            g.fillRect(x*BLOCK_SIZE + 9, y*BLOCK_SIZE + 5, 15, 7);
        }
        
        void paintString(Graphics g, String str, int x, int y, Color color) {
            g.setColor(color);
            g.setFont(new Font("", Font.BOLD, BLOCK_SIZE));
            g.drawString(str, x*BLOCK_SIZE + 8, y*BLOCK_SIZE + 26);
        }

        void paint(Graphics g, int x, int y,Game game) {
            g.setColor(Color.lightGray);
            g.drawRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            if (!game.GetCell(x, y).IsOpened()) 
            {
                if ((!game.IsStarted() && !game.GetCell(x, y).IsBlownUp()) && game.GetCell(x, y).IsMined()) 
                    paintBomb(g, x, y, Color.black);
                else 
                {
                    g.setColor(Color.lightGray);
                    g.fill3DRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, true);
                    if (game.GetCell(x, y).IsMineMark()) 
                        paintFlag(g, x, y);
                }
            } else
                if (game.GetCell(x, y).IsMined()) 
                    paintBomb(g, x, y, game.GetCell(x, y).IsBlownUp() ? Color.red : Color.black);
                else
                    if (game.GetCell(x, y).GetMinesAround() > 0)
                        paintString(g, Integer.toString(game.GetCell(x, y).GetMinesAround()), x, y, new Color(COLOR_OF_NUMBERS[game.GetCell(x, y).GetMinesAround() - 1]));
        }
    }