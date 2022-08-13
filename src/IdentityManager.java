import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class IdentityManager {
    HashMap<String, User> users;
    User currUser;
    String fileNme;

    public IdentityManager(String name) throws IOException {
        users = new HashMap<>();
        fileNme = name;
        currUser = Admin.getInstance();
        users.put("admin", currUser);
        Boot();

    }

    public void createUser(String name, String password) {
        if (!currUser.getName().equals("admin")) {
            System.out.println("You don't have this privilege !!");
            return;
        }
        User temp = new User(name, password);
        addUser(temp);
    }


    public void addUser(User user) {
        if (!users.containsKey(user.getName())) {
            users.put(user.getName(), user);
            System.out.println("User is created");
        } else {
            System.out.println("User already exists !!");
        }

    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    public void Boot() throws IOException {
        String name, pass;
        File file1 = new File("user.txt");
        Scanner myReader = new Scanner(file1);
        while (myReader.hasNextLine()) {
            String msg = myReader.nextLine();
            String arr[] = msg.split(",");
            if (arr.length != 0) {
                name = arr[0];
                //System.out.println (name );
                pass = arr[1];
                //System.out.println (pass );
                User user = new User(name, pass);
                users.put(name, user);
            }

        }
        myReader.close();

        File file2 = new File(fileNme);
        myReader = new Scanner(file2);
        String path, nam;
        int capability;
        while (myReader.hasNextLine()) {
            String text = myReader.nextLine();
            String arr2[] = text.split(",");
            if (arr2.length != 0) {
                path = arr2[0];
                System.out.println(path);
                for (int i = 1; i < arr2.length; i += 2) {
                    nam = arr2[i];
                    //System.out.println (nam );
                    capability = Integer.parseInt(arr2[i + 1]);
                    //System.out.println (capability );
                    User u = users.get(nam);
                    ArrayList<Integer> cap = new ArrayList<>(2);
                    cap.add(capability / 10);
                    cap.add(capability % 10);
                    u.addCapability(path, cap);

                }
            }

        }

        myReader.close();

    }

    public void LogIn(String username, String pass) {
        User temp = users.get(username);
        if (temp == null) {
            System.out.println("User doesn't exist !!");
            return;
        }

        if (!temp.getPassword().equals(pass)) {
            System.out.println("Wrong Password !!");
            return;
        }

        currUser = temp;
    }

    public void tellUser() {
        System.out.println(currUser.getName());
    }

    public Admin isLoggedAdmin() {
        if (currUser.getName().equals("admin")) {
            return (Admin) currUser;
        }
        return null;
    }

    public User getUser(String name) {
        return users.get(name);
    }

    public void shutDown(VirtualFileSystem vfs) throws IOException {
        ArrayList<String> temp = vfs.getFolders();
        HashMap<String, ArrayList<String>> folderCap = new HashMap<>();
        for (int i = 0; i < temp.size(); i++) {
            folderCap.put(temp.get(i), new ArrayList<>());
        }
        for (User user : users.values()) {
            HashMap<String, ArrayList<Integer>> h1 = user.getCapability();
            for (String folderPath : h1.keySet()) {
                ArrayList<Integer> tempArr = h1.get(folderPath);
                String rights = Integer.toString(tempArr.get(0)) + Integer.toString(tempArr.get(1));
                if(folderCap.containsKey(folderPath))
                    folderCap.get(folderPath).add("," + user.getName() + "," + rights);
            }
        }
        FileWriter myWriter = new FileWriter(fileNme);
        for (String folderName : folderCap.keySet()) {
            ArrayList<String> temp1 = folderCap.get(folderName);
            if (temp1.size() == 0) {
                continue;
            }
            myWriter.write(folderName);
            for (int i = 0; i < temp1.size(); i++) {
                myWriter.write(temp1.get(i));
            }
            myWriter.write("\n");
        }
        myWriter.close();
        myWriter = new FileWriter("user.txt");
        for (String name : users.keySet()) {
            if (!name.equals("admin")) {
                User user = users.get(name);
                myWriter.write(user.getName() + "," + user.getPassword() + "\n");
            }
        }
        myWriter.close();
    }
}
