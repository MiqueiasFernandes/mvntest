/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miqueias.com.br.testemavenserver.engine.chain;

import javax.websocket.Session;
import miqueias.com.br.testemavenserver.engine.strategy.AbstractWebSocketsStrategy;

/**
 *
 * @author mfernandes
 */
public abstract class AbstractMessageHandler {

    AbstractMessageHandler next;

    public void setNext(AbstractMessageHandler next) {
        if (this.next == null) {
            this.next = next;
        } else {
            this.next.setNext(next);
        }
    }

    public void doHandle(Session session, String message, AbstractWebSocketsStrategy strategy) {
        if (accept(session, message)) {
            handle(session, message, strategy);
        }
        if (next != null) {
            next.doHandle(session, message, strategy);
        }
    }

    public abstract boolean accept(Session session, String message);

    public abstract void handle(Session session, String message, AbstractWebSocketsStrategy strategy);

}
