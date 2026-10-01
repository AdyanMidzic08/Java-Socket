package htl.nwt.http.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("orf.at", 80);
        OutputStream os = socket.getOutputStream();
    }
}
