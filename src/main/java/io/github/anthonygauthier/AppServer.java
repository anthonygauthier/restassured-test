package io.github.anthonygauthier;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class AppServer {
    private Server server;

    public void start() {
        this.server = new Server(3000);
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.setContextPath("/");
        this.server.setHandler(servletContextHandler);
        ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/v1/*");
        servletHolder.setInitOrder(0);
        servletHolder.setInitParameter(
                "jersey.config.server.provider.packages",
                "io.github.anthonygauthier.api.resources"
        );
        try {
            this.server.start();
            System.out.println("System running on port 3000");
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            this.server.destroy();
        }
    }

    public void stop() {
        try {
            this.server.stop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.server.destroy();
        }
    }
}