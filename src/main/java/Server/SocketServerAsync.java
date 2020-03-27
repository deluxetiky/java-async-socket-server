package Server;

import Socket.SocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class SocketServerAsync implements SocketServer {

    private final SocketHandler handler;
    private final AsynchronousServerSocketChannel server;
    private static Logger LOGGER = LoggerFactory.getLogger(SocketServer.class.getName());

    public SocketServerAsync(SocketHandler handler) throws IOException {
        this(handler, "0.0.0.0", 5050);
    }

    public SocketServerAsync(SocketHandler handler, String host, int port) throws IOException {
        this.handler = handler;
        server = AsynchronousServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress("0.0.0.0", 5050);
        server.bind(address);
        server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        LOGGER.info("Device socket server started on {}", address);
    }

    public boolean start() throws IOException, InterruptedException {
        try {


            if (server.isOpen()) {
                server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

                    @Override
                    public void completed(AsynchronousSocketChannel result, Object attachment) {
                        if (server.isOpen()) {
                            server.accept(null, this);
                        }
                        handler.handleAcceptConnection(result);
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        if (server.isOpen()) {
                            server.accept(null, this);
                            LOGGER.error("Connection handler error: {}", exc);
                        }
                    }
                });
                return true;
            }
        } catch (Exception ex) {
            LOGGER.error("Probe server start error!", ex);
            return false;
        }
        return false;
    }
}

