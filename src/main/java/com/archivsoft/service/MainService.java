package com.archivsoft.service;

//import com.archivsoft.domain.entity.MainEntity;
//import com.archivsoft.domain.repository.MainRepository;
//import com.archivsoft.dto.MainDto;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MainService {

//    private MainRepository mainRepository;
//
//    @Transactional
//    public MainDto getPost(Long id){
//        Optional<MainEntity> mainEntityWrapper = mainRepository.findById(id);
//        MainEntity mainEntity = mainEntityWrapper.get();
//
//        MainDto mainDto = MainDto.builder()
//                .id(mainEntity.getId())
//                .title(mainEntity.getTitle())
//                .content(mainEntity.getContent())
//                .writer(mainEntity.getWriter())
//                .build();
//
//        return mainDto;
//    }
//
//    @Transactional
//    public void deletePost(Long id){
//        mainRepository.deleteById(id);
//    }
//
//    @Transactional
//    public Long savePost(MainDto mainDto){
//        return mainRepository.save(mainDto.toEntity()).getId();
//    }
//
//
//    @Transactional
//    public List<MainDto> getMianList() {
//        List<MainEntity> mainEntities = mainRepository.findAll();
//        List<MainDto> mainDtoList = new ArrayList<>();
//
//        for( MainEntity mainEntity : mainEntities){
//            MainDto mainDto = MainDto.builder()
//                    .id(mainEntity.getId())
//                    .title(mainEntity.getTitle())
//                    .writer(mainEntity.getWriter())
//                    .content(mainEntity.getContent())
//                    .build();
//
//            mainDtoList.add(mainDto);
//        }
//        return mainDtoList;
//    }

//    public Page<MainEntity> getMainList(Pageble pageable){
//        int page = (pageable.getPageNumber() == 0) ? 0 : (pageble.getPageNumber() - 1);
//        pageable = PageRequest.of(page, 10);
//
//        return mainRepository.findAll(pageable);
//    }
}

