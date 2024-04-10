package com.example.hairshop.service;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Style;
import com.example.hairshop.domain.StyleSubCategory;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.dto.StyleDto;
import com.example.hairshop.repository.DesignerRepository;
import com.example.hairshop.repository.StyleRepository;
import com.example.hairshop.repository.StyleSubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DesignerService {

    private final DesignerRepository designerRepository;
    private final StyleSubCategoryRepository subCategoryRepository;
    private final StyleRepository styleRepository;

    /** 총 관리자 로그인 **/
    @Transactional
    public Designer kakaoJoin(Designer designer) {
        Optional<Designer> findDesigner = designerRepository.findOptionalByKakao(designer.getKakaoId());
        if (findDesigner.isEmpty()){
            designerRepository.save(designer);
            return designer;
        }
        return findDesigner.get();
    }

    /** 디자이너 생성 **/
    @Transactional
    public void join(DesignerDto dto) {
        Designer designer = Designer.createDesigner(dto.getName());
        designer.setImg(dto.getImg());
        designer.setContent(dto.getContent());
        designer.setCareer(dto.getCareer());

        if (dto.getStyles().size() > 0) {
            for (StyleDto styleDto : dto.getStyles()) {
                StyleSubCategory subCategory1 = subCategoryRepository.findByName(styleDto.getCategory1());
                StyleSubCategory subCategory2 = subCategoryRepository.findByName(styleDto.getCategory2());

                Style style = new Style();
                style.setImgUrl(styleDto.getImgUrl());
                style.setDesigner(designer);
                style.getSubCategorys().add(subCategory1);
                style.getSubCategorys().add(subCategory2);
                subCategory1.getStyles().add(style);
                subCategory2.getStyles().add(style);
                designer.getStyles().add(style);

            }
        }
        designerRepository.save(designer);
    }

    /** 디자이너 삭제 **/
    @Transactional
    public void remove(String selectedId) {
        long id = Long.parseLong(selectedId);
        Designer designer = designerRepository.findOne(id);
        List<Style> styles = designer.getStyles();
        for (Style style : styles) {
            List<StyleSubCategory> subCategorys = style.getSubCategorys();
            for (StyleSubCategory subCategory : subCategorys) {
                subCategory.getStyles().remove(style);
            }
            style.getSubCategorys().clear();
        }

        designerRepository.delete(designer);
    }

    /** 디자이너 정보 수정 **/
    @Transactional
    public Designer modifyDesignerInfo(DesignerDto dto) {
        Designer originDesigner = designerRepository.findOne(dto.getId());

        originDesigner.setName(dto.getName());
        originDesigner.setImg(dto.getImg());
        originDesigner.setContent(dto.getContent());
        originDesigner.setCareer(dto.getCareer());

        List<Style> styles = originDesigner.getStyles();
        for (Style style : styles) {
            List<StyleSubCategory> subCategorys = style.getSubCategorys();
            for (StyleSubCategory subCategory : subCategorys) {
                subCategory.getStyles().remove(style);
            }
            style.getSubCategorys().clear();
            styleRepository.delete(style);
        }
        originDesigner.getStyles().clear();

        if (dto.getStyles().size() > 0) {
            for (StyleDto styleDto : dto.getStyles()) {
                StyleSubCategory subCategory1 = subCategoryRepository.findByName(styleDto.getCategory1());
                StyleSubCategory subCategory2 = subCategoryRepository.findByName(styleDto.getCategory2());

                Style style = new Style();
                style.setImgUrl(styleDto.getImgUrl());
                style.setDesigner(originDesigner);
                style.getSubCategorys().add(subCategory1);
                style.getSubCategorys().add(subCategory2);
                subCategory1.getStyles().add(style);
                subCategory2.getStyles().add(style);
                originDesigner.getStyles().add(style);

                styleRepository.save(style);
            }
        }
        return originDesigner;
    }

    public Designer findById(String selectedId) {
        long id = Long.parseLong(selectedId);
        return designerRepository.findOne(id);
    }

    public Designer findOne(String kakaoId) {
        return designerRepository.findByKakao(kakaoId);
    }

    /** 디자이너 이름 조회 **/
    public List<Designer> findByName(String name) {
        return designerRepository.findByName(name);
    }

    /** 디자이너 전체 조회 **/
    public List<Designer> findAll() {
        return designerRepository.findAll();
    }

    //---------------------------------------------------
    //패아장
    /** 디자이너 페이징 전체 조회 **/
    public List<DesignerDto> findPageAll(int offset, int limit) {
        List<Designer> pageAll = designerRepository.findPageAll(offset, limit);
        //dto로 변경
        List<DesignerDto> pageDtoList = pageAll.stream()
                .map(d -> new DesignerDto(d.getId(), d.getName(), d.getImg(), d.getContent(), d.getCareer())).toList();
        return pageDtoList;
    }

    /** 전체 카운트 쿼리 **/
    public long countQueryAll() {
        return designerRepository.countQueryAll();
    }

    /** 디자이너 페이징 이름 조회 **/
    public List<DesignerDto> findPageByName(String name, int offset, int limit) {
        List<Designer> pageByName = designerRepository.findPageByName(name, offset, limit);
        List<DesignerDto> list = pageByName.stream()
                .map(d -> new DesignerDto(d.getId(), d.getName(), d.getImg(), d.getContent(), d.getCareer())).toList();
        return list;
    }

    /** 이름별 카운트 쿼리 **/
    public long countQueryByName(String name) {
        return designerRepository.countQueryByName(name);
    }
}
