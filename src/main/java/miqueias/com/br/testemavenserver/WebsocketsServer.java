/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miqueias.com.br.testemavenserver;

import miqueias.com.br.testemavenserver.servers.ServerWebSocketsLiveR;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.websocket.DeploymentException;
import miqueias.com.br.testemavenserver.enumns.ERRORS;
import miqueias.com.br.testemavenserver.servers.ServerWebSocketsAlerts;
import miqueias.com.br.testemavenserver.servers.ServerWebSocketsFiles;
import miqueias.com.br.testemavenserver.servers.ServerWebSocketsPrompt;
import org.glassfish.tyrus.server.Server;

/**
 *
 * @author mfernandes
 */
public class WebsocketsServer {

    static int time_to_close = 10;
    public static int time_to_close_on_disconnect = 3600;

    public static void main(String[] args) {

//        if (args == null || args.length != 1) {
//            System.out.println("usage: java -jar \"8080\"");
//            System.exit(ERROR_ARGS_INDEFINIDE.ordinal());
//        }
//
//        int porta = 0;
//        try {
//            porta = Integer.parseInt(args[0]);
//        } catch (NumberFormatException ex) {
//            System.err.println("falha ao traduzir porta invalida: " + args[0] + ".\nDetalhes: " + ex);
//            System.exit(ERRORS.ERROR_PORTA_INVALIDA.ordinal());
//        }
//
//        new Thread(() -> {
//            int i = 0;
//            while (i++ < time_to_close) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException ex) {
//                    System.out.println("não foi possivel interromper thread time-close, " + ex);
//                }
//            }
//            if (!conected) {
//                System.err.println("fechando conexão, timeout por não conectado em " + time_to_close + " seg.");
//                System.exit(ERRORS.ERROR_TIMEOUT_CONEXION_NO_OPEN.ordinal());
//            }
//        }).start();
        Class[] classes = {
            ServerWebSocketsLiveR.class,
            ServerWebSocketsPrompt.class,
            ServerWebSocketsAlerts.class,
            ServerWebSocketsFiles.class
        };

        System.out.println("inicializando server...");
        Server server = new Server("localhost", 8090, "/ws", null, classes);

        try {

            System.out.println("startando server...");
            server.start();
            System.out.println("server startado com sucesso...");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please press a key to stop the server...");
            reader.readLine();
        } catch (DeploymentException | IOException e) {
            System.err.println("ERRO ao instanciar server: " + e);
            System.exit(ERRORS.ERROR_INSTANTIATION_SERVER.ordinal());
        } finally {
            server.stop();
        }
    }

}
