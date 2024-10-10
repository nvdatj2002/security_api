package com.example.security.Entity;

import com.example.security.Config.JwtService;
import com.example.security.Responsitories.RoleUserReponsitory;
import com.example.security.Responsitories.UserReponsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserReponsitory userReponsitory;
    private final RoleUserReponsitory roleUserReponsitory;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = new Role();
        role.setId(1);
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(role);

        List<RoleUser> roleUsers = new ArrayList<>();
        roleUsers.add(roleUser);

        var user = User.builder()
                .us(request.getUsername())
                .pw(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .roles(roleUsers)
                .build();
        roleUser.setUser(user);
        userReponsitory.save(user);
        roleUserReponsitory.save(roleUser);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRqeuest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userReponsitory.findByUsername(request.getUsername()).orElseThrow();
        if (user == null) {
            return AuthenticationResponse.builder()
                    .status(false)
                    .token("")
                    .build();
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .status(true)
                .token(jwtToken)
                .build();
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public List<User> getAll() {
        return userReponsitory.findAll();
    }
}
