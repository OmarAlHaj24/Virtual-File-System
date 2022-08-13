import java.util.ArrayList;

public class Block{
    ArrayList<Integer> blocks;
    public Block(){
        blocks = new ArrayList<> ();
    }

    public void setBlocks ( ArrayList < Integer > blocks ) {
        this.blocks = blocks;
    }

    public ArrayList < Integer > getBlocks ( ) {
        return blocks;
    }

}
