import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for handling the user thread of the client
 *
 * @author Hao Liu
 * @version 1.0
 */
public class UserThread extends Thread {

    /**
     * Socket between client and server
     */
    public Socket socket;

    /**
     * Receive user input
     */
    public InputStream inputStream;

    /**
     * The output shown to the user
     */
    public OutputStream outputStream;

    /**
     * Controller responsible for login function
     */
    public LoginController loginController;

    /**
     * Constructor of UserThread
     * Initialize user thread
     * @param socket Socket between client and server
     */
    public UserThread(Socket socket) {

        loginController = new LoginController();
        this.socket = socket;
        try {
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute user thread
     */
    @Override
    public void run() {
        try {

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String welcomeMsg = "Welcome to conference's server !";
            sendMessage(outputStream, welcomeMsg);

            Map<String, String> userInfo = getUserInfo();
            boolean isLogIn = loginController.checkLogIn(userInfo.get("Username"), userInfo.get("Password"));
            while (!isLogIn) {
                sendMessage(outputStream, "Authentication failed, tyr again");
                userInfo = getUserInfo();
                isLogIn = loginController.checkLogIn(userInfo.get("Username"), userInfo.get("Password"));
            }
            String msg = "successful connected....";
            sendMessage(outputStream, msg);

            msg = readMessage();

            while (!"Quit".equals(msg)) {
                for (int i = 0; i < ConferenceServer.listUserThread.size(); i++) {
                    UserThread userThread = ConferenceServer.listUserThread.get(i);
                    if (userThread != this) {
                        sendMessage(userThread.outputStream, msg);
                    }
                }
                msg = readMessage();
            }

        } catch (Exception e) {
            System.out.println("Wrong disconnect");
			e.printStackTrace();
        }

        try {
            inputStream.close();
            outputStream.close();
            socket.close();
            ConferenceServer.listUserThread.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get user information entered by the user
     * @return Map for storing user information, including user email and password
     * @throws Exception
     */
    private Map<String, String> getUserInfo() throws Exception {
        Map<String, String> userInfo = new HashMap<>();
        sendMessage(outputStream, "Please input your email:");
        String userName = readMessage();
        sendMessage(outputStream, "Please input your password:");
        String passWord = readMessage();
        userInfo.put("Username", userName);
        userInfo.put("Password", passWord);
        return userInfo;
    }

    /**
     * Output message to user screen
     * @param os Output stream
     * @param msg String message to send
     * @throws IOException
     */
    public void sendMessage(OutputStream os, String msg) throws IOException {
        os.write(msg.getBytes());
        os.write(13);
        os.write(10);
        os.flush();
    }

    /**
     * Read messages from the input stream
     * @return String message read from the input stream
     * @throws Exception
     */
    public String readMessage() throws Exception {
        int value = inputStream.read();
        String str = "";
        while (value != 10) {
            if (value == -1) {
                throw new Exception();
            }
            str = str + ((char) value);
            value = inputStream.read();
        }
        str = str.trim();
        return str;
    }

}