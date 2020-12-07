package Gateway;

import UseCase.UserManager;

import java.io.*;

public class UserSave implements Gateway {

    @Override
    public void save(Object um) throws IOException {
        OutputStream file = new FileOutputStream("UserSave.ser");
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(um);
        output.close();
    }

    @Override
    public UserManager read() throws IOException {
        try {
            InputStream file = new FileInputStream("UserSave.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            UserManager um = (UserManager) input.readObject();
            input.close();
            return um;
        } catch (IOException | ClassNotFoundException ignored) {
            System.out.println("couldn't read User Save file.");
            return new UserManager();
        }

    }
}
