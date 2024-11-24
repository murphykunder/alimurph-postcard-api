package com.alimurph.postcard.postcard;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("postcard")
@Tag(name="Postcard")
public class PostcardController {

    private final PostcardService postcardService;

    public PostcardController(PostcardService postcardService) {
        this.postcardService = postcardService;
    }
	
	@GetMapping(value="/status", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Alimurph-postcard API is running");
    }

    @PostMapping(value = "/export", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Object> export(@RequestBody @Valid Postcard request) throws Exception {
        ByteArrayOutputStream pdfStream = this.postcardService.export(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=card.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfStream.size())
                .body(pdfStream.toByteArray());

    }
}
