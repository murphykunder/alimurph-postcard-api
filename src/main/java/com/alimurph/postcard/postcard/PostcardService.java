package com.alimurph.postcard.postcard;

import com.alimurph.postcard.common.services.PdfService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PostcardService {

    private final PdfService pdfService;

    PostcardService(PdfService pdfService){
        this.pdfService = pdfService;
    }

    public ByteArrayOutputStream export(Postcard postcard) throws Exception {
        return this.pdfService.generatePdf(postcard.occasion(), postcard.message());
    }
}
