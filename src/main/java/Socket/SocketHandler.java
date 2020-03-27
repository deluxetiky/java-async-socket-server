package Socket;


import java.nio.channels.AsynchronousSocketChannel;

public interface SocketHandler {
    void handleAcceptConnection(AsynchronousSocketChannel asyncSocketChannel);
}
