package com.example.hairshop.service;

import com.example.hairshop.domain.*;
import com.example.hairshop.dto.ReservationDto;
import com.example.hairshop.dto.ReservationForm;
import com.example.hairshop.repository.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final DesignerRepository designerRepository;
    private final ScheduleRepository scheduleRepository;
    private final MenuRepository menuRepository;
    private final EntityManager em;

    @Transactional
    public ReservationDto create(ReservationForm form) {
        //유저에 전화번호 저장
        long userId = Long.parseLong(form.getUserId());
        User user = userRepository.findOne(userId);
        user.setPhoneNum(form.getPhoneNum());

        //해당 디자이너 찾기
        long designerId = Long.parseLong(form.getDesignerId());
        Designer designer = designerRepository.findOne(designerId);

        //운영시간 30분단위로 나누기
        Shop shop = designer.getShop();
        String[] timeSlots = Schedule.getTimeSlots(shop);
        //받아온 시간이 몇번째 인덱스인지 확인
        int index = Arrays.asList(timeSlots).indexOf(form.getTime());

        //해당 디자이너와 날짜로 스케쥴 객체를 찾고 없으면 생성
        LocalDate date = LocalDate.parse(form.getDate());
        LocalTime time = LocalTime.parse(form.getTime());
        Optional<Schedule> findSchedule = scheduleRepository.findSchedule(designerId, date);
        if (findSchedule.isEmpty()) {
            Schedule schedule = new Schedule(date, timeSlots.length);
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
        reservation.setStatus(Status.예약완료); //예약상태
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

    /** 예약 조회 **/
    public ReservationDto findById(String reservationId) {
        long id = Long.parseLong(reservationId);
        Reservation reservation = reservationRepository.findOne(id);
        ReservationDto dto = new ReservationDto(reservation);
        return dto;
    }

    /** 유저 예약 리스트 조회 (페이징) **/
    public List<ReservationDto> findByUserId(Long userId, String statusName, int offset, int limit) {
        Status status = Status.valueOf(statusName);
        List<Reservation> list = reservationRepository.findByUserId(userId, status, offset, limit);
        List<ReservationDto> result = list.stream().map(ReservationDto::new).toList();
        return result;
    }
    /** 카운트쿼리 **/
    public Long countQueryByUserId(Long userId, String statusName) {
        Status status = Status.valueOf(statusName);
        return reservationRepository.countQueryByUserId(userId, status);
    }

    /** 매장 아이디로 조회 (예약중) **/
    public List<ReservationDto> findByShopId(Long id, Status status) {
        List<Reservation> reservations = reservationRepository.findByShopId(id, status);
        List<ReservationDto> list = reservations.stream().map(ReservationDto::new).toList();
        return list;
    }

    /** 매장 아이디 & 디자이너 아이디로 조회 (예약중) **/
    public List<ReservationDto> findByShopAndDesigner(long id1, long id2, Status status) {
        List<Reservation> reservations = reservationRepository.findByShopAndDesigner(id1, id2, status);
        List<ReservationDto> list = reservations.stream().map(ReservationDto::new).toList();
        return list;
    }

    /** 예약 취소 **/
    @Transactional
    public ReservationDto changeCancel(String reservationId) {
        //기존 예약 찾아서 상태 변경
        long id = Long.parseLong(reservationId);
        Reservation reservation = reservationRepository.findOne(id);
        reservation.setStatus(Status.예약취소);

        Designer designer = reservation.getDesigner();
        Long designerId = designer.getId();

        //예약 일시로 스케쥴객체 찾기
        LocalDateTime dateTime = reservation.getDateTime();
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        Schedule schedule = scheduleRepository.findSchedule(designerId, date).get();

        //운영시간 30분단위로 나누기
        Shop shop = reservation.getShop();
        String[] timeSlots = Schedule.getTimeSlots(shop);

        //받아온 시간이 몇번째 인덱스인지 확인
        int index = Arrays.asList(timeSlots).indexOf(time.toString());

        //스케쥴 객체에서 해당 시간 false로 변경
        boolean[] timeArray = schedule.getTime();
        timeArray[index] = false;

        //연관관계 제거
        designer.getReservations().remove(reservation);
        shop.getReservations().remove(reservation);
        reservation.getMenu().getReservations().remove(reservation);

        return new ReservationDto(reservation);
    }

    /** 예약 시간이 지나면 자동으로 시술 완료 변경 **/
    @Scheduled(fixedDelay = 1000*60*30)
    @Transactional
    public void updateComplete() {
        List<Reservation> all = reservationRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Reservation reservation : all) {
            if (reservation.getStatus() == Status.예약완료 && reservation.getDateTime().isBefore(now)) {
                reservation.setStatus(Status.시술완료);
            }
        }
    }

    /** 매 자정마다 지나간 날짜의 스케쥴객체 삭제(보류) **/
//    @Scheduled(cron = "0 0 0 * * *")
//    @Transactional
//    public void deleteSchedule() {
//        LocalDate now = LocalDate.now();
//        List<Schedule> all = scheduleRepository.findAll();
//        for (Schedule schedule : all) {
//            if(schedule.getDate().isBefore(now)) {
//                schedule.getDesigner().getSchedules().remove(schedule);
//                scheduleRepository.remove(schedule);
//            };
//        }
//    }
}
