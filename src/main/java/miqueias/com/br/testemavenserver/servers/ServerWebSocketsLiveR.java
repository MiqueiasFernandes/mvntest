/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miqueias.com.br.testemavenserver.servers;

import java.util.HashSet;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import miqueias.com.br.testemavenserver.engine.ConexoesSingleton;
import miqueias.com.br.testemavenserver.engine.strategy.OnCloseStrategy;
import miqueias.com.br.testemavenserver.engine.strategy.OnMessageStrategy;
import miqueias.com.br.testemavenserver.engine.strategy.OnOpenStrategy;

/**
 *
 * @author mfernandes
 */
@ServerEndpoint(value = "/liver")
public class ServerWebSocketsLiveR {

    String path = "liveR";
    HashSet<Session> set = ConexoesSingleton.getInstance().getLivers();
    OnOpenStrategy openStrategy = new OnOpenStrategy(set, path);
    OnCloseStrategy closeStrategy = new OnCloseStrategy(set, path);
    OnMessageStrategy messageStrategy = new OnMessageStrategy(set, path);

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        openStrategy.OnOpen(session);
    }

    @OnClose
    public void onClose(Session session) {
        closeStrategy.OnClose(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        messageStrategy.OnMessage(session, message);
    }

}
