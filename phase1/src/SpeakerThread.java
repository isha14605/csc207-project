import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SpeakerThread extends Thread {
    public Socket socket;
    public InputStream inputStream;
    public OutputStream outputStream;

    public SpeakerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            String msg = "Welcome to conference's server !";

            sendMsg(outputStream, msg);
            String userinfo = "Please input your username:";
            sendMsg(outputStream, userinfo);
            String userName = readMsg(inputStream);

            String passwordInfo = "Please input your password:";
            sendMsg(outputStream, passwordInfo);

            String pass = readMsg(inputStream);

            boolean flag = loginCheck(userName, pass);
            while (!flag) {
                msg = "Fail to connect server......";
                sendMsg(outputStream, msg);
                msg = "please check your name and password and login again.....";
                sendMsg(outputStream, msg);
                msg = "please input your name:";
                sendMsg(outputStream, msg);

                userName = readMsg(inputStream);

                msg = "please input your password:";
                sendMsg(outputStream, msg);

                pass = readMsg(inputStream);
                flag = loginCheck(userName, pass);
            }

            // 校验成功后：开始聊天
            msg = "successful connected..... you can chat with your friends now ......";
            sendMsg(outputStream, msg);
            // 聊天处理逻辑
            //读取客户端发来的消息
            msg = readMsg(inputStream);
            //输入bye结束聊天
            while (!"bye".equals(msg)) {
                //给容器中的每个对象转发消息
                for (int i = 0; i < ConferenceServer.listSpeakerThread.size(); i++) {
                    SpeakerThread st = ConferenceServer.listSpeakerThread.get(i);
                    //不该自己转发消息
                    if (st != this) {
                        sendMsg(st.outputStream, userName + "  is say:" + msg);
                    }
                }
                //等待读取下一次的消息
                msg = readMsg(inputStream);
            }

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

    public void showWelcomeMsg() {
    }

    public boolean loginCheck(String name, String pwd) {
        if (name.equals("zhou") && pwd.equals("zhou") || name.equals("user") && pwd.equals("pwd")
                || name.equals("huaxinjiaoyu") && pwd.equals("huaxinjiaoyu")) {

            return true;
        }
        return false;
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
