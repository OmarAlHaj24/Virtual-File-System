import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Indexed implements Techniques {
    int[] blocks = new int[1000];

    @Override
    public void allocate(MyFile file, int numOfBlocks) {
        int count = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0)
                count++;
            if (count == numOfBlocks + 1)
                break;
        }
        if (count < numOfBlocks + 1) {
            System.out.println("There is not enough space");
            return;
        }
        count = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                blocks[i] = 1;
                file.setStartBlock(i);
                break;
            }
        }
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                if (count < numOfBlocks) {
                    blocks[i] = 1;
                    file.block.blocks.add(i);
                    count++;
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public void deAllocate(MyFile file) {
        int idx = file.getStartBlock();
        ArrayList<Integer> temp = file.block.blocks;
        for (int i = 0; i < temp.size(); i++) {
            blocks[temp.get(i)] = 0;
        }
        blocks[idx] = 0;
        file.block.blocks.clear();
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
            file.write(temp.getFilePath() + " " + temp.getStartBlock() + "\n");
            ArrayList<Integer> tempBlocks = temp.block.blocks;
            file.write(temp.getStartBlock() + "  ");
            for (int j = 0; j < tempBlocks.size(); j++) {
                file.write(tempBlocks.get(j) + " ");
            }
            file.write("\n");

        }

        ArrayList<Directory> dir = root.getSubDirectories();
        for (int i = 0; i < dir.size(); i++) {
            if (!dir.get(i).isDeleted()) {
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
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                empty++;
                emptyBlocks.add(i);
            } else {
                alloc++;
                allocBlocks.add(i);
            }
        }
        System.out.println("Empty space: " + empty + " KB");
        System.out.println("Allocated space: " + alloc + " KB");
        System.out.println("Empty Blocks: ");
        for (int i = 0; i < emptyBlocks.size(); i++) {
            System.out.print(emptyBlocks.get(i) + " ");
        }
        System.out.println();
        System.out.println("Allocated Blocks: ");
        for (int i = 0; i < allocBlocks.size(); i++) {
            System.out.print(allocBlocks.get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public void readData(Directory root, Scanner sc) throws IOException {
        for (int i = 0; i < 1000; i++) {
            int j = sc.nextInt();
            blocks[i] = j;
        }

        String msg;
        sc.nextLine();
        while (sc.hasNextLine()) {
            msg = sc.nextLine();

            String param[] = msg.split(" ");
            //System.out.println (param.length );
            MyFile file = root.findFile(param[0]);
            int start = Integer.parseInt(param[1]);
            blocks[start] = 1;
            //System.out.println (start );
            file.setStartBlock(start);
            sc.nextInt();
            while (sc.hasNextInt()) {
                int num = sc.nextInt();
                file.block.blocks.add(num);
                blocks[num] = 1;
            }
            if (sc.hasNextLine()) {
                sc.nextLine();
            }

        }
    }
}
