package de.codeboje.tutorials.feignintroduction.client;

import de.codeboje.tutorials.feignintroduction.config.FeignConfig;
import de.codeboje.tutorials.feignintroduction.model.Board;
import de.codeboje.tutorials.feignintroduction.model.Confirmation;
import de.codeboje.tutorials.feignintroduction.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="FeignKanbanClient", url= "https://kanbanbackend.herokuapp.com/",
        configuration = FeignConfig.class)
public interface FeignKanbanClient {

    @PostMapping("/login")
    ResponseEntity<Void> loginUser();

    @PostMapping(value = "/register")
    String registerUser(User user);

    @DeleteMapping("/unregister")
    ResponseEntity<Void> unregisterUser(@RequestHeader("X-Auth-Token") String authToken, Confirmation user);


    @GetMapping("/boards")
    List<Board> listBoards(@RequestHeader("X-Auth-Token") String authHeader);

    @PostMapping("/boards")
    Board createBoard(@RequestHeader("X-Auth-Token") String authHeader, Board board);

    @PutMapping("/board/{id}")
    Board changeBoard(@RequestHeader("X-Auth-Token") String authHeader, @PathVariable("id") Long id, Board board);


}
