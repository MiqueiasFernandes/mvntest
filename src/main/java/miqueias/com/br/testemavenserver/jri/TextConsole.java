/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miqueias.com.br.testemavenserver.jri;

import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;

/**
 *
 * @author mfernandes
 */
public class TextConsole implements RMainLoopCallbacks {

    private static StringBuffer buffer;
    private static IJRISends jRISends;

    public TextConsole(IJRISends jRISends) {
        TextConsole.jRISends = jRISends;
        TextConsole.buffer = new StringBuffer();
    }

    public void addMessageReceived(String text) {
        TextConsole.buffer.append(text);
    }

    @Override
    public void rWriteConsole(Rengine re, String text, int oType) {
        printLine(text);
    }

    @Override
    public void rBusy(Rengine re, int which) {
        TextConsole.jRISends.sendAlert("rBusy(" + which + ")" + "\n");
    }

    @Override
    public String rReadConsole(Rengine re, String prompt, int addToHistory) {
        TextConsole.jRISends.sendPrompt(prompt);
        String s = getLine(re);
        printLine(s);
        return s;
    }

    @Override
    public void rShowMessage(Rengine re, String message) {
        TextConsole.jRISends.sendAlert(message);
    }

    @Override
    public String rChooseFile(Rengine re, int newFile) {
        try {
            return TextConsole.jRISends.getMessageAlert((newFile == 0) ? "Select a file" : "Select a new file" + "\n");
        } catch (Exception ex) {
            System.err.println("não foi possivel esperar por thread...\nDetalhes: " + ex);
        }
        return null;
    }

    public String getLine(Rengine re) {
        try {
            int indiceLine;
            while ((indiceLine = TextConsole.buffer.indexOf("\n")) < 0) {
                Thread.sleep(100);
                System.out.println("requerendo msg buffer: " + buffer.toString());
            }

            String line = TextConsole.buffer.substring(0, indiceLine + 1);
            TextConsole.buffer.delete(0, indiceLine + 1);
            System.out.println("received message: \"" + line + "\"");
            return line;
        } catch (Exception e) {
            System.out.println("jriReadConsole exception: " + e.getMessage());
        }

        System.out.println("houve uma falha ao receber dados remoto, abortando conexão...");
        re.end();
        System.exit(-5);
        return null;
    }

    public void printLine(String text) {
        System.out.println("send message: " + text);
        TextConsole.jRISends.sendText(text);
    }

    @Override
    public void rFlushConsole(Rengine rngn) {
        TextConsole.jRISends.sendAlert("flush");
    }

    @Override
    public void rSaveHistory(Rengine rngn, String string) {
        jRISends.sendAlert("rSaveHistory: " + string);
    }

    @Override
    public void rLoadHistory(Rengine rngn, String string) {
        TextConsole.jRISends.sendAlert("rLoadHistory: " + string);
    }

}
