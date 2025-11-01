package xyz.jplan.room.services;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import xyz.jplan.room.services.data.Message;

class MessageEncoder implements Encoder.Text<Message> {

    private static Jsonb jsonb = JsonbBuilder.create();

    @Override
    public String encode(Message message) throws EncodeException {
	return jsonb.toJson(message);
    }

}
