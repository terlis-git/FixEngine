package com.snap.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.SessionID;
import quickfix.field.MsgType;

public class MessageProcessor {
	private static Logger log = LoggerFactory.getLogger(MessageProcessor.class);

    public void process(quickfix.Message message, SessionID sessionID) {
        try {
            MsgType msgType = new MsgType();
            log.info("Message : " + message);
                if (message.getHeader().getField(msgType).valueEquals("8")) {
                    log.info("Execution Report: " + message);
                } else {
                	log.info("Not an execution report");
                }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
}