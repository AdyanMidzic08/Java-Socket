# NWT HTTP Client

A minimal HTTP/1.1 client written in plain Java. It uses raw TCP sockets instead of an HTTP library, so requests and responses are written and parsed by hand. The project was created for the NWT course.

## Features

- Builds and sends HTTP requests directly over a `java.net.Socket`
- Parses the status line, headers and body of a response
- Reads the response body based on the `Content-Length` header
- Packages into a single executable JAR

## Project Structure

```text
.
├── pom.xml                 Parent POM
└── http
    ├── common              Shared protocol classes (http-common)
    │   └── HttpRequest, HttpResponse
    └── client              Command-line application (http-client)
        └── HttpClient
```

| Class | Purpose |
|-------|---------|
| `HttpRequest` | Writes the request line, headers and optional body to an output stream |
| `HttpResponse` | Reads the status line, headers and body from an input stream |
| `HttpClient` | Entry point: sends a `GET` request to `orf.at` and prints the response body |

## Requirements

- JDK 17 or newer
- Apache Maven 3

## Build

Run from the repository root:

```bash
mvn clean package
```

This creates the executable JAR at `http/client/target/wget.jar`.

## Usage

```bash
java -jar http/client/target/wget.jar
```

The client requests `http://orf.at/` on port 80 and prints the response body. The server currently answers with a redirect to HTTPS, so the expected output is a short `301 Moved Permanently` page:

```html
<title>301 Moved Permanently</title>
...
<p>The document has moved <a href="https://orf.at/">here</a>.</p>
```

## Limitations

- The target host and path are hard-coded in `HttpClient`.
- Only plain HTTP is supported, not HTTPS.
- Redirects are not followed.
- Responses must include a `Content-Length` header, so chunked transfer encoding is not supported.
