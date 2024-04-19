package com.example.hairshop.controller;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.service.DesignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserDesignerController {

    private final DesignerService designerService;

    /** 디자이너 상세정보 **/
    @GetMapping("/designer/info")
    public String designerInfo(@RequestParam("designerId") String designerId, Model m) {
        Designer d = designerService.findById(designerId);
        DesignerDto designer = new DesignerDto(d);
        m.addAttribute("designer", designer);

        return "/user/designerInfo";
    }

}
