package Server;

import java.io.IOException;

public interface SocketServer {
    boolean start() throws IOException, InterruptedException;
}
