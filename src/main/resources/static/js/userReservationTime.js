// 오늘부터 2주 동안의 날짜를 구하는 함수
function getTwoWeeksDates() {
    const dates = [];
    const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토']; // 요일 표시를 위한 배열
    const today = new Date();
    for (let i = 0; i < 14; i++) {
        const date = new Date(today.getTime() + i * 24 * 60 * 60 * 1000);
        const dayOfMonth = date.getDate().toString().padStart(2, '0');
        const dayOfWeek = daysOfWeek[date.getDay()]; // 요일 가져오기
        dates.push({ date, dayOfMonth, dayOfWeek }); // 일자와 요일을 객체로 저장
    }
    return dates;
}

// 화면에 날짜를 표시하는 함수
function renderDates() {
    const dateContainer = document.getElementById('selectDate');
    const dates = getTwoWeeksDates(); // 날짜
    dates.forEach(({ date, dayOfMonth, dayOfWeek }) => {
        const dateInput = document.createElement('input');
        dateInput.setAttribute("id", `radio_day_${dayOfMonth}`)
        dateInput.type = 'radio';
        dateInput.classList.add('dateInput');
        dateInput.name = 'selectedDate';
        dateInput.value = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
        dateContainer.appendChild(dateInput);

        const dateLabel = document.createElement('label');
        dateLabel.classList.add('dateLabel');
        dateLabel.setAttribute('for', `radio_day_${dayOfMonth}`);
        dateLabel.setAttribute('onclick', 'renderTimeSlots(this)');
        const yoil = document.createElement("p");
        yoil.textContent = dayOfWeek;
        const dates = document.createElement("p");
        dates.textContent = dayOfMonth;
        dateLabel.appendChild(yoil);
        dateLabel.appendChild(dates);
        dateContainer.appendChild(dateLabel);
    });
}

// 시간 배열 표시함수
function generateTimeArray(startTime, endTime) {
    const timeArray = [];
    for (let hour = startTime; hour <= endTime; hour++) {
        for (let minute = 0; minute < 60; minute += 30) { // 30분 단위로 반복
            const timeString = `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`;
            timeArray.push(timeString);
        }
    }
    return timeArray;
}

function renderTimeSlots(element) {
    //기존 시간선택창 지우기
    const timeSlotList = document.getElementById('timeSlotList');
    while (timeSlotList.firstChild) {
        timeSlotList.removeChild(timeSlotList.firstChild);
    }

    const startTime = parseInt(shop.openTime.split(':')[0], 10); // 시작 시간
    const endTime = parseInt(shop.closeTime.split(':')[0], 10) - 1; // 종료 시간

    // 시간배열 생성
    const timeArray = generateTimeArray(startTime, endTime);
    let count = 0;

    // 시간배열 루프 돌면서 input, label 생성
    timeArray.forEach(time => {
        const timeSlotIndex = count++;
        const radioInput = document.createElement('input');
        radioInput.setAttribute('id', `time_${timeSlotIndex}`)
        radioInput.setAttribute('class', 'timeSlotInput');
        radioInput.type = 'radio';
        radioInput.name = 'selectedTimeSlot';
        radioInput.value = time;
        timeSlotList.appendChild(radioInput);

        const label = document.createElement('label');
        label.setAttribute('for', `time_${timeSlotIndex}`);
        label.setAttribute('class', 'timeLabel');
        label.innerHTML += time;
        timeSlotList.appendChild(label);
    })

    // 스케쥴에서 날짜 가져오기
    schedules.forEach(schedule => {
        // 클릭한 날짜와 동일하면 true(예약된 날짜)index 가져오기
        if (value === schedule.date) {
            let trueIndex = schedule.time.map((value, index) => value ? index : -1)
                .filter(index => index !== -1);

            if (trueIndex.length >= 1) {
                trueIndex.forEach(index => {
                    let reservedTime = document.getElementById(`time_${index}`);
                    reservedTime.disabled = true;
                    let reservedLabel = document.querySelector(`label[for="time_${index}"]`);
                    reservedLabel.style.border = '1px solid #ccc';
                    reservedLabel.style.color = '#ccc';
                })
            }
        }
    })

}

// 화면에 날짜를 표시
renderDates();


function submitReservation() {
    let selectedDateValue = null;
    const dateInputs = document.querySelectorAll('.dateInput');

    dateInputs.forEach(dateInput => {
        if (dateInput.checked) {
            selectedDateValue = dateInput.value;
        }
    });

    let selectedTimeValue = null;
    const timeInputs = document.querySelectorAll('.timeSlotInput');

    timeInputs.forEach(timeInput => {
        if (timeInput.checked) {
            selectedTimeValue = timeInput.value;
        }
    });

    if (selectedDateValue === null) {
        alert("날짜를 선택해주세요.")
    } else if (selectedTimeValue === null) {
        alert("시간을 선택해주세요.")
    } else {
        let reservationForm = document.getElementById('reservationForm');

        let designerIdInput = document.createElement('input');
        designerIdInput.type = 'hidden';
        designerIdInput.id = 'designerId';
        designerIdInput.name = 'designerId';
        designerIdInput.value = designerId;
        reservationForm.appendChild(designerIdInput);

        let menuIdInput = document.createElement('input');
        menuIdInput.type = 'hidden';
        menuIdInput.id = 'menuId';
        menuIdInput.name = 'menuId';
        menuIdInput.value = menuId;
        reservationForm.appendChild(menuIdInput);

        let selectedDateInput = document.createElement('input');
        selectedDateInput.type = 'hidden';
        selectedDateInput.id = 'selectedDate';
        selectedDateInput.name = 'date';
        selectedDateInput.value = selectedDateValue;
        reservationForm.appendChild(selectedDateInput);

        let selectedTimeInput = document.createElement('input');
        selectedTimeInput.type = 'hidden';
        selectedTimeInput.id = 'selectedTime';
        selectedTimeInput.name = 'time';
        selectedTimeInput.value = selectedTimeValue;
        reservationForm.appendChild(selectedTimeInput);

        reservationForm.submit();
    }
}