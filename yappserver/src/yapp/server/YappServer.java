package yapp.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import yapp.servlet.LoginServlet;
import yapp.servlet.LogoutServlet;
import yapp.servlet.RootServlet;
import yapp.servlet.SignupServlet;

public class YappServer {
	private static int PORT = 8080;
	
	public static void main(String args[]) throws Exception {
		Logger logger = Logger.getLogger(YappServer.class.getName());
		
		Server server = new Server(PORT);
		
		ServletContextHandler servhandler = new ServletContextHandler(ServletContextHandler.SESSIONS);        
        server.setHandler(servhandler);
 
        servhandler.addServlet(RootServlet.class, "/");
        servhandler.addServlet(LoginServlet.class, "/login");
        servhandler.addServlet(LogoutServlet.class, "/logout");
        servhandler.addServlet(SignupServlet.class, "/signup");
        
        server.setHandler(servhandler);
        logger.log(Level.INFO, "Starting YappServer...");
        
        server.start();
        logger.log(Level.INFO, "YappServer started...");
        
        server.join();
	}
}