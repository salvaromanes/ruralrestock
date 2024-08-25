package com.tfg.ruralrestock.controller.chat;

import com.tfg.ruralrestock.dbo.chat.ChatRequest;
import com.tfg.ruralrestock.dbo.chat.ChatResponse;
import com.tfg.ruralrestock.model.chat.Chat;
import com.tfg.ruralrestock.repository.chat.ChatRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatRepository chatRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatResponse createChat(@RequestBody ChatRequest chatRequest, HttpSession session) {
        String key = ((String) session.getAttribute("username")) + chatRequest.getOrigen() + chatRequest.getDestino() + chatRequest.getFecha();

        Chat chatFound = chatRepository.findById(key)
                .orElse(null);

        if (chatFound == null) {
            Chat chat = Chat.builder()
                    .clave(key)
                    .autor(((String) session.getAttribute("username")))
                    .origen(chatRequest.getOrigen())
                    .destino(chatRequest.getDestino())
                    .fecha(chatRequest.getFecha())
                    .plazas(chatRequest.getPlazas())
                    .interesados(new ArrayList<>())
                    .build();

            chatRepository.insert(chat);
            log.info("El chat se ha creado con exito");
            return mapToChatResponse(chat);
        } else {
            log.info("Chat ya existente");
            throw new RuntimeException("El chat no se puede crear");
        }
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ChatResponse> getAllChats() {
        List<Chat> chats = chatRepository.findAll();
        return chats.stream().map(this::mapToChatResponse).toList();
    }

    @PostMapping("/estoyInteresado")
    @ResponseStatus(HttpStatus.OK)
    public String interesado(@RequestBody Chat chat, HttpSession session) {
        String key = chat.getAutor() + chat.getOrigen() + chat.getDestino() + chat.getFecha();

        Chat chatFound = chatRepository.findById(key).orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        if (chatFound.getInteresados().contains((String) session.getAttribute("username"))) {
            updateMoreSeats(key, session);
            return "Plazas incrementadas";
        } else {
            updateLessSeats(key, session);
            return "Plazas decrementadas";
        }
    }

    @PutMapping("/updateLess/{clave}")
    @ResponseStatus(HttpStatus.OK)
    public void updateLessSeats(@PathVariable String clave, HttpSession session) {
        Chat chatFound = chatRepository.findById(clave)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        if (Integer.parseInt(chatFound.getPlazas()) == 0) {
            throw new RuntimeException("Error: No hay plazas suficientes en este viaje");
        }

        chatFound.setPlazas(String.valueOf(Integer.parseInt(chatFound.getPlazas()) - 1));
        List<String> interesados = chatFound.getInteresados();
        String persona = (String) session.getAttribute("username");
        interesados.add(persona);
        chatFound.setInteresados(interesados);

        chatRepository.save(chatFound);
        log.info("Plazas del chat decrementadas correctamente");
    }

    @PutMapping("/updateMore/{clave}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMoreSeats(@PathVariable String clave, HttpSession session) {
        Chat chatFound = chatRepository.findById(clave)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        chatFound.setPlazas(String.valueOf(Integer.parseInt(chatFound.getPlazas()) + 1));
        List<String> interesados = chatFound.getInteresados();
        String persona = (String) session.getAttribute("username");
        interesados.remove(persona);
        chatFound.setInteresados(interesados);

        chatRepository.save(chatFound);
        log.info("Plazas del chat incrementadas correctamente");
    }

    @DeleteMapping("/delete/{autor}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChatByAutor(@PathVariable String autor){
        for (Chat chat:chatRepository.findAll()) {
            if (chat.getClave().contains(autor)) {
                String key = chat.getAutor() + chat.getOrigen() + chat.getDestino() + chat.getFecha();
                chatRepository.deleteById(key);
                log.info("Chat {} eliminado correctamente", chat.getClave());
            }
        }
    }

    private ChatResponse mapToChatResponse(Chat chat) {
        return ChatResponse.builder()
                .autor(chat.getAutor())
                .origen(chat.getOrigen())
                .destino(chat.getDestino())
                .fecha(chat.getFecha())
                .plazas(chat.getPlazas())
                .interesados(chat.getInteresados())
                .build();
    }
}