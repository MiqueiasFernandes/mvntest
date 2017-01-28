/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miqueias.com.br.testemavenserver.engine;

import java.util.Set;
import javax.websocket.Session;
import miqueias.com.br.testemavenserver.WebsocketsServer;
import static miqueias.com.br.testemavenserver.WebsocketsServer.time_to_close_on_disconnect;
import miqueias.com.br.testemavenserver.engine.chain.AbstractMessageHandler;
import miqueias.com.br.testemavenserver.engine.strategy.AbstractWebSocketsStrategy;
import miqueias.com.br.testemavenserver.enumns.ERRORS;
import miqueias.com.br.testemavenserver.jri.IJRISends;
import miqueias.com.br.testemavenserver.jri.JRIconnector;

/**
 *
 * @author mfernandes
 */
public class Protocol implements IJRISends {

    private final JRIconnector jRIconnector;
    private final AbstractMessageHandler handler;

    public Protocol() {
        System.out.println("O gerenciador de protocolo sera iniciado...");
        this.jRIconnector = new JRIconnector();
        handler = new AbstractMessageHandler() {
            @Override
            public boolean accept(Session session, String message) {
                return true;
            }

            @Override
            public void handle(Session session, String message, AbstractWebSocketsStrategy strategy) {
                System.out.println("Nova mensagem recebida: " + message);
            }
        };

        System.out.println("iniciou gerenciador de protocolo...");
    }

    public void onClientAdded(Session session, AbstractWebSocketsStrategy strategy) {
        strategy.getSessions().add(session);
        startJRI();
    }

    public void onClientClosed(Session session, AbstractWebSocketsStrategy strategy) {
        strategy.getSessions().remove(session);
        endJRI();
    }

    public void onMessage(Session session, String message, AbstractWebSocketsStrategy strategy) {
        handler.doHandle(session, message, strategy);
    }

    @Override
    public void sendText(String text) {
        sendBroadCastMessage(text, getConSing().getLivers());
    }

    @Override
    public void sendPrompt(String prompt) {
        sendBroadCastMessage(prompt, getConSing().getPrompts());
    }

    @Override
    public void sendAlert(String alert) {
        sendBroadCastMessage(alert, getConSing().getAlerts());
    }

    @Override
    public String getMessageAlert(String alert) throws Exception {
        return null;
    }

    public void sendBroadCastMessage(String message, Set<Session> sessoes) {
        for (Session sessao : sessoes) {
            sessao.getAsyncRemote().sendText(message);
        }
    }

    public JRIconnector getjRIconnector() {
        return jRIconnector;
    }

    public void startJRI() {
        if (getConSing().getLivers().size() > 0
                && getConSing().getPrompts().size() > 0
                && getConSing().getAlerts().size() > 0
                && jRIconnector.getRengine() == null) {
            System.out.println("starting JRI engine...");
            jRIconnector.start(this);
            System.out.println("JRI engine was started...");
        }
    }

    public void endJRI() {
        if (jRIconnector.getRengine() != null) {
            if (getConSing().getLivers().size() < 1) {
                new Thread(() -> {
                    int i = 0;
                    while (i++ < time_to_close_on_disconnect) {
                        System.out.println("terminando sessao em " + (time_to_close_on_disconnect - i) + " sg.");
                        if (getConSing().getLivers().size() > 0) {
                            return;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            System.out.println("não foi possivel interromper thread time-close-ondisconect, " + ex);
                        }
                    }
                    if (getConSing().getLivers().size() < 1) {
                        jRIconnector.getRengine().end();
                        System.err.println("fechando conexão, timeout por não conectado em " + time_to_close_on_disconnect + " seg.");
                        System.exit(ERRORS.ERROR_TIMEOUT_CONEXION_NO_OPEN_ON_CLOSE.ordinal());
                    }
                }).start();
            }
        }
    }

    public ConexoesSingleton getConSing() {
        return ConexoesSingleton.getInstance();
    }

}
