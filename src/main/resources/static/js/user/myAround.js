//------------------------------------------------------------------------------------
//지도 생성
let mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.5642135, 127.0016985), // 지도의 중심좌표
        level: 4 // 지도의 확대 레벨
    };

let map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

//------------------------------------------------------------------------------------
//현재 위치 조회
// HTML5의 geolocation으로 사용할 수 있는지 확인합니다
if (navigator.geolocation) {

    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    navigator.geolocation.getCurrentPosition(function(position) {

        let lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도

        let locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
            message = '<div style="padding:5px;">현재 내 위치</div>'; // 인포윈도우에 표시될 내용입니다

        // 마커와 인포윈도우를 표시합니다
        displayMarker(locPosition, message);

        // 지도 중심좌표를 접속위치로 변경합니다
        map.setCenter(locPosition);
    });
}

// 지도에 마커와 인포윈도우를 표시하는 함수입니다
function displayMarker(locPosition, message) {

    // 마커를 생성합니다
    let marker = new kakao.maps.Marker({
        map: map,
        position: locPosition
    });

    let iwContent = message, // 인포윈도우에 표시할 내용
        iwRemoveable = true;

    // 인포윈도우를 생성합니다
    let infowindow = new kakao.maps.InfoWindow({
        content : iwContent,
        removable : iwRemoveable
    });

    // 인포윈도우를 마커위에 표시합니다
    infowindow.open(map, marker);
}

//------------------------------------------------------------------------------------
//현재 지도화면 정보 가져와서 핀 찍기
// 지도가 이동, 확대, 축소로 인해 지도영역이 변경되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'idle', function() {

    // 지도 영역정보를 얻어옵니다
    let bounds = map.getBounds();

    // 영역정보의 남서쪽 정보를 얻어옵니다
    let swLatlng = bounds.getSouthWest();

    // 영역정보의 북동쪽 정보를 얻어옵니다
    let neLatlng = bounds.getNorthEast();

    let areaDto = {
        swLat: swLatlng.getLat(),
        swLng: swLatlng.getLng(),
        neLat: neLatlng.getLat(),
        neLng: neLatlng.getLng()
    };

    $.ajax({
        url: "/myAround/newArea",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(areaDto),
        success: function(response) {
            createMarker(response)
        },
        error: function(xhr) {

        }
    });
});

function createMarker(element) {
    let positions = []

    element.forEach(shop => {
        let shopName = shop.shopName
        let lat = shop.x;
        let lon = shop.y;

        positions.push(
            {
                title: shopName,
                latlng: new kakao.maps.LatLng(lat + 0.003, lon)
            }
        )
    })
    let infowindows = [];

    for (let i = 0; i < positions.length; i ++) {

        // 마커를 생성합니다
        let marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: positions[i].latlng, // 마커를 표시할 위치
        });

        // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
        let iwContent = `<div style="padding:5px;">${positions[i].title}</div>`, // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

        // 인포윈도우를 생성합니다
        let infowindow = new kakao.maps.InfoWindow({
            content : iwContent,
            removable : iwRemoveable
        });
        infowindows.push(infowindow)

        // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, 'click', function() {
            // 모든 인포윈도우를 닫습니다
            closeAllInfowindows();
            // 마커 위에 인포윈도우를 표시합니다
            infowindow.open(map, marker);
        });
    }
    function closeAllInfowindows() {
        for (let i = 0; i < infowindows.length; i++) {
            infowindows[i].close();
        }
    }
}