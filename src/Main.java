import MineswipperLogic.Game;
import UserInterfaces.MinesweeperGUI;
import static UserInterfaces.Constants.*;

public class Main {
    public static void main(String[] args){  
       new MinesweeperGUI(new Game(ROW_NUM,COL_NUM,MINE_NUM));
    }   
}