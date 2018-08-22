package com.tasker.taskapplication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.SessionCallback;
import org.springframework.jms.support.JmsUtils;

@Service
public class QueueService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    @Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;

    public boolean publishMessage(final String msg, final String queueName) {
        boolean published=false;
        //final String queueName = (String)Constants.Config.get(Constants.queueGroupMapping).getConfig().get(msg.getOmsVersion()+"-"+msg.getQueueGroup());

        //for testing PUT, uncomment below line and add the same queue in listener
        //final String queueName = "EMAIL.CONFIRM.QC01";

        logger.info("resolved queue name is " + queueName);

        try{
            final MessageCreator messageCreator = new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    //form the textmessage from the msg
                    TextMessage textMessage = session.createTextMessage(msg);
                    return textMessage;
                }
            };

            jmsTemplate.execute(new SessionCallback<Session>() {
                public Session doInJms(Session session) throws JMSException {
                    MessageProducer producer = null;
                    Destination downloadQueue = jmsTemplate.getDestinationResolver().resolveDestinationName(session, queueName, false);
                    try {
                        Message requestMessage = messageCreator.createMessage(session);
                        producer = session.createProducer(downloadQueue);

                        producer.send(requestMessage, javax.jms.DeliveryMode.PERSISTENT, javax.jms.Message.DEFAULT_PRIORITY, 1800000);
                        logger.info("Message sent to the queue");

                    } finally {
                        JmsUtils.closeMessageProducer(producer);
                    }
                    return session;
                }
            }, true);


            published =true;
        }
        catch(Exception e){
            logger.error("Something went wrong while publishing the message to queue",e);
            e.printStackTrace();
        }

        return published;
    }
}
