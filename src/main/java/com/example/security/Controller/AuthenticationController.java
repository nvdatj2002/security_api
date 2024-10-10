package com.example.security.Controller;

import com.example.security.Entity.*;
import com.example.security.Responsitories.RoleUserReponsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    @Autowired
    RoleUserReponsitory roleUserReponsitory;
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRqeuest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> get() {
        return ResponseEntity.ok(authenticationService.getAll());

    }
    @GetMapping("/get/role")
    public ResponseEntity<List<RoleUser>> getRole() {
        return ResponseEntity.ok(roleUserReponsitory.findAll());

    }
}
