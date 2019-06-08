package sparktest.chatwebapp.utils;

import com.google.gson.Gson;
import pojo.MessageDTO;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<MessageDTO> {

    private static Gson gson = new Gson();

    @Override
    public String encode(MessageDTO message) throws EncodeException {
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
