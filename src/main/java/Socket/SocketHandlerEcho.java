package Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SocketHandlerEcho implements SocketHandler {
    private static Logger logger = LoggerFactory.getLogger(SocketHandler.class.getName());
    @Override
    public void handleAcceptConnection(AsynchronousSocketChannel ch) {
        ByteBuffer byteBuffer = ByteBuffer.allocate( 1024 );

        try {
            // Read the first data
            int bytesRead = ch.read(byteBuffer).get(40, TimeUnit.SECONDS);
            boolean active = true;
            while (bytesRead != -1 && active) {

                // Make sure that we have data to read
                if (byteBuffer.position() > 0) {
                    // Make the buffer ready to read
                    byteBuffer.flip();
                    // Convert the buffer into a line
                    byte[] lineBytes = new byte[bytesRead];
                    byteBuffer.get(lineBytes, 0, bytesRead);
                    String line = new String(lineBytes);

                    // Debug
                    System.out.println("Message: " + line);
//                    TimeUnit.SECONDS.sleep(10);
                    // Echo back to the caller
                    ch.write(ByteBuffer.wrap(line.getBytes()));

                    // Make the buffer ready to write
                    byteBuffer.clear();

                    // Read the next line
                    bytesRead = ch.read(byteBuffer).get();
                } else {
                    // An empty line signifies the end of the conversation in our protocol
                    active = false;
                    logger.debug("Socket is closing...");
                    ch.close();
                }
            }
        } catch (InterruptedException e) {
            logger.error("Socket Interupted exception",e);
        } catch (ExecutionException e) {
            logger.error("Socket Execution error",e);
        } catch (TimeoutException e) {
            // The user exceeded the X second timeout, so close the connection
            logger.error( "Connection timed out, closing connection",e);
        } catch (IOException e) {
            logger.error("Socket IO Exception",e);
        }
    }
}
