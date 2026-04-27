package fr.mycellius.web.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
@RestController
public class MetaController {
 @Value("${mycellius.env}")
 private String env;
 @Value("${mycellius.version}")
 private String version;
 @GetMapping("/api/version")
 public Map<String, String> version() {
 return Map.of(
 "env", env,
 "version", version
 );
 }
}