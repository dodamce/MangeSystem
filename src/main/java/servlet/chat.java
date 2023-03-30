package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import sql.Mesage;
import sql.MsgDAO;

@ServerEndpoint("/ChatServlet")
public class chat {
    private static final List<Session> sessions = Collections.synchronizedList(new ArrayList<Session>());

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("WebSocket opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message received: " + message);
        try {
            Gson gson = new Gson();
            Mesage mesage = gson.fromJson(message, Mesage.class);
            MsgDAO msgDAO = new MsgDAO();
            msgDAO.insert(mesage);
            ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
            broadcast(chatMessage);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("WebSocket closed: " + session.getId());
    }

    private void broadcast(ChatMessage message) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ChatMessage {
        private String sender;
        private String message;

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }
}
