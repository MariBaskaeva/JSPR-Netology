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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
     ExecutorService pool;
     Map<String, Map<String, Handler>> handlers = new HashMap<>();

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
        List<String> list = new ArrayList<>();
        try (
                final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final var out = new BufferedOutputStream(socket.getOutputStream())
        ) {
            RequestBuilder requestBuilder = new RequestBuilder();
            var requestLine = in.readLine();
            System.out.println(requestLine);
            final String[] parts = requestLine.split(" ");
            if (parts.length != 3){
                return;
            }
            //запись метода, пути и версии
            //запись headers
            while((requestLine = in.readLine()).length() > 0){
                list.add(requestLine);
            }
            StringBuilder stringBuilder = new StringBuilder();
            //запись body
            String bodyLine;
            if(!parts[0].equals("GET")) {
                while ((bodyLine = in.readLine()) != null) {
                    System.out.println(bodyLine);
                    stringBuilder.append(bodyLine + "\n");
                }
            }
            Request request = requestBuilder.setMethod(parts[0])
                    .setPath(parts[1])
                    .setProtocolVersion(parts[2])
                    .setHeaders(list)
                    .setBody(stringBuilder.toString())
                    .build();
            System.out.println(request.toString());

            Map<String, Handler> innerMap = handlers.get(request.getMethod());
            Handler handler = innerMap.get(request.getPath());
            if(handler == null){
                handler = handlers.get("GET").get("/404.html");
            }
            handler.handle(request, out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addHandler(String method, String path, Handler handler){
        Map<String, Handler> innerMap = handlers.get(method);
        if(innerMap == null)
            innerMap = new HashMap<>();
        innerMap.put(path, handler);
        handlers.put(method, innerMap);
    }
}
