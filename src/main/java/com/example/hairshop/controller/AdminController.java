package com.example.hairshop.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.domain.User;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.service.CategoryService;
import com.example.hairshop.service.DesignerService;
import com.example.hairshop.service.S3Service;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final DesignerService designerService;
    private final CategoryService categoryService;
    private final S3Service s3Service;

    /** 어드민 홈 **/
    @GetMapping("/admin")
    public String admin() {
        return "admin/home";
    }

    /** 회원 정보 조회 **/
    @GetMapping("/admin/myPage")
    public String myPage(Model m, HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        Designer findDesigner = designerService.findOne(userId);

        // 디자이너 정보
        DesignerDto dto = new DesignerDto();
        dto.setName(findDesigner.getName());
        dto.setImg(findDesigner.getImg());
        dto.setContent(findDesigner.getContent());
        dto.setCareer(findDesigner.getCareer());

        m.addAttribute("designerInfo", dto);

        return "admin/designerInfo";
    }

    @PostMapping("img/upload")
    public ResponseEntity<String> imgUpload(@RequestPart("file") MultipartFile file) throws IOException {
        try {
            String imgUrl = s3Service.upload(file);
            return new ResponseEntity<>(imgUrl, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/admin/myPage/modify")
    public ResponseEntity<?> modifyMyPage(HttpSession session, @RequestBody DesignerDto dto) {
        try {
            String userId = session.getAttribute("userId").toString();
            Designer findDesigner = designerService.findOne(userId);

            Designer designer = designerService.modifyDesignerInfo(findDesigner, dto.getImg(), dto.getContent(), dto.getCareer());

            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
