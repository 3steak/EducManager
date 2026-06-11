package com.educmanager.controller;

import com.educmanager.dto.LoginRequest;
import com.educmanager.dto.LoginResponse;
import com.educmanager.entity.UserAccount;
import com.educmanager.repository.UserAccountRepository;
import com.educmanager.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Chercher l'utilisateur par username
        UserAccount user = userAccountRepository.findByUsername(request.getUsername()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body(null); // Utilisateur pas trouvé
        }

        // Vérifier le mot de passe (en production, utiliser BCrypt !)
        if (!user.getPasswordHash().equals(request.getPassword())) {
            return ResponseEntity.status(401).body(null); // Mauvais mot de passe
        }

        // Générer le token JWT
        String token = jwtUtils.generateToken(user.getUsername(), user.getRole().toString());

        // Retourner le token
        LoginResponse response = new LoginResponse(token, user.getRole().toString(), user.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser() {
        // Cette route retourne l'utilisateur connecté
        // Le JwtFilter a déjà validé le token
        return ResponseEntity.ok("Utilisateur connecté");
    }
}