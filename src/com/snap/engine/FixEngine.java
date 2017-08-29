package com.snap.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.FileStoreFactory;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.Message;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.RejectLogon;
import quickfix.ScreenLogFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;
import quickfix.UnsupportedMessageType;
import quickfix.field.MsgType;

public class FixEngine implements Application {

	private static Logger log = LoggerFactory.getLogger(FixEngine.class);
	private Initiator initiator = null;
	private boolean initiatorStarted = false;
	private MessageProcessor messageProcessor = new MessageProcessor();

	
	public static void main(String[] args) {
		System.out.println("Ome Vighneswaraya namaha");
		log.info("Ome Vighneswaraya namaha");
		String sessionCfgFileName = args[0];
		try {
			SessionSettings settings = new SessionSettings(new FileInputStream(sessionCfgFileName));
			MessageStoreFactory messageStoreFactory = new FileStoreFactory(settings);
			boolean logHeartbeats = Boolean.valueOf(System.getProperty("logHeartbeats", "true")).booleanValue();
			LogFactory logFactory = new ScreenLogFactory(true, true, true, logHeartbeats);
			MessageFactory messageFactory = new DefaultMessageFactory();
			FixEngine engine = new FixEngine();
			engine.initialize(engine, messageStoreFactory, settings, logFactory, messageFactory);
			engine.logon();
			while(true) {
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void fromAdmin(Message arg0, SessionID arg1)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fromApp(Message arg0, SessionID arg1)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		log.info("Message Recieved" + arg0.toString());
		messageProcessor.process(arg0, arg1);
	}

	@Override
	public void onCreate(SessionID arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLogon(SessionID arg0) {
		System.out.println("ON LOGON IS BEING CALLED");
		log.info("OnLogon Sucessful");
		
	}

	@Override
	public void onLogout(SessionID arg0) {
		log.info("Logged out Sucessfully");
		
	}

	@Override
	public void toAdmin(Message arg0, SessionID arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toApp(Message arg0, SessionID arg1) throws DoNotSend {
		// TODO Auto-generated method stub
		
	}
	
	public void initialize(Application application, MessageStoreFactory messageStoreFactory,
            SessionSettings settings, LogFactory logFactory, MessageFactory messageFactory) {
		try {
			initiator = new SocketInitiator(application, messageStoreFactory, settings, logFactory, messageFactory);
		} catch (ConfigError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public synchronized void logon() {
        if (!initiatorStarted) {
            try {
                initiator.start();
                initiatorStarted = true;
            } catch (Exception e) {
                log.error("Logon failed", e);
            }
        } else {
            Iterator<SessionID> sessionIds = initiator.getSessions().iterator();
            while (sessionIds.hasNext()) {
                SessionID sessionId = (SessionID) sessionIds.next();
                Session.lookupSession(sessionId).logon();
            }
        }
    }
	
	
	
	

}
