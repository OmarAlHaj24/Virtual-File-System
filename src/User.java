import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String name;
    private String password;
    private HashMap<String, ArrayList<Integer>> Capability;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        Capability = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addCapability(String fname, ArrayList<Integer> arr) {
        Capability.put(fname, arr);

    }

    public void setCapability(HashMap<String, ArrayList<Integer>> capability) {
        Capability = capability;
    }

    public HashMap<String, ArrayList<Integer>> getCapability() {
        return Capability;
    }
}
