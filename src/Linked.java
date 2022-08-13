import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public  class Linked implements Techniques {
    int[] blocks = new int[1000];

    @Override
    public void allocate (MyFile file, int numOfBlocks) {
        int remBlocks = numOfBlocks;
        ArrayList<Integer> allocBlocks = new ArrayList<>();
        for(int i=0; i<1000;i++){
            if(remBlocks==0)
                break;
            if(blocks[i]==-2){
                allocBlocks.add(i);
                remBlocks--;
            }
        }
        if(remBlocks!=0){
            System.out.println("Cannot create file. There is not enough space.");
            return;

        }
        file.setStartBlock(allocBlocks.get(0));
        for(int i =0; i<allocBlocks.size()-1; i++)
            blocks[allocBlocks.get(i)] = allocBlocks.get(i+1);
        file.setEndBlock(allocBlocks.get(allocBlocks.size()-1));
        blocks[allocBlocks.get(allocBlocks.size()-1)] = -1;
    }

    @Override
    public void deAllocate(MyFile file){
        int index = file.getStartBlock();
        int prev = file.getStartBlock();
        while(blocks[index]!=-1){
            prev = index;
            index=blocks[prev];
            blocks[prev]=-2;
        }
        blocks[index] = -2;
    }

    @Override
    public void display( FileWriter file, Directory root) throws IOException {
        file.write ( "\n" );
        for (int i =0 ;i<1000;i++){
            if(blocks[i]!=-2)
                file.write ( 1 + " " );
            else
                file.write ( 0 + " " );
        }
        file.write ( "\n" );
        writeFiles ( file,root );
    }

    public void writeFiles (FileWriter file, Directory root)throws IOException {
        ArrayList<MyFile> files =  root.getFiles ();
        for(int i =0; i< files.size();i++){
            MyFile temp = files.get ( i );
            if(temp.isDeleted())
                continue;
            file.write(temp.getFilePath ()+" "+temp.getStartBlock ()+ " "+ temp.getEndBlock ()+ "\n");
            int start = temp.getStartBlock();
            int end = temp.getEndBlock();
            while(start!=end){
                file.write(start+" " + blocks[start] + "\n");
                start = blocks[start];
            }
            file.write(end+" NIL\n");
        }

        ArrayList<Directory> dir = root.getSubDirectories ();
        for(int i =0; i< dir.size();i++){
            if(!dir.get(i).isDeleted()){
                writeFiles(file, dir.get(i));
            }
        }

    }

    @Override
    public void printStatus() {
        int empty = 0;
        ArrayList<Integer> emptyBlocks = new ArrayList<>();
        int alloc = 0;
        ArrayList<Integer> allocBlocks = new ArrayList<>();
        for(int i =0; i<blocks.length; i++){
            if(blocks[i]==-2){
                empty++;
                emptyBlocks.add(i);
            }
            else{
                alloc++;
                allocBlocks.add(i);
            }
        }
        System.out.println("Empty space: "+empty +" KB");
        System.out.println("Allocated space: "+alloc +" KB");
        System.out.println("Empty Blocks: ");
        for(int i =0; i<emptyBlocks.size(); i++){
            System.out.print(emptyBlocks.get(i)+" ");
        }
        System.out.println();
        System.out.println("Allocated Blocks: ");
        for(int i =0; i<allocBlocks.size(); i++){
            System.out.print(allocBlocks.get(i)+" ");
        }
        System.out.println();
    }

    @Override
    public void readData(Directory root, Scanner reader){
        for(int i =0; i<1000;i++) {
            int j = reader.nextInt();
            if (j == 0)
                blocks[i] = -2;

        }
        String msg;
        reader.nextLine();
        while ( reader.hasNextLine ( ) ){
            msg = reader.nextLine ();
            ///
            System.out.println(msg);
            String[] param =msg.split(" ");
            MyFile file = root.findFile(param[0]);
            int start = Integer.parseInt(param[1]);
            int end = Integer.parseInt(param[2]);
            file.setStartBlock(start);
            file.setEndBlock(end);
            int index = -1;
            while(true){
                String temp = reader.nextLine();
                String[] param1 = temp.split(" ");
                if(param1[1].equals("NIL") || param1[1].equals("nil")){
                    index = Integer.parseInt(param1[0]);
                    break;
                }else{
                    index = Integer.parseInt(param1[0]);
                    start = Integer.parseInt(param1[1]);
                }
                ////
                blocks[index]=start;
            }
            blocks[end] =-1;
        }
    }
}
