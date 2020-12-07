import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import UseCase.EventManager;

/**
 * Event Calendar and its functionality.
 *
 * @author Hao Liu
 * @version 1.0
 */

public class EventCalendar extends JFrame implements ItemListener {
	private static final long serialVersionUID = -2286709968657606550L;
	JPanel p1, p2;
	JComboBox<String> month;
	JComboBox<Integer> year;
	EventManager eventManager;
	int days[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	String weekdays[] = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	public EventCalendar(String title) {
		super();
		eventManager = new EventManager();
		eventManager.createEvent("panel", "Event for today", "This event will be reoccur eveyday", LocalTime.now(),
				LocalTime.now(), LocalDate.now(), 10, true);
		setTitle(title);
		p1 = new JPanel();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		setLocation(x, y);
		p1.setLayout(new FlowLayout());
		month = new JComboBox<String>();
		for (int i = 0; i < months.length; i++) {
			month.addItem(months[i]);
		}
		LocalDate date = LocalDate.now();
		month.setSelectedItem(months[date.getMonth().getValue() - 1]);
		month.addItemListener(this);
		year = new JComboBox<Integer>();
		for (int i = 1980; i <= 2099; i++) {
			year.addItem(i);
		}
		year.setSelectedItem(date.getYear());
		year.addItemListener(this);
		p1.add(month);
		p1.add(year);
		p2 = new JPanel();
		p2.setLayout(new GridLayout(0, 7, 5, 5));
		drawCalendar(Integer.toString(date.getMonthValue()), date.getYear());
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		add(p1);
		add(p2);
		setVisible(true);
		setBounds(200, 200, 800, 800);
		setSize(800, 800);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String args[]) {
		new EventCalendar("Event Calendar");
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			int j = 0;
			for (int i = 0; i < months.length; i++) {
				j++;
				if (months[i] == month.getSelectedItem()) {
					if (i < 10) {
						drawCalendar(String.format("%02d", j), (Integer) year.getSelectedItem());
					} else {
						drawCalendar(Integer.toString(j), (Integer) year.getSelectedItem());
					}
				}
			}
		}
	}

	public void drawCalendar(String inputMonth, int inputYear) {
		p2.removeAll();
		for (int i = 0; i < weekdays.length; i++) {
			JLabel label = new JLabel(weekdays[i]);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			p2.add(label);
		}
		LocalDate date = LocalDate.parse(inputYear + "-" + inputMonth + "-" + "01");
		int noOfDaysInMonth = days[date.getMonth().getValue() - 1];
		if (date.getYear() % 4 == 0 && date.getMonth().getValue() == 1) {
			noOfDaysInMonth = 29;
		}

		for (int day = 1; day <= noOfDaysInMonth;) {
			for (int j = 0; j < 7; j++) {
				if (day == 1) {
					if (j == date.getDayOfWeek().getValue()) {
						final int dayd = day;
						JLabel label = null;
						LocalDate dat = null;
						if (dayd < 10) {
							dat = LocalDate.parse(inputYear + "-" + inputMonth + "-" + String.format("%02d", dayd));
						} else {
							dat = LocalDate.parse(inputYear + "-" + inputMonth + "-" + dayd);
						}
						if (eventManager.getEventsOn(dat).size() > 0) {
							label = new JLabel(String.valueOf(day) + "*");
						} else {
							label = new JLabel(String.valueOf(day));
						}
						Border blackline = BorderFactory.createLineBorder(Color.lightGray);
						label.setBorder(blackline);
						label.setPreferredSize(new Dimension(100, 100));
						label.setHorizontalAlignment(SwingConstants.CENTER);
						label.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								LocalDate date = null;
								if (dayd < 10) {
									date = LocalDate
											.parse(inputYear + "-" + inputMonth + "-" + String.format("%02d", dayd));
								} else {
									date = LocalDate.parse(inputYear + "-" + inputMonth + "-" + dayd);
								}
								List<Entities.Event> events = eventManager.getEventsOn(date);
								List<Map<String, String>> list = new ArrayList<>();
								Map<String, String> map = new HashMap<>();
								for (Entities.Event event : events) {
									map.put("Type", event.eventType());
									map.put("Name", event.getName());
									map.put("Description", event.getEventDescription());
									map.put("Start time", event.getStartTime().toString());
									map.put("End time", event.getEndTime().toString());
									map.put("Room", event.getRoomName());
									map.put("Attendee", event.getAttendeeEmails().toString());
									map.put("Organizers", event.getOrganizerEmails().toString());
									map.put("Tech Requirements", event.getTechRequirements().toString());
									map.put("Attendee Capacity", String.valueOf(event.getAttendeeCapacity()));
									list.add(map);
								}
								JOptionPane.showMessageDialog(null, "Events on: " + date.getYear() + "-"
										+ date.getMonth() + "-" + dayd + "\n" + list.toString());
							}
						});
						p2.add(label);
						day++;
					} else {
						JLabel label = new JLabel("");
						label.setPreferredSize(new Dimension(100, 100));
						p2.add(label);
					}
				} else {
					final int dayd = day;
					JLabel label = null;
					LocalDate dat = null;
					if (dayd < 10) {
						dat = LocalDate.parse(inputYear + "-" + inputMonth + "-" + String.format("%02d", dayd));
					} else {
						dat = LocalDate.parse(inputYear + "-" + inputMonth + "-" + dayd);
					}
					if (eventManager.getEventsOn(dat).size() > 0) {
						label = new JLabel(String.valueOf(day) + "*");
					} else {
						label = new JLabel(String.valueOf(day));
					}
					Border blackline = BorderFactory.createLineBorder(Color.lightGray);
					label.setBorder(blackline);
					label.setPreferredSize(new Dimension(100, 100));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							LocalDate date = null;
							if (dayd < 10) {
								date = LocalDate
										.parse(inputYear + "-" + inputMonth + "-" + String.format("%02d", dayd));
							} else {
								date = LocalDate.parse(inputYear + "-" + inputMonth + "-" + dayd);
							}
							List<Entities.Event> events = eventManager.getEventsOn(date);
							List<Map<String, String>> list = new ArrayList<>();
							Map<String, String> map = new HashMap<>();
							for (Entities.Event event : events) {
								map.put("Type", event.eventType());
								map.put("Name", event.getName());
								map.put("Description", event.getEventDescription());
								map.put("Start time", event.getStartTime().toString());
								map.put("End time", event.getEndTime().toString());
								map.put("Room", event.getRoomName());
								map.put("Attendee", event.getAttendeeEmails().toString());
								map.put("Organizers", event.getOrganizerEmails().toString());
								map.put("Tech Requirements", event.getTechRequirements().toString());
								map.put("Attendee Capacity", String.valueOf(event.getAttendeeCapacity()));
								list.add(map);
							}
							JOptionPane.showMessageDialog(null, "Events on: " + date.getYear() + "-" + date.getMonth()
									+ "-" + dayd + "\n" + list.toString());
						}
					});
					p2.add(label);
					day++;
				}
				if (day > noOfDaysInMonth) {
					break;
				}
			}
		}
		p2.validate();
		setTitle(months[Integer.parseInt(inputMonth) - 1] + ", " + inputYear);
	}
}