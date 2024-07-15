package uz.uychiitschool.system.certificate.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.properties.HorizontalAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] createPdfWithQRCode(String name, byte[] qrCodeImage) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        document.setMargins(0,0,0,0);
        Image image = new Image(ImageDataFactory.create("src/main/resources/static/assets/images/certificate.png"));
        image.setMarginTop(200);
        image.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(image);

        PageSize pageSize = pdfDoc.getDefaultPageSize();
        Image qrCode = new Image(ImageDataFactory.create(qrCodeImage));
        qrCode.setHorizontalAlignment(HorizontalAlignment.CENTER);
        qrCode.setFixedPosition((pageSize.getWidth()/2) - (qrCode.getImageWidth()/2),248);
        document.add(qrCode);

        PdfFont robotoFont = PdfFontFactory.createFont("src/main/resources/static/fonts/Roboto-Regular.ttf", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        Paragraph paragraph = new Paragraph(name);
        paragraph.setFontSize(26);
        paragraph.setFont(robotoFont);
        paragraph.setFixedPosition(33,403,paragraph.getWidth());
        document.add(paragraph);

        StringBuilder stringBuilder = new StringBuilder(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        stringBuilder.append("\n");
        stringBuilder.append("Berilgan sana");
        Paragraph paragraph2 = new Paragraph(stringBuilder.toString());
        paragraph2.setFontSize(14);
        paragraph2.setFont(robotoFont);
        paragraph2.setFixedPosition(430,255,paragraph2.getWidth());
        document.add(paragraph2);

        document.close();
        pdfDoc.close();
        writer.close();
        return outputStream.toByteArray();
    }
}
