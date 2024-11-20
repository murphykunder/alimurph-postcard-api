package com.alimurph.postcard.postcard;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record Postcard(
        @NotNull(message = "100")
        @NotBlank(message = "100")
        String from,
        @NotNull(message = "101")
        @NotBlank(message = "101")
        String to,
        @NotNull(message = "102")
        @NotBlank(message = "102")
        String occasion,
        Long audio,
        @NotNull(message = "103")
        @NotBlank(message = "103")
        String message
) implements Serializable {
}
