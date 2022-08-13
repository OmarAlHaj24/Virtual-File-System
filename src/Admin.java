import java.util.ArrayList;

public class Admin extends User{
    private static Admin admin;
    private Admin(){
        super("admin","admin");

    }
    public static Admin getInstance(){
        if (admin == null){
            admin = new Admin ();
        }
        return admin;
    }

    public void Grant(User user, String path, Integer cap){
        ArrayList <Integer> capability = new ArrayList<> (2 );
        capability.add ( cap/10);
        capability.add ( cap%10 );
        user.addCapability ( path,capability );


    }


}
