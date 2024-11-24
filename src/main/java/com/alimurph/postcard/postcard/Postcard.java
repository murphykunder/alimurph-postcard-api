package com.alimurph.postcard.postcard;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record Postcard(
        @NotNull(message = "From cannot be blank")
        @NotBlank(message = "From cannot be blank")
        String from,
        @NotNull(message = "To cannot be blank")
        @NotBlank(message = "To cannot be blank")
        String to,
        @NotNull(message = "Occasion cannot be blank")
        @NotBlank(message = "Occasion cannot be blank")
        String occasion,
        Long audio,
        @NotNull(message = "Message cannot be blank")
        @NotBlank(message = "Message cannot be blank")
        String message
) implements Serializable {
}
