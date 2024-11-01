package projects.moyethon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import projects.moyethon.service.S3UploadService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MusicController {

    private final S3UploadService s3UploadService;

    @PostMapping("/api/music/upload")
    public ResponseEntity<?> uploadAlbumImage(@RequestParam("file") MultipartFile file) {

        try{
            String imagePath = s3UploadService.uploadImage(file);
            return ResponseEntity.ok().body(imagePath);
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("image upload failed");
        }

    }
}
