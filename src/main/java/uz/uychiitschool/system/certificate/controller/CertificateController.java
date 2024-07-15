package uz.uychiitschool.system.certificate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.uychiitschool.system.certificate.service.PdfService;
import uz.uychiitschool.system.certificate.service.QrCodeService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/certificate")
@RequiredArgsConstructor
public class CertificateController {

    private final QrCodeService qrCodeService;

    private final PdfService pdfService;

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateCertificate(@RequestParam String name,
                                                      @RequestParam String qrText) {
        try {
            byte[] qrCodeImage = qrCodeService.generateQRCode(qrText, 80, 80);
            byte[] pdfBytes = pdfService.createPdfWithQRCode(name, qrCodeImage);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "file.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
