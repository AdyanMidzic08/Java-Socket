package htl.nwt.http.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class HttpClient {
    private static final Logger log =
            Logger.getLogger(HttpClient.class.getCanonicalName());

    public static void main(String[] args) throws IOException {

        Socket socket = null;
        try {
            socket = new Socket("orf.at", 80);
            OutputStream os = socket.getOutputStream();
        } catch (IOException e) {
            log.severe(e.getMessage());
        }finally {
            if(socket != null) {
                socket.close();
            }
        }
    }
}
