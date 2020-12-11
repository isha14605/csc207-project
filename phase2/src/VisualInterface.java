import Controllers.EventSystem;
import Controllers.LoginSystem;
import Controllers.MessagingSystem;
import Controllers.SignUpSystem;
import Entities.Event;
import Entities.Panel;
import Entities.*;
import Gateway.ConferenceSave;
import Gateway.EventSave;
import Gateway.RoomSave;
import Gateway.UserSave;
import Presenter.ConferencePresenter;
import UseCase.ConferenceManager;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;


class Test {

    public static class StartScreen implements ActionListener{
        //Main Screen
        JButton createAccount, login;
        JFrame startScreen;

        //Create Account variables
        JFrame createAcc;
        JButton create, backC;
        JTextField name,email,password;
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
                if(ls.checkLogIn(userNameI.getText(), String.valueOf(passwordI.getPassword()))) {
                    f.setVisible(false);

                    try {
                        if(new UserSave().read().findUser(userNameI.getText()).userType()=='A'||
                                new UserSave().read().findUser(userNameI.getText()).userType()=='V'){
                            new AttendeeScreen(userNameI.getText());
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    try {
                        if(new UserSave().read().findUser(userNameI.getText()).userType()=='O') {
                            new OrganizerScreen(userNameI.getText());
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

    public static class AttendeeScreen implements ActionListener{
        //Attendant screen
        UserManager um = new UserSave().read();
        EventManager em = new EventSave().read();
        ConferenceManager cm = new ConferenceSave().read();
        User userAccount;
        JFrame attendeeScreen,signupScreen,cancelScreen,inboxScreen,messageScreen,signUpEvent,signUpCon;
        JButton signUp,cancelRegistration,inbox,back;
        String user;

        //sign up buttons
        JComboBox<String> conferences, conferencesSignUP;
        JButton event,conference, backSU;
        JTextArea eventViewer = new JTextArea();
        JTextPane timeInfo = new JTextPane();

        //cancel Event button
        JButton select, backCE;
        JComboBox<Integer> eventsAttending;

        //Inbox
        JButton sendMessage, viewMessage, backIB;
        String messageTotal;
        JTextArea messaging;

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
            cancelRegistration.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] option = {"Cancel Event Registration","Cancel Conference Registration"};
                    Object reply = JOptionPane.showInputDialog(signUpCon,"What would you like to cancel",
                            "cancel",JOptionPane.QUESTION_MESSAGE,null,option,0);
                    try {
                        if (reply.equals("Cancel Event Registration")) {
                            attendeeScreen.setVisible(false);
                            try {
                                CancelRegistration();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }

                        if(reply.equals("Cancel Conference Registration")){
                        attendeeScreen.setVisible(false);
                        CancelRegCon();
                    }

                    }catch (Exception e1){}
                }
            });
            attendeeScreen.add(cancelRegistration);
            y = y + space;

            inbox = new JButton("Inbox");
            inbox.setBounds(150,y,200,25);
            inbox.addActionListener(this);
            attendeeScreen.add(inbox);
            y = y + space;

            JButton calender = new JButton("View Calander");
            calender.setBounds(150,y,200,25);
            calender.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new EventCalendar("Events");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            attendeeScreen.add(calender);
            y = y + space;

            if(userAccount.userType() == 'V'){
                vipB = new JButton("Check VIP points");
                vipB.setBounds(150,y,200,25);
                vipB.addActionListener(this);
                attendeeScreen.add(vipB);
                y = y + space;
            }

            if(userAccount.userType() == 'O'){
                JButton org = new JButton("Back To Organizer Screen");
                org.setBounds(150,y,200,25);
                org.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        attendeeScreen.setVisible(false);
                        new OrganizerScreen(user);
                    }
                });
                attendeeScreen.add(org);
                y = y + space;
            }
            calender = new JButton("View Calendar");
            calender.setBounds(150,y,200,25);
            calender.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new EventCalendar("Events");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });

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
            select.addActionListener(e -> {
                if(!(conferences.getSelectedItem() =="") && !(conferences.getSelectedItem() =="None")){
                    SignUpSystem sus = null;
                    try {
                        sus = new SignUpSystem();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    assert sus != null;
                    try {
                        if(sus.signUpEvent(user, (em.getEventIds().get(conferences.getSelectedIndex()-1)))){
                            JOptionPane.showMessageDialog(attendeeScreen,"SignUp was Successful");
                            try {
                                new EventSave().save(sus.eM);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            em = new EventSave().read();
                            Event ev = em.findEvent(em.getEventIds().get(conferences.getSelectedIndex()-1));
                            timeInfo.setText("\nDate: " + ev.getEventDate() +
                                    "\n\nStart Time: " + ev.getStartTime() +
                                    "\n\nEnd Time: " + ev.getEndTime());

                            eventViewer.setText("Event Name: " + ev.getName() +
                                    "\nEvent Id :" + ev.getEventId() +
                                    "\n\nDescription : " + ev.getEventDescription() +
                                    "\n\nRoom : " + ev.getRoomName() +
                                    "\n\nTech Requirements : " + ev.getTechRequirements() +
                                    "\n\nCapacity : " + (ev.getOrganizerEmails().size()+ev.getAttendeeEmails().size())+ "/"
                                    + ev.getAttendeeCapacity() + "");
                            try {
                                new UserSave().save(sus.uM);
                                userAccount = sus.uM.findUser(user);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }else{
                            JOptionPane.showMessageDialog(attendeeScreen,"Unable to Join event");
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
            });
            select.setBounds(75,90,100,25);
            signUpEvent.add(select);

            backSU = new JButton("Back");
            signUpEvent.add(backSU);
            backSU.addActionListener(this);
            backSU.setBounds(75,125,100,25);


            ArrayList<String> con = em.getEventString();
            con.add(0,"");
            String[] conference = con.toArray(new String[0]);
            conferences = new JComboBox<>(conference);
            conferences.setBounds(25,50,200,25);
            conferences.addActionListener(this);
            signUpEvent.add(conferences);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Event Info");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No event selected");
            eventViewer.setBounds(250,25,240,320);
            signUpEvent.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
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
            JTextArea eventViewer1 = new JTextArea();
            JTextPane timeInfo1 = new JTextPane();

            //Select Button
            select = new JButton("Select");
            select.addActionListener(e -> {
                if(!(conferencesSignUP.getSelectedItem() =="") && !(conferencesSignUP.getSelectedItem() =="None")){
                    SignUpSystem sus = null;
                    try {
                        sus = new SignUpSystem();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    assert sus != null;
                    try {
                        if(sus.attendConf(Objects.requireNonNull(conferencesSignUP.getSelectedItem()).toString(),
                                user)){
                            JOptionPane.showMessageDialog(attendeeScreen,"SignUp was Successful");
                        }else{
                            JOptionPane.showMessageDialog(attendeeScreen,"Unable to Join event");
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
            });
            select.setBounds(75,90,100,25);
            signUpCon.add(select);

            //back button
            backSU = new JButton("Back");
            signUpCon.add(backSU);
            backSU.addActionListener(this);
            backSU.setBounds(75,125,100,25);


            ArrayList<String> con = cm.getConferenceList();
            String[] conference = con.toArray(new String[0]);
            conferencesSignUP = new JComboBox<>(conference);
            conferencesSignUP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(conferencesSignUP.getSelectedItem()!=null&&conferencesSignUP.getSelectedItem()!=""){
                        Conference conference1 = cm.findConference(conferencesSignUP.getSelectedItem().toString());
                        timeInfo1.setText("\nDate: " + conference1.getConfDate()+
                                "\n\nStart Time: " + conference1.getStartTime() +
                                "\n\nEnd Time: " + conference1.getEndTime());

                        eventViewer1.setText("Event Name: " + conference1.getName() +
                                "\n\nDescription : " + conference1.getConfDescription() +
                                "\n\nEvents " + em.eventConferenceList(conference1.getEventIds()).toString());
                    }
                    else {
                        timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
                        eventViewer.setText("");
                    }

                    }});
            conferencesSignUP.setBounds(25,50,200,25);
            signUpCon.add(conferencesSignUP);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Conference Info");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up

            eventViewer1.setLineWrap(true);
            eventViewer1.setEditable(false);
            eventViewer1.setText("No event selected");
            eventViewer1.setBounds(250,25,240,320);
            signUpCon.add(eventViewer1);
            eventViewer1.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
            timeInfo1.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
            timeInfo1.setBounds(0,190,250,190);
            timeInfo1.setEditable(false);
            signUpCon.add(timeInfo1);
            timeInfo1.setBorder(time);
        }

        void CancelRegistration() throws IOException {
            JTextPane timeInfo = new JTextPane();
            JTextArea eventViewer = new JTextArea();

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
            select.addActionListener(e -> {
                if(!(eventsAttending.getSelectedItem() =="") && !(eventsAttending.getSelectedItem() =="None")||
                eventsAttending.getSelectedItem()!=null){
                    SignUpSystem sus = null;
                    try {
                        sus = new SignUpSystem();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    assert sus != null;
                    try {
                        if(sus.cancelRegEvent(user,Integer.parseInt(Objects.requireNonNull(eventsAttending
                                .getSelectedItem()).toString()))){
                            JOptionPane.showMessageDialog(attendeeScreen,"Cancel was Successful");
                            cancelScreen.setVisible(false);
                            attendeeScreen.setVisible(true);

                            try {
                                new EventSave().save(sus.eM);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            try {
                                new UserSave().save(sus.uM);
                                userAccount = sus.uM.findUser(user);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }else{
                            JOptionPane.showMessageDialog(attendeeScreen,"Wasn't able to cancel event");
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }


                }
            });
            select.setBounds(75,90,100,25);
            cancelScreen.add(select);

            JButton backS = new JButton("Back");
            cancelScreen.add(backS);
            backS.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cancelScreen.setVisible(false);
                    attendeeScreen.setVisible(true);
                }
            });
            backS.setBounds(75,125,100,25);

            if(userAccount.userType()=='A'||userAccount.userType()=='V') {
                Attendee a = (Attendee) userAccount;
                ArrayList<Integer> con = a.getEventsAttending();
                Integer[] conference = con.toArray(new Integer[0]);
                eventsAttending = new JComboBox<>(conference);
                eventsAttending.addActionListener(e -> {
                    if (!Objects.equals(eventsAttending.getSelectedItem(), "")) {
                        Event event = em.findEvent(Integer.parseInt(Objects.requireNonNull(eventsAttending
                                .getSelectedItem()).toString()));
                        timeInfo.setText("\nDate: " + event.getEventDate() +
                                "\n\nStart Time: " + event.getStartTime() +
                                "\n\nEnd Time: " + event.getEndTime());

                        eventViewer.setText("Event Name: " + event.getName() +
                                "\nEvent Id :" + event.getEventId() +
                                "\n\nDescription : " + event.getEventDescription() +
                                "\n\nRoom : " + event.getRoomName() +
                                "\n\nTech Requirements : " + event.getTechRequirements() +
                                "\n\nCapacity : " + event.getAttendeeCapacity() +
                                "");
                    } else {
                        timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
                    }
                });
                eventsAttending.setBounds(25, 50, 200, 25);
                cancelScreen.add(eventsAttending);
            }
            if(userAccount.userType()=='O'){
                Organizer a = (Organizer) userAccount;
                ArrayList<Integer> con = a.getEventsOrganizing();
                Integer[] conference = con.toArray(new Integer[0]);
                eventsAttending = new JComboBox<>(conference);
                eventsAttending.addActionListener(e -> {
                    if (!Objects.equals(eventsAttending.getSelectedItem(), "")) {
                        Event event = em.findEvent(Integer.parseInt(Objects.requireNonNull(eventsAttending
                                .getSelectedItem()).toString()));
                        timeInfo.setText("\nDate: " + event.getEventDate() +
                                "\n\nStart Time: " + event.getStartTime() +
                                "\n\nEnd Time: " + event.getEndTime());

                        eventViewer.setText("Event Name: " + event.getName() +
                                "\nEvent Id :" + event.getEventId() +
                                "\n\nDescription : " + event.getEventDescription() +
                                "\n\nRoom : " + event.getRoomName() +
                                "\n\nTech Requirements : " + event.getTechRequirements() +
                                "\n\nCapacity : " + event.getAttendeeCapacity() +
                                "");
                    } else {
                        timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
                    }
                });
                eventsAttending.setBounds(25, 50, 200, 25);
                cancelScreen.add(eventsAttending);
            }

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Event Info");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No event selected");
            eventViewer.setBounds(250,25,240,320);
            cancelScreen.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
            timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
            timeInfo.setBounds(0,190,250,190);
            timeInfo.setEditable(false);
            cancelScreen.add(timeInfo);
            timeInfo.setBorder(time);

        }

        void CancelRegCon(){
            JFrame cancelCon = new JFrame();
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            setFrame(cancelCon,"Cancel Registration Con");

            JTextArea eventViewer1 = new JTextArea();
            JTextPane timeInfo1 = new JTextPane();


            Attendee a = (Attendee) userAccount;
            ArrayList<String> con = a.getConferenceAttending();
            String[] conference = con.toArray(new String[0]);
            JComboBox<String> conferencesSignUP1 = new JComboBox<>(conference);
            conferencesSignUP1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(conferencesSignUP1.getSelectedItem()!=null){
                        Conference conference1 = cm.findConference(conferencesSignUP1.getSelectedItem().toString());
                        timeInfo1.setText("\nDate: " + conference1.getConfDate()+
                                "\n\nStart Time: " + conference1.getStartTime() +
                                "\n\nEnd Time: " + conference1.getEndTime());

                        eventViewer1.setText("Event Name: " + conference1.getName() +
                                "\n\nDescription : " + conference1.getConfDescription() +
                                "\n\nEvents " + em.eventConferenceList(conference1.getEventIds()).toString());
                    }
                    else {
                        timeInfo1.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
                        eventViewer1.setText("");
                    }

                }});
            conferencesSignUP1.setBounds(25,50,200,25);
            cancelCon.add(conferencesSignUP1);

            //Select Button
            JButton select1 = new JButton("Select");
            select1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!(conferencesSignUP1.getSelectedItem() =="")&&!(conferencesSignUP1.getSelectedItem() =="None"&&
                                    conferencesSignUP1.getSelectedItem()==null)){
                        try {
                            if(new SignUpSystem().cancelRegConf((conferencesSignUP1.getSelectedItem()).toString(),
                                    user)){
                                JOptionPane.showMessageDialog(attendeeScreen,"Conference was Successfully removed");
                                cancelCon.setVisible(false);
                                SignUp();
                            }else{
                                JOptionPane.showMessageDialog(attendeeScreen,"Conference wasn't removed");
                            }
                        } catch (Exception NullPointerException) {
                        }
                    }

                }
            });
            select1.setBounds(75,90,100,25);
            cancelCon.add(select1);

            //back button
            JButton backS = new JButton("Back");
            cancelCon.add(backS);
            backS.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cancelCon.setVisible(false);
                    attendeeScreen.setVisible(true);
                }
                });
            backS.setBounds(75,125,100,25);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Conference Info");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up

            eventViewer1.setLineWrap(true);
            eventViewer1.setEditable(false);
            eventViewer1.setText("No event selected");
            eventViewer1.setBounds(250,25,240,320);
            cancelCon.add(eventViewer1);
            eventViewer1.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
            timeInfo1.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
            timeInfo1.setBounds(0,190,250,190);
            timeInfo1.setEditable(false);
            cancelCon.add(timeInfo1);
            timeInfo1.setBorder(time);
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

            int y = 40,space = 40;
            if(userAccount.userType() == 'O'){
                JButton messageAll = new JButton("Message All Users");
                messageAll.setBounds(150,y,200,30);
                messageAll.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        inboxScreen.setVisible(false);
                        JFrame messageEvent = new JFrame();
                        setFrame(messageEvent, "Message Screen");
                        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

                        Font f = new Font(Font.DIALOG_INPUT,Font.BOLD,18);
                        JLabel l = new JLabel("Message All Users");
                        l.setBounds(200,50,135,30);
                        l.setFont(f);
                        messageEvent.add(l);

                        messaging = new JTextArea();
                        messaging.setBounds(100,90,300,80);
                        messaging.setBorder(raisedetched);
                        messageEvent.add(messaging);

                        sendMessage = new JButton("Send");
                        sendMessage.setBounds(200,190,100,25);
                        sendMessage.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                MessagingSystem ms = null;
                                try {
                                    ms = new MessagingSystem();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                ArrayList<String> users = um.getEmail();
                                assert ms != null;
                                try {
                                    if(ms.sendMessage(user,users,messaging.getText())){
                                        JOptionPane.showMessageDialog(inboxScreen,"Message Was Sent");
                                        messageEvent.setVisible(false);
                                        inboxScreen.setVisible(true);
                                    }
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        });
                        messageEvent.add(sendMessage);

                        JButton back =  new JButton("Back");
                        back.setBounds(200,220,100,25);
                        back.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                messageEvent.setVisible(false);
                                inboxScreen.setVisible(true);
                            }
                        });
                        messageEvent.add(back);
                    }
                });
                inboxScreen.add(messageAll);
                y = y + space;

                JButton messageAllSpeaker = new JButton("Message All Speaker");
                messageAllSpeaker.setBounds(150,y,200,30);
                messageAllSpeaker.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        inboxScreen.setVisible(false);
                        JFrame messageEvent = new JFrame();
                        setFrame(messageEvent, "Message Screen");
                        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

                        Font f = new Font(Font.DIALOG_INPUT,Font.BOLD,12);
                        JLabel l = new JLabel("Events Speaking:");
                        l.setBounds(100,50,135,30);
                        l.setFont(f);
                        messageEvent.add(l);

                        messaging = new JTextArea();
                        messaging.setBounds(100,90,300,80);
                        messaging.setBorder(raisedetched);
                        messageEvent.add(messaging);

                        sendMessage = new JButton("Send");
                        sendMessage.setBounds(200,190,100,25);
                        sendMessage.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                MessagingSystem ms = null;
                                try {
                                    ms = new MessagingSystem();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                ArrayList<String> users = um.getSpeaker();
                                assert ms != null;
                                try {
                                    if(ms.sendMessage(user,users,messaging.getText())){
                                        JOptionPane.showMessageDialog(inboxScreen,"Message Was Sent");
                                        messageEvent.setVisible(false);
                                        inboxScreen.setVisible(true);
                                    }
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        });
                        messageEvent.add(sendMessage);


                        JButton back =  new JButton("Back");
                        back.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                messageEvent.setVisible(false);
                                inboxScreen.setVisible(true);
                            }
                        });
                        back.setBounds(200,220,100,25);
                        messageEvent.add(back);
                    }
                });
                inboxScreen.add(messageAllSpeaker);
                y = y + space;

                JButton messageEvents = new JButton("Message Events");
                messageEvents.setBounds(150,y,200,30);
                messageEvents.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        inboxScreen.setVisible(false);
                        JFrame messageEvent = new JFrame();
                        setFrame(messageEvent, "Message Screen");
                        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

                        ArrayList<Integer> events = em.getEventIds();
                        Integer[] allEvents = events.toArray(new Integer[0]);
                        JComboBox<Integer> comboBox = new JComboBox<>(allEvents);
                        comboBox.setBounds(225,50,175,30);

                        messageEvent.add(comboBox);

                        Font f = new Font(Font.DIALOG_INPUT,Font.BOLD,12);
                        JLabel l = new JLabel("Events :");
                        l.setBounds(100,50,135,30);
                        l.setFont(f);
                        messageEvent.add(l);

                        messaging = new JTextArea();
                        messaging.setBounds(100,90,300,80);
                        messaging.setBorder(raisedetched);
                        messageEvent.add(messaging);

                        sendMessage = new JButton("Send");
                        sendMessage.setBounds(200,190,100,25);
                        sendMessage.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                MessagingSystem ms = null;
                                try {
                                    ms = new MessagingSystem();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                ArrayList<String> recipients1 = em.findEvent(Integer.parseInt(comboBox.getSelectedItem().
                                        toString())).getAttendeeEmails();
                                ArrayList<String> recipients2 = em.findEvent(Integer.parseInt(comboBox.getSelectedItem().
                                        toString())).getOrganizerEmails();
                                recipients1.addAll(recipients2);
                                ArrayList<String> recipients = recipients1;
                                recipients.add(Objects.requireNonNull(comboBox.getSelectedItem()).toString());
                                assert ms != null;
                                try {
                                    if(ms.sendMessage(user,recipients,messaging.getText())){
                                        JOptionPane.showMessageDialog(inboxScreen,"Message Was Sent");
                                        messageEvent.setVisible(false);
                                        inboxScreen.setVisible(true);
                                    }
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        });
                        messageEvent.add(sendMessage);

                        JButton back = new JButton("Back");
                        back.setBounds(200,220,100,25);
                        back.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                messageEvent.setVisible(false);
                                inboxScreen.setVisible(true);
                            }
                        });
                        messageEvent.add(back);
                    }
                });
                inboxScreen.add(messageEvents);
                y = y + space;
            }

            sendMessage = new JButton("Send message");
            sendMessage.setBounds(150,y,200,30);
            sendMessage.addActionListener(this);
            inboxScreen.add(sendMessage);
            y = y + space;

            viewMessage = new JButton("View Message History");
            viewMessage.setBounds(150,y,200,30);
            viewMessage.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    inboxScreen.setVisible(false);
                    try {
                        new MessageScreen(user);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            inboxScreen.add(viewMessage);
            y = y + space;

            backIB =  new JButton("Back");
            backIB.setBounds(200,y,100,25);
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
            sendMessage.addActionListener(e -> {
                MessagingSystem ms = null;
                try {
                    ms = new MessagingSystem();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                ArrayList<String> recipients = new ArrayList<>();
                recipients.add(Objects.requireNonNull(comboBox.getSelectedItem()).toString());
                assert ms != null;
                try {
                    if(ms.sendMessage(user,recipients,messaging.getText())){
                        JOptionPane.showMessageDialog(inboxScreen,"Message Was Sent");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            });
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

            if(e.getSource()==conferences){

                if(!Objects.equals(conferences.getSelectedItem(), "")&&
                        !Objects.equals(conferences.getSelectedItem(), "None")) {
                    Event event = em.findEvent(conferences.getSelectedIndex());

                    timeInfo.setText("\nDate: " + event.getEventDate() +
                            "\n\nStart Time: " + event.getStartTime() +
                            "\n\nEnd Time: " + event.getEndTime());

                    eventViewer.setText("Event Name: " + event.getName() +
                            "\nEvent Id :" + event.getEventId() +
                            "\n\nDescription : " + event.getEventDescription() +
                            "\n\nRoom : " + event.getRoomName() +
                            "\n\nTech Requirements : " + event.getTechRequirements() +
                            "\n\nCapacity : " + (event.getOrganizerEmails().size()+event.getAttendeeEmails().size())+ "/" + event.getAttendeeCapacity() +
                            "");
                }
                else {
                    timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
                    eventViewer.setText("");
                }
            }

            if(e.getSource()==conferencesSignUP){
                if(!Objects.equals(conferencesSignUP.getSelectedItem(), "")&&
                        !Objects.equals(conferencesSignUP.getSelectedItem(), "None")) {
                    Conference conference = cm.findConference(conferencesSignUP.getSelectedItem().toString());
                    timeInfo.setText("\nDate: " + conference.getConfDate()+
                            "\n\nStart Time: " + conference.getStartTime() +
                            "\n\nEnd Time: " + conference.getEndTime());

                    eventViewer.setText("Event Name: " + conference.getName() +
                            "\n\nDescription : " + conference.getConfDescription() +
                            "Events " + conference.getEventIds());
                }
                else {
                    timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
                }
            }


            if(e.getSource()==inbox){
                attendeeScreen.setVisible(false);
                inboxScreen();
            }

            if(e.getSource()==back){
                attendeeScreen.setVisible(false);
                try {
                    new StartScreen();
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
                    JOptionPane.showMessageDialog(attendeeScreen,"Event has been removed");
                }
            }

            if(e.getSource()==backCE){
                cancelScreen.setVisible(false);
                attendeeScreen.setVisible(true);
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
        String user;

        //Browse Events
        JFrame browseEvent;
        JButton select,back;

        //Inbox
        JFrame messageEvent,messageUser;
        JButton sendMessageEvent,sendMessageUser, viewMessage, sendMessage;
        String messageTotal;
        JTextArea messaging;
        JComboBox<String> contact;

        UserManager um;

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
            um = new UserSave().read();
            this.user = user;
            speakerScreen = new JFrame();
            speaker = um.findSpeaker(user);
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

        void browseEvents() throws IOException {
            JTextPane timeInfo = new JTextPane();
            JTextArea eventViewer = new JTextArea();
            browseEvent = new JFrame();
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            setFrame(browseEvent, "Browse Event");
            current = browseEvent;

            back = new JButton("Back");
            browseEvent.add(back);
            back.addActionListener(this);
            back.setBounds(75,125,100,25);

            um = new UserSave().read();
            ArrayList<Integer> con = um.findSpeaker(user).getEventsSpeaking();
            Integer[] events = con.toArray(new Integer[0]);
            JComboBox<Integer> eventsSpeaking = new JComboBox<>(events);
            eventsSpeaking.setBounds(25,50,200,25);
            browseEvent.add(eventsSpeaking);
            eventsSpeaking.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!Objects.equals(eventsSpeaking.getSelectedItem(), "")) {
                        Event event = em.findEvent(Integer.parseInt(Objects.requireNonNull(eventsSpeaking
                                .getSelectedItem()).toString())) ;
                        timeInfo.setText("\nDate: " + event.getEventDate() +
                                "\n\nStart Time: " + event.getStartTime() +
                                "\n\nEnd Time: " + event.getEndTime());

                        eventViewer.setText("Event Name: " + event.getName() +
                                "\nEvent Id :" + event.getEventId() +
                                "\n\nDescription : " + event.getEventDescription() +
                                "\n\nRoom : " + event.getRoomName() +
                                "\n\nTech Requirements : " + event.getTechRequirements() +
                                "\n\nCapacity : " + event.getAttendeeCapacity() +
                                "");
                    }
                    else {
                        timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
                    }
                }
            });

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Event Info");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No event selected");
            eventViewer.setBounds(250,25,240,320);
            browseEvent.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
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
            JLabel l = new JLabel("Events Speaking:");
            l.setBounds(100,50,135,30);
            l.setFont(f);
            messageEvent.add(l);

            messaging = new JTextArea();
            messaging.setBounds(100,90,300,80);
            messaging.setBorder(raisedetched);
            messageEvent.add(messaging);

            sendMessage = new JButton("Send");
            sendMessage.setBounds(200,190,100,25);
            sendMessage.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MessagingSystem ms = null;
                    try {
                        ms = new MessagingSystem();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    ArrayList<String> recipients1 = em.findEvent(Integer.parseInt(comboBox.getSelectedItem().
                            toString())).getAttendeeEmails();
                    ArrayList<String> recipients2 = em.findEvent(Integer.parseInt(comboBox.getSelectedItem().
                            toString())).getOrganizerEmails();
                    recipients1.addAll(recipients2);
                    ArrayList<String> recipients = recipients1;
                    recipients.add(Objects.requireNonNull(comboBox.getSelectedItem()).toString());
                    assert ms != null;
                    try {
                        if(ms.sendMessage(user,recipients,messaging.getText())){
                            JOptionPane.showMessageDialog(inboxScreen,"Message Was Sent");
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
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
                try {
                    browseEvents();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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

    public static class MessageScreen implements ActionListener{

        void setFrame(JFrame frame, String pageTitle) {
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
            l.setBounds(0, 0, 500, 380);
            frame.setVisible(true);
        }

        MessageScreen(String user) throws IOException {
            JFrame ms = new JFrame();
            String[] option = new String[0];
            JComboBox<String> optionCB;
            setFrame(ms, "");
            Border b;
            UserManager um = new UserSave().read();

            JButton back = new JButton("back");
            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ms.setVisible(false);
                    try {
                        new AttendeeScreen(user);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            back.setBounds(60,90,100,25);


            //inbox option
            JPanel panel = new JPanel();
            panel.setSize(220,130);
            panel.setBounds(20,10,220,130);
            panel.setLayout(null);
            ms.add(panel);
            panel.add(back);

            b = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
            b = BorderFactory.createTitledBorder(b, "Inbox Option");
            option = new String[]{"", "Sent Messages", "Recived Messages"};
            optionCB = new JComboBox<>(option);
            optionCB.setBounds(10, 50, 200, 30);
            panel.add(optionCB);
            panel.setBorder(b);


            //Inbox
            b = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
            b = BorderFactory.createTitledBorder(b, "Inbox");
            JPanel inboxPanel = new JPanel();
            inboxPanel.setSize(210,310);
            inboxPanel.setBounds(250, 10, 210, 310);
            inboxPanel.setLayout(null);
            ms.add(inboxPanel);
            inboxPanel.setBorder(b);

            b = BorderFactory.createEtchedBorder();
            JList messages = new JList(new String[]{"No Option Selected"}); //data has type Object[]
            messages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            messages.setLayoutOrientation(JList.VERTICAL);
            JScrollPane listScroller = new JScrollPane(messages);
            listScroller.setPreferredSize(new Dimension(190,280));
            messages.setVisibleRowCount(-1);
            messages.setBounds(10, 20, 190, 280);
            messages.setBorder(b);
            inboxPanel.add(messages);

            //Contacts
            b = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
            b = BorderFactory.createTitledBorder(b, "contacts");
            JPanel contactPanel = new JPanel();
            contactPanel.setSize(220,170);
            contactPanel.setBounds(20, 150, 220, 170);
            contactPanel.setLayout(null);
            ms.add(contactPanel);
            contactPanel.setBorder(b);

            b = BorderFactory.createEtchedBorder();
            ArrayList<String> messagable = um.findUser(user).getContacts();
            String[] contactInfo = messagable.toArray(new String[0]);
            JList contacts = new JList(contactInfo);
            contacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            contacts.setLayoutOrientation(JList.VERTICAL);
            contacts.setVisibleRowCount(-1);
            contacts.setBounds(10, 20, 200, 140);
            JScrollPane jScrollPane = new JScrollPane(contacts);
            jScrollPane.setPreferredSize(new Dimension(200,140));
            contactPanel.add(contacts);
            contacts.setBorder(b);
            contactPanel.setVisible(false);

            contacts.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if((optionCB.getSelectedItem()==null)||(optionCB.getSelectedItem()=="")){
                        return;
                    }

                    if(optionCB.getSelectedItem().equals("Sent Messages")){
                        ArrayList<String> sent = um.findUser(user).getMessagesSent()
                                .get(contacts.getSelectedValue().toString());
                        String[] emails = sent.toArray(new String[0]);
                        messages.setListData(emails);
                    }

                    if(optionCB.getSelectedItem().equals("Recived Messages")){
                        ArrayList<String> checkedMessages = um.checkReadM(user,contacts.getSelectedValue().toString());
                        String[] recived = checkedMessages.toArray(new String[0]);
                        messages.setListData(recived);

                    }

                    if(optionCB.getSelectedItem().equals("")){

                    }
                }
            });

            optionCB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(optionCB.getSelectedItem()=="Sent Messages"){
                        messages.setListData(new String[]{"Sent Message select a contact"});
                        contactPanel.setVisible(true);
                    }
                    if(optionCB.getSelectedItem()=="Recived Messages"){
                        contactPanel.setVisible(true);
                        messages.setListData(new String[]{"Recived Message select a contact"});
                    }
                    if(optionCB.getSelectedItem()==""){
                        contactPanel.setVisible(false);
                        messages.setListData(new String[]{"Nothing Selected"});
                    }
                }
            });

            messages.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    try {
                        if (optionCB.getSelectedItem().equals("Recived Messages")) {
                            JOptionPane.showMessageDialog(ms, messages.getSelectedValue().toString());
                            um.findUser(user).updateMessage(contacts.getSelectedValue().toString(),
                                    messages.getSelectedIndex(),
                                    um.markRead(messages.getSelectedValue().toString()));
                            try {
                                new UserSave().save(um);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }

                            ArrayList<String> checkedMessages = um.checkReadM(user, contacts.getSelectedValue().toString());
                            String[] recived = checkedMessages.toArray(new String[0]);
                            messages.setListData(recived);
                        }
                    }catch (Exception npe){

                    }

                    if(optionCB.getSelectedItem().equals("Sent Messages")){
                        JOptionPane.showMessageDialog(ms,messages.getSelectedValue().toString());
                    }

                }
            });

        }

        @Override
        public void actionPerformed(ActionEvent e){

        }



    }

    public static class OrganizerScreen implements ActionListener {
        JButton aeButton, crButton, csButton, eoButton, veButton, vrButton, ibButton, exit;
        public JFrame f = new JFrame();
        ConferenceManager cM;
        RoomManager rM;
        EventManager eM;
        String user;

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

        OrganizerScreen(String user) {
            //Window set up
            f.setTitle("Event Interface System 1.0");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setResizable(true);
            f.setSize(500, 380);
            JLabel l = new JLabel("Event Control Systems");
            f.setLayout(null);
            this.user = user;

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
            exit = new JButton("Exit");

            f.add(aeButton);
            f.add(crButton);
            f.add(csButton);
            f.add(eoButton);
            f.add(exit);

            Dimension d = new Dimension(195, 50);
            int bx = 50;
            int by = 50;
            aeButton.setSize(d);
            crButton.setSize(d);
            csButton.setSize(d);
            eoButton.setSize(d);
            ;
            exit.setSize(d);

            aeButton.setBounds(bx, by, 190, 30);
            crButton.setBounds(bx + 200, by, 190, 30);
            csButton.setBounds(bx, by + 60, 190, 30);
            eoButton.setBounds(bx + 200, by + 60, 190, 30);
            exit.setBounds(200, by + 180, 100, 30);

            exit.addActionListener(this);
            aeButton.addActionListener(this);
            crButton.addActionListener(this);
            csButton.addActionListener(this);
            eoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    f.setVisible(false);
                    UserManagerScreen();
                }
            });

            JButton attendee = new JButton("Attendee Options");
            attendee.setBounds(155,170,190,30);
            attendee.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    f.setVisible(false);
                    try {
                        new AttendeeScreen(user);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            f.add(attendee);
            f.setVisible(true);
        }

        void UserManagerScreen(){
            JFrame userManager = new JFrame();
            setFrame(userManager,"User manager");

            JButton b = new JButton("Create User");
            b.setBounds(150,30,200,40);
            userManager.add(b);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    userManager.setVisible(false);
                    JFrame createAcc;
                    JButton create, backC;
                    JTextField name,email,password;
                    String type;
                    JComboBox<String> usertype;
                    try {
                        UserManager um = new UserSave().read();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

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
                    String[] types = new String[]{"","Attendee","Organizer","Speaker","VIP"};
                    usertype = new JComboBox<>(types);
                    usertype.setBounds(200, y,200,25);
                    createAcc.add(usertype);
                    y = y + space;

                    //create button
                    create = new JButton("Create Account");
                    create.setBounds(180,y,140,25);
                    create.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            UserManager um = null;
                            try {
                                um = new UserSave().read();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            try {
                                String nameText = name.getText();
                                String emailText = email.getText();
                                String passwordText = password.getText();

                                assert um != null;
                                if(um.checkUserExists(emailText)){
                                    JOptionPane.showMessageDialog(createAcc, "Email already exist within our " +
                                            "system.");
                                }
                                if(!um.checkUserExists(emailText)){
                                    JOptionPane.showMessageDialog(createAcc, "Approved, Account has been made");
                                    um.addUser(nameText, emailText, passwordText,
                                            Objects.requireNonNull(usertype.getSelectedItem()).toString());
                                    new UserSave().save(um);
                                    createAcc.setVisible(false);
                                    userManager.setVisible(true);
                                }


                            } catch (HeadlessException | IOException headlessException) {
                                headlessException.printStackTrace();
                            }

                            try {
                                new UserSave().save(um);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    });
                    createAcc.add(create);
                    y = y + space;

                    //Back Button
                    backC = new JButton("Back");
                    backC.setBounds(200,y,100,25);
                    backC.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            createAcc.setVisible(false);
                            userManager.setVisible(true);
                        }
                    });
                    createAcc.add(backC);
                }
            });

            JButton back = new JButton("back");
            back.setBounds(200,80,100,25);
            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    userManager.setVisible(false);
                    f.setVisible(true);
                }
            });
            userManager.add(back);

        }




        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == exit) {
                f.setVisible(false);
                try {
                    new StartScreen();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            if (e.getSource() == aeButton) {
                f.setVisible(false);
                try {
                    new ConferenceScreen(f,user);
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }

            if (e.getSource() == crButton) {
                f.setVisible(false);
                try {
                    new EventScreen(user);
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }

            if (e.getSource()==csButton){
                f.setVisible(false);
                try {
                    new RoomScreen(user);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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
        String user;

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


        ConferenceScreen(JFrame f,String user) throws IOException, ClassNotFoundException, NullPointerException {
            this.f = f;
            cS.setTitle("Event Interface System 1.0");
            cS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            cS.setResizable(true);
            cS.setSize(500, 380);
            JLabel l = new JLabel("Conference Control Systems");
            cS.setLayout(null);
            this.user = user;

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
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            cS.setVisible(false);
                            new OrganizerScreen(user);
                        }
                    });
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
            JButton backc = new JButton("Back");
            backc.setBounds(250,y+100,100,50);
            submit.addActionListener(this);
            backc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cc.setVisible(false);
                    try {
                        new ConferenceScreen(f,user);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
            });
            cc.add(submit);
            cc.add(backc);

        }

        void modifyConference(){
            JFrame mc = new JFrame();
            this.mc = mc;
            setFrame(mc,"Modify Conference Panel");
            JLabel name = new JLabel("Select A Conference");

            ArrayList<String> con = cM.getConferenceList();
            String[] conference = con.toArray(new String[0]);
            conferences = new JComboBox<>(conference);
            conferences.addActionListener(this);
            JButton backm = new JButton("Back");
            conferences.setBounds(150,100,200,50);
            name.setBounds(195, 75,200,25);
            select.setBounds(200,175,100,25);
            select.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!conferences.getSelectedItem().equals("")) {
                        mc.setVisible(false);
                        try {
                            new ModifyConference(conferences.getSelectedItem().toString(),user);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            });
            backm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mc.setVisible(false);
                    cS.setVisible(true);
                }
            });
            backm.setBounds(200,220,100,25);
            mc.add(conferences);
            mc.add(select);
            mc.add(name);
            mc.add(backm);
        }

        void viewConference(){
            current = vc;
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            vc.setResizable(false);
            setFrame(vc,"View Conference Panel");

            JLabel name = new JLabel("Select A Conference");
            name.setBounds(60,60,200,25);
            vc.add(name);

            JButton backv = new JButton("back");
            vc.add(backv);
            backv.setBounds(75,160,100,25);
            backv.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cS.setVisible(true);
                    vc.setVisible(false);
                }
            });

            ArrayList<String> con = cM.getConferenceList();
            String[] conference = con.toArray(new String[0]);
            conferences = new JComboBox<>(conference);
            conferences.setBounds(25,100,200,25);
            vc.add(conferences);
            conferences.addActionListener(e -> {
                ConferenceManager cm = null;
                try {
                    cm = new ConferenceSave().read();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                if(!Objects.equals(conferences.getSelectedItem(), "")&&
                        !Objects.equals(conferences.getSelectedItem(), "None")) {
                    assert cm != null;
                    Conference conference1 = cm.findConference(conferences.getSelectedItem().toString());
                    EventManager em = null;
                    try {
                         em = new EventSave().read();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    timeInfo.setText("\nDate: " + conference1.getConfDate()+
                            "\n\nStart Time: " + conference1.getStartTime() +
                            "\n\nEnd Time: " + conference1.getEndTime());

                    eventViewer.setText("Event Name: " + conference1.getName() +
                            "\n\nDescription : " + conference1.getConfDescription() +
                            "\n\nEvents " + em.eventConferenceList(conference1.getEventIds()).toString());
                }
                else {
                    timeInfo.setText("\nDate: N/A\n\nStart Time: N/A\n\nEnd Time: N/A");
                    eventViewer.setText("");
                }
            });

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


        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource()==create){
                try {
                    createConference();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                cS.setVisible(false);
            }

            if(e.getSource()==modify){
                modifyConference();
                cS.setVisible(false);
            }

            if(e.getSource()==select){
                current.setVisible(false);
                if(!conferences.getSelectedItem().equals("")&&(conferences.getSelectedItem().equals(null))) {
                    try {
                        new ModifyConference(conferences.getItemAt(conferences.getSelectedIndex()), user);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }

            if(e.getSource()==view){
                viewConference();
                cS.setVisible(false);
            }

            if(e.getSource()==submit){
                try {
                    if(eventSystem.addConference(nameText.getText(), descText.getText(),
                            timeOptionsS.getItemAt(timeOptionsS.getSelectedIndex()),
                            timeOptionE.getItemAt(timeOptionE.getSelectedIndex()),date.getText())){
                        JOptionPane.showMessageDialog(cc,"Conference was Added");
                    }
                    else{
                        JOptionPane.showMessageDialog(cc,"Conference was not added due to:");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        }
    }
    
    public static class EventScreen implements ActionListener{
        JFrame eS = new JFrame(), f, currentFrame;
        String currentPage;
        JButton create, modify, view, back, select;
        EventManager eM = new EventSave().read();
        RoomManager rm = new RoomSave().read();
        EventSystem eventSystem = new EventSystem();
        //Event info
        JTextField enI, edI, timeS, timeE, dateI;
        //MEO Buttons
        JButton scheduleSpeaker, scheduleRoom, cancel, changeTime, backMeo;
        //Create Event
        JButton submit;
        JTextField nameText,descText,capacityText;
        JFormattedTextField date;
        JComboBox<String> timeOptionsS, timeOptionE;
        JComboBox<String> list;
        Integer event;
        JComboBox<String> eventsChoice;
        JTextPane timeInfo = new JTextPane();
        JTextArea eventViewer = new JTextArea();
        String user, t;

        EventScreen(String user) throws IOException, ClassNotFoundException {
            this.user = user;
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
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            eS.setVisible(false);
                            new OrganizerScreen(user);
                        }
                    });
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
            list = new JComboBox<>(data);
            list.setBounds(200,y+120,200,25);
            list.addActionListener(this);
            ce.add(list);


            submit = new JButton("Submit");
            submit.setBounds(125,300,100, 25);
            back.setBounds(250,300,100,25);
            submit.addActionListener(this);
            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ce.setVisible(false);
                    eS.setVisible(true);
                }
            });
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
            eventsChoice = new JComboBox<>(conference);
            eventsChoice.addActionListener(this);
            eventsChoice.setBounds(150,100,200,50);
            name.setBounds(195, 75,200,25);
            select.setBounds(200,175,100,25);

            JButton backMo = new JButton("Back");
            backMo.setBounds(200,225,100,25);
            backMo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    me.setVisible(false);
                    eS.setVisible(true);
                }
            });
            me.add(backMo);

            me.add(eventsChoice);
            me.add(select);
            me.add(name);


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

            JButton backmvr = new JButton("Back");
            backmvr.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ve.setVisible(false);
                    modifyEvent();
                }
            });
            ve.add(backmvr);
            backmvr.setBounds(75,125,100,25);

            ArrayList<String> con = eventSystem.getEm().getEventString();
            String[] conference = con.toArray(new String[0]);
            JComboBox<String> conferences = new JComboBox<>(conference);
            conferences.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Event ev = eM.findEvent(eM.getEventIds().get(conferences.getSelectedIndex()));
                    timeInfo.setText("\nDate: " + ev.getEventDate() +
                            "\n\nStart Time: " + ev.getStartTime() +
                            "\n\nEnd Time: " + ev.getEndTime());
                    String speaker = "None";
                    if(ev.eventType().equals("Talk")){
                        Talk t = (Talk) ev;
                        speaker =  t.getSpeakerEmail();
                    }
                    if(ev.eventType().equals("Panel")){
                        assert ev instanceof Panel;
                        Panel t = (Panel) ev;
                        speaker =  t.getSpeakerEmails().toString();
                    }

                    eventViewer.setText("Event Name: " + ev.getName() +
                            "\nEvent Id :" + ev.getEventId() +
                            "\n\nEvent type : " + ev.eventType() +
                            "\n\nSpeaker(s) email: " + speaker +
                            "\n\nDescription : " + ev.getEventDescription() +
                            "\n\nRoom : " + ev.getRoomName() +
                            "\n\nTech Requirements : " + ev.getTechRequirements() +
                            "\n\nCapacity : " + ev.getAttendeeCapacity() +
                            "");
                }
            });
            conferences.setBounds(25,50,200,25);
            ve.add(conferences);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Event Info");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No event selected");
            eventViewer.setBounds(250,25,240,320);
            ve.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
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


            //Shedule Speaker
            int y = 35, x = 140;
            scheduleSpeaker = new JButton("Schedule Speaker");
            scheduleSpeaker.setBounds(x,y,220,35);
            meo.add(scheduleSpeaker);
            y = y +50;
            scheduleSpeaker.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(eM.findEvent(event).eventType().equals("Party")){
                        JOptionPane.showMessageDialog(meo, "Parties cannot add speakers");
                        return;
                    }
                    UserManager um = null;
                    try {
                        um = new UserSave().read();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    assert um != null;
                    try {
                        um = new UserSave().read();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    String[] speakers = um.getSpeaker().toArray(new String[0]);
                    try {
                        String speaker = JOptionPane.showInputDialog(meo, "Chose a speaker", "Speaker",
                                JOptionPane.PLAIN_MESSAGE, null, speakers, 0).toString();
                        try {
                            if (eventSystem.speakerSchedule(event, speaker)) {
                                JOptionPane.showMessageDialog(meo, "Speaker has been scheduled for " +
                                        eventsChoice.getSelectedItem());
                            } else {
                                JOptionPane.showMessageDialog(meo, "Speaker was not added.");
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }catch (Exception e1){}
                }
            });

            //Schedule Rooms
            scheduleRoom = new JButton("Schedule room");
            scheduleRoom .setBounds(x,y,220,35);
            meo.add(scheduleRoom );
            y = y +50;
            scheduleRoom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] rooms = new String[0];
                    try {
                        rooms = eventSystem.usableRooms(event).toArray(new String[0]);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    if(rooms.length!=0) {
                        String room = null;
                        try {
                            room = JOptionPane.showInputDialog(meo, "A room", "Room",
                                    JOptionPane.PLAIN_MESSAGE, null, rooms, 0).toString();
                        }catch (Exception e1){}

                        try {
                            if (!(room == null)) {
                                if (eventSystem.schedule_room(room, event)) {
                                    JOptionPane.showMessageDialog(meo, "Room was scheduled");
                                } else {
                                    JOptionPane.showMessageDialog(meo, "Room was not scheduled");
                                }
                            }

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    }else{
                        JOptionPane.showMessageDialog(meo,"No Suitable Rooms for this event");
                    }
                }
            });

            //Cancel Events
            cancel = new JButton("Cancel Event");
            cancel.setBounds(x,y,220,35);
            meo.add(cancel);
            y = y +50;
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int n = JOptionPane.showConfirmDialog(
                            meo, "Are you sure you want to remove this event?",
                            "Are you sure?",
                            JOptionPane.YES_NO_OPTION);
                    if(n == 0) {
                        try {
                            if (eventSystem.cancel_event(event)) {
                                JOptionPane.showMessageDialog(eS, "Event Was Removed");
                                meo.setVisible(false);
                                modifyEvent();
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            });

            //Change time of Events
            changeTime = new JButton("Change time");
            changeTime.setBounds(x,y,220,35);
            meo.add(changeTime);
            changeTime.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] timeslot = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00",
                            "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
                            "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
                    String start =JOptionPane.showInputDialog(meo,"Pick a new start Time","Change time",
                            JOptionPane.QUESTION_MESSAGE,null,timeslot,0).toString();
                    String end = JOptionPane.showInputDialog(meo,"Pick a new end Time","Change time",
                            JOptionPane.QUESTION_MESSAGE,null,timeslot,0).toString();
                    if(!(start==null)&&!(end==null)){
                        // eventSystem.changeTime(event,start,end,user);
                    }
                }
            });

            //Back button
            backMeo = new JButton("Back");
            backMeo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    meo.setVisible(false);
                    modifyEvent();
                }
            });
            backMeo.setBounds(200,240,100, 30);
            meo.add(backMeo);

        }

        @Override
        public void actionPerformed(ActionEvent e) {

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
                    if(!(eventsChoice.getSelectedItem() =="None")) {
                        currentFrame.setVisible(false);
                        event = eM.getEventIds().get(eventsChoice.getSelectedIndex());
                        modifyEventOptions();
                    }
                }
            }

            if(e.getSource()==submit){
                int choice = JOptionPane.showConfirmDialog(null, "Is this a VIP event?", "VIP?",
                        JOptionPane.YES_NO_CANCEL_OPTION);

                if(choice == 0 ||choice == 1){
                    boolean vip;
                    vip = choice == 0;
                    String[] types = {"talk","party","panel"};
                    Object type = JOptionPane.showInputDialog(null,
                             "What type of event is this?","Event Type",
                            JOptionPane.QUESTION_MESSAGE,null,types,0);

                    ConferencePresenter p =  new ConferencePresenter(eventSystem);

                    try {
                        JOptionPane.showMessageDialog(null,p.addingEvents(type.toString(),
                                nameText.getText(),descText.getText(),
                                timeOptionsS.getItemAt(timeOptionsS.getSelectedIndex()),
                                timeOptionE.getItemAt(timeOptionE.getSelectedIndex()), date.getText(),
                                Integer.parseInt(capacityText.getText()), vip));
                        eventSystem = new EventSystem();
                        eM = new EventSave().read();
                        eventSystem.addTechEvent(eM.getEventIds().get(eM.getEventIds().size()-1),
                                list.getSelectedItem().toString());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        eventSystem.addEvent(type.toString(),nameText.getText(),descText.getText(),
                                timeOptionsS.getItemAt(timeOptionsS.getSelectedIndex()),
                                timeOptionE.getItemAt(timeOptionE.getSelectedIndex()), date.getText(),
                                Integer.parseInt(capacityText.getText()), vip);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }


                }

            }
            
        }
    }

    public static class RoomScreen implements ActionListener {
        JFrame rS = new JFrame(), cr;
        //main buttons
        JButton create, modify, view, back, submit = new JButton(), select = new JButton(), backmro;
        //Manager
        EventManager em = new EventManager();
        JComboBox<String> conferences;
        String currentPage;
        String user;

        RoomScreen(String user) throws IOException {
            this.user = user;
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

            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rS.setVisible(false);
                    new OrganizerScreen(user);
                }
            });

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

        void createRoom() throws IOException {
            //Create Rooms Information
            RoomManager rm = new RoomSave().read();
            JTextField roomNameI, capacity, timeS, timeE;
            cr = new JFrame();
            setFrame(cr, "Create Room");
            final String[] techreqirement = new String[1];

            int x = 100, y = 40, space = 40;
            JLabel roomName = new JLabel("Room Name");
            roomName.setBounds(x,y,100,25);
            cr.add(roomName);

            roomNameI = new JTextField();
            roomNameI.setBounds(x+100,y,200,25);
            cr.add(roomNameI);
            y = y + space;

            JLabel cap= new JLabel("Capacity");
            cap.setBounds(x,y,100,25);
            cr.add(cap);

            capacity = new JTextField();
            capacity.setBounds(x+100,y,200,25);
            cr.add(capacity);
            capacity.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                        capacity.setEditable(true);
                    } else {
                        JOptionPane.showMessageDialog(null,"* Enter only numeric digits(0-9)");
                        capacity.setText("");
                    }
                }
            });
            cr.add(capacity);
            y = y + space;

            JLabel start = new JLabel("Open");
            start.setBounds(x,y,50,25);
            cr.add(start);

            String[] timeslot = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00",
                    "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00",
                    "19:00", "20:00", "21:00", "22:00", "23:00"};
            JComboBox<String> timeOptionsS = new JComboBox<>(timeslot);
            timeOptionsS.setBounds(175,y,75,25);
            cr.add(timeOptionsS);

            JLabel end = new JLabel("Close");
            end.setBounds(x+175,y,50,25);
            cr.add(end);

            JComboBox<String> timeOptionE = new JComboBox<>(timeslot);
            timeOptionE.setBounds(x+225,y,75,25);
            cr.add(timeOptionE);
            y = y+space;

            JLabel techOptions = new JLabel("Tech options");
            techOptions.setBounds(100,y,100,25);
            cr.add(techOptions);

            Border blackLine = BorderFactory.createLineBorder(Color.black);
            ArrayList<String> options = rm.getTechOptions();
            String[] data = options.toArray(new String[0]);
            JComboBox<String> list = new JComboBox<>(data);
            list.addActionListener(this);
            list.setBounds(200,y,200,25);
            cr.add(list);

            JButton create = new JButton("Create Room");
            create.setBounds(190,y+75,120,25);
            create.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    EventSystem es = null;
                    try {
                        es = new EventSystem();
                    } catch (IOException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    try {
                        assert es != null;
                        String room = roomNameI.getText();
                        if(es.addRoom(roomNameI.getText(),Integer.parseInt(capacity.getText()),
                                Objects.requireNonNull(timeOptionsS.getSelectedItem()).toString(),
                                Objects.requireNonNull(timeOptionE.getSelectedItem()).toString())){
                            JOptionPane.showMessageDialog(cr,"Room was created");
                            es.addTech(room,list.getSelectedItem().toString());
                            cr.setVisible(false);
                            rS.setVisible(true);
                        }else{
                            JOptionPane.showMessageDialog(cr, "Room wasn't added");
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            cr.add(create);

            JButton back = new JButton("back");
            back.setBounds(200,y+125,100,25);
            back.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cr.setVisible(false);
                    rS.setVisible(true);
                }
            });
            cr.add(back);

        }

        void modifyRooms() throws IOException {
            RoomManager rm = new RoomSave().read();
            JFrame mr = new JFrame();
            setFrame(mr, "Modify Rooms");
            currentPage = "modifyRoom";
            select = new JButton("Select");
            select.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String room;
                    room = Objects.requireNonNull(conferences.getSelectedItem()).toString();
                    if (!(room == null) && !(room.equals("None"))) {
                        mr.setVisible(false);
                        try {
                            modifyRoomsOptions();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            });

            ArrayList<String> con = rm.getRoomsString();
            String[] conference = con.toArray(new String[0]);
            JLabel name = new JLabel("Select A Room");
            conferences = new JComboBox<>(conference);
            conferences.addActionListener(this);

            conferences.setBounds(150, 100, 200, 50);
            name.setBounds(195, 75, 200, 25);
            select.setBounds(200, 175, 100, 25);
            JButton backm = new JButton("Back");
            backm.setBounds(200,210,100,25);
            backm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mr.setVisible(false);
                    rS.setVisible(true);
                }
            });
            mr.add(conferences);
            mr.add(select);
            mr.add(name);
            mr.add(backm);
        }

        void viewRooms() throws IOException {
            RoomManager rm = new RoomSave().read();
            JTextPane timeInfo = new JTextPane();
            JTextArea eventViewer = new JTextArea();
            JFrame vr = new JFrame();
            setFrame(vr, "View Room");
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            JLabel name = new JLabel("Select A Room");
            name.setBounds(85, 25, 200, 25);
            vr.add(name);

            JButton backr = new JButton("Back");
            vr.add(backr);
            backr.setBounds(75, 125, 100, 25);
            backr.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    vr.setVisible(false);
                    rS.setVisible(true);
                }
            });

            ArrayList<String> con = rm.getRoomsString();
            String[] room = con.toArray(new String[0]);
            JComboBox<String> roomsList = new JComboBox<>(room);
            roomsList.setBounds(25, 50, 200, 25);
            roomsList.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!Objects.equals(roomsList.getSelectedItem(), "None")) {
                        Room r = rm.find_room(Objects.requireNonNull(roomsList.getSelectedItem()).toString());
                        timeInfo.setText(
                                "\n\nOpen Time: " + r.getOpenTime() +
                                        "\n\nClose Time: " + r.getCloseTime());

                        eventViewer.setText("Room: " + r.getName() +
                                "\nAvailable Tech : " + r.getTechAvailable() +
                                "\n\nCapacity : " + r.getRoomCapacity() +
                                "\n\nBookings : " + rm.bookingToString(r)
                        );
                    }
                }
            });
            vr.add(roomsList);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel, "Room Information");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No Room selected");
            eventViewer.setBounds(250, 25, 240, 320);
            vr.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel, "Time");
            event.setTitleJustification(TitledBorder.LEADING);
            timeInfo.setText("\n\nOpen Time: N/A\n\nClose Time: N/A");
            timeInfo.setBounds(0, 190, 250, 190);
            timeInfo.setEditable(false);
            vr.add(timeInfo);
            timeInfo.setBorder(time);

        }

        void modifyRoomsOptions() throws IOException {
            RoomManager rm = new RoomSave().read();
            JFrame mro = new JFrame();
            setFrame(mro, "Modify Menu");
            JButton addTech = new JButton("Add Tech Option"), removeTech = new JButton("Remove Tech Option"),
                    changeCapacity=new JButton("Change Capacity");
            JButton[] buttons = new JButton[]{addTech, removeTech, changeCapacity};
            String[] names = new String[]{"Add Tech Available", "Remove Tech Available", "Change Capacity"};
            int y = 35, x = 140, i = 0;
            for (JButton b : buttons) {
                b.setBounds(x, y, 220, 35);
                b.addActionListener(this);
                mro.add(b);
                y = y + 50;
                i = i + 1;
            }

            addTech.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String room;
                    room = Objects.requireNonNull(conferences.getSelectedItem()).toString();
                    Room r = rm.find_room(room);
                    EventSystem es = null;
                    try {
                        es = new EventSystem();
                    } catch (IOException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    String[] options = rm.getTechOptions().toArray(new String[0]);
                    String tech = JOptionPane.showInputDialog(mro,"which tech would you like to add to this room?",null,
                            JOptionPane.PLAIN_MESSAGE, null, options, 0).toString();
                    if(tech!="None"||tech!=null) {
                        try {
                            assert es != null;
                            es.addTech(room, tech);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            });

            removeTech.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String room;
                    room = Objects.requireNonNull(conferences.getSelectedItem()).toString();
                    Room r = rm.find_room(room);
                    String[] options = r.getTechAvailable().toArray(new String[0]);
                    String removed = JOptionPane.showInputDialog(mro,"which tech would you like to remove from this room?",
                            null, JOptionPane.PLAIN_MESSAGE, null, options, 0).toString();
                    if(!(removed==null)) {
                        try {
                            new EventSystem().removeTech(room, removed);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            });

            changeCapacity.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String room;
                    room = Objects.requireNonNull(conferences.getSelectedItem()).toString();
                    Room r = rm.find_room(room);
                    int cap = Integer.parseInt(JOptionPane.showInputDialog(mro,
                            "What would you like to change the capacity of the room to?"));
                    try {
                        new EventSystem().changeCap(room,cap);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
            });

            backmro = new JButton("Back");
            backmro.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mro.setVisible(false);
                    try {
                        modifyRooms();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            backmro.setBounds(200, 240, 100, 30);
            mro.add(backmro);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == create) {
                try {
                    createRoom();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                rS.setVisible(false);
            }
            if (e.getSource() == modify) {
                try {
                    modifyRooms();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                rS.setVisible(false);
            }
            if (e.getSource() == view) {
                try {
                    viewRooms();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                rS.setVisible(false);
            }

        }
    }

    public static class ModifyConference implements ActionListener{
        JButton addEvent, cancelEvent, changeTime, back;
        JFrame modifyScreen, current;
        Conference con;
        ConferenceManager cm =  new ConferenceSave().read();
        EventManager em = new EventSave().read();
        String user;

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

        ModifyConference(String con, String user) throws IOException {
            this.con = cm.findConference(con);
            String conName = con;
            modifyScreen = new JFrame();
            current = modifyScreen;
            setFrame(modifyScreen,"Modify Screen");
            this.user = user;

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

            back = new JButton("Back");
            back.setBounds(200,200,100,25);
            back.addActionListener(this);
            modifyScreen.add(back);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==addEvent){
                try {
                    ArrayList<String> events = em.eventToStrings(con.getConfDate());
                    ArrayList<Integer> eventId = em.eventToList(con.getConfDate());
                    String[] eve = events.toArray(new String[0]);
                    Object addedevent = JOptionPane.showInputDialog(modifyScreen, "Select Event", "Add Event",
                            JOptionPane.PLAIN_MESSAGE, null, eve, 0);
                    if (addedevent != null) {
                        if (new EventSystem().addEventToConference(con.getName(),
                                eventId.get(events.indexOf(addedevent)))) {
                                JOptionPane.showMessageDialog(modifyScreen, "Event was added to conference");
                                em = new EventSave().read();
                                cm = new ConferenceSave().read();
                            }
                        else {
                                JOptionPane.showMessageDialog(modifyScreen, "Event wasn't added");
                            }
                    }
                }catch (Exception e1){

                }
            }


            if(e.getSource()==cancelEvent){
                EventSystem es = null;
                try {
                    es = new EventSystem();
                } catch (IOException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                try {
                    ArrayList<Integer> events = con.getEventIds();
                    Integer[] eve = events.toArray(new Integer[0]);
                    Integer eventId = Integer.parseInt(JOptionPane.showInputDialog(modifyScreen, "Select Event", "Cancel Event",
                            JOptionPane.PLAIN_MESSAGE, null, eve, 0).toString());

                    if (eventId != null) {
                        try {
                            assert es != null;
                            es.cancelEventInConference(con.getName(), em.getEventIds().get(events.indexOf(eventId)));
                            JOptionPane.showMessageDialog(modifyScreen, "Event was Removed to conference");
                            em = new EventSave().read();
                            cm = new ConferenceSave().read();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }catch (Exception e1){

                }
            }

            if(e.getSource()==back){
                current.setVisible(false);
                new OrganizerScreen(user);
            }

        }
    }

    public static void main(String[] args) throws IOException {
        new StartScreen();
    }
}






