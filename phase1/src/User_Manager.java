import java.util.ArrayList;

public class User_Manager {
    protected ArrayList<User> users = new ArrayList<User>();
    protected ArrayList<String> email = new ArrayList<String>();

    protected User_Manager(){}

    protected boolean add_user(String name, String password, String email){
        if (!this.email.contains(email)){
            User u1 = new User(name, password, email);
            users.add(u1);
            return true;
        }
        return false;
    }

    protected boolean verify_login(String email, String password){
        if (this.email.contains(email)){
            for(User u: users){
                if (u.getEmail().equals(email) && u.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }
}
