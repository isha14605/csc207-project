import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConferenceServer {

//    public static ArrayList<SpeakerThread> listSpeakerThread = new ArrayList<SpeakerThread>();
//    public static ArrayList<OrganizerThread> listOrganizerThread = new ArrayList<OrganizerThread>();
    public static ArrayList<UserThread> listUserThread = new ArrayList<UserThread>();

    private ServerSocket serverSocket = null;

    public ConferenceServer() {

    }

    public void initServer() {
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Server established...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runServer() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("User establishes connection!");

                UserThread userThread = new UserThread(socket);
                userThread.start();
                listUserThread.add(userThread);

//                if (listOrganizerThread.size() == 0) {
//                    OrganizerThread organizerThread = new OrganizerThread(socket);
//                    organizerThread.start();
//                    listOrganizerThread.add(organizerThread);
//                } else {
//                    SpeakerThread speakerThread = new SpeakerThread(socket);
//                    speakerThread.start();
//                    listSpeakerThread.add(speakerThread);
//                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}