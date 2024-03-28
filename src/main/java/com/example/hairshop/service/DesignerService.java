package com.example.hairshop.service;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.User;
import com.example.hairshop.repository.DesignerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DesignerService {

    private final DesignerRepository designerRepository;

    @Transactional
    public Designer join(Designer designer) {
        Optional<Designer> findDesigner = designerRepository.findByKakao(designer.getKakaoId());
        if (findDesigner.isEmpty()){
            designerRepository.save(designer);
            return designer;
        }
        return findDesigner.get();
    }


}
