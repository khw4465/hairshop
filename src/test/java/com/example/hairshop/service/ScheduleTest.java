package com.example.hairshop.service;

import com.example.hairshop.domain.Designer;
import com.example.hairshop.domain.Schedule;
import com.example.hairshop.domain.Shop;
import com.example.hairshop.dto.DesignerDto;
import com.example.hairshop.dto.ScheduleDto;
import com.example.hairshop.repository.DesignerRepository;
import com.example.hairshop.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class ScheduleTest {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    DesignerRepository designerRepository;

    @Test
    @Rollback(false)
    public void 스케쥴생성() {
        Designer designer = designerRepository.findOne(2L);
        DesignerDto dto = new DesignerDto(designer.getId(), designer.getName(), designer.getImg(), designer.getContent(), designer.getCareer());
        System.out.println("dto = " + dto);

        //샵에서 운영시간 가져오기
        Shop shop = designer.getShop();
        LocalTime openTime = shop.getOpenTime();
        LocalTime closeTime = shop.getCloseTime();

        //운영시간 30분단위로 나누기
        String[] timeSlots = Schedule.getTimeSlots(openTime, closeTime);
        //받아온 시간이 몇번째 인덱스인지 확인
        int index = Arrays.asList(timeSlots).indexOf("10:00");

        //해당 디자이너와 날짜로 스케쥴 객체를 찾고 없으면 생성
        LocalDate date = LocalDate.parse("2024-04-19");
        LocalTime time = LocalTime.parse("10:30");
        Optional<Schedule> findSchedule = scheduleRepository.findSchedule(designer.getId(), date);
        if (findSchedule.isEmpty()) {
            Schedule schedule = new Schedule(date);
            schedule.getReserve(index, timeSlots.length);
            schedule.setDesigner(designer);
            scheduleRepository.create(schedule);
            designer.getSchedules().add(schedule);
        } else { //스케쥴 객체가 있으면 해당 시간대 true로 변경
            Schedule schedule = findSchedule.get();
            schedule.getReserve(index, timeSlots.length);
        }
    }

    @Test
    public void 스케쥴조회() {
        Designer designer = designerRepository.findOne(2L);
        DesignerDto dto = new DesignerDto(designer.getId(), designer.getName(), designer.getImg(), designer.getContent(), designer.getCareer());
        System.out.println("dto = " + dto);

        List<Schedule> allScheduleByDesigner = scheduleRepository.findAllScheduleByDesigner(designer.getId());
        List<ScheduleDto> list = allScheduleByDesigner.stream().map(ScheduleDto::new).toList();
        for (ScheduleDto scheduleDto : list) {
            System.out.println("scheduleDto = " + scheduleDto);
        }
    }

    @Test
    @Rollback(false)
    public void 스케쥴삭제() {
        Designer designer = designerRepository.findOne(2L);

        LocalDate date = LocalDate.parse("2024-04-19");
        String string = LocalTime.parse("10:00").toString();

        Schedule schedule = scheduleRepository.findSchedule(designer.getId(), date).get();

        //샵에서 운영시간 가져오기
        Shop shop = designer.getShop();
        LocalTime openTime = shop.getOpenTime();
        LocalTime closeTime = shop.getCloseTime();

        System.out.println("openTime = " + openTime);
        System.out.println("closeTime = " + closeTime);

        //운영시간 30분단위로 나누기
        String[] timeSlots = Schedule.getTimeSlots(openTime, closeTime);
        System.out.println("timeSlots = " + Arrays.toString(timeSlots));
        //받아온 시간이 몇번째 인덱스인지 확인
        int index = Arrays.asList(timeSlots).indexOf(string);
        System.out.println("index = " + index);

        //스케쥴 객체에서 해당 시간 false로 변경
        boolean[] time = schedule.getTime();
        time[index] = false;
    }
}
