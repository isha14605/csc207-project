import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Program entry of conference system program
 *
 * @author Hao Liu
 * @version 1.0
 */
public class ConferenceServer {

    /**
     * Thread pool for storing user threads
     */
    public static ArrayList<UserThread> listUserThread = new ArrayList<UserThread>();

    /**
     * ServerSocket is responsible for receiving client connection requests
     * and generating a Socket connected to the client
     */
    private ServerSocket serverSocket = null;

    /**
     * Constructor
     */
    public ConferenceServer() {

    }

    /**
     * Initialize the server, create sersocket, monitor port 8080
     */
    public void initServer() {
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Server established...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Monitor port 8080 and receive the socket sent by the client and create a user thread for the client
     */
    public void runServer() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("User establishes connection!");

                UserThread userThread = new UserThread(socket);
                userThread.start();
                listUserThread.add(userThread);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}