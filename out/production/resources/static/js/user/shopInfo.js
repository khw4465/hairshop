// 현재 보이는 슬라이드의 인덱스
let currentSlide = 0;
// 이미지 목록
const slides = document.querySelectorAll('#shopImg img');

// 이전 또는 다음 슬라이드로 이동하는 함수
function moveSlide(n) {
    currentSlide += n;
    // 마지막 슬라이드에서 다음 버튼을 클릭하면 첫 번째 슬라이드로 이동
    if (currentSlide >= slides.length) {
        currentSlide = 0;
    }
    // 첫 번째 슬라이드에서 이전 버튼을 클릭하면 마지막 슬라이드로 이동
    if (currentSlide < 0) {
        currentSlide = slides.length - 1;
    }
    // 모든 슬라이드를 숨김
    slides.forEach(slide => slide.style.display = 'none');
    // 현재 슬라이드만 보이도록 설정
    slides[currentSlide].style.display = 'block';
}