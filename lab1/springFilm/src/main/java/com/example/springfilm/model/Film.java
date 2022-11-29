package com.example.springfilm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private static int count = 0;
    private int id;
    private String title;
    private String director;

    public Film(String title, String director) {
        this.title = title;
        this.director = director;
        id = count++;
    }
}
