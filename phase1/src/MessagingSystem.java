import java.util.Scanner;

public class MessagingSystem {

    UserManager um = new UserManager();

    public MessagingSystem(){};

    public String[] readMessage() {
        Scanner scanner = new Scanner(System.in);
        String[] record;
        record = scanner.nextLine().split("\n");
        scanner.close();
        return record;
    }

    public boolean sendMessages(Messageable from, Messageable to, String message){
        message = readMessage().toString();
        return um.message(from, to, message);
    }


}
