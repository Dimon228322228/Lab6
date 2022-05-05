package reader;

import transmission.Request;

import java.io.IOException;
import java.util.List;


/**
 * an interface which read command from console
 */
public interface Reader {
    /**
     * reads commands in console mode
     */
    List<Request> run() throws IOException;

}
