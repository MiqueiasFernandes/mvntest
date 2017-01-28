/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miqueias.com.br.testemavenserver.engine.strategy;

import java.util.Set;
import javax.websocket.Session;
import miqueias.com.br.testemavenserver.engine.ConexoesSingleton;

/**
 *
 * @author mfernandes
 */
public class OnOpenStrategy extends AbstractWebSocketsStrategy {

    public OnOpenStrategy(Set<Session> sessions, String path) {
        super(sessions, path);
    }

    public void OnOpen(Session session) {
        ConexoesSingleton.getInstance().getProtocol().onClientAdded(session, this);
        System.out.println("sessao para " + path + " foi aberta...");
    }

}
