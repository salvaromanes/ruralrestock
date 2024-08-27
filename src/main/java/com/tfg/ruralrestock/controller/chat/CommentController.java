package com.tfg.ruralrestock.controller.chat;

import com.tfg.ruralrestock.dbo.chat.CommentRequest;
import com.tfg.ruralrestock.dbo.chat.CommentResponse;
import com.tfg.ruralrestock.dbo.town.TownResponse;
import com.tfg.ruralrestock.model.chat.Comment;
import com.tfg.ruralrestock.repository.chat.CommentRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentRepository commentRepository;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse createComment(@RequestBody CommentRequest commentRequest, HttpSession session) {
        Comment commentFound = commentRepository.findById(commentRequest.getTema())
                .orElse(null);

        if (commentFound == null) {
            Comment comment = Comment.builder()
                    .tema(commentRequest.getTema())
                    .autor((String) session.getAttribute("username"))
                    .descripcion(commentRequest.getDescripcion())
                    .build();

            commentRepository.insert(comment);
            log.info("El comentario se ha creado con exito");
            return mapToCommentResponse(comment);
        } else {
            log.info("Ya existen comentarios sobre este tema");
            throw new RuntimeException("El comentario no se puede crear");
        }
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(this::mapToCommentResponse).toList();
    }

    @DeleteMapping("/delete/{tema}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCommentByTema(@PathVariable String tema){
        Comment comment = commentRepository.findById(tema).orElseThrow(() -> new RuntimeException("Comentario no encontrado"+commentRepository.findAll().toString()));
        commentRepository.delete(comment);
        log.info("Comentario con tema {} eliminado correctamente", tema);
    }

    @GetMapping("/download/csv")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> downloadCSV() {
        StringBuilder csvBuilder = new StringBuilder();

        csvBuilder.append("Tema, Autor, Descripción\n");

        List<CommentResponse> comentarios = getAllComments();

        Iterator<CommentResponse> iterator = comentarios.iterator();;

        while (iterator.hasNext()) {
            CommentResponse commentResponse = iterator.next();

            csvBuilder.append(String.join(", ",
                            commentResponse.getTema(),
                            commentResponse.getAutor(),
                            commentResponse.getDescripcion()))
                    .append("\n");
        }

        byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=comentarios.csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    private CommentResponse mapToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .autor(comment.getAutor())
                .tema(comment.getTema())
                .descripcion(comment.getDescripcion())
                .build();
    }
}