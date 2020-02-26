/**
 * @type    : function
 * @param   : productIdList 체크된 상품 리스트를 ","로 구분하여 리스트 값 저장할 변수
 * @param   : boardUserIdx 선택한 사용자 아이디 저장할 변수
 * @param   : boardCarryoutDt 반출일 저장할 변수
 * @param   : boardCarryinDt 사용 위치 저장할 변수
 * @param   : usePlace 반출 위치 내부 : "Y", 외부 : "N" 저장할 변수
 * @param   : useYN 반출 위치 내부 : "Y", 외부 : "N" 저장할 변수
 * @param   : ischeckbox 체크된 상품 리스트 저장할 변수
 * @param   : boardCarryYN 반입 여부 저장할 변수
 * @access  : public
 * @desc    : post방식의 ajax 통신을 통해서 등록정보 보내는 함수
 * @return  : json
 * @author  : 김주찬
 */
function createBoard() {
  const token = document.querySelector("meta[name='_csrf']").content;
  const header = document.querySelector("meta[name='_csrf_header']").content;

  let productIdList = "";
  let boardUserIdx = document.getElementById("selectbox_chk").value;
  let boardCarryoutDt = document.getElementById("carryoutdt").value;
  let boardCarryinDt = document.getElementById("carryindt").value;
  let usePlace = document.getElementById("useplace").value;
  let useYN = document.querySelectorAll('input[type="radio"]:checked')[0].value;
  let ischeckbox = document.querySelectorAll('input[type="checkbox"]:checked');
  let boardCarryYN = useYN == 'Y' ? '반입' : '미반입';

  const xhttp = new XMLHttpRequest();
  const xhr = new XMLHttpRequest();
  /*체크박스 값 productList에 담기*/
  for (var i = 0; i < ischeckbox.length; i++) {
    productIdList += ischeckbox[i].value;
    /*마지막에 , 추가 안하려고 넣은 조건*/
    if (i != ischeckbox.length - 1) {
      productIdList += ',';
    }
  }
  /*게시판 등록 정보를 보낼 객체*/
  const jdata = {
    "productIdList": productIdList,
    "boardUserIdx": boardUserIdx,
    "boardCarryoutDt": boardCarryoutDt,
    "boardCarryinDt": boardCarryinDt,
    "boardUsePlace": useYN == "Y" ? "본사" : usePlace,  /*사용처 저장 내부(N) = 본사 : 외부(Y) = usePlace*/
    "boardCarryYN": boardCarryYN /*사실 아직 잘모르겠음*/
  };
  xhttp.onreadystatechange = function (error) {
    console.log(error);
  };
  xhr.open('POST', '/admin/createBoard', true);
  xhr.setRequestHeader('Content-type', 'application/json');
  xhr.setRequestHeader(header, token);

  xhr.onload = function () {
    if (xhr.status == 200) {
      alert("등록 성공");
      window.location.href = "/admin/boardList";
    } else {
      alert("등록 실패");
    }
  }
  xhr.send(JSON.stringify(jdata));

}

/**
 * @type    : function
 * @param   : productIdList 체크된 상품 리스트를 ","로 구분하여 리스트 값 저장할 변수
 * @param   : boardUserIdx 선택한 사용자 아이디 저장할 변수
 * @param   : boardCarryoutDt 반출일 저장할 변수
 * @param   : boardCarryinDt 사용 위치 저장할 변수
 * @param   : usePlace 반출 위치 내부 : "Y", 외부 : "N" 저장할 변수
 * @param   : useYN 반출 위치 내부 : "Y", 외부 : "N" 저장할 변수
 * @param   : ischeckbox 체크된 상품 리스트 저장할 변수
 * @param   : boardCarryYN 반입 여부 저장할 변수
 * @access  : public
 * @desc    : post방식의 ajax 통신을 통해서 수정정보 보내는 함수
 * @return  : json
 * @author  : 김주찬
 */
function updateBoard() {
  const token = document.querySelector("meta[name='_csrf']").content;
  const header = document.querySelector("meta[name='_csrf_header']").content;

  let productLists = "";
  let userIdx = document.getElementById("selectbox_chk").value;
  let CarryoutDt = document.getElementById("carryoutdt").value;
  let CarryinDt = document.getElementById("carryindt").value;
  let usePlace = document.getElementById("useplace").value;
  let useYN = document.querySelectorAll('input[type="radio"]:checked')[0].value;
  let ischeckbox = document.querySelectorAll('input[type="checkbox"]:checked');
  let resultUseYN = useYN == 'Y' ? '반입' : '미반입';

  const xhttp = new XMLHttpRequest();
  const xhr = new XMLHttpRequest();
  /*체크박스 값 productList에 담기*/
  for (var i = 0; i < ischeckbox.length; i++) {
    productLists += ischeckbox[i].value;
    /*마지막에 , 추가 안하려고 추가한 조건 */
    if (i != ischeckbox.length - 1) {
      productLists += ',';
    }
  }
  /*게시판 등록 정보를 보낼 객체*/
  const jdata = {
    "productIdList": productLists,
    "boardUserIdx": userIdx,
    "boardCarryoutDt": CarryoutDt,
    "boardCarryinDt": CarryinDt,
    "boardUsePlace": useYN == "N" ? "본사" : usePlace,  /*사용처 저장 내부(N) = 본사 : 외부(Y) = usePlace*/
    "boardCarryYN": resultUseYN
  };
  /*해당 URL에 데이터 전송*/
  xhttp.onreadystatechange = function (error) {
    console.log(error);
  };
  xhr.open('POST', '/admin/updateBoard', true);
  xhr.setRequestHeader('Content-type', 'application/json');
  xhr.setRequestHeader(header, token);

  xhr.onload = function () {
    if (xhr.status == 200) {
      alert("업데이트 성공");
      window.location.href = "/admin/boardList";
    } else {
      alert("업데이트 실패");
    }
  }
  xhr.send(JSON.stringify(jdata));
}

/**
 * @type    : function
 * @access  : public
 * @desc    : form 태그를 통해서 GET방식으로 삭제할 해당 게시판 BoardIdx 정보 전달 메소드
 * @return  : form type
 * @author  : 김주찬
 */
function deleteBoard() {
  /*form 태그를 통해서 삭제에 필요한 BoardIdx 정보 전달*/
  let deleteInfoForm = document.boardUserIdx;
  deleteInfoForm.action = "/admin/deleteBoard";
  deleteInfoForm.submit();
}

// function checkboxBtn() {
//   /**/
//   let getOtherProductIdList = document.querySelector("#otherProductIdList").value.split(',');
//   let getProductLen = document.querySelectorAll(".product_chk").length;
//   /*s*/
//   for (let j = 0; j < getProductLen; j++) {
//     for (let i = 0; i < getOtherProductIdList.length; i++) {
//       if (document.querySelectorAll(".product_chk").item(j).value == getOtherProductIdList[i])
//         document.querySelectorAll(".product_chk").item(i).attributes("disabled", true);
//     }
//   }
// }

/**
 * @type    : function
 * @access  : public
 * @desc    : 초기 화면 시작시 선택된 상품 리스트만 가져와 체크 값 true 변경해주는 작업
 * @author  : 김주찬
 */
document.addEventListener("DOMContentLoaded", function () {
  /*선택된 상품 리스트 "," 제거하여 각 리스트에 저장 및 길이 저장할 변수*/
  let pIdList = document.querySelector('#productIdList').value.split(',');
  let pItemList = document.querySelectorAll(".product_chk").length;
  let btnUseYN = document.querySelector("#btnUseYN").value;

  if (btnUseYN == "Y") {
    document.getElementById("createBoard").style.setProperty("display", "none");
  } else {
    document.getElementById("updateBoard").style.setProperty("display", "none");
    document.getElementById("deleteBoard").style.setProperty("display", "none");
  }

  /*선택된 상품 리스트 가져와 체크값 True 변경해주는 작업*/
  for (let j = 0; j < pIdList.length; j++) {
    for (let i = 0; i < pItemList; i++) {
      /*선택된 값이 체크 박스 리스트 값과 일치하면 체크 상태 True로 변경*/
      if (document.querySelectorAll(".product_chk").item(i).value == pIdList[j]) {
        document.querySelectorAll(".product_chk").item(i).checked = true;
      }
    }
  }

});
