package org.example.utilites;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.exceptions.LOLDIDNTREAD;
import org.example.main.Request;
import org.example.main.Response;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerMessaging {
    private ServerMessaging() {
    }

    public static Response nioRead(SocketChannel clientChannel) throws IOException, LOLDIDNTREAD {
        ByteBuffer buf = ByteBuffer.allocate(clientChannel.socket().getReceiveBufferSize());
        int read = clientChannel.read(buf);
        if (read > 0) {
            buf.flip();
            String s = new String(ByteBuffer.allocate(read).put(buf.array(), 0, read).array());
            return ObjectConverter.deserialize(s, new TypeReference<>() {
            });

        } else throw new LOLDIDNTREAD();
    }

    public static void nioSend(SocketChannel clientChannel, String message) throws IOException {
        Request resp = new Request();
        resp.addMessage(message);
        message = ObjectConverter.toJson(resp);
        ByteBuffer buf = ByteBuffer.allocate(message.getBytes().length).put(message.getBytes());
        buf = buf.flip();
        while (buf.hasRemaining()) {
            clientChannel.write(buf);
        }
        System.out.println("sended " + message);
    }

    public static void nioSend(SocketChannel clientChannel, Request resp) throws IOException {
        String message = ObjectConverter.toJson(resp);
        ByteBuffer buf = ByteBuffer.allocate(message.getBytes().length).put(message.getBytes());
        buf = buf.flip();
        while (buf.hasRemaining()) {
            clientChannel.write(buf);
        }
        System.out.println("sended " + message);
    }

}
