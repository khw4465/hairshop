package com.example.hairshop.service;

import com.example.hairshop.domain.StyleTip;
import com.example.hairshop.repository.StyleTipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StyleTipService {

    private final StyleTipRepository styleTipRepository;

    @Transactional
    public void save(StyleTip styleTip) {
        styleTipRepository.save(styleTip);
    }

    public List<StyleTip> findAll() {
        return styleTipRepository.findAll();
    }
}
