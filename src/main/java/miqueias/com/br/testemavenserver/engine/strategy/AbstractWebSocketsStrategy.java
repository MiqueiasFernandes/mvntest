/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miqueias.com.br.testemavenserver.engine.strategy;

import java.util.Set;
import javax.websocket.Session;

/**
 *
 * @author mfernandes
 */
public abstract class AbstractWebSocketsStrategy {

    private final Set<Session> sessions;
    protected final String path;

    public AbstractWebSocketsStrategy(Set<Session> sessions, String path) {
        this.sessions = sessions;
        this.path = path;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

}
