/*global kakao*/ 
import React, { useEffect } from 'react'

const Map=()=>{

  useEffect(()=>{
    var container = document.getElementById('map');
    var options = {
      center: new kakao.maps.LatLng(37.54699, 127.09598), //지도 중심좌표
      level: 4
    };
    var map = new kakao.maps.Map(container, options);

    var imageSrc = 'https://i.imgur.com/2ZqGXEZ.png',
    imageSize = new kakao.maps.Size(52, 63), //마커 이미지 크기
    imageOption = {offset: new kakao.maps.Point(27, 69)}; //마커의 이미지 안에서의 좌표

    //마커 이미지 생성
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
    markerPosition = new kakao.maps.LatLng(37.54699, 127.09598); //마커 표시 위치

    //마커 생성
    var marker = new kakao.maps.Marker({
      position: markerPosition,
      image: markerImage
    });

    marker.setMap(map);
  }, [])

  return (
      <div>
        <div id="map" style={{width:"400px", height:"350px"}}></div> 
      </div>
  )
}

export default Map;