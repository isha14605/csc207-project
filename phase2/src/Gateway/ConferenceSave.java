package Gateway;

import UseCase.ConferenceManager;

import java.io.*;

public class ConferenceSave implements Gateway {

    @Override
    public void save(Object cm) throws IOException {
        OutputStream file = new FileOutputStream("ConferenceSave.ser");
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(cm);
        output.close();
    }

    @Override
    public ConferenceManager read() throws IOException {
        try {
            InputStream file = new FileInputStream("ConferenceSave.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            ConferenceManager cm = (ConferenceManager) input.readObject();
            input.close();
            return cm;
        } catch (IOException | ClassNotFoundException ignored) {
            System.out.println("couldn't read Conference file.");
            return new ConferenceManager();
        }

    }
}
