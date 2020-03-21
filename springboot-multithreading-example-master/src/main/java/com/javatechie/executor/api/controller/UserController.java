package com.javatechie.executor.api.controller;

import com.javatechie.executor.api.entity.User;
import com.javatechie.executor.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveUsers(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            service.saveUsers(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/users", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllUsers() {
       return  service.findAllUsers().thenApply(ResponseEntity::ok);
    }

    @GetMapping(value = "/users1", produces = "application/json")
    public CompletionStage<ResponseEntity> findAllUsers1() {
        logger.info("get list of user in controller class "+Thread.currentThread().getName());
        return service.findAllUsers1().thenApply(ResponseEntity::ok);
    }
    //completablefuture complete method
   /* you can create an instance of this class with a no-arg constructor to represent some future
        result, hand it out to the consumers and complete it at some time in the future
    using the complete method. The consumers may use the get method to block the current
    thread until this result will be provided*/
    @GetMapping(value = "/count", produces = "application/json")
    public ResponseEntity findUsersCount() throws ExecutionException, InterruptedException {
        CompletableFuture<List<User>> cf = new CompletableFuture<>();
        logger.info(" In findUsers count method controller"+Thread.currentThread().getName());
        service.countUsers(cf);
        logger.info("Does the count method got invoked");
        cf.complete(service.findAllUsers1().get());
        return ResponseEntity.ok().build();
    }


    /*
    With this kind of implementation we can return the response to client
    and in the backgroup we can run the process like downloading some doc
    or invoking some third party like stuff in runAsync().
     */
    @PostMapping(value = "/saveauser", produces = "application/json")
    public CompletionStage<Integer> saveAUser() {
        logger.info("save a user in controller class  this is main thread"+Thread.currentThread().getName());
        return supplyAsync(() -> service.saveAndUpdateUser());
    }


    @GetMapping(value = "/getUsersByThread", produces = "application/json")
    public  ResponseEntity getUsers(){
        CompletableFuture<List<User>> users1=service.findAllUsers();
        CompletableFuture<List<User>> users2=service.findAllUsers();
        CompletableFuture<List<User>> users3=service.findAllUsers();
        CompletableFuture.allOf(users1,users2,users3).join();
        return  ResponseEntity.status(HttpStatus.OK).build();
    }


    /*public CompletionStage<ResponseEntity> getArticlesReactive(int amount) {
        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        service.findAllUsers().
                .thenApply(u -> u.subList(1,10))
                .exceptionally(throwable -> {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }) .whenComplete((response, throwable) -> {
            future.complete(response);
        });
    }*/
}
