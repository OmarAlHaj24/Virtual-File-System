import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Directory {
    private String directoryName;
    private String directoryPath;
    private ArrayList<MyFile> files;
    private ArrayList<Directory> subDirectories;
    private boolean deleted = false;

    public Directory(String path, String name){
        directoryName = name;
        directoryPath = path;
        files = new ArrayList<MyFile>();
        subDirectories = new ArrayList<>();
    }


    public String getDirectoryName() {
        return directoryName;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void addFile(MyFile file){
        files.add(file);
    }

    public void addDirectory(Directory directory){
        subDirectories.add(directory);
    }

    public void printDirectoryStructure(int level) {
        for(int j =0; j<level;j++){
            System.out.print("    ");
        }
        System.out.println("-" + this.getDirectoryName());
        level++;
        for(int i =0; i< files.size();i++){
            if(!files.get(i).isDeleted()){
                for(int j =0; j<level;j++){
                    System.out.print("    ");
                }
                System.out.println("." + files.get(i).getFileName());
            }
        }
        for(int i =0; i< subDirectories.size();i++){
            if(!subDirectories.get(i).isDeleted()){
                subDirectories.get(i).printDirectoryStructure(level);
            }
        }
    }

    public void writeToFile(int level, FileWriter writer) throws IOException {
        for(int j =0; j<level;j++){
            writer.write("    ");
        }
        writer.write("-" + this.getDirectoryName()+"\n");
        level++;
        for(int i =0; i< files.size();i++){
            if(!files.get(i).isDeleted()){
                for(int j =0; j<level;j++){
                    writer.write("    ");
                }
                writer.write("." + files.get(i).getFileName()+"\n");
            }
        }
        for(int i =0; i< subDirectories.size();i++){
            if(!subDirectories.get(i).isDeleted()){
                subDirectories.get(i).writeToFile(level, writer);
            }
        }
    }
    public Directory findDirectory(String path){
        if (directoryPath.equals ( path ) && !isDeleted()){
            return this;

        }
        if (subDirectories.size()!=0){
            for (int i=0;i<subDirectories.size ();i++){
                Directory dir = subDirectories.get ( i ).findDirectory ( path );
                if (dir != null && !dir.isDeleted()){
                    return dir;
                }
            }
        }
        return null;
    }

    public MyFile findFileInThisDirectory(String path){
        if (files.size()!=0){
            for (int i=0;i<files.size ();i++){
                MyFile file = files.get(i);
                if(file.getFilePath().equals(path) && !file.isDeleted()){
                    return file;
                }
            }
        }
        return null;
    }
    public Directory findDirectoryInThisDirectory(String path){
        if (subDirectories.size()!=0){
            for (int i=0;i<subDirectories.size ();i++){
                Directory dir = subDirectories.get(i);
                if(dir.getDirectoryPath().equals(path) && !dir.isDeleted()){
                    return dir;
                }
            }
        }
        return null;
    }
    public MyFile findFile(String path){
        if (files.size()!=0){
            for (int i=0;i<files.size ();i++){
                MyFile file = files.get(i);
                if(file.getFilePath().equals(path) && !file.isDeleted()){
                    return file;
                }
            }
        }
        if(subDirectories.size()!=0){
            for (int i =0; i<subDirectories.size();i++){
                if(!subDirectories.get(i).isDeleted()){
                    return subDirectories.get(i).findFile(path);
                }
            }
        }
        return null;
    }
    public void delete(Techniques tech) throws IOException {
        deleted = true;
        for(int i =0; i<files.size(); i++){
            files.get(i).setDeleted(true);
            tech.deAllocate(files.get(i));
        }
        for (int i =0; i<subDirectories.size(); i++){
            subDirectories.get(i).delete(tech);
        }
    }
    public ArrayList < MyFile > getFiles ( ) {
        return files;
    }
    public void setFiles ( ArrayList < MyFile > files ) {
        this.files = files;
    }
    public void setDirectoryName ( String directoryName ) {
        this.directoryName = directoryName;
    }
    public ArrayList < Directory > getSubDirectories ( ) {
        return subDirectories;
    }
    public void getFolders(ArrayList<String> temp){
        if(isDeleted())
            return;
        temp.add ( directoryPath );
        for (int i=0;i<subDirectories.size ();i++){
            subDirectories.get ( i ).getFolders ( temp);

        }
    }
}