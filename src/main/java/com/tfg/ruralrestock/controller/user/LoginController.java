package com.tfg.ruralrestock.controller.user;

import com.tfg.ruralrestock.dbo.user.LoginRequest;
import com.tfg.ruralrestock.dbo.user.UserRequest;
import com.tfg.ruralrestock.dbo.user.UserResponse;
import com.tfg.ruralrestock.model.user.User;
import com.tfg.ruralrestock.repository.user.UserRepository;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserRepository userRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        User userFound = userRepository.findById(userRequest.getDni()).orElse(null);

        if(userFound == null) {
            User user = User.builder()
                    .dni(userRequest.getDni())
                    .nombre(userRequest.getNombre())
                    .apellidos(userRequest.getApellidos())
                    .rol("visitante")
                    .email(userRequest.getEmail())
                    .password(userRequest.getPassword())
                    .municipio(userRequest.getMunicipio())
                    .bloqueado(Boolean.FALSE)
                    .dadoBaja(Boolean.FALSE)
                    .build();

            userRepository.insert(user);
            log.info("Usuario {} creado correctamente", user.getEmail());
            return mapToUserResponse(user);
        }else
            throw new RuntimeException("El usuario no se puede crear");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);

        if (user != null && user.getPassword().equals(loginRequest.getPassword()) && !user.getBloqueado() && !user.getDadoBaja()) {
            session.setAttribute("name", user.getNombre());
            session.setAttribute("username", loginRequest.getEmail());
            session.setAttribute("role", user.getRol());

            return switch (user.getRol()) {
                case "visitante" -> ResponseEntity.ok("http://localhost:8080/ruralrestock/mainMenu/quienesSomosLogin.html");
                case "administrador" -> ResponseEntity.ok("http://localhost:8080/ruralrestock/admin/panelAdmin.html");
                case "tecnico_ayuntamiento" -> ResponseEntity.ok("http://localhost:8080/ruralrestock/admin/panelTecnico.html");
                case "personal_autorizado" -> ResponseEntity.ok("http://localhost:8080/ruralrestock/admin/panelAutorizado.html");
                default -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            };
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Cierre de sesión exitoso");
    }

    @GetMapping("/some-protected-route")
    public ResponseEntity<String> getProtectedData(HttpSession session) {
        String name = (String) session.getAttribute("name");

        if (name == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay sesión");
        } else {
            return ResponseEntity.ok().body(name);
        }
    }

    @GetMapping("/protected-email")
    public ResponseEntity<String> getProtectedEmail(HttpSession session) {
        String name = (String) session.getAttribute("username");

        if (name == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay sesión");
        } else {
            return ResponseEntity.ok().body(name);
        }
    }

    @GetMapping("/getAllActive")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllActiveUser() {
        List<User> users = userRepository.findAll();
        List<User> actives = new ArrayList<>();

        for (User u:users) {
            if (!u.getBloqueado()) {
                actives.add(u);
            }
        }

        return actives.stream().map(this::mapToUserResponse).toList();
    }

    @GetMapping("/getAllBlock")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllBlockUser() {
        List<User> users = userRepository.findAll();
        List<User> blocks = new ArrayList<>();

        for (User u:users) {
            if (u.getBloqueado()) {
                blocks.add(u);
            }
        }

        return blocks.stream().map(this::mapToUserResponse).toList();
    }

    @GetMapping("/getAllDown")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllDownUser() {
        List<User> users = userRepository.findAll();
        List<User> removes = new ArrayList<>();

        for (User u:users) {
            if (u.getDadoBaja()) {
                removes.add(u);
            }
        }

        return removes.stream().map(this::mapToUserResponse).toList();
    }

    @GetMapping("/getMisDatos/{username}")
    @ResponseStatus(HttpStatus.OK)
    public User getMisDatos(@PathVariable String username) {
        return userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse update(@RequestBody UserRequest userRequest, HttpSession httpSession) {
        String username = (String) httpSession.getAttribute("username");
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setMunicipio(userRequest.getMunicipio());
        user.setPassword(userRequest.getPassword());

        userRepository.save(user);
        return mapToUserResponse(user);
    }

    @GetMapping("/blockUser/{dni}")
    @ResponseStatus(HttpStatus.OK)
    public void blockUser(@PathVariable String dni){
        User user = userRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (user.getBloqueado()) {
            user.setBloqueado(Boolean.FALSE);
            log.info("Usuario {} desbloqueado correctamente", user.getEmail());
        } else {
            user.setBloqueado(Boolean.TRUE);
            log.info("Usuario {} bloqueado correctamente", user.getEmail());
        }

        userRepository.save(user);
    }

    @GetMapping("/darseBaja")
    @ResponseStatus(HttpStatus.OK)
    public void baja(HttpSession httpSession){
        String username = (String) httpSession.getAttribute("username");
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setDadoBaja(Boolean.TRUE);

        userRepository.save(user);
    }

    @DeleteMapping("/delete/{dni}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String dni){
        userRepository.deleteById(dni);
        log.info("Usuario con dni {} borrado correctamente", dni);
    }

    @GetMapping("/download/csv")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> downloadCSV() {
        StringBuilder csvBuilder = new StringBuilder();

        csvBuilder.append("DNI, Nombre, Apellidos, Email, Municipio, Bloqueado, Dado de baja\n");

        List<UserResponse> activos = getAllActiveUser();
        List<UserResponse> bloqueados = getAllBlockUser();
        List<UserResponse> baja = getAllDownUser();

        List<UserResponse> usuarios = new ArrayList<>();

        usuarios.addAll(activos);
        usuarios.addAll(bloqueados);
        usuarios.addAll(baja);

        Iterator<UserResponse> iterator = usuarios.iterator();;

        while (iterator.hasNext()) {
            UserResponse userResponse = iterator.next();

            csvBuilder.append(String.join(", ",
                            userResponse.getNombre(),
                            userResponse.getApellidos(),
                            userResponse.getEmail(),
                            userResponse.getMunicipio(),
                            userResponse.getBloqueado().toString(),
                            userResponse.getDadoBaja().toString()))
                    .append("\n");
        }

        byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=usuarios.csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .dni(user.getDni())
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .rol(user.getRol())
                .email(user.getEmail())
                .password(user.getPassword())
                .municipio(user.getMunicipio())
                .bloqueado(user.getBloqueado())
                .dadoBaja(user.getDadoBaja())
                .build();
    }
}