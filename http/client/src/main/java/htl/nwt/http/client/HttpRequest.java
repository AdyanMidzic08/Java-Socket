package htl.nwt.http.client;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpRequest {

    private static final byte[] DELIMETER = {0x0d, 0x0a};

    private String requestLine;
    private String[] headers;
    private byte[] body;

    public HttpRequest(String requestLine, String... headers) {
        this(requestLine,null,headers);
    }

    public HttpRequest(String requestLine,byte[] body,String... headers) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }


    public void sent(OutputStream os) throws IOException {
        os.write(requestLine.getBytes(StandardCharsets.UTF_8));
        os.write(DELIMETER);

        for (String header : headers) {
            os.write(header.getBytes(StandardCharsets.UTF_8));
            os.write(DELIMETER);
        }

        if (body != null) {
            os.write(("Content-Length" + body.length).getBytes(StandardCharsets.UTF_8));
            os.write(DELIMETER);
        }

        os.write(DELIMETER);
        os.flush();
        if (body != null) {
            os.write(body);
        }
        os.flush();

    }
}
