/**=================================================================
 *@FileName       : Product.js
 *@LastModifyDate : 2020. 2. 3.
 *@LastModifier   : 강병수
 *@LastVersion    : 1.0
 *Change History
 *    2020. 2. 3.    강병수    1.0    최초생성
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
function fncAjaxCall(method, url, data) {
  const token = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
  const header = document.querySelector("meta[name='_csrf']").getAttribute("content");
  const xhr = new XMLHttpRequest();

  xhr.open(method, "http://localhost:8080" + url, true);
  xhr.setRequestHeader('Content-Type', 'application/json');
  xhr.setRequestHeader(token, header);
  xhr.send(JSON.stringify(data));

  return xhr;
}

/*
 * @type		: function
 * @access	: public
 * @desc		: productItem selectBox를 생성한다.
 * @param		: company - select태그 productCompany에서 선택된 값을 저장하기 위한 변수
 * @param		: selectItem - select태그 productItem을 만들기 위한 변수
 * @param		: selectModel - select태그 productModelName 만들기 위한 변수
 * @param		: opts - select태그 productItem에 추가할 option태그
 * @param		: opt - select태그 productModelName 추가할 option태그
 * @param		: xhr -  ajax 통신 후 전송상태를 확인하기 위한 변수
 * @param		: data - ajax 통신때 전송할 데이터를 저장하기 위한 변수
 * @param		: itemOption - select태그 productItem에 추가할 option태그
 * @param		: res - ajax 통신 후 전송받은 데이터를 저장하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncDownLev2() {
  const company = document.getElementById('productCompany').value;
  const selectItem = document.getElementById('productItem');
  const selectModel = document.getElementById('productModelName');
  const opts = document.createElement('option');
  const opt = document.createElement('option');
  opts.innerText = '선택';
  opt.innerText = '선택';
  selectItem.options.length = 0;
  selectItem.append(opts);
  const data = {"productCompany": company};
  var xhr = fncAjaxCall("POST", "/admin/product/selectBoxSetting", data);

  xhr.onreadystatechange = function (error) {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      var res = JSON.parse(xhr.responseText);
      if (res.itemList.length > 0) {
        for (var idx = 0; idx < res.itemList.length; idx++) {
          var itemOption = document.createElement('option');
          itemOption.setAttribute('value', res.itemList[idx]);
          itemOption.innerText = res.itemList[idx];
          selectItem.append(itemOption);
        }
        selectModel.options.length = 0;
        selectModel.append(opt);
      } else {
        selectItem.options.length = 0;
        selectItem.append(opts);
        selectModel.options.length = 0;
        selectModel.append(opt);
      }
    }
  };
}

/*
 * @type		: function
 * @access	: public
 * @desc		: productModelName selectBox를 생성한다.
 * @param		: company - select태그 productCompany에서 선택된 값을 저장하기 위한 변수
 * @param		: item - select태그 productItem 선택된 값을 저장하기 위한 변수
 * @param		: selectItem - select태그 productItem을 만들기 위한 변수
 * @param		: selectModel - select태그 productModelName 만들기 위한 변수
 * @param		: opt - select태그 productModelName 추가할 option태그
 * @param		: xhr -  ajax 통신 후 전송상태를 확인하기 위한 변수
 * @param		: data - ajax 통신때 전송할 데이터를 저장하기 위한 변수
 * @param		: modelOption - select태그 productModelName 추가할 option태그
 * @param		: res - ajax 통신 후 전송받은 데이터를 저장하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncDownLev3() {
  var company = document.getElementById('productCompany').value;
  var item = document.getElementById('productItem').value;
  var selectModel = document.getElementById('productModelName');
  var opt = document.createElement('option');
  opt.innerText = '선택';
  selectModel.options.length = 0;
  selectModel.append(opt);

  const data = {"productCompany": company, "productItem": item};
  var xhr = fncAjaxCall("POST", "/admin/product/selectBoxSetting", data);

  xhr.onreadystatechange = function (error) {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      var res = JSON.parse(xhr.responseText);
      if (res.modelList.length > 0) {
        for (var idx = 0; idx < res.modelList.length; idx++) {
          var modelOption = document.createElement('option');
          modelOption.setAttribute('value', res.modelList[idx]);
          modelOption.innerText = res.modelList[idx];
          selectModel.append(modelOption);
        }
      } else {
        selectModel.options.length = 0;
        selectModel.append(opt);
      }
    }
  };
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 입력받은 값들이 형식에 맞는지 확인한다.
 * @param		: chk - 확인 결과를 저장하기 위한 변수
 * @param		: now - 현재 날짜를 저장하기 위한 변수
 * @param		: company - 입력받은 productCompany를 전달하는 매개변수
 * @param		: item - 입력받은 productItem을 전달하는 매개변수
 * @param		: model - 입력받은 productModelName을 전달하는 매개변수
 * @param		: sn - 입력받은 serialNum을 전달하는 매개변수
 * @param		: pbdt - 입력받은 날짜를 전달하는 매개변수
 * @param		: buyday - 매개변수 pbdt 를 날짜형식으로 변환하여 저장하기 위한 변수
 * @param		: pattern - 문자열의 규칙을 저장하기 위한 변수
 * @return	: boolean
 * @author	: 강병수
 */
function fncChkVal(company, item, model, sn, pbdt) {
  var chk = false;
  var now = new Date();
  var buyday = new Date(pbdt);
  var pattern = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

  if (company == '선택') {
    alert("제조사를 선택해주십시오.");
  } else if (item == '선택') {
    alert("품목을 선택해주십시오.");
  } else if (model == '선택') {
    alert("모델명을을 선택해주십시오.");
  } else if (sn == "" || pattern.test(sn)) {
    alert("S/N을 입력해주십시오. 한글은 입력할 수 없습니다.");
  } else if (pbdt == '') {
    alert("구입연월를 입력해주십시오.");
  } else if (now.getTime() < buyday.getTime()) {
    alert("구입연월를 정확히 입력해주십시오.");
  } else {
    chk = true;
  }
  return chk;
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 등록할 상품정보를 전송한다.
 * @param		: pfrm - productForm 객체를 대입하기 위한 변수
 * @param		: company - form데이터 중 productCompany를 저장하기 위한 변수
 * @param		: item - form데이터 중 productItem을 저장하기 위한 변수
 * @param		: model - form데이터 중 productModelName을 저장하기 위한 변수
 * @param		: sn - form데이터 중 productSerial 저장하기 위한 변수
 * @param		: pbdt - form데이터 중 productBuyDt 저장하기 위한 변수
 * @param		: chk - 입력받은 값들이 맞는지 확인한 결과를 저장하기 위한 변수
 * @param		: xhr -  ajax 통신 후 전송상태를 확인하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncRegProduct() {
  var pfrm = document.productForm;
  var company = document.getElementById('productCompany').value;
  var item = document.getElementById('productItem').value;
  var model = document.getElementById('productModelName').value;
  var sn = pfrm.productSerial.value;
  var pbdt = pfrm.productBuyDt.value;
  var chk = fncChkVal(company, item, model, sn, pbdt);

  if (chk) {
    var xhr = fncAjaxCall("POST", "/products/");
    xhr.onreadystatechange = function (error) {
      if (xhr.readyState == XMLHttpRequest.DONE) {
        alert("등록되었습니다.");
        pfrm.action = "/admin/products/regist";
        pfrm.submit();
      }
    };
  }
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 선택한 상품정보를 삭제한다.
 * @param		: pfrm - productForm 객체를 대입하기 위한 변수
 * @param		: stat - form데이터 중 productStatus를 저장하기 위한 변수
 * @param		: xhr -  ajax 통신 후 전송상태를 확인하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncDelProduct() {
  var pfrm = document.productForm;
  var stat = pfrm.productUseYN.value;

  if (stat == 'Y') {
    alert("현재 사용중인 품목으로 삭제할 수 없습니다.");
  } else {
    if (confirm("삭제하시겠습니까? 삭제된 후에는 복구 할 수 없습니다.")) {
      var xhr = fncAjaxCall("POST", "/products/");
      xhr.onreadystatechange = function (error) {
        if (xhr.readyState == XMLHttpRequest.DONE) {
          alert("삭제되었습니다.");
          pfrm.action = "/admin/products/delete";
          pfrm.submit();
        }
      };
    }
  }
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 상품정보 수정을 취소한다.
 * @param		: pfrm - productForm 객체를 대입하기 위한 변수
 * @param		: xhr -  ajax 통신 후 전송상태를 확인하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncCancel() {
  var pfrm = document.productForm;
  var xhr = fncAjaxCall("POST", "/products/");

  xhr.onreadystatechange = function (error) {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      pfrm.action = "/admin/products/cancel";
      pfrm.submit();
    }
  };
}

/*
 * @type		: function
 * @access	: public
 * @desc		: 수정한 상품정보를 전송한다.
 * @param		: pfrm - productForm 객체를 대입하기 위한 변수
 * @param		: company - form데이터 중 productCompany를 저장하기 위한 변수
 * @param		: item - form데이터 중 productItem을 저장하기 위한 변수
 * @param		: model - form데이터 중 productModelName을 저장하기 위한 변수
 * @param		: sn - form데이터 중 productSerial 저장하기 위한 변수
 * @param		: pbdt - form데이터 중 productBuyDt 저장하기 위한 변수
 * @param		: chk - 입력받은 값들이 맞는지 확인한 결과를 저장하기 위한 변수
 * @param		: xhr -  ajax 통신 후 전송상태를 확인하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncUpdateProduct() {
  var pfrm = document.productForm;
  var company = document.getElementById('productCompany').value;
  var item = document.getElementById('productItem').value;
  var model = document.getElementById('productModelName').value;
  var sn = pfrm.productSerial.value;
  var pbdt = pfrm.productBuyDt.value;
  var chk = fncChkVal(company, item, model, sn, pbdt);

  if (chk) {
    var xhr = fncAjaxCall("POST", "/products/");

    xhr.onreadystatechange = function (error) {
      if (xhr.readyState == XMLHttpRequest.DONE) {
        alert("수정되었습니다.");
        pfrm.action = "/admin/products/modify";
        pfrm.submit();
      }
    };
  }
}
