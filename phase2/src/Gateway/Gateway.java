package Gateway;
import java.io.*;

public interface Gateway {
    void save() throws IOException;

    Object read();
}

