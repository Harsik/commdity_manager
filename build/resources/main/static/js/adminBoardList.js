// /*
// // Get the modal
// const modal = document.getElementById("myModal");
// // Get the <span> element that closes the modal
// const span = document.getElementsByClassName("close")[0];
//
// // When the user clicks on <span> (x), close the modal
// span.onclick = function() {
//   modal.style.display = "none";
// };
//
// // When the user clicks anywhere outside of the modal, close it
// window.onclick = function(event) {
//   if (event.target == modal) {
//     modal.style.display = "none";
//   }
// };
//
// function modal_popup(){
//  modal.style.display = "block"
// }
// */
//
// /**
//  * Request Ajax
//  * */
//
// const token = document.querySelector("meta[name='_csrf']").content;
// const header = document.querySelector("meta[name='_csrf_header']").content;
//
//
// function createBoard(){
//
//   let productLists = "";
//   let userIdx = document.getElementById("selectbox_chk").value;
//
//   let CarryoutDt = document.getElementById("carryoutdt").value; //반출일
//   let CarryinDt = document.getElementById("carryindt").value;  //반입 예정일
//   let usePlace = document.getElementById("useplace").value; //사용처
//   let useYN = document.querySelectorAll('input[type="radio"]:checked')[0].value;  //반출 위치 : (내부,외부) useYN값이 True이면 내부 False면 외부
//
//   let ischeckbox = document.querySelectorAll('input[type="checkbox"]:checked');
//   let productidx = "";
//   let productstatus = (useYN == 'Y') ? '미사용' : '사용중';
//
//   let resultUseYN = useYN == 'Y' ? '반입' : '미반입';
//   var xhttp = new XMLHttpRequest();
//   const xhr = new XMLHttpRequest();
//
//   /!*체크박스 값 List에 담기*!/
//   for (var i = 0; i < ischeckbox.length; i++) {
//
//     productLists += ischeckbox[i].value;
//     //마지막에 , 추가 (x)
//     if (i != ischeckbox.length - 1) {
//       productLists += ',';
//     }
//   }
//
//   const jdata = {
//     "productIdList": productLists,
//     "boardUserIdx": userIdx,
//     "boardCarryoutDt": CarryoutDt,
//     "boardCarryinDt": CarryinDt,
//     "boardUsePlace": useYN == "N" ? "본사" : usePlace,  /*사용처 저장 내부(N) = 본사 : 외부(Y) = usePlace*/
//     "boardCarryYN": resultUseYN /*사실 아직 잘모르겠음*/
//   };
//
//   xhttp.onreadystatechange = function (error) {
//     console.log(error);
//   };
//
//   xhr.open('POST', '/getBoard', true);
//   xhr.setRequestHeader('Content-type', 'application/json');
//   xhr.setRequestHeader(header, token);
//
//   xhr.onload = function () {
//     if (xhr.status == 200) {
//       alert("등록 성공");
//       window.location.href="http://localhost:8080/boardList";
//     }else{
//       alert("등록 실패");
//     }
//   }
//   xhr.send(JSON.stringify(jdata));
//
// }
//
//
//
// function updateBoard() {
//
//   let productLists = new Array();
//   let userIdx = document.getElementById("selectbox_chk").value;
//
//   let CarryoutDt = document.getElementById("carryoutdt").value; //반출일
//   let CarryinDt = document.getElementById("carryindt").value;  //반입 예정일
//   let usePlace = document.getElementById("useplace").value; //사용처
//   let useYN = document.querySelectorAll('input[type="radio"]:checked')[0].value;  //반출여부 (내부,외부)
//   let ischeckbox = document.querySelectorAll('input[type="checkbox"]:checked');
//
//   let productidx = "";
//   let productstatus = (useYN == 'Y') ? '미사용' : '사용중';
//   let resultUseYN = useYN == 'Y' ? '반입' : '미반입';
//   var xhttp = new XMLHttpRequest();
//   const xhr = new XMLHttpRequest();
//
//   /!*체크박스 값 List에 담기*!/
//   for (var i = 0; i < ischeckbox.length; i++) {
//     productLists += ischeckbox[i].value;
//     //마지막에 , 추가 (x)
//     if (i != ischeckbox.length - 1) {
//       productLists += ',';
//     }
//   }
//
//   const jdata = {
//     "productIdList": productLists,
//     "boardUserIdx": userIdx,
//     "boardCarryoutDt": CarryoutDt,
//     "boardCarryinDt": CarryinDt,
//     "boardUsePlace": useYN == "N" ? "본사" : usePlace,  /*사용처 저장 내부(N) = 본사 : 외부(Y) = usePlace*/
//     "boardCarryYN": resultUseYN
//   };
//
//   xhttp.onreadystatechange = function (error) {
//     console.log(error);
//   };
//
//   xhr.open('POST', '/updateBoard', true);
//   xhr.setRequestHeader('Content-type', 'application/json');
//   xhr.setRequestHeader(header, token);
//
//   xhr.onload = function () {
//     if (xhr.status == 200) {
//       alert("업데이트 성공");
//       window.location.href = "http://localhost:8080/updateBoard";
//     }else{
//       alert("업데이트 실패");
//
//     }
//   }
//
//   xhr.send(JSON.stringify(jdata));
//
// }
//
//
// function deleteBoard() {
//
//   let bfrm = document.boardUserIdx;
//   bfrm.action = "/deleteBoardList";
//   bfrm.submit();
//
// }
//
// function checkbox_btn() {
//
//   let getOtherProductIdList = document.querySelector("#otherProductIdList").value.split(',');
//   let getProductLen = document.querySelectorAll(".product_chk").length;
//
//   for (let j = 0; j < getProductLen; j++) {
//     for (let i = 0; i < getOtherProductIdList.length; i++) {
//       if (document.querySelectorAll(".product_chk").item(j).value == getOtherProductIdList[i])
//         document.querySelectorAll(".product_chk").item(i).attributes("disabled", true);
//     }
//   }
//
// }
//
// document.addEventListener("DOMContentLoaded", function () {
//
//   //선택된 상품 리스트 가져와 체크값 true 변경해주는 작업
//   let pIdList = document.querySelector('#productIdList').value.split(',');
//   let pItemList = document.querySelectorAll(".product_chk").length;
//
//   for (let j = 0; j < pIdList.length; j++) {
//     for (let i = 0; i < pItemList; i++) {
//       if (document.querySelectorAll(".product_chk").item(i).value == pIdList[j]) {
//         document.querySelectorAll(".product_chk").item(i).checked = true;
//       }
//
//     }
//   }
//
// });
