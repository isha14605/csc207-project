/**
 * Program entry of conference system program
 *
 * @author Hao Liu
 * @version 1.0
 */
public class App {

    /**
     * Initialize the server and start the server
     * @param args
     */
    public static void main(String[] args) {
        ConferenceServer conferenceServer = new ConferenceServer();
        conferenceServer.initServer();
        conferenceServer.runServer();
    }

}
