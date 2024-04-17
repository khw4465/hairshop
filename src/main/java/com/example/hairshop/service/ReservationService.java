package com.example.hairshop.service;

import com.example.hairshop.domain.*;
import com.example.hairshop.dto.ReservationDto;
import com.example.hairshop.dto.ReservationForm;
import com.example.hairshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final DesignerRepository designerRepository;
    private final ScheduleRepository scheduleRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public ReservationDto create(ReservationForm form) {
        //유저에 전화번호 저장
        long userId = Long.parseLong(form.getUserId());
        User user = userRepository.findOne(userId);
        user.setPhoneNum(form.getPhoneNum());

        //해당 디자이너 찾기
        long designerId = Long.parseLong(form.getDesignerId());
        Designer designer = designerRepository.findOne(designerId);

        //샵에서 운영시간 가져오기
        Shop shop = designer.getShop();
        LocalTime openTime = shop.getOpenTime();
        LocalTime closeTime = shop.getCloseTime();

        //운영시간 30분단위로 나누기
        String[] timeSlots = Schedule.getTimeSlots(openTime, closeTime);
        //받아온 시간이 몇번째 인덱스인지 확인
        int index = Arrays.asList(timeSlots).indexOf(form.getTime());

        //해당 디자이너와 날짜로 스케쥴 객체를 찾고 없으면 생성
        LocalDate date = LocalDate.parse(form.getDate());
        LocalTime time = LocalTime.parse(form.getTime());
        Optional<Schedule> findSchedule = scheduleRepository.findSchedule(designerId, date);
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

        //예약 생성
        Reservation reservation = new Reservation();
        reservation.setContent(form.getRequest());

        //주문한 메뉴 찾기
        long menuId = Long.parseLong(form.getMenuId());
        Menu menu = menuRepository.findById(menuId);

        //예약 일시
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        reservation.setDateTime(dateTime);
        reservation.setPrice(form.getPrice());
        reservation.setStatus(Status.Reservation); //예약상태
        reservation.setUser(user);
        reservation.setDesigner(designer);
        reservation.setShop(shop);
        reservation.setMenu(menu);
        reservationRepository.save(reservation);

        //연관관계 맺어주기
        user.getReservations().add(reservation);
        designer.getReservations().add(reservation);
        shop.getReservations().add(reservation);
        menu.getReservations().add(reservation);

        return new ReservationDto(reservation);
    }
}
