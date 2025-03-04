package io.tidy.bigsecretary.test;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

    @GetMapping("/api/test")
    public ResponseEntity<String> success() {
        return ResponseEntity.ok("LOGIN SUCCESS: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
