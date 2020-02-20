import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory ;
import org.glassfish.jersey.server.ResourceConfig;
import java.net.URI;

public class StartServer {

    private static final String HOST = "localhost";
    private static final int PORT = 1337;

    public static void main(String[] args) throws Exception {
   
        HttpServer server = null;

        ResourceConfig rc = new ResourceConfig(PointService.class);
        URI endpoint = new URI("http://"+HOST+":"+PORT+"/");
        server = JdkHttpServerFactory.createHttpServer(endpoint, rc);

        System.out.println("Server running!");
        System.out.println("Server started on: http://"+HOST+":"+PORT);

        System.out.println("Hit return to stop.");
        System.in.read();
        server.stop(0);
        System.out.println("Server stopped");
        System.exit(0);
    }
}
