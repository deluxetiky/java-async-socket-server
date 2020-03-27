# Java Async Server

> When writing low level socket applications, it is mondatory that not spinning up thread for each session. It costs you in terms of bot memory and cpu. This repo aims to show simple example of async socket with **nio**.

## Getting Started

> **Server Configuration**

```language
SocketHandler echoHandler = new SocketHandlerEcho();
        SocketServer server = new SocketServerAsync(echoHandler, "0.0.0.0", 5050);
        server.start();
        Thread.currentThread().join();//Wait forever
```

## Client

> **Testing Connection**

Multiple concurrent instances can be spin up quickly with telnet.

```
telnet localhost 5050
```

## Implementing Custom Socket Handler

> Sockethandler interface can be extended by implement custom binary protocols which handles session and bidirectional communication.

```language

package Socket;

import java.nio.channels.AsynchronousSocketChannel;

public class SocketHandlerCustom implements SocketHandler {
    @Override
    public void handleAcceptConnection(AsynchronousSocketChannel asyncSocketChannel) {
        
    }
}

```

How to spin up project quickly is on the [blog](https://sinanbir.com/java-async-server-socket-example/)