/**
 * @type    : function
 * @access  : public
 * @desc    : get방식의 ajax 통신을 통해 요청을 보내는 메소드
 * @param    : url - 요청을 보낼 주소
 * @return  : xhttp
 * @author  : 홍성관
 */
function fncGetData(url) {
  const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
  const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
  const xhttp = new XMLHttpRequest();

  xhttp.open("get", url, true);
  xhttp.setRequestHeader('Content-Type', 'application/json');
  xhttp.setRequestHeader("X-CSRF-TOKEN", csrfToken);
  xhttp.send();

  return xhttp;
}

/**
 * @type    : function
 * @access  : public
 * @desc    : post방식의 ajax 통신을 통해 요청을 보내는 메소드
 * @param    : url - 요청을 보낼 주소
 * @param    : data - 요청에 보낼 데이터
 * @return  : xhttp
 * @author  : 홍성관
 */
function fncPostData(url, data) {
  const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
  const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
  const xhttp = new XMLHttpRequest();

  xhttp.open("post", url, true);
  xhttp.setRequestHeader('Content-Type', 'application/json');
  xhttp.setRequestHeader("X-CSRF-TOKEN", csrfToken);
  xhttp.send(JSON.stringify(data));

  return xhttp;
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 검색조건에 해당하는 매입리스트를 엑셀 파일로 다운 받는 메소드
 * @return  : void
 * @author  : 홍성관
 */
function fncExcelDown(){
  const searchForm = document.getElementById("searchForm");
  searchForm.action="/admin/purchaseList/writerExcel";
  searchForm.submit();
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 검색조건을 초기화 하는 메소드
 * @return  : void
 * @author  : 홍성관
 */
function fncResetSearch(){
  const searchForm = document.getElementById("searchForm");
  const searchDate = searchForm.childNodes[1].childNodes[1];
  const searchCom = searchForm.childNodes[1].childNodes[3];

  searchDate.childNodes[3].childNodes[0].value = "";
  searchDate.childNodes[7].childNodes[0].value = "";
  searchDate.childNodes[11].childNodes[0].value = "";
  searchDate.childNodes[15].childNodes[0].value = "";

  searchCom.childNodes[3].childNodes[0].value = "";
  searchCom.childNodes[7].childNodes[0].value = "All";
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 지급관리 팝업창을 닫는 메소드
 * @return  : void
 * @author  : 홍성관
 */
function fncPaymentModalClose() {
  document.getElementById("paymentModal").style.display = "none";
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 지급관리 팝업창을 여는 메소드
 * @param    : approveNum - 지급관리 대상의 등록번호
 * @return  : void
 * @author  : 홍성관
 */
function fncPaymentModalPopup(approveNum) {
  const paymentModal = document.getElementById("paymentModal");
  const inputModal = paymentModal.childNodes[3].childNodes[1].childNodes[1];
  inputModal.childNodes[1].childNodes[3].childNodes[0].value = "Y";
  inputModal.childNodes[3].childNodes[3].childNodes[0].value = "";
  inputModal.childNodes[5].childNodes[1].childNodes[0].value = approveNum;
  paymentModal.style.display = "block";
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 지급여부를 결정하는 요청을 보내는 메소드
 * @return  : void
 * @author  : 홍성관
 */
function fncPaymentUpdate() {
  const paymentModal = document.getElementById("paymentModal");
  const inputModal = paymentModal.childNodes[3].childNodes[1].childNodes[1];
  const approveNum = inputModal.childNodes[5].childNodes[1].childNodes[0].value;
  const paymentYN = inputModal.childNodes[1].childNodes[3].childNodes[0].value;
  const paymentDt = inputModal.childNodes[3].childNodes[3].childNodes[0].value;
  const xhttp = fncGetData("/savePurchase?approveNum="+approveNum+"&paymentDt="+paymentDt+"&paymentYN="+paymentYN);

  xhttp.onreadystatechange = function (error) {
    if (xhttp.readyState == XMLHttpRequest.DONE) {
      alert("지급여부가 입력되었습니다.")
      fncPaymentModalClose();
    }
  };
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 페이지
 * @param    : page - 요청을 보낼 페이지
 * @return  : void
 * @author  : 홍성관
 */
function fncSumitForm(page){
  const searchForm = document.getElementById("searchForm");
  searchForm.childNodes[2].value = page;
  searchForm.action="/admin/purchaseList/"+page;
  searchForm.submit();
}

