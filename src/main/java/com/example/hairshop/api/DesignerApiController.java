package com.example.hairshop.api;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.service.DesignerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DesignerApiController {

    private final DesignerService designerService;

    // 회원 정보 조회
    @GetMapping("/api/myPage")
    public DesignerDto myPage(HttpSession session) {
        String userId = session.getAttribute("userId").toString();
        Designer findDesigner = designerService.findOne(userId);

        DesignerDto dto = new DesignerDto();
        dto.setName(findDesigner.getName());
        dto.setImg(findDesigner.getImg());
        dto.setContent(findDesigner.getContent());
        dto.setCareer(findDesigner.getCareer());

        return dto;
    }

}
