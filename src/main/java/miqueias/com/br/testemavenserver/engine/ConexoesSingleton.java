/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miqueias.com.br.testemavenserver.engine;

import java.util.HashSet;
import javax.websocket.Session;

/**
 *
 * @author mfernandes
 */
public class ConexoesSingleton {

    private static HashSet<Session> livers, prompts, alerts, files;
    private static Protocol protocol;

    private ConexoesSingleton() {
        System.out.println("O gerenciador de conexoes sera inicializado...");
        livers = new HashSet<>();
        prompts = new HashSet<>();
        alerts = new HashSet<>();
        files = new HashSet<>();
        protocol = new Protocol();
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

//    public Session[] getSessionslivers() {
//        return livers.toArray(new Session[]{});
//    }
//
//    public Session[] getSessionsprompts() {
//        return prompts.toArray(new Session[]{});
//    }
//
//    public Session[] getSessionsalerts() {
//        return alerts.toArray(new Session[]{});
//    }
//
//    public Session[] getSessionsfiles() {
//        return files.toArray(new Session[]{});
//    }
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
