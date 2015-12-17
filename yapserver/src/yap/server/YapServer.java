package yap.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import yap.servlet.AddBusinessServlet;
import yap.servlet.AddReviewServlet;
import yap.servlet.BusinessServlet;
import yap.servlet.LoginServlet;
import yap.servlet.LogoutServlet;
import yap.servlet.ReviewServlet;
import yap.servlet.RootServlet;
import yap.servlet.SignupServlet;

public class YapServer {
	private static int PORT = 8050;

	public static void main(String args[]) throws Exception {
		Logger logger = Logger.getLogger(YapServer.class.getName());
		Server server = new Server(PORT);

        WebAppContext webapp = new WebAppContext("WebContent", "/");
        server.setHandler(webapp);

        logger.log(Level.INFO, "Starting YapServer...");
        server.start();

        logger.log(Level.INFO, "YapServer started...");
        server.join();
	}
}
