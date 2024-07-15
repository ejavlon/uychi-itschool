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
import uz.uychiitschool.system.certificate.service.QrCodeService;

@RestController
@RequestMapping("/api/v1/qr")
@RequiredArgsConstructor
public class QrCodeGeneratorController {

    private final QrCodeService qrCodeService;

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateQRCode(@RequestParam String text,
                                                 @RequestParam int width,
                                                 @RequestParam int height) {
        byte[] qrCodeImage = qrCodeService.generateQRCode(text, width, height);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
    }
}
