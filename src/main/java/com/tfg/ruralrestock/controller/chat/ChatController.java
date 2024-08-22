package com.tfg.ruralrestock.controller.chat;

import com.tfg.ruralrestock.dbo.chat.ChatRequest;
import com.tfg.ruralrestock.dbo.chat.ChatResponse;
import com.tfg.ruralrestock.model.chat.Chat;
import com.tfg.ruralrestock.repository.chat.ChatRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatRepository chatRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatResponse createChat(@RequestBody ChatRequest chatRequest) {
        String key = chatRequest.getAutor() + chatRequest.getOrigen() + chatRequest.getDestino() + chatRequest.getFecha();

        Chat chatFound = chatRepository.findById(key)
                .orElse(null);

        if (chatFound == null) {
            Chat chat = Chat.builder()
                    .clave(key)
                    .autor(chatRequest.getAutor())
                    .origen(chatRequest.getOrigen())
                    .destino(chatRequest.getDestino())
                    .fecha(chatRequest.getFecha())
                    .descripcion(chatRequest.getDescripcion())
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
                .descripcion(chat.getDescripcion())
                .build();
    }
}