package com.duan.javastuff.jms;

import javax.jms.*;

/**
 * Created on 2018/1/12.
 *
 * @author DuanJiaNing
 */
public class QueueJmsTest implements QueueConnectionFactory {


    public static void main(String[] args) {
    }

    @Override
    public QueueConnection createQueueConnection() throws JMSException {
        return null;
    }

    @Override
    public QueueConnection createQueueConnection(String s, String s1) throws JMSException {
        return null;
    }

    @Override
    public Connection createConnection() throws JMSException {
        return null;
    }

    @Override
    public Connection createConnection(String s, String s1) throws JMSException {
        return null;
    }
}
