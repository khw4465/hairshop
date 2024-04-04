package com.example.hairshop.service;

import com.example.hairshop.domain.Designer;
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
        Optional<Designer> findDesigner = designerRepository.findOptionalByKakao(designer.getKakaoId());
        if (findDesigner.isEmpty()){
            designerRepository.save(designer);
            return designer;
        }
        return findDesigner.get();
    }
    public Designer findOne(String kakaoId) {
        return designerRepository.findByKakao(kakaoId);
    }

    /** 디자이너 정보 수정 **/
    @Transactional
    public Designer modifyDesignerInfo(Designer designer, String imgUrl, String content, String career) {
        if (!imgUrl.equals("")) {
            designer.setImg(imgUrl);
        }
        designer.setContent(content);
        designer.setCareer(career);

        return designer;
    }


}
