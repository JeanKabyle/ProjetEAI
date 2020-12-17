/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projeteai;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.JMSException;

/**
 *
 * @author samir
 */
public class SampleListener implements MessageListener {

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage text = (TextMessage) message;
            try {
                System.out.println("Received: " + text.getText());
            } catch (JMSException exception) {
                System.err.println("Failed to get message text: " + exception);
            }
        }
    }
}
