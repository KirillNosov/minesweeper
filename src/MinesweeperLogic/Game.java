package MinesweeperLogic;

public class Game{
    private int rowNum;                         
    private int colNum;                         
    private int mineNum;                       
    private int minesLeft;                      
    private Cell cells[][];                     
    private int openedCells;                    
    private boolean started;                    
    private boolean finished;                   
    private boolean won;                        
    
    public Game(int rowNum, int colNum, int mineNum){
       cells=new Cell[rowNum][colNum];
       for (int i=0;i<rowNum;i++)
            for (int j=0;j<colNum;j++)
                cells[i][j]=new Cell();
       this.rowNum=rowNum;
       this.colNum=colNum;
       this.mineNum=mineNum;
       minesLeft=mineNum; 
       openedCells=0;
       started=false;
       finished=false;
    }
    
    public Cell GetCell(int i, int j){
        return(cells[i][j]);
    }
    
    public void SetMineFlag(int xCell, int yCell){
        if (cells[xCell][yCell].SetMineMark())
            minesLeft--;
        else
            minesLeft++;
    }
    
    public int GetRowNum(){
        return(rowNum);
    }
    
    public int GetColNum(){
        return(colNum);
    }
    
    public int GetMineNum(){
        return(mineNum);
    }
    
    public int GetMinesLeft(){
        return(minesLeft);
    }
    
    public boolean IsStarted(){
        return(started);
    }
    
    public boolean IsFinished(){
        return(finished);
    }
    
    public boolean IsWon(){
        return(won);
    }
    
    public void CheckCell(int xCell, int yCell){
        if (!started)  
            Start(xCell,yCell);  
       OpenCell(xCell,yCell);
    }
    
    private void Start(int xStart, int yStart){
        started=true;
        LayMines(xStart,yStart);
        InitBoard();    
    } 
    
    private void LayMines(int xStart, int yStart){
        int x;
        int y;
        int laidMines=0;
        do
        {
            x=(int)(Math.random() * rowNum);
            y=(int)(Math.random() * colNum);
            if ((!cells[x][y].IsMined()) && ((x!=xStart) || (y!=yStart)))
            {
                cells[x][y].SetMine();
                laidMines++;
            }
        }
        while (laidMines<mineNum);
    }
    
    private void InitBoard(){
        for (int i=0;i<rowNum;i++)
            for (int j=0;j<colNum;j++)
                cells[i][j].SetMinesAround(GetNumOfMinesAroundCell(i,j));    
    }
   
    private int GetNumOfMinesAroundCell(int xCell, int yCell){
        int minesAround=0;
        for (int i=xCell-1;i<=xCell+1;i++)
            for (int j=yCell-1;j<=yCell+1;j++)
                try
                {
                    if (cells[i][j].IsMined())
                        minesAround++;
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    
                }
        return(minesAround-(cells[xCell][yCell].IsMined()?1:0));
    }
    
    private void OpenCell(int xCell, int yCell){
        if (cells[xCell][yCell].IsMined())
        {    
            cells[xCell][yCell].Open();
            cells[xCell][yCell].Bang();
            FinishGame(false);
        }
        else
            ShowCell(xCell,yCell);
    }
    
    private void ShowCell(int xCell, int yCell){
            cells[xCell][yCell].Open();
            openedCells++;
            if (openedCells<(rowNum*colNum-mineNum))
            {
                if (cells[xCell][yCell].GetMinesAround()==0)
                    for (int i=xCell-1;i<=xCell+1;i++)
                        for (int j=yCell-1;j<=yCell+1;j++)
                            try
                            {
                                if (!cells[i][j].IsOpened())
                                    ShowCell(i, j);
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                            
                            }
            }
            else
            {        
                FinishGame(true);
            }
    }
    
    private void FinishGame(boolean won){
        this.won=won;
        started=false;
        finished=true;
    } 
}