package com.example.hairshop.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DesignerRepositoryTest {

    @Autowired DesignerRepository designerRepository;

    @Test
    @Rollback(false)
    public void 디자이너_생성() {

    }
}