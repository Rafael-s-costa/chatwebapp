package sparktest.chatwebapp.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import sparktest.chatwebapp.pojo.MessageDTO;

@WebSocket
public class ChatEndpoint {

    private Session session;
    private static Set<ChatEndpoint> chatEndpoints
            = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @OnWebSocketConnect
    public void onConnect(Session username) throws Exception {
        this.session = session;
        chatEndpoints.add(this);
        users.put(session.getId(), username);

        MessageDTO message = new MessageDTO();
        message.setFrom(username);
        message.setContent("Connected!");
        broadcast(message);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, MessageDTO message) throws IOException {
        message.setFrom(users.get(session.getId()));
        broadcast(message);
    }

    @OnWebSocketClose
    public void onClose(Session session) throws IOException {

        chatEndpoints.remove(this);
        MessageDTO message = new MessageDTO();
        message.setFrom(users.get(session.getId()));
        message.setContent("Disconected!");
        broadcast(message);
    }

    @OnWebSocketError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    public static void broadcast(MessageDTO message)
        throws IOException {

            chatEndpoints.forEach(endpoint -> {
                synchronized (endpoint) {
                    try {
                        endpoint.session.getRemote().sendObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
    }
}
