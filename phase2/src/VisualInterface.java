import Controllers.EventSystem;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


class Test {

    public static class LoginScreen implements ActionListener {
        JButton loginButton;
        JPasswordField passwordI;
        JTextField userNameI;
        JFrame f = new JFrame();

        LoginScreen() {

            Border border = BorderFactory.createLineBorder(Color.darkGray, 2);

            //Window set up
            f.setTitle("Entities.Event Interface System 1.0");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setResizable(true);
            f.setSize(500, 140);
            JLabel l = new JLabel("Welcome to the Entities.Event Systems");
            f.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            l.setBorder(border);
            f.add(l);
            l.setBounds(0, 0, 500, 300);

            //--------Login in----------------

            //user text in box
            JLabel userNameT = new JLabel("Entities.User Name:");
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
                System.out.println(userNameI.getText());
                System.out.println(passwordI.getPassword());
                // Controllers.LoginSystem ls = new Controllers.LoginSystem(new UseCase.UserManager());
                f.setVisible(false);
                new OrganizerScreen();
            }

        }
    }

    public static class OrganizerScreen implements ActionListener {
        JButton aeButton, crButton, csButton, eoButton, veButton, vrButton, ibButton, exit;
        public JFrame f = new JFrame();

        OrganizerScreen() {


            //Window set up
            f.setTitle("Entities.Event Interface System 1.0");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setResizable(true);
            f.setSize(500, 380);
            JLabel l = new JLabel("Entities.Event Control Systems");
            f.setLayout(null);

            //title setup
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            f.add(l);
            l.setBounds(0, 0, 500, 300);

            aeButton = new JButton("Add Entities.Event");
            crButton = new JButton("Create Entities.Room");
            csButton = new JButton("Create Entities.Speaker");
            eoButton = new JButton("Entities.Event Options");
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

            Dimension d = new Dimension(190, 50);
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
                new AddEvent(this);
            }
            if (e.getSource() == crButton) {
                f.setVisible(false);
                new CreateRoom(this);
            }

            if (e.getSource() == csButton || e.getSource() == eoButton
                    || e.getSource() == veButton || e.getSource() == vrButton || e.getSource() == ibButton) {
                JOptionPane.showMessageDialog(null, "Not implemented yet");
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
            ae.setTitle("Entities.Event Interface System 1.0");
            ae.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ae.setResizable(true);
            ae.setSize(500, 380);
            JLabel l = new JLabel("Entities.Event Creator");
            ae.setLayout(null);

            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            ae.add(l);
            l.setBounds(0, 0, 500, 300);

            JLabel eventName = new JLabel("Entities.Event Name");
            ae.add(eventName);
            JLabel eventDescription = new JLabel("Entities.Event Description");
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
                    // I don't know if this input is right for add_event, please check @Chevoy
                    if(ec.add_event(enI.getName(), enI.getName(), edI.getText(),timeS.getText(),
                            timeE.getText(),dateI.getText(),2,false)){
                        JOptionPane.showMessageDialog(null,"Entities.Event was added");
                        ae.setVisible(false);
                        os.f.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Entities.Event was not added due to" +
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
            cr.setTitle("Entities.Event Interface System 1.0");
            cr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            cr.setResizable(true);
            cr.setSize(500, 380);
            JLabel l = new JLabel("Entities.Room Creator");
            cr.setLayout(null);

            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.TOP);
            cr.add(l);
            l.setBounds(0, 0, 500, 300);

            JLabel eventName = new JLabel("Entities.Room Name");
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
                        JOptionPane.showMessageDialog(null, "Entities.Room was not added due to" +
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





