import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public interface Techniques {

    public void allocate ( MyFile file, int numOfBlocks);

    public void deAllocate(MyFile file);

    public void display( FileWriter file, Directory root) throws IOException;

    public void printStatus();

    public void readData(Directory root, Scanner sc) throws IOException;
}
