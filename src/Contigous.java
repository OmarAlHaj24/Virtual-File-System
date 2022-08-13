import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Contigous implements Techniques {
    int[] blocks = new int[1000];

    @Override
    public void allocate(MyFile file, int numOfBlocks) {
        int startBlock = -1, cnt = 1005;
        int tempCnt = 0;
        int i;
        for (i = 0; i < blocks.length; i++) {
            int idx = i;
            if (blocks[idx] == 0) {
                while (idx < 1000 && blocks[idx] == 0) {
                    tempCnt++;
                    idx++;
                }
                if (tempCnt == numOfBlocks) {
                    startBlock = i;
                    cnt = tempCnt;
                    tempCnt = 0;
                    break;
                }
                if (tempCnt < cnt && tempCnt >= numOfBlocks) {
                    startBlock = i;
                    cnt = tempCnt;
                    tempCnt = 0;
                }
                i = idx - 1;
            }
        }
        if (cnt < numOfBlocks) {
            System.out.println("There is not enough free blocks to allocate your file");
        } else {
            for (int j = startBlock; j < startBlock + numOfBlocks; j++) {
                blocks[j] = 1;
            }
            file.setSize(numOfBlocks);
        }
    }

    @Override
    public void deAllocate(MyFile file){
        for (int i = file.getStartBlock(); i < file.getStartBlock() + file.getSize(); i++) {
            blocks[i] = 0;
        }
    }

    @Override
    public void display(FileWriter file, Directory root) throws IOException {
        file.write("\n");
        for (int i = 0; i < 1000; i++) {
            file.write(blocks[i] + " ");
        }
        file.write("\n");
        writeFiles(file, root);

    }

    public void writeFiles(FileWriter file, Directory root) throws IOException {
        ArrayList<MyFile> files = root.getFiles();
        for (int i = 0; i < files.size(); i++) {
            MyFile temp = files.get(i);
            if (temp.isDeleted())
                continue;
            file.write(temp.getFilePath() + " " + temp.getStartBlock() + " " + temp.getSize() + "\n");
        }

        ArrayList<Directory> dir = root.getSubDirectories();
        for (int i = 0; i < dir.size(); i++) {
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
            if(blocks[i]==0){
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
    public void readData(Directory root, Scanner sc) {
        for (int i = 0; i < 1000; i++) {
            int j = sc.nextInt();
            blocks[i] = j;
        }
        String msg;
        sc.nextLine();
        while (sc.hasNextLine()) {
            msg = sc.nextLine();
            String[] param = msg.split(" ");
            MyFile file = root.findFile(param[0]);
            int start = Integer.parseInt(param[1]);
            System.out.println (start );
            int count = Integer.parseInt(param[2]);
            System.out.println (start );
            file.setStartBlock(start);
            file.setSize(count);
            for (int i = start; i < start + count; i++)
                blocks[i] = 1;
        }
    }
}
