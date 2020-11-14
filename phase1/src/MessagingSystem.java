import java.util.Scanner;

public class MessagingSystem {

    UserManager um = new UserManager();

    public MessagingSystem(){};

    public String[] read_message() {
        Scanner scanner = new Scanner(System.in);
        String[] record;
        record = scanner.nextLine().split("\n");
        scanner.close();
        return record;
    }

    public boolean send_messages(Messageable from, Messageable to, String message){
        message = read_message().toString();
        return um.message(from, to, message);
    }


}
