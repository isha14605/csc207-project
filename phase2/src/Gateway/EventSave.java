package Gateway;

import UseCase.EventManager;

import java.io.*;

public class EventSave implements Gateway {

    @Override
    public void save(Object em) throws IOException {
        OutputStream file = new FileOutputStream("EventSave.ser");
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(em);
        output.close();
    }

    @Override
    public EventManager read() throws IOException {
        try {
            InputStream file = new FileInputStream("EventSave.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            EventManager rm = (EventManager) input.readObject();
            input.close();
            return rm;
        } catch (IOException | ClassNotFoundException ignored) {
            System.out.println("couldn't read event file.");
            return new EventManager();
        }
    }
}
