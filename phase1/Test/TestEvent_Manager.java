import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class TestEvent_Manager {

    @Test(timeout = 50)
    public void test_add_Event(){
        EventManager em = new EventManager();
        assertTrue(em.events.isEmpty());

        String n = new String("test");
        String d = new String("testing talks");
        LocalTime start = LocalTime.of(9,0);
        LocalTime end = LocalTime.of(9,0);
        LocalDate date = LocalDate.of(2020,10,3);
        em.create_event(n,d,start,end,date);
        System.out.println(em.getEvents());
        assertFalse(em.events.isEmpty());
        em.eventToString(em.events.get(0));
    }

    @Test(timeout = 150)
    public void Test_Event_Controller_add_Event(){
        EventController ec = new EventController();
        EventManager em = new EventManager();

        assertTrue(em.getEvents().isEmpty());

        ec.add_event("test","test","09:00", "12:00","2020-10-03" );
        ec.add_event("test1","test1","09:00", "12:00","2020-10-04" );
        assertEquals(2, ec.get_events().size());
        ec.add_event("test1","test1","dragon", "12:00","2020-10-04" );
        assertEquals(2, ec.get_events().size());
    }




}
