package htl.nwt.http.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
    private String responseLine;
    private String[] headers;
    private byte[] body;

    public String getResponseLine() {
        return responseLine;
    }

    public String[] getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void receive(InputStream is) throws IOException {
        boolean crFound = false;

        byte[] buf = new byte[4096];
        int pos = 0;

        int b = is.read();
        while (b >= 0) {
            if(b == 0xd) {
                crFound = true;
            } else {
                if((crFound) && b == 0xa) {
                    responseLine = new String(buf,0,pos, StandardCharsets.UTF_8);

                }else if (crFound) {
                    buf[pos] = 0xd;
                    pos++;
                    buf[pos] = (byte) b;
                } else {
                    buf[pos] = (byte) b;
                }
            }

            pos++;
            b = is.read();
        }

        // is.read() ...
        // responseLine = ...
        // headers = ...
        // body = ...
    }
}
