let slideIndex = 0; // 이미지의 인덱스를 추적하는 변수

// 이미지 슬라이드 함수
function moveSlide(n) {
    let imgs = shopImgs;
    let imgElement = document.querySelector(".mainImg");
    slideIndex += n; // 인덱스 증가 또는 감소

    // 인덱스가 배열 범위를 벗어나면 처음 또는 마지막 이미지로 이동
    if (slideIndex >= imgs.length) {
        slideIndex = 0;
    } else if (slideIndex < 0) {
        slideIndex = imgs.length - 1;
    }

    // 새 이미지 설정
    imgElement.src = imgs[slideIndex];
    imgElement.style.left = (-slideIndex * 100) + "%";
}

function submitForm(element) {
    element.closest('form').submit();
}