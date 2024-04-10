package com.example.hairshop.service;

import com.example.hairshop.domain.Shop;
import org.aspectj.lang.annotation.RequiredTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@SpringBootTest
@Transactional
public class ShopServiceTest {

    @Autowired ShopService shopService;

    @Test
    public void 샵조회() {
//        List<Shop> byCategory = shopService.findByCategory("헤어샵");
//        System.out.println("byCategory = " + byCategory);
    }
}
