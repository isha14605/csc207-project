package Gateway;
import java.io.*;

public interface Gateway {
    void save(Object o) throws IOException;

    Object read() throws IOException;
}

