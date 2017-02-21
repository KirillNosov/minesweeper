package MinesweeperLogic;

public class Cell{
    private boolean mined;
    private boolean opened;
    private boolean markedAsMine;
    private boolean blownUp;
    private int numMinesAround;
    
    public Cell(){
        mined=false;
        opened=false;
        markedAsMine=false;
        blownUp=false;
        numMinesAround=0;
    }
    
    public void SetMine(){
        mined=true;
    }
    
    public boolean IsMined(){
        return(mined);
    }
    
    public void Open(){
        opened=true;
    }
    
    public boolean IsOpened(){
        return(opened);
    }
    
    public boolean SetMineMark(){
        markedAsMine=!markedAsMine;
        return(markedAsMine);
    }
    
    public boolean IsMineMark(){
        return(markedAsMine);
    }
    
    public void SetMinesAround(int num){
        numMinesAround=num;
    }
    
    public int GetMinesAround(){
        return(numMinesAround);
    }
    
    public void Bang(){
        blownUp=true;
    }
    
    public boolean IsBlownUp(){
        return(blownUp);
    }
}
