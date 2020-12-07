import Controllers.EventSystem;
import Controllers.LoginSystem;
import Entities.Conference;
import Entities.Speaker;
import Entities.User;
import Entities.VIP;
import Gateway.ConferenceSave;
import Gateway.EventSave;
import Gateway.UserSave;
import UseCase.ConferenceManager;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


class Test {

    public static class StartScreen implements ActionListener{
        //Main Screen
        JButton createAccount, login;
        JFrame startScreen;

        //Create Account variables
        JFrame createAcc;
        JButton create, backC;
        JTextField name,email,password;
        int itemIndex;
        String type;
        JComboBox<String> usertype;
        UserManager um = new UserSave().read();

        StartScreen() throws IOException {
            startScreen = new JFrame();
            startScreen.setTitle("Event Interface System 1.0");
            startScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            startScreen.setResizable(true);
            startScreen.setResizable(false);
            startScreen.setSize(500, 380);
            JLabel l = new JLabel("Start Screen");
            startScreen.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            startScreen.add(l);
            l.setBounds(0, 0, 500, 380);
            startScreen.setVisible(true);

            login = new JButton("Login");
            login.setBounds(150,70,200,50);
            login.addActionListener(this);
            startScreen.add(login);

            createAccount = new JButton("Create Account");
            createAccount.setBounds(150,150,200,50);
            createAccount.addActionListener(this);
            startScreen.add(createAccount);


    }

        void createAccount(){
            createAcc = new JFrame();
            createAcc.setTitle("Event Interface System 1.0");
            createAcc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            createAcc.setResizable(true);
            createAcc.setResizable(false);
            createAcc.setSize(500, 380);
            JLabel l = new JLabel("Create Account");
            createAcc.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            createAcc.add(l);
            l.setBounds(0, 0, 500, 380);
            createAcc.setVisible(true);

            //Component Set Up
            String[] labels = new String[]{"Name:","Email:","Password:"};
            int i = 0, x = 100, y = 50, space = 40;

            //Name text field
            l = new JLabel(labels[i]);
            l.setBounds(x,y,100,25);
            createAcc.add(l);

            name = new JTextField();
            name.setBounds(200,y,200,25);
            name.addActionListener(this);
            createAcc.add(name);
            y = y + space;
            i = i+1;

            //email text field
            l = new JLabel(labels[i]);
            l.setBounds(x,y,100,25);
            createAcc.add(l);

            email = new JTextField();
            email.setBounds(200,y,200,25);
            email.addActionListener(this);
            createAcc.add(email);
            y = y + space;
            i = i+1;

            //password text field
            l = new JLabel(labels[i]);
            l.setBounds(x,y,100,25);
            createAcc.add(l);

            password = new JTextField();
            password.setBounds(200,y,200,25);
            password.addActionListener(this);
            createAcc.add(password);
            y = y + space;

            //Drop down box
            l = new JLabel("Account Type:");
            l.setBounds(x,y,100,25);
            createAcc.add(l);
            String[] types = new String[]{"","Attendee","Organizer","Speaker"};
            usertype = new JComboBox<>(types);
            usertype.setBounds(200, y,200,25);
            usertype.addActionListener(this);
            createAcc.add(usertype);
            y = y + space;

            //create button
            create = new JButton("Create Account");
            create.setBounds(180,y,140,25);
            create.addActionListener(this);
            createAcc.add(create);
            y = y + space;

            //Back Button
            backC = new JButton("Back");
            backC.setBounds(200,y,100,25);
            backC.addActionListener(this);
            createAcc.add(backC);


        }


        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource()==login){
                startScreen.setVisible(false);
                try {
                    new LoginScreen();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            if(e.getSource()==createAccount){
                startScreen.setVisible(false);
                createAccount();
            }

            if(e.getSource()==usertype){
                type = usertype.getItemAt(usertype.getSelectedIndex());
                System.out.println(type);
            }

            if(e.getSource()==create){
                try {
                    String nameText = name.getText();
                    String emailText = email.getText();
                    String passwordText = password.getText();

                    if(um.checkUserExists(emailText)){
                            JOptionPane.showMessageDialog(startScreen, "Email already exist within our " +
                                    "system.");
                        }
                    if(!um.checkUserExists(emailText)){
                            JOptionPane.showMessageDialog(startScreen, "Approved, Account has been made");
                            um.addUser(nameText, emailText, passwordText, type);
                        }
                    System.out.println(type);
                    createAcc.setVisible(false);
                        try {
                            new StartScreen();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                    }
                } catch (HeadlessException headlessException) {
                    headlessException.printStackTrace();
                }

                try {
                    new UserSave().save(um);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }

            if(e.getSource()==backC){
                createAcc.setVisible(false);
                startScreen.setVisible(true);
            }

        }
    }

    public static class LoginScreen implements ActionListener {
        JButton loginButton;
        JPasswordField passwordI;
        JTextField userNameI;
        JFrame f = new JFrame();
        UserManager um = new UserSave().read();
        LoginSystem ls = new LoginSystem();

        LoginScreen() throws IOException {
            Border border = BorderFactory.createLineBorder(Color.darkGray, 2);

            //Window set up
            f.setTitle("Event Interface System 1.0");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setResizable(true);
            f.setSize(500, 140);
            JLabel l = new JLabel("Welcome to the Event Systems");
            f.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            l.setBorder(border);
            f.add(l);
            l.setBounds(0, 0, 500, 300);

            //--------Login in----------------

            //user text in box
            JLabel userNameT = new JLabel("User Name:");
            userNameT.setBounds(25, 25, 100, 25);
            f.add(userNameT);

            userNameI = new JTextField();
            userNameI.setBounds(125, 25, 200, 25);
            f.add(userNameI);

            //password set up
            JLabel passWordT = new JLabel("Password:");
            passWordT.setBounds(25, 50, 100, 25);
            f.add(passWordT);

            passwordI = new JPasswordField();
            passwordI.setBounds(125, 50, 200, 25);
            f.add(passwordI);

            //login button
            loginButton = new JButton("Login");
            loginButton.setBounds(350, 25, 100, 50);
            f.add(loginButton);
            loginButton.addActionListener(this);

            f.setVisible(true);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                System.out.println(um.findUser(userNameI.getText()));
                if(ls.checkLogIn(userNameI.getText(), String.valueOf(passwordI.getPassword()))) {
                    f.setVisible(false);

                    try {
                        if(new UserSave().read().findUser(userNameI.getText()).userType()=='A'){
                            new AttendeeScreen(userNameI.getText());
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    try {
                        if(new UserSave().read().findUser(userNameI.getText()).userType()=='O') {
                            new OrganizerScreen();
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        if(new UserSave().read().findUser(userNameI.getText()).userType()=='S') {
                            new SpeakerScreen(userNameI.getText());
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect Login Credentials ");
                }
            }

        }
    }

    public static class MainScreen implements ActionListener{
        JButton organizerOptions, userOptions, logOut;
        JFrame main = new JFrame();
        User userAccount;

        MainScreen(String user) throws IOException {
            main.setTitle("Event Interface System 1.0");
            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main.setResizable(false);
            main.setSize(500, 380);
            main.setVisible(true);
            main.setLayout(null);
            UserManager um = new UserManager();
            this.userAccount = um.findUser(user);

            JLabel l = new JLabel("Welcome " + userAccount.getName());
            l.setBounds(0, 0, 500, 380);
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            Font font = new Font(Font.DIALOG_INPUT,Font.BOLD, 20);
            l.setFont(font);
            main.add(l);

            int y = 80;
            userOptions = new JButton("User Options");
            userOptions.setBounds(150,y,200,30);
            main.add(userOptions);
            userOptions.addActionListener(this);


            if(userAccount.userType() == 'O'){
                y = y + 40;
                organizerOptions = new JButton("Organizer Options");
                organizerOptions.setBounds(150,y,200,30);
                main.add(organizerOptions);
                organizerOptions.addActionListener(this);
                y = y + 40;
            }

            logOut = new JButton("Log Out");
            logOut.setBounds(200,y,100,30);
            main.add(logOut);
            logOut.addActionListener(this);



        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==organizerOptions){
                main.setVisible(false);
                new OrganizerScreen();
            }
            if(e.getSource()==userOptions){
                main.setVisible(false);
                try {
                    new AttendeeScreen(userAccount.getName());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
            if(e.getSource()==logOut){
                main.setVisible(false);
                try {
                    new LoginScreen();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public static class AttendeeScreen implements ActionListener{
        //Attendant screen
        UserManager um = new UserSave().read();
        EventManager em = new EventSave().read();
        User userAccount;
        JFrame attendeeScreen,signupScreen,cancelScreen,inboxScreen,messageScreen,signUpEvent,signUpCon;
        JButton signUp,cancelRegistration,inbox,back;
        String user;

        //sign up buttons
        JButton event,conference, backSU;

        //cancel Event button
        JButton select, backCE;

        //Inbox
        JButton sendMessage, viewMessage, backIB;
        String messageTotal;
        JTextArea messaging;
        JComboBox<String> contact;

        //vip
        JButton vipB;

        AttendeeScreen(String user) throws IOException {
            this.user = user;
            this.userAccount = um.findUser(user);
            attendeeScreen = new JFrame();
            attendeeScreen.setTitle("Event Interface System 1.0");
            attendeeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            attendeeScreen.setResizable(false);
            attendeeScreen.setSize(500, 380);

            Font f = new Font(Font.MONOSPACED,Font.BOLD,20);
            JLabel l = new JLabel("Welcome, " + userAccount.getName());
            l.setFont(f);
            attendeeScreen.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            attendeeScreen.add(l);
            l.setBounds(0, 0, 500, 380);
            attendeeScreen.setVisible(true);

            int y = 40, space = 40;
            signUp = new JButton("Sign Ups");
            signUp.setBounds(150,y,200,25);
            signUp.addActionListener(this);
            attendeeScreen.add(signUp);
            y = y + space;

            cancelRegistration = new JButton("Cancel Registration");
            cancelRegistration.setBounds(150,y,200,25);
            cancelRegistration.addActionListener(this);
            attendeeScreen.add(cancelRegistration);
            y = y + space;

            inbox = new JButton("Inbox");
            inbox.setBounds(150,y,200,25);
            inbox.addActionListener(this);
            attendeeScreen.add(inbox);
            y = y + space;

            if(userAccount.userType() == 'V'){
                vipB = new JButton("Check VIP point");
                vipB.setBounds(150,y,200,25);
                vipB.addActionListener(this);
                attendeeScreen.add(vipB);
                y = y + space;
            }

            back = new JButton("Back");
            if(new UserSave().read().findUser(user).userType()=='A'){
                back.setText("Log Out");
            }
            back.setBounds(200,y,100,25);
            back.addActionListener(this);
            attendeeScreen.add(back);

        }

        void setFrame(JFrame frame, String pageTitle) {
            frame.setTitle("Event Interface System 1.0");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(true);
            frame.setResizable(false);
            frame.setSize(500, 380);
            JLabel l = new JLabel(pageTitle);
            frame.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            frame.add(l);
            l.setBounds(0, 0, 500, 380);
            frame.setVisible(true);
        }

        void SignUp(){
            signupScreen = new JFrame();
            //Window set up
            signupScreen.setTitle("Event Interface System 1.0");
            signupScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            signupScreen.setResizable(true);
            signupScreen.setSize(500, 380);
            JLabel l = new JLabel("Welcome to the Event Systems");
            signupScreen.setLayout(null);
            signupScreen.setVisible(true);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            signupScreen.add(l);
            l.setBounds(0, 0, 500, 380);

            int y = 120;
            event = new JButton("Sign Up Event");
            event.setBounds(25,y,200,40);
            signupScreen.add(event);
            event.addActionListener(this);

            conference = new JButton("Sign Up Conference");
            conference.setBounds(500-225,y,200,40);
            signupScreen.add(conference);
            conference.addActionListener(this);

            backSU = new JButton("Back");
            backSU.setBounds(200,200,100,30);
            signupScreen.add(backSU);
            backSU.addActionListener(this);


        }

        void SignUpEvent(){

            signUpEvent = new JFrame();
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            setFrame(signUpEvent,"Sign Up For Event");

            select = new JButton("Select");
            select.addActionListener(this);
            select.setBounds(75,90,100,25);
            signUpEvent.add(select);

            backSU = new JButton("Back");
            signUpEvent.add(backSU);
            backSU.addActionListener(this);
            backSU.setBounds(75,125,100,25);

            ArrayList<String> con = em.getEventString();
            String[] conference = con.toArray(new String[0]);
            JComboBox<String> conferences = new JComboBox<>(conference);
            conferences.setBounds(25,50,200,25);
            signUpEvent.add(conferences);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Event Info");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            JTextArea eventViewer = new JTextArea();
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No event selected");
            eventViewer.setBounds(250,25,240,320);
            signUpEvent.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
            JTextPane timeInfo = new JTextPane();
            timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
            timeInfo.setBounds(0,190,250,190);
            timeInfo.setEditable(false);
            signUpEvent.add(timeInfo);
            timeInfo.setBorder(time);
        }

        void SignUpConference(){

            signUpCon = new JFrame();
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            setFrame(signUpCon,"Sign Up For Conference");

            select = new JButton("Select");
            select.addActionListener(this);
            select.setBounds(75,90,100,25);
            signUpCon.add(select);

            backSU = new JButton("Back");
            signUpCon.add(backSU);
            backSU.addActionListener(this);
            backSU.setBounds(75,125,100,25);

            ArrayList<String> con = em.getEventString();
            String[] conference = con.toArray(new String[0]);
            JComboBox<String> conferences = new JComboBox<>(conference);
            conferences.setBounds(25,50,200,25);
            signUpCon.add(conferences);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Conference Info");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            JTextArea eventViewer = new JTextArea();
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No event selected");
            eventViewer.setBounds(250,25,240,320);
            signUpCon.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
            JTextPane timeInfo = new JTextPane();
            timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
            timeInfo.setBounds(0,190,250,190);
            timeInfo.setEditable(false);
            signUpCon.add(timeInfo);
            timeInfo.setBorder(time);
        }

        void CancelRegistration(){
            cancelScreen = new JFrame();
            EventManager em = new EventSave().read();
            cancelScreen.setTitle("Event Interface System 1.0");
            cancelScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            cancelScreen.setResizable(false);
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            cancelScreen.setSize(500, 380);
            JLabel l = new JLabel("Cancel Event");
            cancelScreen.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            cancelScreen.add(l);
            l.setBounds(0, 0, 500, 380);
            cancelScreen.setVisible(true);

            select = new JButton("Select");
            select.addActionListener(this);
            select.setBounds(75,90,100,25);
            cancelScreen.add(select);

            backCE = new JButton("Back");
            cancelScreen.add(backCE);
            backCE.addActionListener(this);
            backCE.setBounds(75,125,100,25);

            ArrayList<String> con = em.getEventString();
            String[] conference = con.toArray(new String[0]);
            JComboBox<String> conferences = new JComboBox<>(conference);
            conferences.setBounds(25,50,200,25);
            cancelScreen.add(conferences);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Event Info");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            JTextArea eventViewer = new JTextArea();
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No event selected");
            eventViewer.setBounds(250,25,240,320);
            cancelScreen.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
            JTextPane timeInfo = new JTextPane();
            timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
            timeInfo.setBounds(0,190,250,190);
            timeInfo.setEditable(false);
            cancelScreen.add(timeInfo);
            timeInfo.setBorder(time);

        }

        void inboxScreen(){
            inboxScreen = new JFrame();
            inboxScreen.setTitle("Event Interface System 1.0");
            inboxScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            inboxScreen.setResizable(false);
            inboxScreen.setSize(500, 380);
            JLabel l = new JLabel("Start Screen");
            inboxScreen.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            inboxScreen.add(l);
            l.setBounds(0, 0, 500, 380);
            inboxScreen.setVisible(true);

            sendMessage = new JButton("Send message");
            sendMessage.setBounds(150,60,200,40);
            sendMessage.addActionListener(this);
            inboxScreen.add(sendMessage);

            viewMessage = new JButton("View Message History");
            viewMessage.setBounds(150,120,200,40);
            viewMessage.addActionListener(this);
            inboxScreen.add(viewMessage);

            backIB =  new JButton("Back");
            backIB.setBounds(200,180,100,25);
            backIB.addActionListener(this);
            inboxScreen.add(backIB);


        }

        void messageScreen(){
            messageScreen = new JFrame();
            setFrame(messageScreen, "Message Screen");
            Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

            ArrayList<String> contacts = userAccount.getContacts();
            String[] contact = contacts.toArray(new String[0]);
            JComboBox<String> comboBox = new JComboBox<>(contact);
            comboBox.setBounds(150,50,200,30);
            messageScreen.add(comboBox);

            messaging = new JTextArea();
            messaging.setBounds(100,90,300,80);
            messaging.setBorder(raisedetched);
            messageScreen.add(messaging);

            sendMessage = new JButton("Send");
            sendMessage.setBounds(200,190,100,25);
            sendMessage.addActionListener(this);
            messageScreen.add(sendMessage);

            messageScreen.add(backIB);
            backIB.setBounds(200, 235,100,25);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource()==signUp){
                attendeeScreen.setVisible(false);
                SignUp();
            }

            if(e.getSource()==sendMessage){
                inboxScreen.setVisible(false);
                messageScreen();
            }

            if(e.getSource()==event){
                signupScreen.setVisible(false);
                SignUpEvent();
            }

            if(e.getSource()==conference){
                signupScreen.setVisible(false);
                SignUpConference();
            }

            if(e.getSource()==cancelRegistration){
                attendeeScreen.setVisible(false);
                CancelRegistration();
            }

            if(e.getSource()==inbox){
                attendeeScreen.setVisible(false);
                inboxScreen();
            }

            if(e.getSource()==back){
                attendeeScreen.setVisible(false);
                try {
                    if(new UserSave().read().findUser(user).userType() == 'O') {
                        new MainScreen(user);
                    }
                    else{
                        new LoginScreen();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            if(e.getSource()==backSU){
                signupScreen.setVisible(false);
                attendeeScreen.setVisible(true);

                if(signUpEvent!=null&&signUpEvent.isVisible()){
                    signUpEvent.setVisible(false);
                }
                if(signUpCon!=null&&signUpCon.isVisible()){
                    signUpCon.setVisible(false);
                }
            }

            if(e.getSource()==backIB){
                inboxScreen.setVisible(false);
                attendeeScreen.setVisible(true);
                if(messageScreen!=null&&messageScreen.isVisible()){
                    messageScreen.setVisible(false);
                }
            }

            if(e.getSource()==select){
                int reply = JOptionPane.showConfirmDialog(cancelRegistration,"Are you sure cancel your " +
                                "participation in this event?",
                        "Cancel Participation?", JOptionPane.YES_NO_OPTION);
                if(reply==0) {
                    System.out.println("Event Removed");
                }
            }

            if(e.getSource()==backCE){
                cancelScreen.setVisible(false);
                attendeeScreen.setVisible(true);
            }

            if(e.getSource()==viewMessage){
                messageTotal = JOptionPane.showInputDialog(inboxScreen,"How many messages " +
                        "would you like to see?");

            }

            if(e.getSource()==vipB){
                VIP vip = (VIP) userAccount;

                JOptionPane.showMessageDialog(attendeeScreen,"Current Rank: " +
                        vip.getMemberStatus() + "\n Current Points: " + vip.getMemberPoints());
            }
        }
    }

    public static class SpeakerScreen implements ActionListener{
        //main Screen
        JFrame speakerScreen,inboxScreen, current;
        JButton browseB, inboxB, logOut;
        EventManager em = new EventSave().read();
        Speaker speaker;

        //Browse Events
        JFrame browseEvent;
        JButton select,back;

        //Inbox
        JFrame messageEvent,messageUser;
        JButton sendMessageEvent,sendMessageUser, viewMessage, sendMessage;
        String messageTotal;
        JTextArea messaging;
        JComboBox<String> contact;

        UserManager um = new UserSave().read();

        void setFrame(JFrame frame, String pageTitle) {
            frame.setTitle("Event Interface System 1.0");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(true);
            frame.setResizable(false);
            frame.setSize(500, 380);
            JLabel l = new JLabel(pageTitle);
            frame.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            frame.add(l);
            l.setBounds(0, 0, 500, 380);
            frame.setVisible(true);
        }

        SpeakerScreen(String user) throws IOException {
            speaker = (Speaker) um.findUser(user);
            speakerScreen = new JFrame();
            setFrame(speakerScreen,"Main welcome " + um.findUser(user).getName());

            browseB = new JButton("Browse My Events");
            browseB.setBounds(150,40,200,30);
            browseB.addActionListener(this);
            speakerScreen.add(browseB);

            inboxB = new JButton("Inbox");
            inboxB.setBounds(150,80,200,30);
            inboxB.addActionListener(this);
            speakerScreen.add(inboxB);

            logOut = new JButton("LogOut");
            logOut.setBounds(200,140, 100,30);
            logOut.addActionListener(this);
            speakerScreen.add(logOut);

        }

        void browseEvents(){
            browseEvent = new JFrame();
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            setFrame(browseEvent, "Browse Event");
            current = browseEvent;

            select = new JButton("Select");
            select.addActionListener(this);
            select.setBounds(75,90,100,25);
            browseEvent.add(select);

            back = new JButton("Back");
            browseEvent.add(back);
            back.addActionListener(this);
            back.setBounds(75,125,100,25);

            ArrayList<String> con = em.getEventString();
            String[] conference = con.toArray(new String[0]);
            JComboBox<String> conferences = new JComboBox<>(conference);
            conferences.setBounds(25,50,200,25);
            browseEvent.add(conferences);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Event Info");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            JTextArea eventViewer = new JTextArea();
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No event selected");
            eventViewer.setBounds(250,25,240,320);
            browseEvent.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
            JTextPane timeInfo = new JTextPane();
            timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
            timeInfo.setBounds(0,190,250,190);
            timeInfo.setEditable(false);
            browseEvent.add(timeInfo);
            timeInfo.setBorder(time);

        }

        void viewInbox(){

            inboxScreen = new JFrame();
            setFrame(inboxScreen,"Inbox");
            current = inboxScreen;

            sendMessageEvent = new JButton("Message Event");
            sendMessageEvent.setBounds(150,60,200,40);
            sendMessageEvent.addActionListener(this);
            inboxScreen.add(sendMessageEvent);

            sendMessageUser = new JButton("Message User");
            sendMessageUser.setBounds(150,110,200,40);
            sendMessageUser.addActionListener(this);
            inboxScreen.add(sendMessageUser);

            viewMessage = new JButton("View Message History");
            viewMessage.setBounds(150,160,200,40);
            viewMessage.addActionListener(this);
            inboxScreen.add(viewMessage);

            back =  new JButton("Back");
            back.setBounds(200,220,100,25);
            back.addActionListener(this);
            inboxScreen.add(back);
        }

        void messageEventScreen(){
            messageEvent = new JFrame();
            current = messageEvent;
            setFrame(messageEvent, "Message Screen");
            Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

            ArrayList<Integer> contacts = speaker.getEventsSpeaking();
            Integer[] contact = contacts.toArray(new Integer[0]);
            JComboBox<Integer> comboBox = new JComboBox<>(contact);
            comboBox.setBounds(225,50,175,30);
            messageEvent.add(comboBox);

            Font f = new Font(Font.DIALOG_INPUT,Font.BOLD,12);
            JLabel l = new JLabel("Events Speaker:");
            l.setBounds(100,50,125,30);
            l.setFont(f);
            messageEvent.add(l);

            messaging = new JTextArea();
            messaging.setBounds(100,90,300,80);
            messaging.setBorder(raisedetched);
            messageEvent.add(messaging);

            sendMessage = new JButton("Send");
            sendMessage.setBounds(200,190,100,25);
            sendMessage.addActionListener(this);
            messageEvent.add(sendMessage);

            messageEvent.add(back);
            back.setBounds(200, 235,100,25);
        }

        void messageUserScreen(){
            messageUser = new JFrame();
            current = messageUser;
            setFrame(messageUser, "Message Screen");
            Border raisedEtched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

            ArrayList<String> contacts = speaker.getContacts();
            String[] contact = contacts.toArray(new String[0]);
            JComboBox<String> comboBox = new JComboBox<>(contact);
            comboBox.setBounds(200,50,200,30);
            messageUser.add(comboBox);

            Font f = new Font(Font.DIALOG_INPUT,Font.BOLD,13);
            JLabel l = new JLabel("Contacts:");
            l.setBounds(100,50,100,30);
            l.setFont(f);
            messageUser.add(l);

            messaging = new JTextArea();
            messaging.setBounds(100,90,300,80);
            messaging.setBorder(raisedEtched);
            messageUser.add(messaging);

            sendMessage = new JButton("Send");
            sendMessage.setBounds(200,190,100,25);
            sendMessage.addActionListener(this);
            messageUser.add(sendMessage);

            messageUser.add(back);
            back.setBounds(200, 235,100,25);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==browseB){
                speakerScreen.setVisible(false);
                browseEvents();
            }

            if(e.getSource()==inboxB){
                speakerScreen.setVisible(false);
                viewInbox();
            }

            if(e.getSource()==sendMessageEvent){
                inboxScreen.setVisible(false);
                messageEventScreen();
            }
            if(e.getSource()==sendMessageUser){
                inboxScreen.setVisible(false);
                messageUserScreen();
            }

            if(e.getSource()==logOut){
                speakerScreen.setVisible(false);
                try {
                    new StartScreen();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            if(e.getSource()==back){
                current.setVisible(false);
                speakerScreen.setVisible(true);
            }

        }
    }

    public static class OrganizerScreen implements ActionListener {
        JButton aeButton, crButton, csButton, eoButton, veButton, vrButton, ibButton, exit;
        public JFrame f = new JFrame();
        ConferenceManager cM;
        RoomManager rM;
        EventManager eM;

        OrganizerScreen() {
            //Window set up
            f.setTitle("Event Interface System 1.0");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setResizable(true);
            f.setSize(500, 380);
            JLabel l = new JLabel("Event Control Systems");
            f.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            f.add(l);
            l.setBounds(0, 0, 500, 300);

            String t,p;
            t = "Create/Manage";
            p = " Conference";
            aeButton = new JButton(t+p);
            crButton = new JButton("Create/Manage Events");
            csButton = new JButton("Create/Manage Rooms");
            eoButton = new JButton("Manage Users");
            veButton = new JButton("SignUp");
            vrButton = new JButton("Cancel Registration");
            ibButton = new JButton("Inbox");
            exit = new JButton("Exit");

            f.add(aeButton);
            f.add(crButton);
            f.add(csButton);
            f.add(eoButton);
            f.add(veButton);
            f.add(vrButton);
            f.add(ibButton);
            f.add(exit);

            Dimension d = new Dimension(195, 50);
            int bx = 50;
            int by = 50;
            aeButton.setSize(d);
            crButton.setSize(d);
            csButton.setSize(d);
            eoButton.setSize(d);
            veButton.setSize(d);
            vrButton.setSize(d);
            ibButton.setSize(d);
            exit.setSize(d);

            aeButton.setBounds(bx, by, 190, 30);
            crButton.setBounds(bx + 200, by, 190, 30);
            csButton.setBounds(bx, by + 60, 190, 30);
            eoButton.setBounds(bx + 200, by + 60, 190, 30);
            veButton.setBounds(bx, by + 120, 190, 30);
            vrButton.setBounds(bx + 200, by + 120, 190, 30);
            ibButton.setBounds(bx, by + 180, 190, 30);
            exit.setBounds(bx + 200, by + 180, 190, 30);

            exit.addActionListener(this);
            aeButton.addActionListener(this);
            crButton.addActionListener(this);
            csButton.addActionListener(this);
            eoButton.addActionListener(this);
            veButton.addActionListener(this);
            vrButton.addActionListener(this);
            ibButton.addActionListener(this);


            f.setVisible(true);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == exit) {
                f.setVisible(false);
                try {
                    new LoginScreen();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            if (e.getSource() == aeButton) {
                f.setVisible(false);
                try {
                    new ConferenceScreen(f);
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }

            if (e.getSource() == crButton) {
                f.setVisible(false);
                new EventScreen();
            }

            if (e.getSource()==csButton){
                f.setVisible(false);
                new RoomScreen(f,rM);
            }

            if(e.getSource()==eoButton){
            }

            if (e.getSource() == eoButton
                    || e.getSource() == veButton || e.getSource() == vrButton || e.getSource() == ibButton) {
                JOptionPane.showMessageDialog(null, "Not implemented yet");
            }

        }
    }

    public static class ConferenceScreen implements ActionListener{
        JFrame cS = new JFrame(), f;
        JButton create, modify, view, back;
        JFrame cc  = new JFrame(),mc;
        JButton submit;
        EventSystem eventSystem = new EventSystem();
        ConferenceManager cM = new ConferenceSave().read();

        //create Conference
        JTextField nameText,descText;
        JFormattedTextField date;
        JComboBox<String> timeOptionsS, timeOptionE;

        //Modify
        JButton select = new JButton("Select");

        //View
        JFrame vc = new JFrame(),current = new JFrame();
        JComboBox<String> conferences;
        JTextPane timeInfo = new JTextPane();
        JTextArea eventViewer = new JTextArea();


        ConferenceScreen(JFrame f) throws IOException, ClassNotFoundException, NullPointerException {
            this.f = f;
            cS.setTitle("Event Interface System 1.0");
            cS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            cS.setResizable(true);
            cS.setSize(500, 380);
            JLabel l = new JLabel("Conference Control Systems");
            cS.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            cS.add(l);
            l.setBounds(0, 0, 500, 380);

            create = new JButton("Create Conference");
            modify = new JButton("Manage Conference");
            view = new JButton("View Conferences");
            back = new JButton("Back");

            ArrayList<JButton> b = new ArrayList<>();
            b.add(create);b.add(modify);b.add(view);b.add(back);
            Dimension d = new Dimension(200, 50);
            int y = 30;

            for(JButton button: b){

                int x = 150;
                if(button == back){
                    button.setSize(100,25);
                    button.setBounds(x+50,y,100,25);
                    button.addActionListener(this);
                    cS.add(button);
                    break;
                }
                button.setSize(d);
                button.setBounds(x,y,200,50);
                button.addActionListener(this);
                cS.add(button);
                y = y + 70;


            }

            cS.setVisible(true);
        }

        void setFrame(JFrame frame, String pageTitle){
            frame.setTitle("Event Interface System 1.0");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(true);
            frame.setResizable(false);
            frame.setSize(500, 380);
            JLabel l = new JLabel(pageTitle);
            frame.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            frame.add(l);
            l.setBounds(0, 0, 500, 380);
            frame.setVisible(true);
        }

        void createConference() throws ParseException {
            setFrame(cc,"Create Conference Panel");

            JLabel name,desc,dateT,start, end;

            name = new JLabel("Name");desc = new JLabel("Description"); dateT = new JLabel("Date: year-mm-dd");
            start = new JLabel("Start Time"); end = new JLabel("End Time");
            ArrayList<JLabel> labels = new ArrayList<>();

            labels.add(name);labels.add(desc);

            nameText = new JTextField();descText = new JTextField();
            MaskFormatter mf = new MaskFormatter("####-##-##");
            mf.setPlaceholderCharacter('#');  date = new JFormattedTextField(mf);

            ArrayList<JTextField> tf = new ArrayList<>();
            tf.add(nameText);tf.add(descText);
            int y = 25;
            int x = 125;

            for(JTextField box: tf){
                JLabel label = labels.get(tf.indexOf(box));
                label.setBounds(25,y,100,25);
                box.setBounds(x,y,275,25);
                cc.add(box);
                cc.add(label);
                y = y + 50;
            }

            date.setBounds(x+75,y,100,25);
            dateT.setBounds(25,y,125,25);
            cc.add(dateT);
            cc.add(date);

            String[] timeslot = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00",
                    "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00",
                    "19:00", "20:00", "21:00", "22:00", "23:00"};

            timeOptionsS = new JComboBox<>(timeslot);
            timeOptionsS.setBounds(125,y+50,75,25);
            start.setBounds(50,y+50,100,25);
            cc.add(start);
            cc.add(timeOptionsS);

            timeOptionE = new JComboBox<>(timeslot);
            timeOptionE.setBounds(x+175,y+50,75,25);
            end.setBounds(x+110,y+50,100,25);
            cc.add(end);
            cc.add(timeOptionE);

            submit = new JButton("Submit");
            submit.setBounds(125,y+100,100, 50);
            back.setBounds(250,y+100,100,50);
            submit.addActionListener(this); back.addActionListener(this);
            cc.add(submit);
            cc.add(back);

        }

        void modifyConference(){
            JFrame mc = new JFrame();
            this.mc = mc;
            current = mc;
            setFrame(mc,"Modify Conference Panel");
            JLabel name = new JLabel("Select A Conference");

            ArrayList<String> con = cM.getConferenceList();
            String[] conference = con.toArray(new String[0]);
            conferences = new JComboBox<>(conference);

            conferences.setBounds(150,100,200,50);
            name.setBounds(195, 75,200,25);
            select.setBounds(200,175,100,25);
            select.addActionListener(this);

            mc.add(conferences);
            mc.add(select);
            mc.add(name);
            mc.add(back);
        }

        void viewConference(){
            current = vc;
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            vc.setResizable(false);
            setFrame(vc,"View Conference Panel");

            JLabel name = new JLabel("Select A Conference");
            name.setBounds(60,60,200,25);
            vc.add(name);

            vc.add(back);
            back.setBounds(75,160,100,25);

            ArrayList<String> con = cM.getConferenceList();
            String[] conference = con.toArray(new String[0]);
            conferences = new JComboBox<>(conference);
            conferences.setBounds(25,100,200,25);
            vc.add(conferences);
            conferences.addActionListener(this);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Event");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No Events Scheduled");
            eventViewer.setBounds(250,25,240,320);
            vc.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
            timeInfo.setText("\nDate: ----\n\nStart Time: -----\n\nEnd Time: -----");
            timeInfo.setBounds(0,190,250,190);
            timeInfo.setEditable(false);
            vc.add(timeInfo);
            timeInfo.setBorder(time);
        }

        void update(JTextPane timeInfo, JTextArea eventViewer){
            current = vc;
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            vc.setResizable(false);
            setFrame(vc,"View Conference Panel");

            JLabel name = new JLabel("Select A Conference");
            name.setBounds(60,60,200,25);
            vc.add(name);

            vc.add(back);
            back.setBounds(75,160,100,25);

            ArrayList<String> con = cM.getConferenceList();
            String[] conference = con.toArray(new String[0]);
            conferences = new JComboBox<>(conference);
            conferences.setBounds(25,100,200,25);
            vc.add(conferences);
            conferences.addActionListener(this);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Event");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            eventViewer.setEditable(false);
            eventViewer.setBounds(250,25,240,320);
            vc.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
            timeInfo.setBounds(0,190,250,190);
            timeInfo.setEditable(false);
            vc.add(timeInfo);
            timeInfo.setBorder(time);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==back){
                cS.setVisible(false);
                f.setVisible(true);
                current.setVisible(false);
                if(!(cc == null)&&cc.isVisible()){
                    cc.setVisible(false);
                    f.setVisible(false);
                    cS.setVisible(true);
                }
                if(!(mc == null)&&mc.isVisible()){
                    mc.setVisible(false);
                    f.setVisible(false);
                    cS.setVisible(true);
                }
            }

            if(e.getSource()==create){
                try {
                    createConference();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                cS.setVisible(false);
            }

            if(e.getSource()==conferences){
                String conferenceN = conferences.getItemAt(conferences.getSelectedIndex());
                System.out.println(conferences.getItemAt(conferences.getSelectedIndex()));

                    timeInfo.setText("\nDate: " + cM.findConference(conferenceN).getConfDate() +
                            "\n\nStart Time: " + cM.findConference(conferenceN).getStartTime()+
                            "\n\nEnd Time: " + cM.findConference(conferenceN).getEndTime());
                    eventViewer.setText("Conference :" + conferenceN + "\n\nDescription :" +
                            cM.findConference(conferenceN).getConfDescription());
                    current.setVisible(false);
                    update(timeInfo,eventViewer);


            }

            if(e.getSource()==modify){
                modifyConference();
                cS.setVisible(false);
            }

            if(e.getSource()==select){
                current.setVisible(false);
                try {
                    new ModifyConference(conferences.getItemAt(conferences.getSelectedIndex()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            if(e.getSource()==view){
                viewConference();
                cS.setVisible(false);
            }

            if(e.getSource()==submit){
                if(eventSystem.addConference(nameText.getText(), descText.getText(),
                        timeOptionsS.getItemAt(timeOptionsS.getSelectedIndex()),
                        timeOptionE.getItemAt(timeOptionE.getSelectedIndex()),date.getText())){
                    JOptionPane.showMessageDialog(cc,"Conference was Added");
                    try {
                        new ConferenceSave().save(eventSystem.getCm());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(cc,"Conference was not added due to:");
                }

            }
        }
    }
    
    public static class EventScreen implements ActionListener{
        JFrame eS = new JFrame(), f, currentFrame;
        String currentPage;
        JButton create, modify, view, back, select;
        EventManager eM = new EventManager();
        RoomManager rm = new RoomManager(eM);
        //Event info
        JTextField enI, edI, timeS, timeE, dateI;
        //MEO Buttons
        JButton scheduleSpeaker, scheduleRoom, cancel, changeTime, backMeo;
        //Create Event
        JButton submit;
        JTextField nameText,descText,capacityText;
        JFormattedTextField date;
        JComboBox<String> timeOptionsS, timeOptionE;
        JList<String> list;

        EventScreen(){
            eS.setTitle("Event Interface System 1.0");
            eS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            eS.setResizable(true);
            eS.setSize(500, 380);
            JLabel l = new JLabel("Event Control Systems");
            eS.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            eS.add(l);
            l.setBounds(0, 0, 500, 380);

            create = new JButton("Create Event");
            modify = new JButton("Manage Events");
            view = new JButton("View Event");
            back = new JButton("Back");

            ArrayList<JButton> b = new ArrayList<>();
            b.add(create);b.add(modify);b.add(view);b.add(back);
            Dimension d = new Dimension(200, 40);
            int y = 30;

            for(JButton button: b){

                int x = 150;
                if(button == back){
                    button.setSize(100,25);
                    button.setBounds(x+50,y,100,25);
                    button.addActionListener(this);
                    eS.add(button);
                    break;
                }
                button.setSize(d);
                button.setBounds(x,y,200,50);
                button.addActionListener(this);
                eS.add(button);
                y = y + 70;


            }

            eS.setVisible(true);
        }

        void setFrame(JFrame frame, String pageTitle){
            frame.setTitle("Event Interface System 1.0");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setSize(500, 380);
            JLabel l = new JLabel(pageTitle);
            frame.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            frame.add(l);

            frame.setVisible(true);
        }

        void createEvent() throws ParseException {
            JFrame ce = new JFrame();
            setFrame(ce,"Create Event");
            currentFrame = ce;
            JLabel name,desc,dateT,start, end;
            name = new JLabel("Event Name");desc = new JLabel("Description"); dateT = new JLabel("Date: year-mm-dd");
            start = new JLabel("Start Time"); end = new JLabel("End Time");

            ArrayList<JLabel> labels = new ArrayList<>();

            labels.add(name);labels.add(desc);

            nameText = new JTextField();
            descText = new JTextField();
            MaskFormatter mf = new MaskFormatter("####-##-##");
            mf.setPlaceholderCharacter('#');
            date = new JFormattedTextField(mf);

            ArrayList<JTextField> tf = new ArrayList<>();
            tf.add(nameText);tf.add(descText);
            int y = 25;
            int x = 200;

            for(JTextField box: tf){
                JLabel label = labels.get(tf.indexOf(box));
                label.setBounds(100,y,100,25);
                box.setBounds(x,y,200,25);
                ce.add(box);
                ce.add(label);
                y = y + 40;
            }

            date.setBounds(250,y,100,25);
            dateT.setBounds(100,y,125,25);
            ce.add(dateT);
            date.setHorizontalAlignment(SwingConstants.CENTER);
            ce.add(date);

            String[] timeslot = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00",
                    "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00",
                    "19:00", "20:00", "21:00", "22:00", "23:00"};

            timeOptionsS = new JComboBox<>(timeslot);
            timeOptionsS.setBounds(175,y+40,75,25);
            start.setBounds(100,y+40,75,25);
            ce.add(start);
            ce.add(timeOptionsS);

            timeOptionE = new JComboBox<>(timeslot);
            timeOptionE.setBounds(325,y+40,75,25);
            end.setBounds(260,y+40,100,25);
            ce.add(end);
            ce.add(timeOptionE);

            JLabel capacity = new JLabel("Capacity");
            capacity.setBounds(100,y+80,100,25);
            ce.add(capacity);

            capacityText = new JTextField();
            capacityText.setBounds(250,y+80,100,25);
            capacityText.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                        capacityText.setEditable(true);
                    } else {
                        JOptionPane.showMessageDialog(null,"* Enter only numeric digits(0-9)");
                        capacityText.setText("");
                    }
                }
            });
            ce.add(capacityText);

            JLabel techOptions = new JLabel("Tech options");
            techOptions.setBounds(100,y+120,100,25);
            ce.add(techOptions);

            Border blackLine = BorderFactory.createLineBorder(Color.black);
            ArrayList<String> options = rm.getTechOptions();
            String[] data = options.toArray(new String[0]);
            list = new JList<>(data);
            list.setBounds(200,y+120,200,50);
            list.setLayoutOrientation(JList.VERTICAL_WRAP);
            list.setVisibleRowCount(-1);
            JScrollPane listScroller = new JScrollPane(list);
            listScroller.setPreferredSize(new Dimension(200,50));
            list.setBorder(blackLine);
            ce.add(list);


            submit = new JButton("Submit");
            submit.setBounds(125,300,100, 25);
            back.setBounds(250,300,100,25);
            submit.addActionListener(this);
            back.addActionListener(this);
            ce.add(submit);
            ce.add(back);
        }

        void modifyEvent(){
            JFrame me = new JFrame();
            setFrame(me,"Modify Event");
            currentFrame = me;
            currentPage = "modifyEvent";
            JLabel name = new JLabel("Select A Event");
            select = new JButton("Select");
            select.addActionListener(this);

            ArrayList<String> con = eM.getEventString();
            String[] conference = con.toArray(new String[0]);
            JComboBox<String> conferences = new JComboBox<>(conference);

            conferences.setBounds(150,100,200,50);
            name.setBounds(195, 75,200,25);
            select.setBounds(200,175,100,25);

            me.add(conferences);
            me.add(select);
            me.add(name);
            me.add(back);

        }

        void viewEvents(){
            JFrame ve = new JFrame();
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            setFrame(ve, "view Events");
            currentFrame = ve;

            JLabel name = new JLabel("Select A Event");
            name.setBounds(80,25,200,25);
            ve.add(name);

            select = new JButton("Select");
            select.addActionListener(this);
            select.setBounds(75,90,100,25);
            ve.add(select);

            ve.add(back);
            back.setBounds(75,125,100,25);

            ArrayList<String> con = eM.getEventString();
            String[] conference = con.toArray(new String[0]);
            JComboBox<String> conferences = new JComboBox<>(conference);
            conferences.setBounds(25,50,200,25);
            ve.add(conferences);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Event Info");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            JTextArea eventViewer = new JTextArea();
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No event selected");
            eventViewer.setBounds(250,25,240,320);
            ve.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
            JTextPane timeInfo = new JTextPane();
            timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
            timeInfo.setBounds(0,190,250,190);
            timeInfo.setEditable(false);
            ve.add(timeInfo);
            timeInfo.setBorder(time);
        }

        void modifyEventOptions(){
            JFrame meo = new JFrame();
            setFrame(meo,"Modify Menu");
            currentFrame = meo;

            JButton[] buttons = new JButton[]{scheduleSpeaker, scheduleRoom, cancel, changeTime};
            String[] names = new String[]{"Schedule Speaker","Schedule Room","Cancel Event","Change Time"};
            int y = 35, x = 140,i = 0;
            for(JButton b: buttons){

                b = new JButton(names[i]);
                b.setBounds(x,y,220,35);
                b.addActionListener(this);
                meo.add(b);
                y = y +50;
                i = i + 1;

            }

            backMeo = new JButton("Back");
            backMeo.addActionListener(this);
            backMeo.setBounds(200,240,100, 30);
            meo.add(backMeo);



        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==back||e.getSource()==backMeo){
                eS.setVisible(false);
                new EventScreen();
                if(!(currentFrame == null)) {
                    currentFrame.setVisible(false);
                }
            }

            if(e.getSource()==create){
                try {
                    createEvent();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                eS.setVisible(false);
            }

            if(e.getSource()==modify){
                modifyEvent();
                eS.setVisible(false);
            }

            if(e.getSource()==view){
                viewEvents();
                eS.setVisible(false);
            }

            if(e.getSource()==select){
                if(currentPage.equals("modifyEvent")){
                    currentFrame.setVisible(false);
                    modifyEventOptions();

                }
            }

            if(e.getSource()==submit){
                int choice = JOptionPane.showConfirmDialog(null, "Is this a vip event?", "VIP?",
                        JOptionPane.YES_NO_CANCEL_OPTION);

                if(choice == 0 ||choice == 1){
                    if(choice == 0){
                        boolean vip = true;
                    }else {
                        boolean vip = false;
                    }
                    String[] types = {"Talk","Party","Panel"};
                     Object type = JOptionPane.showInputDialog(null,"What type of event is this?","Event Type",
                            JOptionPane.QUESTION_MESSAGE,null,types,0);
                     System.out.println(type.toString());
                }

            }
            
        }
    }

    public static class RoomScreen implements ActionListener {
        JFrame rS = new JFrame(), f, cr, currentFrame, mro;
        //main buttons
        String currentPage;
        JButton create, modify, view, back, submit = new JButton(), select = new JButton(), backmro;
        //Manager
        EventManager em = new EventManager();
        RoomManager rm = new RoomManager(em);
        //Create Rooms Information
        JTextField roomNameI, capacity, timeS, timeE;
        //modify room options
        JButton addTech, removeTech, changeCapacity, removeRoom;

        RoomScreen(JFrame f, RoomManager rm) {
            this.f = f;
            rS.setTitle("Event Interface System 1.0");
            rS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            rS.setResizable(true);
            rS.setSize(500, 380);
            JLabel l = new JLabel("Room Control Systems");
            rS.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            rS.add(l);
            l.setBounds(0, 0, 500, 380);

            create = new JButton("Create Room");
            modify = new JButton("Manage Rooms");
            view = new JButton("View Rooms");
            back = new JButton("Back");

            ArrayList<JButton> b = new ArrayList<>();
            b.add(create);
            b.add(modify);
            b.add(view);
            b.add(back);
            Dimension d = new Dimension(200, 50);
            int y = 30;

            for (JButton button : b) {

                int x = 150;
                if (button == back) {
                    button.setSize(100, 25);
                    button.setBounds(x + 50, y, 100, 25);
                    button.addActionListener(this);
                    rS.add(button);
                    break;
                }
                button.setSize(d);
                button.setBounds(x, y, 200, 50);
                button.addActionListener(this);
                rS.add(button);
                y = y + 70;


            }

            rS.setVisible(true);
        }

        void setFrame(JFrame frame, String pageTitle) {
            frame.setTitle("Event Interface System 1.0");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(true);
            frame.setResizable(false);
            frame.setSize(500, 380);
            JLabel l = new JLabel(pageTitle);
            frame.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            frame.add(l);
            l.setBounds(0, 0, 500, 380);
            frame.setVisible(true);
        }

        void createRoom() {
            cr = new JFrame();
            setFrame(cr, "Create Room");
            currentFrame = cr;

            JLabel eventName = new JLabel("Room Name");
            cr.add(eventName);
            JLabel capacity = new JLabel("Capacity");
            cr.add(capacity);
            JLabel timeT = new JLabel("Time (HH:mm)");
            cr.add(timeT);
            submit = new JButton("Submit");
            back = new JButton("Back");

            eventName.setBounds(90, 20, 100, 25);
            capacity.setBounds(90, 60, 100, 25);
            timeT.setBounds(90, 100, 100, 25);

            roomNameI = new JTextField();
            this.capacity = new JTextField();
            timeS = new JTextField();
            timeE = new JTextField();

            cr.add(roomNameI);
            cr.add(this.capacity);
            cr.add(timeS);
            cr.add(timeE);

            roomNameI.setBounds(200, 20, 170, 25);
            this.capacity.setBounds(200, 60, 170, 25);
            timeS.setBounds(200, 100, 70, 25);
            timeE.setBounds(300, 100, 70, 25);

            cr.add(submit);
            submit.setBounds(230, 180, 100, 25);

            cr.add(back);
            back.setBounds(230, 220, 100, 25);


            submit.addActionListener(this);
            back.addActionListener(this);
        }

        void modifyRooms() {
            JFrame mr = new JFrame();
            currentFrame = mr;
            setFrame(mr, "Modify Rooms");
            currentPage = "modifyRoom";
            select = new JButton("Select");
            select.addActionListener(this);

            ArrayList<String> con = rm.getRoomsString();
            String[] conference = con.toArray(new String[0]);
            JLabel name = new JLabel("Select A Room");
            JComboBox<String> conferences = new JComboBox<>(conference);

            conferences.setBounds(150, 100, 200, 50);
            name.setBounds(195, 75, 200, 25);
            select.setBounds(200, 175, 100, 25);

            mr.add(conferences);
            mr.add(select);
            mr.add(name);
            mr.add(back);
        }

        void viewRooms() {
            JFrame vr = new JFrame();
            currentFrame = vr;
            setFrame(vr, "View Room");
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            JLabel name = new JLabel("Select A Room");
            name.setBounds(85, 25, 200, 25);
            vr.add(name);

            select = new JButton("Select");
            select.addActionListener(this);
            select.setBounds(75, 90, 100, 25);
            vr.add(select);

            vr.add(back);
            back.setBounds(75, 125, 100, 25);

            ArrayList<String> con = rm.getRoomsString();
            String[] conference = con.toArray(new String[0]);
            JComboBox<String> conferences = new JComboBox<>(conference);
            conferences.setBounds(25, 50, 200, 25);
            vr.add(conferences);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel, "Room Information");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            JPanel conferenceSelector;
            JTextArea eventViewer = new JTextArea();
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No Room selected");
            eventViewer.setBounds(250, 25, 240, 320);
            vr.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel, "Time");
            event.setTitleJustification(TitledBorder.LEADING);
            JTextPane timeInfo = new JTextPane();
            timeInfo.setText("\n\nOpen Time: N/A\n\nClose Time: N/A");
            timeInfo.setBounds(0, 190, 250, 190);
            timeInfo.setEditable(false);
            vr.add(timeInfo);
            timeInfo.setBorder(time);

        }

        private void modifyRoomsOptions() {
            JFrame mro = new JFrame();
            setFrame(mro, "Modify Menu");
            currentFrame = mro;
            currentPage = "modifyEvent";

            JButton[] buttons = new JButton[]{addTech, removeTech, changeCapacity, removeRoom};
            String[] names = new String[]{"Add Tech Available", "Remove Tech Available", "Change Capacity",
                    "Remove Room"};
            int y = 35, x = 140, i = 0;
            for (JButton b : buttons) {

                b = new JButton(names[i]);
                b.setBounds(x, y, 220, 35);
                b.addActionListener(this);
                mro.add(b);
                y = y + 50;
                i = i + 1;

            }

            backmro = new JButton("Back");
            backmro.addActionListener(this);
            backmro.setBounds(200, 240, 100, 30);
            mro.add(backmro);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == back||e.getSource()==backmro) {
                rS.setVisible(false);
                f.setVisible(true);
                if (!(currentFrame == null)) {
                    currentFrame.setVisible(false);
                }
            }
            if (e.getSource() == create) {
                createRoom();
                rS.setVisible(false);
            }
            if (e.getSource() == modify) {
                modifyRooms();
                rS.setVisible(false);
            }
            if (e.getSource() == view) {
                viewRooms();
                rS.setVisible(false);
            }

            if (e.getSource() == select) {
                if (currentPage.equals("modifyRoom")) {
                    currentFrame.setVisible(false);
                    modifyRoomsOptions();

                }


            }
        }
    }

    public static class ModifyConference implements ActionListener{
        JButton addEvent, cancelEvent, changeTime, back;
        JFrame modifyScreen, current;
        Conference con;
        ConferenceManager cm =  new ConferenceSave().read();
        EventManager em = new EventSave().read();

        void setFrame(JFrame frame, String pageTitle){
            frame.setTitle("Event Interface System 1.0");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setSize(500, 380);
            JLabel l = new JLabel(pageTitle);
            frame.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            frame.add(l);

            frame.setVisible(true);
        }

        ModifyConference(String con) throws IOException {
            this.con = cm.findConference(con);
            modifyScreen = new JFrame();
            current = modifyScreen;
            setFrame(modifyScreen,"Modify Screen");

            String[] button = {"Add Event","Cancel Event","Cancel Conference"};
            int i = 0,x = 150,y = 30;

            addEvent = new JButton(button[i]);
            addEvent.setBounds(x,y,200,30);
            addEvent.addActionListener(this);
            modifyScreen.add(addEvent);
            i = i + 1;
            y = y + 60;

            cancelEvent = new JButton(button[i]);
            cancelEvent.setBounds(x,y,200,30);
            cancelEvent.addActionListener(this);
            modifyScreen.add(cancelEvent);
            i = i + 1;
            y = y + 60;

            changeTime = new JButton(button[i]);
            changeTime.setBounds(x,y,200,30);
            changeTime.addActionListener(this);
            modifyScreen.add(changeTime);


            back = new JButton("Back");
            back.setBounds(200,200,100,25);
            back.addActionListener(this);
            modifyScreen.add(back);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==addEvent){
                ArrayList<String> events = em.eventToStrings(con.getConfDate());
                String[] eve = events.toArray(new String[0]);
                JOptionPane.showInputDialog(modifyScreen,"Select Event","Add Event",
                        JOptionPane.PLAIN_MESSAGE,null,eve,0);
            }

            if(e.getSource()==cancelEvent){
                ArrayList<Integer> events = con.getEventIds();
                ArrayList<String> event = em.eventConferenceList(events);
                String[] eve = event.toArray(new String[0]);
                JOptionPane.showInputDialog(modifyScreen,"Select Event","Cancel Event",
                        JOptionPane.PLAIN_MESSAGE,null,eve,0);

            }

            if(e.getSource()==changeTime){
                System.out.println(":)");
            }

            if(e.getSource()==back){
                current.setVisible(false);
                new OrganizerScreen();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        new StartScreen();
    }
}






