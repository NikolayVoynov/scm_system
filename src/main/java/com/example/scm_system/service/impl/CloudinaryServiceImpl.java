package com.example.scm_system.service.impl;

import com.cloudinary.Cloudinary;
import com.example.scm_system.service.CloudinaryImage;
import com.example.scm_system.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public CloudinaryImage upload(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);

        try {
            @SuppressWarnings("unchecked")
            Map<String, String> uploadResult = cloudinary.
                    uploader().
                    upload(tempFile, Map.of());

            String url = uploadResult.getOrDefault(URL,
                    "https://i.pinimg.com/originals/c5/21/64/c52164749f7460c1ededf8992cd9a6ec.jpg");
            String publicId = uploadResult.getOrDefault(PUBLIC_ID, "");

            CloudinaryImage cloudinaryImage = new CloudinaryImage();

            cloudinaryImage.setPublicId(publicId);
            cloudinaryImage.setUrl(url);


            return cloudinaryImage;

        } finally {
            tempFile.delete();
        }
    }
}
