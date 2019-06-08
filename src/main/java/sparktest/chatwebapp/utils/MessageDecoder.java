package sparktest.chatwebapp.utils;

import com.google.gson.Gson;
import pojo.MessageDTO;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<MessageDTO> {

    private static Gson gson = new Gson();

    @Override
    public MessageDTO decode(String s) throws DecodeException {
        return gson.fromJson(s, MessageDTO.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
