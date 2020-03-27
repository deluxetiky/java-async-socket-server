package Server;

import Socket.SocketHandler;
import Socket.SocketHandlerEcho;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class SocketStandAlone {
    private static Logger LOGGER = LoggerFactory.getLogger(SocketStandAlone.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        SocketHandler echoHandler = new SocketHandlerEcho();
        SocketServer server = new SocketServerAsync(echoHandler, "0.0.0.0", 5050);
        server.start();
        Thread.currentThread().join();//Wait forever
    }
}
