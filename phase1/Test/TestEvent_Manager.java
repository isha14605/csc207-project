import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class testEvent_Manager {

    @Test(timeout = 50)
    public void test_add_Event(){

        String n = new String("test");
        String d = new String("testing talks");
        LocalDateTime start = LocalDateTime.of(2020,3,12,9,0);
        LocalDateTime end = LocalDateTime.of(2020,3,12,17,0);

        Event_Manager event_manager = new Event_Manager();
        assertTrue("event is added", event_manager.add_Event(n, d, start, end));
        assertFalse("event shouldn't have been added", event_manager.add_Event(n,d,start,end));
        Event_Manager event_manager2 = new Event_Manager();
        assertEquals("size should not reset on creation of new events", Event_Manager.events.size(), 1);

    }

    @Test(timeout = 50)
    public void test_add_talk(){

        String n = new String("test");
        String d = new String("testing talks");
        LocalDateTime start = LocalDateTime.of(2020,3,12,9,0);
        LocalDateTime end = LocalDateTime.of(2020,3,12,17,0);
        Talk t = new Talk("2020","12","13");

        Event_Manager event_manager = new Event_Manager();
        event_manager.add_Event(n,d,start,end);
        assertTrue("talk is added to this event talks", event_manager.add_talk(t,Event_Manager.events.get(0)));
        assertFalse("duplicate shouldn't be added", event_manager.add_talk(t,Event_Manager.events.get(0)));

    }

    @Test(timeout = 50)
    public void test_remove_talk(){

        String n = new String("test");
        String d = new String("testing talks");
        LocalDateTime start = LocalDateTime.of(2020,3,12,9,0);
        LocalDateTime end = LocalDateTime.of(2020,3,12,17,0);
        Talk t = new Talk("2020","12","13");

        Event_Manager event_manager = new Event_Manager();
        event_manager.add_Event(n,d,start,end);
        assertTrue("talk is added to this event talks",
                event_manager.add_talk(t,Event_Manager.events.get(0)));
        assertTrue("talk is removed to this event talks",
                event_manager.remove_talk(t,Event_Manager.events.get(0)));
        assertEquals("talk is removed correctly", 0,
                Event_Manager.events.get(0).talks.size());
    }

    @Test(timeout = 50)
    public void test_remove_talk2(){

        String n = new String("test");
        String d = new String("testing talks");
        LocalDateTime start = LocalDateTime.of(2020,3,12,9,0);
        LocalDateTime end = LocalDateTime.of(2020,3,12,17,0);

        Talk t1 = new Talk("2020","12","13");
        Talk t2 = new Talk("2020","9","10");
        Talk t3 = new Talk("2020","14","15");

        Event_Manager event_manager = new Event_Manager();
        event_manager.add_Event(n,d,start,end);

        event_manager.add_talk(t1, Event_Manager.events.get(0));
        event_manager.add_talk(t2, Event_Manager.events.get(0));
        event_manager.add_talk(t3, Event_Manager.events.get(0));

        assertTrue("talk is removed to this event talks",
                event_manager.remove_talk(t2,Event_Manager.events.get(0)));
        assertFalse("talk is not part of talks",
                Event_Manager.events.get(0).getTalks().contains(t2));
    }

    @Test(timeout = 50)
    public void test_schedule_speaker(){

        String n = new String("test");
        String d = new String("testing talks");
        LocalDateTime start = LocalDateTime.of(2020,3,12,9,0);
        LocalDateTime end = LocalDateTime.of(2020,3,12,17,0);

        Talk t1 = new Talk("2020","12","13");
        Speaker s = new Speaker("David", "123", "ted@usa");
        Event_Manager event_manager = new Event_Manager();
        event_manager.add_Event(n,d,start,end);
        event_manager.add_talk(t1, Event_Manager.events.get(0));

        assertTrue("speaker is added", event_manager.schedule_speaker(s,t1,
                Event_Manager.events.get(0)));

        assertTrue("speaker has talk added to their list", s.getEvents_attending().contains(t1));
    }

    @Test(timeout = 50)
    public void test_schedule_speaker2(){

        String n = new String("test");
        String d = new String("testing talks");
        LocalDateTime start = LocalDateTime.of(2020,3,12,9,0);
        LocalDateTime end = LocalDateTime.of(2020,3,12,17,0);

        Talk t1 = new Talk("2020","12","13");
        Speaker s = new Speaker("David", "123", "ted@usa");
        Event_Manager event_manager = new Event_Manager();
        event_manager.add_Event(n,d,start,end);
        event_manager.add_talk(t1, Event_Manager.events.get(0));

        assertTrue("speaker is added", event_manager.schedule_speaker(s,t1,
                Event_Manager.events.get(0)));

        assertFalse("Speaker can't be assigned to the same room", event_manager.schedule_speaker(s,t1,
                Event_Manager.events.get(0)));
    }
}
