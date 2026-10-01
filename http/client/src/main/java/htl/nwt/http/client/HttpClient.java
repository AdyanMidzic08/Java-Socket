package htl.nwt.http.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.logging.Logger;

public class HttpClient {
    private static final Logger log =
            Logger.getLogger(HttpClient.class.getCanonicalName());
    public static void main(String[] args) throws IOException {

        Socket socket = null;
        log.info("Starting...");
        try {
            socket = new Socket("orf.at", 80);
            OutputStream os = socket.getOutputStream();

            HttpRequest request = new HttpRequest("GET / HTTP/1.1",
                                                "Host: orf.at",
                                                "User-Agent: Java HTTP Client",
                                                "Accept: */*");
            request.sent(os);


            InputStream is = socket.getInputStream();
            HttpResponse response = new HttpResponse();
            response.receive(is);
            //log.fine(new String(response.getBody(),StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.severe(e.getMessage());
        }

        log.info("Exiting...");
    }
}
