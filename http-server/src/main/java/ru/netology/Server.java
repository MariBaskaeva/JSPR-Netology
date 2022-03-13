package ru.netology;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
     ExecutorService pool;

    private final int THREAD_POOL_COUNT = 64;

    public Server() {
        pool = Executors.newFixedThreadPool(THREAD_POOL_COUNT);
    }

    public void listen(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                final var socket = serverSocket.accept();
                pool.submit(() -> connectionProcessing(socket));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void connectionProcessing(Socket socket) {
        try (
                final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final var out = new BufferedOutputStream(socket.getOutputStream())
        ) {
            final var requestLine = in.readLine();
            final String[] parts = requestLine.split(" ");
            if (parts.length != 3){
                return;
            }
            final String path = parts[1];
            Response response = createResponse(path);
            response.send(out);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Response createResponse(String path) throws IOException {
        final var filePath = Path.of(".", "public", path);
        final var mimeType = Files.probeContentType(filePath);

        // special case for classic
        if(!filePath.toFile().exists()) {
            return new ResponseBuilder()
                    .setStatus(404)
                    .setStatusMessage("Not Found")
                    .build();
        }else if (path.equals("/classic.html")) {
            String content;
            final var template = Files.readString(filePath);
            content = template.replace(
                    "{time}",
                    LocalDateTime.now().toString()
            );
            return new ResponseBuilder()
                    .setStatus(200)
                    .setStatusMessage("Ok")
                    .setContentType(mimeType)
                    .setContent(content.getBytes())
                    .build();
        }else{
            return new ResponseBuilder()
                    .setStatus(200)
                    .setStatusMessage("Ok")
                    .setContentType(mimeType)
                    .setContent(Files.readAllBytes(filePath))
                    .build();
        }
    }
}
