package sparktest.chatwebapp.webapp;

import static spark.Spark.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;

import sparktest.chatwebapp.resources.ChatEndpoint;

public class App {
	// this map is shared between sessions and threads, so it needs to be thread-safe (http://stackoverflow.com/a/2688817)
    static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
    static int nextUserNumber = 1; //Used for creating the next username

    public static void main(String[] args) {
    	webSocket("/chat", ChatEndpoint.class);
        init();
    }
}