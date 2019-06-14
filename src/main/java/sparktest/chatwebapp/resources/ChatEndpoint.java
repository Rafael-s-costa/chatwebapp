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
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sparktest.chatwebapp.pojo.MessageDTO;
import sparktest.chatwebapp.webapp.App;

@WebSocket
public class ChatEndpoint {

    private Session session;
    private String sender, msg;
    final Logger logger = LoggerFactory.getLogger(ChatEndpoint.class);

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
    	String username = "User" + App.nextUserNumber++;
    	logger.info("New user connected");
    	App.userUsernameMap.put(user, username);
    	App.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) throws IOException {
    	logger.info("Message " + "message");
    	App.broadcastMessage(sender = App.userUsernameMap.get(user), msg = message);
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) throws IOException {
    	logger.info("User disconected");
    	String username = App.userUsernameMap.get(user);
    	App.userUsernameMap.remove(user);
    }

    @OnWebSocketError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}
