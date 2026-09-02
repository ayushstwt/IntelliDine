package com.intellidine.portal.apis;

import com.intellidine.common.dto.ApiResponse;
import com.intellidine.common.filter.TraceIdFilter;
import com.intellidine.portal.dtos.AuthRequest;
import com.intellidine.portal.dtos.ResponseDTO;
import com.intellidine.portal.dtos.SignUpRequest;
import com.intellidine.portal.entities.UserEntity;
import com.intellidine.portal.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Slf4j
public class AuthAPI {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        log.info("Sign up request received for username: {}", signUpRequest.getUsername());
        authenticationService.registerUser(signUpRequest);
        return new ResponseEntity<>(
                ApiResponse.ok("User registered successfully", "User registration completed", request.getRequestURI(), TraceIdFilter.getTraceId()),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<ResponseDTO>> authenticateUser(@Valid @RequestBody AuthRequest authRequest, HttpServletRequest request) {
        log.info("Login request received for username: {}", authRequest.getUsername());
        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserEntity user = (UserEntity) authentication.getPrincipal();

        ResponseDTO responseDTO = ResponseDTO.builder()
                .token(this.authenticationService.generateToken(authentication))
                .username(user.getUsername())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(ApiResponse.ok(responseDTO, "Authentication successful", request.getRequestURI(), TraceIdFilter.getTraceId()));
    }

    @GetMapping("/validate")
    public Boolean validateToken(@RequestParam("token") String token) {
        log.info("Entered validate token method");
        return authenticationService.validateToken(token);
    }
}

