package org.example.hexlet;

import io.javalin.Javalin;

public class HelloWorld {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });
        app.get("/", ctx -> ctx.result("Hello, World!"));
        app.get("/hello", ctx -> {
            var name = ctx.queryParamAsClass("name", String.class).getOrDefault("World");
            ctx.result("Hello, " + name + "!");
        });
        app.get("users/{userId}/post/{postId}", ctx -> {
            ctx.result("User ID: " + ctx.pathParam("userId")
                        + "\nPost ID: " + ctx.pathParam("postId"));
        });
        app.start(7070);
    }
}
