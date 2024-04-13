package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;
import org.example.hexlet.model.Course;
import org.example.hexlet.dto.courses.CoursesPage;

import java.util.ArrayList;
import java.util.List;

public class HelloWorld {

    private static List<Course> COURSES = List.of(
            new Course(1L, "Курс № 1", "Какое-то описание для курса № 1"),
            new Course(1L, "Курс № 2", "Какое-то описание для курса № 2"),
            new Course(1L, "Курс № 3", "Какое-то описание для курса № 3"),
            new Course(2L, "Курс № 4", "Какое-то описание для курса № 4"),
            new Course(2L, "Курс № 5", "Какое-то описание для курса № 5"),
            new Course(3L, "Курс № 6", "Какое-то описание для курса № 6")
    );

    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParamAsClass("id", Long.class).get();
            var courses = new ArrayList<Course>();
            for (Course iterator: COURSES) {
                if (iterator.getId().equals(id)) {
                    courses.add(iterator);
                }
            }
            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.render("courses/index.jte", model("page", page));
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
