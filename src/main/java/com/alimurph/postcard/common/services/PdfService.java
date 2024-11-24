package com.alimurph.postcard.common.services;

import com.alimurph.postcard.postcard.OccasionType;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.helper.W3CDom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfService {

    private final SpringTemplateEngine templateEngine;
    @Value("${application.assets.path}")
    private String assetsPath;
	
    public PdfService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public ByteArrayOutputStream generatePdf(String occasion, String message) throws Exception {

        String cardHtml = generateHTML(occasion, message);

        if (cardHtml != null) {
            return generatePdf(cardHtml);
        }

        return null;
    }

    private String generateHTML(String occasion, String message){

        String cardBackground = null;
        if(occasion.equalsIgnoreCase(OccasionType.ANNIVERSARY.name())){
//            cardBackground = File.separator + "images" + File.separator + "anniversary_background_a4.jpg";
            cardBackground = "images/anniversary_background_a4.jpg";
        }
        else if(occasion.equalsIgnoreCase(OccasionType.BIRTHDAY.name())){
//            cardBackground = File.separator + "images" + File.separator + "birthday_background_a4.jpg";
            cardBackground = "images/birthday_background_a4.jpg";
        }

        if(cardBackground != null) {

            Map<String, Object> cardTextProperties = new HashMap<String, Object>();
            cardTextProperties.put("message", message);
            cardTextProperties.put("card_path", cardBackground);

            Context context = new Context();
            context.setVariables(cardTextProperties);

            return templateEngine.process("postcard", context);

        }

        return null;
    }

    private ByteArrayOutputStream generatePdf(String cardHtml) throws Exception {
		
		String baseUrl = FileSystems.getDefault().getPath(this.assetsPath + File.separator).toUri().toURL().toString();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		File font1 = new File(this.assetsPath + File.separator + "fonts" + File.separator + "NotoEmoji-VariableFont_wght.ttf");
		System.out.println(font1.getPath());
		File font2 = new File(this.assetsPath + File.separator + "fonts" + File.separator + "JosefinSans-Regular.ttf");
		System.out.println(font2.getPath());

        // jsoup to convert the HTML string to a jsoup Document to render XHTML
        Document xHTMLdocument = Jsoup.parse(cardHtml);
        xHTMLdocument.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

        // PdfRendererBuilder will take this XHTML document and create a PDF as the output file
        PdfRendererBuilder builder = new PdfRendererBuilder();
		builder.withW3cDocument(new W3CDom().fromJsoup(xHTMLdocument), baseUrl);// W3c DOM - standard html doc
        builder.useFont(font1, "NotoEmoji-VariableFont_wght"); // to render emojis
        builder.useFont(font2, "JosefinSans-Regular");
        builder.toStream(byteArrayOutputStream);
        builder.run();
		byteArrayOutputStream.close();
		return byteArrayOutputStream;
    }
}
