package ru.netology;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Main {
  public static void main(String[] args) {
    Server server = new Server();

    server.addHandler("GET", "/index.html", (request, responseStream) -> {
      final var filePath = Path.of(".", "public", request.getPath());
      final var mimeType = Files.probeContentType(filePath);

      new ResponseBuilder()
              .setStatus(200)
              .setStatusMessage("Ok")
              .setContentType(mimeType)
              .setContent(Files.readAllBytes(filePath))
              .build()
              .send(responseStream);
    });

    server.addHandler("GET", "/classic.html", (request, responseStream) -> {
      final var filePath = Path.of(".", "public", request.getPath());
      final var mimeType = Files.probeContentType(filePath);

      final var template = Files.readString(filePath);
      String content;
      content = template.replace(
              "{time}",
              LocalDateTime.now().toString()
      );
      new ResponseBuilder()
              .setStatus(200)
              .setStatusMessage("Ok")
              .setContentType(mimeType)
              .setContent(content.getBytes())
              .build()
              .send(responseStream);
    });

    server.addHandler("GET", "/404.html", (request, responseStream) -> {
      new ResponseBuilder()
              .setStatus(404)
              .setStatusMessage("Not Found")
              .build()
              .send(responseStream);
    });

    server.listen(9999);
  }
}