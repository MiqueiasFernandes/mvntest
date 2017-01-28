/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miqueias.com.br.testemavenserver.engine.chain;

import javax.websocket.Session;
import miqueias.com.br.testemavenserver.engine.ConexoesSingleton;
import miqueias.com.br.testemavenserver.engine.strategy.AbstractWebSocketsStrategy;

public class LiveRHandler extends AbstractMessageHandler {

    @Override
    public boolean accept(Session session, String message) {
        return ConexoesSingleton.getInstance().getLivers().contains(session);
    }

    @Override
    public void handle(Session session, String message, AbstractWebSocketsStrategy strategy) {
        ConexoesSingleton.getInstance().getjRIconnector().addMessageInput(message);
    }

}
