/**=================================================================
 *@FileName       : Sales.js
 *@LastModifyDate : 2020. 2. 10.
 *@LastModifier   : 강병수
 *@LastVersion    : 1.0
 *Change History
 *    2020. 2. 10.    강병수    1.0    최초생성
 ==================================================================*/

/*
 * @type		: function
 * @access	: public
 * @desc		: ajax 방식으로 엑셀파일을 전송한다.
 * @param		: token - HTTP 요청 헤더 이름
 * @param		: header - HTTP 요청 헤더값
 * @param		: xhr - HTTP 요청 할당
 * @return	: XMLHttpRequest
 * @author	: 강병수
 */
function fncAjaxCallExcelUpload(method, url, data) {
  const token = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
  const header = document.querySelector("meta[name='_csrf']").getAttribute("content");
  const xhr = new XMLHttpRequest();

  xhr.open(method, "http://localhost:8080" + url, true);
  xhr.setRequestHeader(token, header);
  xhr.send(data);
  return xhr;
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 엑셀 업로드를 위한 팝업창을 연다.
 * @return	: void
 * @author	: 강병수
 */
function fncOpenExcelUpload() {
  window.open("/salesListUpload", "new", "width=500,height=150");
}

/*
 * @type		: function
 * @access	: public
 * @desc		: ajax 방식으로 엑셀파일을 전송한다.
 * @param		: frm - excelUploadForm 객체를 대입하기 위한 변수
 * @param		: file - 업로드 하려는 엑셀파일의 확장자를 확인하기 위한 변수
 * @param		: data - excelUploadForm의 데이터를 저장하기 위한 변수
 * @param		: xhr -  ajax 통신 후 전송상태를 확인하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncExcelUpload() {
  var frm = document.excelUploadForm;
  var file = document.getElementById("excelfile").value;
  var data = new FormData(frm);
  file = file.slice(file.indexOf(".") + 1).toLowerCase();

  if (file != "xlsx") {
    alert("xlsx파일만 업로드 가능합니다.");
  } else {
    var xhr = fncAjaxCallExcelUpload("POST", "/salesListExcelUpload", data);
    alert("승인번호를 입력하지 않은 항목은 업로드 되지 않습니다.");
    xhr.onreadystatechange = function (error) {
      if (xhr.readyState == XMLHttpRequest.DONE) {
        alert(xhr.responseText);
        window.close();
        window.opener.location.reload();
      }
    };
  }
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 검색조건에 해당하는 목록을 엑셀로 다운로드한다.
 * @param		: frm - searchForm 객체를 대입하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncExcelDown() {
  var frm = document.searchForm;
  frm.action = "/salesListExcelDownload";
  frm.submit();
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 수금여부를 수정하기 위한 팝업창을 연다.
 * @return	: void
 * @author	: 강병수
 */
function fncUpdateInfo(apnum) {
  window.open("/updateInfo?approveNum=" + apnum, "new", "width=700,height=200");
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 수금여부를 변경한다.
 * @param		: frm - submitPayForm 객체를 대입하기 위한 변수
 * @param		: cmdt - 입력한 수금일자를 저장하기 위한 변수
 * @param		: chk - cmdt에 저장된 날짜가 맞는지 확인한 결과를 저장하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncSubmitPay() {
  var frm = document.submitPayForm;
  var cmdt = frm.collectionMoneyDt.value;
  var chk = fncChkdate(cmdt);

  if (chk) {
    frm.action = "/updateCollectionMoney";
    frm.submit();
    alert("변경되었습니다.");
    window.opener.location.reload();
    window.close();
  }
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 입력한 날짜가 형식에 맞는지 확인한다.
 * @param		: chk - 확인 결과를 저장하기 위한 변수
 * @param		: now - 현재 날짜를 저장하기 위한 변수
 * @param		: cmdt - 입력받은 날짜를 전달하는 매개변수
 * @param		: cmday - 매개변수 cmdt 를 날짜형식으로 변환하여 저장하기 위한 변수
 * @return	: boolean
 * @author	: 강병수
 */
function fncChkdate(cmdt) {
  var chk = false;
  var now = new Date();
  var cmday = new Date(cmdt);

  if (cmdt == '') {
    alert("날짜를 입력해주십시오.");
  } else if (now.getTime() < cmday.getTime()) {
    alert("날짜를 정확히 입력해주십시오.");
  } else {
    chk = true;
  }
  return chk;
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 입력한 검색조건으로 목록을 조회한다.
 * @param		: frm - searchForm 객체를 대입하기 위한 변수
 * @param		: fregdt - 등록날짜 범위의 시작을 저장하기 위한 변수
 * @param		: lregdt - 등록날짜 범위의 마지막을 저장하기 위한 변수
 * @param		: fissuedt - 발급날짜 범위의 시작을 저장하기 위한 변수
 * @param		: lissuedt - 발급날짜 범위의 마지막을 저장하기 위한 변수
 * @param		: collectionMoneyYN - 수금여부를 저장하기 위한 변수
 * @param		: companyNM - 상호 명을 저장하기 위한 변수
 * @param		: chk - 입력받은 날짜가 형식에 맞는지 확인한 결과를 저장하기 위한 변수
 * @param		: chkSearchCondition - 입력받은 검색조건이 형식에 맞는지 확인한 결과를 저장하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncSearchList() {
  var frm = document.searchForm;
  var fregdt = frm.firstRegDt.value;
  var lregdt = frm.lastRegDt.value;
  var fissuedt = frm.firstIssueDt.value;
  var lissuedt = frm.lastIssueDt.value;
  var collectionMoneyYN = frm.collectionMoneyYN.value;
  var companyNM = frm.companyNM.value;
  var chk = fncChkday(fregdt, lregdt, fissuedt, lissuedt);
  var chkSearchCondition = fncChkSearchCondition(fregdt, fissuedt, companyNM, collectionMoneyYN);
  if (chk && chkSearchCondition) {
    frm.action = "/salesList";
    frm.submit();
  }
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 입력한 검색조건으로 목록을 조회한다.
 * @param		: frm - searchForm 객체를 대입하기 위한 변수
 * @param		: fregdt - 등록날짜 범위의 시작을 저장하기 위한 변수
 * @param		: fissuedt - 발급날짜 범위의 시작을 저장하기 위한 변수
 * @param		: collectionMoneyYN - 수금여부를 저장하기 위한 변수
 * @param		: companyNM - 상호 명을 저장하기 위한 변수
 * @param		: chk -확인 결과를 저장하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncChkSearchCondition(fregdt, fissuedt, companyNM, collectionMoneyYN) {
  var chk = false;

  if ((fregdt != "" && fissuedt != "" && companyNM != "" && collectionMoneyYN != "T")
    || (fregdt == "" && fissuedt == "" && companyNM == "" && collectionMoneyYN == "T")
    || (fregdt != "" && fissuedt == "" && companyNM == "" && collectionMoneyYN == "T")
    || (fregdt == "" && fissuedt != "" && companyNM == "" && collectionMoneyYN == "T")
    || (fregdt == "" && fissuedt == "" && companyNM != "" && collectionMoneyYN == "T")
    || (fregdt == "" && fissuedt == "" && companyNM == "" && collectionMoneyYN != "T")) {
    chk = true;
  } else {
    alert("검색조건을 하나씩 입력하시거나 모두 입력하시거나 \n 아무것도 입력되지 않을때 검색이 가능합니다.");
  }

  return chk;
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 입력한 날짜들이 형식에 맞는지 확인한다.
 * @param		: chk - 확인 결과를 저장하기 위한 변수
 * @param		: now - 현재 날짜를 저장하기 위한 변수
 * @param		: fregdt - 입력받은 날짜를 전달하는 매개변수
 * @param		: frdt - 매개변수 fregdt 를 날짜형식으로 변환하여 저장하기 위한 변수
 * @param		: lregdt - 입력받은 날짜를 전달하는 매개변수
 * @param		: lrdt - 매개변수 lregdt 를 날짜형식으로 변환하여 저장하기 위한 변수
 * @param		: fissuedt - 입력받은 날짜를 전달하는 매개변수
 * @param		: fidt - 매개변수 fissuedt 를 날짜형식으로 변환하여 저장하기 위한 변수
 * @param		: lissuedt - 입력받은 날짜를 전달하는 매개변수
 * @param		: lidt - 매개변수 lissuedt 를 날짜형식으로 변환하여 저장하기 위한 변수
 * @return	: boolean
 * @author	: 강병수
 */
function fncChkday(fregdt, lregdt, fissuedt, lissuedt) {
  var chk = false;
  var now = new Date();
  var frdt = new Date(fregdt);
  var lrdt = new Date(lregdt);
  var fidt = new Date(fissuedt);
  var lidt = new Date(lissuedt);

  if (fregdt == "" && lregdt != "") {
    alert("검색하려는 작성일 범위의 시작을 입력해주십시오.");
  } else if (lregdt == "" && fregdt != "") {
    alert("검색하려는 작성일 범위의 마지막을 입력해주십시오.");
  } else if (fissuedt == "" && lissuedt != "") {
    alert("검색하려는 발급일 범위의 시작을 입력해주십시오.");
  } else if (lissuedt == "" && fissuedt != "") {
    alert("검색하려는 발급일 범위의 마지막을 입력해주십시오.");
  } else if (now.getTime() < frdt.getTime()) {
    alert("날짜를 정확히 입력해주십시오.");
  } else if (now.getTime() < lrdt.getTime()) {
    alert("날짜를 정확히 입력해주십시오.");
  } else if (now.getTime() < fidt.getTime()) {
    alert("날짜를 정확히 입력해주십시오.");
  } else if (now.getTime() < lidt.getTime()) {
    alert("날짜를 정확히 입력해주십시오.");
  } else {
    chk = true;
  }
  return chk;
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 검색조건을 초기화한다.
 * @param		: sfrm - searchForm 객체를 대입하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncResetSearch() {
  var sfrm = document.searchForm;
  sfrm.firstRegDt.value = "";
  sfrm.lastRegDt.value = "";
  sfrm.firstIssueDt.value = "";
  sfrm.lastIssueDt.value = "";
  sfrm.companyNM.value = "";
  sfrm.collectionMoneyYN.value = "T";
  window.location.reload();
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 페이지를 이동한다.
 * @param		: sfrm - searchForm 객체를 대입하기 위한 변수
 * @param		: page - 입력받은 페이지를 전달하는 매개변수
 * @return	: void
 * @author	: 강병수
 */
function fncMovePage(page) {
  var sfrm = document.searchForm;
  sfrm.pageNum.value = page;
  sfrm.action = "/salesList";
  sfrm.submit();
}
