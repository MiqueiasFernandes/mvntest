/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miqueias.com.br.testemavenserver.jri;

/**
 *
 * @author mfernandes
 */
public interface IJRISends {

    public void sendText(String text);

    public void sendPrompt(String prompt);

    public void sendAlert(String alert);

    public String getMessageAlert(String alert) throws Exception;

}
