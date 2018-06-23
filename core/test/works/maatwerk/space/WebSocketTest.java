package works.maatwerk.space;

import com.github.czyzby.websocket.CommonWebSockets;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import works.maatwerk.space.networking.NetworkManager;

import java.util.Date;

public class WebSocketTest {

    private NetworkManager networkManager;

    @Before
    public void setUp() {
        CommonWebSockets.initiate();
        networkManager = new NetworkManager();
    }

    @Test
    public void connectTest() {
        connect(null);
        if (!networkManager.getWs().isOpen()) Assert.fail("Failed to connect");
    }
    
    @Test
    public void sendBroadcastTest() {

        final long startTime = new Date().getTime();

        connect(new WebSocketListener() {
            Boolean foundTestMessage = false;

            @Override
            public boolean onOpen(WebSocket webSocket) {
                webSocket.send("Test");
                return false;
            }

            @Override
            public boolean onClose(WebSocket webSocket, WebSocketCloseCode code, String reason) {
                return false;
            }

            @Override
            public boolean onMessage(WebSocket webSocket, String packet) {
                if (!packet.equalsIgnoreCase("update")
                        && !packet.equals("Test")) {
                    Assert.fail("Unknown Message Received");
                }
                if (packet.equals("Test")) foundTestMessage = true;

                if (startTime < new Date().getTime() - 700 && !foundTestMessage)
                    Assert.fail("Did not receive test message");
                return false;
            }

            @Override
            public boolean onMessage(WebSocket webSocket, byte[] packet) {
                return false;
            }

            @Override
            public boolean onError(WebSocket webSocket, Throwable error) {
                return false;
            }
        });

        while (startTime > new Date().getTime() - 1000) {
            System.out.println("Waiting for test to complete.");
        }
    }

    private void connect(WebSocketListener listener) {
        networkManager.connect(listener);

        long startTime = new Date().getTime();
        while (networkManager.getWs().isConnecting()
                || startTime > new Date().getTime() - 500) {
            System.out.println("Trying to connect");
        }

        if (startTime < new Date().getTime() - 500) Assert.fail("Timed out websocket");
        if (!networkManager.getWs().isOpen()) Assert.fail("Failed to connect");
    }
}
