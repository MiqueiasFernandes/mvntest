/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miqueias.com.br.testemavenserver.engine;

import java.util.HashSet;
import javax.websocket.Session;
import miqueias.com.br.testemavenserver.jri.JRIconnector;

/**
 *
 * @author mfernandes
 */
public class ConexoesSingleton {

    private static HashSet<Session> livers, prompts, alerts, files;
    private final static Protocol protocol = new Protocol();
    private static final JRIconnector jRIconnector = new JRIconnector();

    private ConexoesSingleton() {
        System.out.println("O gerenciador de conexoes sera inicializado...");
        livers = new HashSet<>();
        prompts = new HashSet<>();
        alerts = new HashSet<>();
        files = new HashSet<>();
        System.out.println("inicializou gerenciador de conexoes...");
    }

    public HashSet<Session> getAlerts() {
        return alerts;
    }

    public HashSet<Session> getFiles() {
        return files;
    }

    public HashSet<Session> getLivers() {
        return livers;
    }

    public HashSet<Session> getPrompts() {
        return prompts;
    }

    public JRIconnector getjRIconnector() {
        return jRIconnector;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public static ConexoesSingleton getInstance() {
        return ConexoesSingletonHolder.INSTANCE;
    }

    private static class ConexoesSingletonHolder {

        private static final ConexoesSingleton INSTANCE = new ConexoesSingleton();
    }
}
