package com.javatechie.executor.api.utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class Util1 {

    public static void process(CompletableFuture<Integer> cf){
        cf.thenApply(k -> k+1)
                .thenAccept(System.out::println);
    }


    static void anyOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");

        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).
                        thenApply(String::toUpperCase))
                .collect(Collectors.toList());
        futures.forEach(cf ->{
            try {
                System.out.println(cf.get());
                cf.whenComplete((res, th) -> {
                    if(th == null) {
                        result.append(res);
                        System.out.println("Result from here "+result);
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });


        CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()]))

                .whenComplete((res, th) -> {
            if(th == null) {
                result.append(res);
                System.out.println("Result is "+result);
            }
        });
        System.out.println("Result was empty "+result.length());
    }

    public static void main(String[] args) {
        CompletableFuture<Integer> cf = new CompletableFuture<>();
        process(cf);
        cf.complete(2);
        anyOfExample();
    }
}
