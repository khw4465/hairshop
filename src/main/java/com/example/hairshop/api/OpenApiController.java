package com.example.hairshop.api;

import com.example.hairshop.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OpenApiController {

    private final OpenApiService openApiService;
    @GetMapping("/openApi")
    public void openApi() throws  IOException{


    }
}
