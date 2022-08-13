import java.util.ArrayList;

public class MyFile {
    private String fileName;
    private String filePath;
    private int size;
    private int startBlock,endBlock;
    private boolean deleted;
    Block block;

    public MyFile(String fileName, String filePath){
        this.filePath = filePath;
        this.fileName = fileName;
        this.deleted = false;
        block = new Block();
    }
    public MyFile(String fileName, int size, String filePath){
        this.filePath = filePath;
        this.fileName = fileName;
        this.size = size;
        this.deleted = false;
        block = new Block();
    }

    public MyFile(String fileName,String filePath, int startBlock, int endBlock){
        this.filePath = filePath;
        this.fileName = fileName;
        this.deleted = false;
        this.startBlock=startBlock;
        this.endBlock = endBlock;
        block = new Block();
    }

    public MyFile(String filePath, int idx){
        this.filePath = filePath;


    }



    public String getFileName ( ) {
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public int getSize ( ) {
        return size;
    }

    public void setSize(int size){
        this.size = size;
    }

    public String getFilePath ( ) {
        return filePath;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public boolean isDeleted ( ) {
        return deleted;
    }

    public void setDeleted ( boolean deleted ) {
        this.deleted = deleted;
    }

    public int getStartBlock ( ) {
        return startBlock;
    }

    public void setStartBlock ( int startBlock ) {
        this.startBlock = startBlock;
    }

    public int getEndBlock ( ) {
        return endBlock;
    }

    public void setEndBlock ( int endBlock ) {
        this.endBlock = endBlock;
    }
}
