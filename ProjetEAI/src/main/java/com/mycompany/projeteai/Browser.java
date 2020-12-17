/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projeteai;

import java.util.Enumeration;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author samir
 */

/**
 * <code>QueueBrowser</code> example.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/11/18 03:28:00 $
 */
public class Browser {

    /**
     * Main line.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "ConnectionFactory";
        String queueName = null;
        Queue queue = null;
        Session session = null;
        QueueBrowser browser = null;

        if (args.length != 1) {
            System.out.println("usage: Browser <queue>");
            System.exit(1);
        }

        queueName = args[0];

        try {
            // create the JNDI initial context.
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);

            // look up the Queue
            queue = (Queue) context.lookup(queueName);

            // create the connection
            connection = factory.createConnection();

            // create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // create the browser
            browser = session.createBrowser(queue);

            // start the connection
            connection.start();

            Enumeration messages = browser.getEnumeration();
            while (messages.hasMoreElements()) {
                Message message = (Message) messages.nextElement();
                if (message instanceof TextMessage) {
                    TextMessage text = (TextMessage) message;
                    System.out.println("Browsed: " + text.getText());
                } else if (message != null) {
                    System.out.println("Browsed non text message");
                }
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        } catch (NamingException exception) {
            exception.printStackTrace();
        } finally {
            // close the context
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException exception) {
                    exception.printStackTrace();
                }
            }

            // close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
