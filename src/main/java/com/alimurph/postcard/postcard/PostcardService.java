package com.alimurph.postcard.postcard;

import com.alimurph.postcard.common.services.EncryptionService;
import com.alimurph.postcard.common.services.PdfService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Objects;

@Service
public class PostcardService {

    private final EncryptionService encryptionService;
    private final PdfService pdfService;

    PostcardService(EncryptionService encryptionService, PdfService pdfService){
        this.encryptionService = encryptionService;
        this.pdfService = pdfService;
    }

    public ByteArrayOutputStream export(String cardId) throws Exception {
        Postcard postcard = getCard(cardId);
        if(postcard != null) {
            return this.pdfService.generatePdf(postcard.occasion(), postcard.message());
        }
        else
            return  null;
    }

    public String createCard(Postcard request) throws GeneralSecurityException, IOException {
        String encryptedData = this.encryptionService.encryptData(request);
        if(!Objects.isNull(encryptedData)){
            return URLEncoder.encode(encryptedData, StandardCharsets.UTF_8);
        }
        else
            return null;
    }

    public Postcard getCard(String cardId) throws GeneralSecurityException, IOException, ClassNotFoundException {
        return (Postcard) this.encryptionService.decryptData(cardId);
    }
}
