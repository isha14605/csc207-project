import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class OrganizerThread extends Thread{
    public Socket socket;
    public InputStream inputStream;
    public OutputStream outputStream;

    public OrganizerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String welcomeMsg = "Welcome to conference's server !";
            sendMsg(outputStream, welcomeMsg);

            String userInfo = "Please input your username:";
            sendMsg(outputStream, userInfo);
            String userName = readMsg(inputStream);

            String passwordInfo = "Please input your password:";
            sendMsg(outputStream, passwordInfo);
            String passWord = readMsg(inputStream);


        } catch (Exception e) {
            System.out.println("客户端不正常关闭......");
//			e.printStackTrace();
        }

        try {
            inputStream.close();
            outputStream.close();
            socket.close();

            ConferenceServer.listSpeakerThread.remove(this);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void sendMsg(OutputStream os, String s) throws IOException {

        byte[] bytes = s.getBytes();
        os.write(bytes);
        os.write(13);
        os.write(10);
        os.flush();

    }

    public String readMsg(InputStream ins) throws Exception {

        int value = ins.read();

        String str = "";
        while (value != 10) {

            if (value == -1) {
                throw new Exception();
            }
            str = str + ((char) value);
            value = ins.read();
        }
        str = str.trim();
        return str;
    }
}
