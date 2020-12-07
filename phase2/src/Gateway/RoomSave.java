package Gateway;

import UseCase.EventManager;
import UseCase.RoomManager;

import java.io.*;

public class RoomSave implements Gateway {

    @Override
    public void save(Object rm) throws IOException {
        OutputStream file = new FileOutputStream("RoomSave.ser");
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(rm);
        output.close();
    }

    @Override
    public RoomManager read() throws IOException {
        try {
            InputStream file = new FileInputStream("RoomSave.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            RoomManager rm = (RoomManager) input.readObject();
            input.close();
            return rm;
        } catch (IOException | ClassNotFoundException ignored) {
            System.out.println("couldn't read room file.");
            return new RoomManager(new EventManager());
        }


    }
}
