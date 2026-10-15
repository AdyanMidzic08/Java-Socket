package htl.nwt.http.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class  HttpResponse {
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

    private int getContentLength(String[] headers) {
        for (String header: headers) {
            if(header.toLowerCase().startsWith("content-length")) {
                return Integer.parseInt(header.substring(15).strip());
            }
        }
        return 0;
    }

    public void receive(InputStream in) throws IOException {
        responseLine = receiveLine(in);
        List<String> headerList = new ArrayList<>();
        String header;
        while (!(header = receiveLine(in)).isEmpty()) {
            headerList.add(header);
        }
        headers = headerList.toArray(new String[0]);

        int contentLength = getContentLength(headers);
        if(contentLength > 0) {
            body = readBody(in, contentLength);
        }
    }

    private byte[] readBody(InputStream in, int contentLength) throws IOException {
        byte[] data = new byte[contentLength];
        int totalRead = 0;
        while (totalRead < contentLength) {
            int bytesRead = in.read(data, totalRead, contentLength - totalRead);
            if (bytesRead == -1) {
                throw new IOException("Stream closed before reading full content length");
            }
            totalRead += bytesRead;
        }
        return data;
    }

    public String receiveLine(InputStream is) throws IOException {
        boolean crFound = false;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        byte[] buf = new byte[4096];
        int pos = 0;

        int b = is.read();
        while (b >= 0) {
            buf[pos] = (byte) b;

            if(b == 0xd) {
                crFound = true;
            } else if((crFound) && b == 0xa) {
               return responseLine = new String(buffer.toByteArray(),0, pos -1 , StandardCharsets.UTF_8);
            }
            buffer.write((byte) b);

            pos++;
            b = is.read();
        }
        throw new IOException("Invalid state when reading HTTP response");
        // is.read() ...
        // responseLine = ...
        // headers = ...
        // body = ...
    }
}
