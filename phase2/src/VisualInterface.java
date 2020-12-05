import Controllers.EventSystem;
import Controllers.LoginSystem;
import Entities.User;
import Gateway.UserSave;
import UseCase.ConferenceManager;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


class Test {
    JFrame mainPage, currentPage;

    public static class LoginScreen implements ActionListener {
        JButton loginButton;
        JPasswordField passwordI;
        JTextField userNameI;
        JFrame f = new JFrame();
        LoginSystem ls = new LoginSystem();

        LoginScreen() {

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
                    new MainScreen(userNameI.getText(), new UserManager());
                }else{
                    JOptionPane.showMessageDialog(null, "Incorrect Login Credentials ");
                }
            }

        }
    }

    public static class MainScreen implements ActionListener{
        JButton organizerOptions, userOptions, logOut;
        JFrame main = new JFrame();
        MainScreen(String user, UserManager um){
            main.setTitle("Event Interface System 1.0");
            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main.setResizable(false);
            main.setSize(500, 380);
            main.setVisible(true);
            main.setLayout(null);
            User userAccount = um.findUser(user);

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
                main.setVisible(true);
                JOptionPane.showMessageDialog(null, "Not implemented yet");
            }
            if(e.getSource()==logOut){
                main.setVisible(false);
                new LoginScreen();
            }
        }
    }

    public static class OrganizerScreen implements ActionListener {
        JButton aeButton, crButton, csButton, eoButton, veButton, vrButton, ibButton, exit;
        public JFrame f = new JFrame(), currentFrame;
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
            veButton = new JButton("View Events");
            vrButton = new JButton("View Rooms");
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

            aeButton.setBounds(bx, by, 190, 50);
            crButton.setBounds(bx + 200, by, 190, 50);
            csButton.setBounds(bx, by + 60, 190, 50);
            eoButton.setBounds(bx + 200, by + 60, 190, 50);
            veButton.setBounds(bx, by + 120, 190, 50);
            vrButton.setBounds(bx + 200, by + 120, 190, 50);
            ibButton.setBounds(bx, by + 180, 190, 50);
            exit.setBounds(bx + 200, by + 180, 190, 50);

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
                new LoginScreen();
            }

            if (e.getSource() == aeButton) {
                f.setVisible(false);
                new ConferenceScreen(f, cM);
            }
            if (e.getSource() == crButton) {
                f.setVisible(false);
                new EventScreen(f,eM);
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
        JFrame cc = null,mc = null;
        JButton submit;
        ConferenceManager cM = new ConferenceManager();

        ConferenceScreen(JFrame f, ConferenceManager cM){
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
            JFrame cc = new JFrame();
            this.cc = cc;
            setFrame(cc,"Create Conference Panel");
            JTextField nameText,descText;
            JFormattedTextField date;
            JComboBox<String> timeOptionsS, timeOptionE;

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
            setFrame(mc,"Modify Conference Panel");
            JLabel name = new JLabel("Select A Conference");
            JButton select = new JButton("Select");

            ArrayList<String> con = cM.getConferenceList();
            String[] conference = con.toArray(new String[0]);
            JComboBox<String> conferences = new JComboBox<>(conference);

            conferences.setBounds(150,100,200,50);
            name.setBounds(195, 75,200,25);
            select.setBounds(200,175,100,25);

            mc.add(conferences);
            mc.add(select);
            mc.add(name);
            mc.add(back);
        }

        void viewConference(){
            JFrame vc = new JFrame();
            Border lowerLevel = BorderFactory.createLoweredBevelBorder();
            vc.setResizable(false);
            setFrame(vc,"View Conference Panel");

            JLabel name = new JLabel("Select A Conference");
            name.setBounds(60,60,200,25);
            vc.add(name);

            JButton select = new JButton("Select");
            select.setBounds(75,130,100,25);
            vc.add(select);

            vc.add(back);
            back.setBounds(75,160,100,25);

            ArrayList<String> con = cM.getConferenceList();
            String[] conference = con.toArray(new String[0]);
            JComboBox<String> conferences = new JComboBox<>(conference);
            conferences.setBounds(25,100,200,25);
            vc.add(conferences);

            TitledBorder event = BorderFactory.createTitledBorder(lowerLevel,"Event");
            event.setTitleJustification(TitledBorder.LEADING);

            //event box set up
            JPanel conferenceSelector;
            JTextArea eventViewer = new JTextArea();
            eventViewer.setLineWrap(true);
            eventViewer.setEditable(false);
            eventViewer.setText("No Events Scheduled");
            eventViewer.setBounds(250,25,240,320);
            vc.add(eventViewer);
            eventViewer.setBorder(event);

            //Time Box set up
            TitledBorder time = BorderFactory.createTitledBorder(lowerLevel,"Time");
            event.setTitleJustification(TitledBorder.LEADING);
            JTextPane timeInfo = new JTextPane();
            timeInfo.setText("\nDate: ----\n\nStart Time: -----\n\nEnd Time: -----");
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
            if(e.getSource()==modify){
                modifyConference();
                cS.setVisible(false);
            }
            if(e.getSource()==view){
                viewConference();
                cS.setVisible(false);
            }
        }
    }
    
    public static class EventScreen implements ActionListener{
        JFrame eS = new JFrame(), f, currentFrame;
        String currentPage;
        JButton create, modify, view, back, select;
        EventManager eM = new EventManager();
        //Event info
        JTextField enI, edI, timeS, timeE, dateI;
        //MEO Buttons
        JButton scheduleSpeaker, scheduleRoom, cancel, changeTime, backmeo;
        EventScreen(JFrame f, EventManager eM){
            this.f = f;
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
            Dimension d = new Dimension(200, 50);
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

            JButton submit;
            JTextField nameText,descText;
            JFormattedTextField date;
            JComboBox<String> timeOptionsS, timeOptionE;

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
                ce.add(box);
                ce.add(label);
                y = y + 50;
            }

            date.setBounds(x+75,y,100,25);
            dateT.setBounds(25,y,125,25);
            ce.add(dateT);
            ce.add(date);



            String[] timeslot = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00",
                    "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00",
                    "19:00", "20:00", "21:00", "22:00", "23:00"};

            timeOptionsS = new JComboBox(timeslot);
            timeOptionsS.setBounds(125,y+50,75,25);
            start.setBounds(50,y+50,100,25);
            ce.add(start);
            ce.add(timeOptionsS);

            timeOptionE = new JComboBox(timeslot);
            timeOptionE.setBounds(x+175,y+50,75,25);
            end.setBounds(x+110,y+50,100,25);
            ce.add(end);
            ce.add(timeOptionE);

            submit = new JButton("Submit");
            submit.setBounds(125,y+100,100, 50);
            back.setBounds(250,y+100,100,50);
            submit.addActionListener(this); back.addActionListener(this);
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
            JPanel conferenceSelector;
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

            backmeo = new JButton("Back");
            backmeo.addActionListener(this);
            backmeo.setBounds(200,240,100, 30);
            meo.add(backmeo);



        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==back||e.getSource()==backmeo){
                eS.setVisible(false);
                f.setVisible(true);
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

            JLabel l = new JLabel("Room Creator");
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            cr.add(l);
            l.setBounds(0, 0, 500, 300);

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



    public static class AddEvent implements ActionListener {
        JFrame ae = new JFrame();
        JButton submit, back;
        OrganizerScreen os;
        JTextField enI, edI, timeS, timeE, dateI;

        AddEvent(OrganizerScreen organizerScreen) {
            os = organizerScreen;
            ae.setTitle("Event Interface System 1.0");
            ae.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ae.setResizable(true);
            ae.setSize(500, 380);
            JLabel l = new JLabel("Event Creator");
            ae.setLayout(null);

            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            ae.add(l);
            l.setBounds(0, 0, 500, 300);

            JLabel eventName = new JLabel("Event Name");
            ae.add(eventName);
            JLabel eventDescription = new JLabel("Event Description");
            ae.add(eventDescription);
            JLabel timeT = new JLabel("Time (HH:mm)");
            ae.add(timeT);
            JLabel date = new JLabel("Date (dd-mm-yyyy)");
            ae.add(date);
            submit = new JButton("Submit");
            back = new JButton("Back");

            eventName.setBounds(90, 20, 100, 25);
            eventDescription.setBounds(90, 60, 100, 25);
            timeT.setBounds(90, 100, 100, 25);
            date.setBounds(90, 140, 100, 25);

            enI = new JTextField(); edI = new JTextField(); timeS = new JTextField();
            timeE = new JTextField(); dateI = new JTextField();

            ae.add(enI);
            ae.add(edI);
            ae.add(timeS);
            ae.add(timeE);
            ae.add(dateI);

            enI.setBounds(200, 20, 170, 25);
            edI.setBounds(200, 60, 170, 25);
            timeS.setBounds(200, 100, 70, 25);
            timeE.setBounds(300, 100, 70, 25);
            dateI.setBounds(200, 140, 170, 25);

            ae.add(submit);
            submit.setBounds(230, 180, 100, 25);

            ae.add(back);
            back.setBounds(230, 220, 100, 25);


            submit.addActionListener(this);
            back.addActionListener(this);
            ae.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==back){
                ae.setVisible(false);
                os.f.setVisible(true);

            }
            if(e.getSource()==submit){
                EventSystem ec = null;
                try {
                    ec = new EventSystem();
                } catch (ClassNotFoundException | IOException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                try {
                    assert ec != null;
                    if(ec.add_event("panel",enI.getName(), edI.getText(),timeS.getText(),
                            timeE.getText(),dateI.getText(),2,false)){
                        JOptionPane.showMessageDialog(null,"Event was added");
                        ae.setVisible(false);
                        os.f.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Event was not added due to" +
                                "input error");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }

        }
    }

    public static class CreateRoom implements ActionListener {
        JFrame cr = new JFrame();
        JButton submit, back;
        OrganizerScreen os;
        JTextField roomNameI, capacity, timeS, timeE;

        CreateRoom(OrganizerScreen organizerScreen) {
            os = organizerScreen;
            cr.setTitle("Event Interface System 1.0");
            cr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            cr.setResizable(true);
            cr.setSize(500, 380);
            JLabel l = new JLabel("Room Creator");
            cr.setLayout(null);

            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            cr.add(l);
            l.setBounds(0, 0, 500, 300);

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

            roomNameI = new JTextField(); this.capacity = new JTextField(); timeS = new JTextField();
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
            cr.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == back) {
                cr.setVisible(false);
                os.f.setVisible(true);
            }
            if (e.getSource() == submit) {
                EventSystem ec = null;
                try {
                    ec = new EventSystem();
                } catch (ClassNotFoundException | IOException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                try {
                    assert ec != null;
                    if (ec.add_room(roomNameI.getText(), Integer.parseInt(capacity.getText()),
                            timeS.getText(), timeE.getText())) {
                        JOptionPane.showMessageDialog(null, "room was added");
                        cr.setVisible(false);
                        os.f.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Room was not added due to" +
                                " input error");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            }
        }
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}






