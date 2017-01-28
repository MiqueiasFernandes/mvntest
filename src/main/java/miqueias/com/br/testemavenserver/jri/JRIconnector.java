/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miqueias.com.br.testemavenserver.jri;

import org.rosuda.JRI.Rengine;

/**
 *
 * @author mfernandes
 */
public class JRIconnector {

    private Rengine rengine = null;
    private TextConsole textConsole = null;

    public void start(IJRISends iJRISends) {

        textConsole = new TextConsole(iJRISends);

        if (!Rengine.versionCheck()) {
            System.err.println("** Version mismatch - Java files don't match library version.");
            System.exit(1);
        }
        System.out.println("Creating Rengine (with arguments)");

        rengine = new Rengine(new String[]{"--no-save"}, false, textConsole);
        System.out.println("Rengine created, waiting for R");
        if (!rengine.waitForR()) {
            System.out.println("Cannot load R");
            return;
        }

        if (true) {
            System.out.println("Stated service R suceful...");
            rengine.startMainLoop();
        } else {
            rengine.end();
            System.out.println("end");
        }
    }

    public void setReceivedMessage(String message) {
        if (textConsole != null) {
            textConsole.addMessageReceived(message);
        } else {
            System.out.println("mensagem não tratada: " + message);
        }
    }

    public Rengine getRengine() {
        return rengine;
    }

    public void addMessageInput(String message) {
        if (textConsole != null) {
            textConsole.addMessageReceived(message);
        } else {
            System.err.println("Messagem não tratada: " + message);
        }
    }

}
