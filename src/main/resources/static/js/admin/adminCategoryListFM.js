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

  xhttp.open("get", "/admin" + url, true);
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

  xhttp.open("post", "/admin" + url, true);
  xhttp.setRequestHeader('Content-Type', 'application/json');
  xhttp.setRequestHeader("X-CSRF-TOKEN", csrfToken);
  xhttp.send(JSON.stringify(data));

  return xhttp;
}

/**
 * @type    : event
 * @access  : public
 * @desc    : 창을 불러올 시 select 데이터를 불러오기 위한 메소드를 사용함
 * @author  : 홍성관
 */
window.onload = function () {
  const selectModelRegistCompanyName = document.getElementById("selectModelRegistCompanyName");
  selectModelRegistCompanyName.addEventListener('change', (event) => {
    fncSelectItemNameLoad(selectModelRegistCompanyName.value);
  });
};

/**
 * @type    : function
 * @access  : public
 * @desc    : 모델명 카데고리에서 select 제조사명에 따라 제품명 카테고리가 나타나도록 하는 메소드
 * @param    : company - 제조사명
 * @return  : void
 * @author  : 홍성관
 */
function fncSelectItemNameLoad(company) {
  const xhttp = fncGetData("/cateMiddleList?company=" + company);
  xhttp.onreadystatechange = function (error) {
    if (xhttp.readyState == XMLHttpRequest.DONE) {
      const results = JSON.parse(xhttp.responseText);
      const itemSelect = document.getElementById("selectModelRegistItemName");
      itemSelect.innerHTML = "";
      results.forEach(result => {
        const itemOption = document.createElement('option');
        const itemText = document.createTextNode(result);
        itemOption.appendChild(itemText);
        itemSelect.appendChild(itemOption);
      });
    }
  };
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 제조사 카데고리를 수정 하는 팝업창을 만드는 메소드
 * @return  : void
 * @author  : 홍성관
 */
function fncCompanyRegistModifyModalPopup() {
  const xhttp = fncGetData("/cateMainList");
  xhttp.onreadystatechange = function (error) {
    if (xhttp.readyState == XMLHttpRequest.DONE) {
      console.log(error);
      const results = JSON.parse(xhttp.responseText);
      const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
      const modifyModal = document.getElementById("modifyModal");
      const modalBody = modifyModal.childNodes[3].childNodes[1];

      modifyModal.style.display = "block";
      modalBody.innerHTML = '';
      results.forEach(result => {
        const trCateCompany = document.createElement('tr');

        const tdModify = document.createElement('td');
        const tdDelete = document.createElement('td');

        const formModify = document.createElement('form');
        const formDelete = document.createElement('form');

        const inputCSRFToken = document.createElement('input');
        const inputCateCompany = document.createElement('input');
        const inputModifyCateIdx = document.createElement('input');
        const inputDeleteCateIdx = document.createElement('input');

        const buttonModify = document.createElement('button');
        const buttonDelete = document.createElement('button');

        const textModify = document.createTextNode("수정");
        const textDelete = document.createTextNode("삭제");

        inputCSRFToken.setAttribute("type", "hidden");
        inputCSRFToken.setAttribute("name", "_csrf");
        inputCSRFToken.setAttribute("value", csrfToken);

        inputModifyCateIdx.setAttribute("type", "hidden");
        inputModifyCateIdx.setAttribute("name", "cateIdx");
        inputModifyCateIdx.setAttribute("value", result.cateIdx);

        inputCateCompany.setAttribute("type", "text");
        inputCateCompany.setAttribute("name", "cateCompany");
        inputCateCompany.setAttribute("value", result.cateCompany);

        buttonModify.setAttribute("type", "submit");
        buttonModify.setAttribute("onclick", "fncButtonCompanyCategoryModify(this)");
        buttonModify.appendChild(textModify)

        formModify.setAttribute("method", "post");
        formModify.setAttribute("action", "/admin/cateSaveMain");
        formModify.appendChild(inputCSRFToken);
        formModify.appendChild(inputModifyCateIdx);
        formModify.appendChild(inputCateCompany);
        formModify.appendChild(buttonModify);

        tdModify.appendChild(formModify);
        trCateCompany.appendChild(tdModify);

        inputDeleteCateIdx.setAttribute("type", "hidden");
        inputDeleteCateIdx.setAttribute("name", "cateIdx");
        inputDeleteCateIdx.setAttribute("value", result.cateIdx);

        buttonDelete.setAttribute("type", "submit");
        buttonDelete.appendChild(textDelete);

        formDelete.setAttribute("method", "get");
        formDelete.setAttribute("action", "/admin/cateDelete");
        formDelete.setAttribute("onsubmit", "alert('삭제되었습니다.');");
        formDelete.appendChild(inputDeleteCateIdx);
        formDelete.appendChild(buttonDelete);

        tdDelete.appendChild(formDelete);
        trCateCompany.appendChild(tdDelete);

        modalBody.appendChild(trCateCompany);
      });
    }
  }
}


/**
 * @type    : function
 * @access  : public
 * @desc    : 제품명 카데고리를 수정 하는 팝업창을 만드는 메소드
 * @return  : void
 * @author  : 홍성관
 */
function fncItemRegistModifyModalPopup() {
  const xhttp = fncGetData("/cateMidList");
  xhttp.onreadystatechange = function (error) {
    if (xhttp.readyState == XMLHttpRequest.DONE) {
      console.log(error);
      const results = JSON.parse(xhttp.responseText);
      const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
      const modifyModal = document.getElementById("modifyModal");
      const modalBody = modifyModal.childNodes[3].childNodes[1];

      modifyModal.style.display = "block";
      modalBody.innerHTML = '';
      results.forEach(result => {
        const trCateItem = document.createElement('tr');

        const tdModify = document.createElement('td');
        const tdDelete = document.createElement('td');

        const formModify = document.createElement('form');
        const formDelete = document.createElement('form');

        const inputCSRFToken = document.createElement('input');
        const inputCateCompany = document.createElement('input');
        const inputCateItem = document.createElement('input');
        const inputModifyCateIdx = document.createElement('input');
        const inputDeleteCateIdx = document.createElement('input');

        const buttonModify = document.createElement('button');
        const buttonDelete = document.createElement('button');

        const textModify = document.createTextNode("수정");
        const textDelete = document.createTextNode("삭제");

        inputCSRFToken.setAttribute("type", "hidden");
        inputCSRFToken.setAttribute("name", "_csrf");
        inputCSRFToken.setAttribute("value", csrfToken);

        inputModifyCateIdx.setAttribute("type", "hidden");
        inputModifyCateIdx.setAttribute("name", "cateIdx");
        inputModifyCateIdx.setAttribute("value", result.cateIdx);

        inputCateCompany.setAttribute("type", "text");
        inputCateCompany.setAttribute("name", "cateCompany");
        inputCateCompany.setAttribute("value", result.cateCompany);

        inputCateItem.setAttribute("type", "text");
        inputCateItem.setAttribute("name", "cateItem");
        inputCateItem.setAttribute("value", result.cateItem);

        buttonModify.setAttribute("type", "submit");
        buttonModify.setAttribute("onclick", "fncButtonItemCategoryModify(this)");
        buttonModify.appendChild(textModify)

        formModify.setAttribute("method", "post");
        formModify.setAttribute("action", "/admin/cateSaveMid");
        formModify.appendChild(inputCSRFToken);
        formModify.appendChild(inputModifyCateIdx);
        formModify.appendChild(inputCateCompany);
        formModify.appendChild(inputCateItem);
        formModify.appendChild(buttonModify);

        tdModify.appendChild(formModify);
        trCateItem.appendChild(tdModify);

        inputDeleteCateIdx.setAttribute("type", "hidden");
        inputDeleteCateIdx.setAttribute("name", "cateIdx");
        inputDeleteCateIdx.setAttribute("value", result.cateIdx);

        buttonDelete.setAttribute("type", "submit");
        buttonDelete.appendChild(textDelete);

        formDelete.setAttribute("method", "get");
        formDelete.setAttribute("action", "/admin/cateDelete");
        formDelete.setAttribute("onsubmit", "alert('삭제되었습니다.');");
        formDelete.appendChild(inputDeleteCateIdx);
        formDelete.appendChild(buttonDelete);

        tdDelete.appendChild(formDelete);
        trCateItem.appendChild(tdDelete);

        modalBody.appendChild(trCateItem);
      });
    }
  };
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 모델명 카데고리를 수정 하는 팝업창을 만드는 메소드
 * @return  : void
 * @author  : 홍성관
 */
function fncModelRegistModifyModalPopup() {
  const xhttp = fncGetData("/cateSubList");
  xhttp.onreadystatechange = function (error) {
    if (xhttp.readyState == XMLHttpRequest.DONE) {
      console.log(error);
      const results = JSON.parse(xhttp.responseText);
      const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
      const modifyModal = document.getElementById("modifyModal");
      const modalBody = modifyModal.childNodes[3].childNodes[1];

      modifyModal.style.display = "block";
      modalBody.innerHTML = '';
      results.forEach(result => {
        const trCateModel = document.createElement('tr');

        const tdModify = document.createElement('td');
        const tdDelete = document.createElement('td');

        const formModify = document.createElement('form');
        const formDelete = document.createElement('form');

        const inputCSRFToken = document.createElement('input');
        const inputCateCompany = document.createElement('input');
        const inputCateItem = document.createElement('input');
        const inputCateModel = document.createElement('input');
        const inputModifyCateIdx = document.createElement('input');
        const inputDeleteCateIdx = document.createElement('input');

        const buttonModify = document.createElement('button');
        const buttonDelete = document.createElement('button');

        const textModify = document.createTextNode("수정");
        const textDelete = document.createTextNode("삭제");

        inputCSRFToken.setAttribute("type", "hidden");
        inputCSRFToken.setAttribute("name", "_csrf");
        inputCSRFToken.setAttribute("value", csrfToken);

        inputModifyCateIdx.setAttribute("type", "hidden");
        inputModifyCateIdx.setAttribute("name", "cateIdx");
        inputModifyCateIdx.setAttribute("value", result.cateIdx);

        inputCateCompany.setAttribute("type", "text");
        inputCateCompany.setAttribute("name", "cateCompany");
        inputCateCompany.setAttribute("value", result.cateCompany);

        inputCateItem.setAttribute("type", "text");
        inputCateItem.setAttribute("name", "cateItem");
        inputCateItem.setAttribute("value", result.cateItem);

        inputCateModel.setAttribute("type", "text");
        inputCateModel.setAttribute("name", "cateModelName");
        inputCateModel.setAttribute("value", result.cateModelName);

        buttonModify.setAttribute("type", "submit");
        buttonModify.setAttribute("onclick", "fncButtonModelCategoryModify(this)");
        buttonModify.appendChild(textModify)

        formModify.setAttribute("method", "post");
        formModify.setAttribute("action", "/admin/cateSaveSub");
        formModify.appendChild(inputCSRFToken);
        formModify.appendChild(inputModifyCateIdx);
        formModify.appendChild(inputCateCompany);
        formModify.appendChild(inputCateItem);
        formModify.appendChild(inputCateModel);
        formModify.appendChild(buttonModify);

        tdModify.appendChild(formModify);
        trCateModel.appendChild(tdModify);

        inputDeleteCateIdx.setAttribute("type", "hidden");
        inputDeleteCateIdx.setAttribute("name", "cateIdx");
        inputDeleteCateIdx.setAttribute("value", result.cateIdx);

        buttonDelete.setAttribute("type", "submit");
        buttonDelete.appendChild(textDelete);

        formDelete.setAttribute("method", "get");
        formDelete.setAttribute("action", "/admin/cateDelete");
        formDelete.setAttribute("onsubmit", "alert('삭제되었습니다.');");
        formDelete.appendChild(inputDeleteCateIdx);
        formDelete.appendChild(buttonDelete);
        formDelete.appendChild(buttonDelete);

        tdDelete.appendChild(formDelete);
        trCateModel.appendChild(tdDelete);

        modalBody.appendChild(trCateModel);
      });
    }
  };
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 제조사 카데고리를 수정 하는 팝업창을 만드는 메소드
 * @param    : e - html의 요소 파라미터
 * @return  : void
 * @author  : 홍성관
 */
function fncButtonCompanyCategoryModify(e) {
  const cateCompany = e.parentNode.parentNode.childNodes[0].childNodes[2].value;
  if (cateCompany == "") {
    alert("제조사를 입력해주십시오.");
    return null;
  }
  alert("수정되었습니다");
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 제품명 카데고리를 수정 하는 팝업창을 만드는 메소드
 * @param    : e - html의 요소 파라미터
 * @return  : void
 * @author  : 홍성관
 */
function fncButtonItemCategoryModify(e) {
  const cateCompany = e.parentNode.parentNode.childNodes[0].childNodes[2].value;
  const cateItem = e.parentNode.parentNode.childNodes[0].childNodes[3].value;
  if (cateCompany == "" || cateItem == "") {
    alert("제조사를 혹은 제품명을 입력해주십시오.");
    return null;
  }
  alert("수정되었습니다");
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 모델명 카데고리를 수정 하는 팝업창을 만드는 메소드
 * @param    : e - html의 요소 파라미터
 * @return  : void
 * @author  : 홍성관
 */
function fncButtonModelCategoryModify(e) {
  const cateCompany = e.parentNode.parentNode.childNodes[0].childNodes[2].value;
  const cateItem = e.parentNode.parentNode.childNodes[0].childNodes[3].value;
  const cateModel = e.parentNode.parentNode.childNodes[0].childNodes[4].value;
  if (cateCompany == "" || cateItem == "" || cateModel == "") {
    alert("빈칸을 입력해주십시오.");
    return null;
  }
  alert("수정되었습니다");
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 카테고리 수정 팝업창을 닫는 메소드
 * @return  : void
 * @author  : 홍성관
 */
function fncModifyModalClose() {
  document.getElementById("modifyModal").style.display = "none";
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 카테고리 상세보기 팝업창을 닫는 메소드
 * @return  : void
 * @author  : 홍성관
 */
function fncDetailModalClose() {
  document.getElementById("detailModal").style.display = "none";
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 제조사 카테고리를 등록하는 메소드
 * @param    : e - html의 요소 파라미터
 * @return  : void
 * @author  : 홍성관
 */
function fncMainCategoryReg(e) {
  const companyName = e.parentNode.parentNode.childNodes[1].childNodes[1].value;
  if (companyName == "") {
    alert("제조사명을 입력해주십시오.");
    return null;
  }
  const data = {
    "cateCompany": companyName
  };
  const xhttp = fncPostData("/cateRegMain", data);
  xhttp.onreadystatechange = function (error) {
    if (xhttp.readyState == XMLHttpRequest.DONE) {
      const result = JSON.parse(xhttp.responseText);
      if (result === true) {
        alert("이미 있는 제조사명입니다.");
      } else {
        alert("제품 등록에 성공했습니다.");
      }
      window.location.reload();
    }
  };
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 제품명 카테고리를 등록하는 메소드
 * @param    : e - html의 요소 파라미터
 * @return  : void
 * @author  : 홍성관
 */
function fncItemCategoryReg(e) {
  const companyName = e.parentNode.parentNode.childNodes[1].childNodes[1].value;
  const itemName = e.parentNode.parentNode.childNodes[1].childNodes[3].value;
  if (itemName == "") {
    alert("제품명을 입력해주십시오.");
    return null;
  }
  const data = {
    "cateCompany": companyName,
    "cateItem": itemName
  };

  const xhttp = fncPostData("/cateRegMid", data);
  xhttp.onreadystatechange = function (error) {
    if (xhttp.readyState == XMLHttpRequest.DONE) {
      const result = JSON.parse(xhttp.responseText);
      if (result === true) {
        alert("이미 있는 제조사명입니다.");
      } else {
        alert("제품 등록에 성공했습니다.");
      }
      window.location.reload();
    }
  };
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 모델명 카테고리를 등록하는 메소드
 * @param    : e - html의 요소 파라미터
 * @return  : void
 * @author  : 홍성관
 */
function fncModelCategoryReg(e) {
  const companyName = e.parentNode.parentNode.childNodes[1].childNodes[1].value;
  const itemName = e.parentNode.parentNode.childNodes[1].childNodes[3].value;
  const modelName = e.parentNode.parentNode.childNodes[1].childNodes[5].value;
  if (modelName == "") {
    alert("모델명을 입력해주십시오.");
    return null;
  }

  const data = {
    "cateCompany": companyName,
    "cateItem": itemName,
    "cateModelName": modelName
  };

  const xhttp = fncPostData("/cateRegSub", data);
  xhttp.onreadystatechange = function (error) {
    if (xhttp.readyState == XMLHttpRequest.DONE) {
      const result = JSON.parse(xhttp.responseText);
      if (result === true) {
        alert("이미 있는 제조사명입니다.");
      } else {
        alert("제품 등록에 성공했습니다.");
      }
      window.location.reload();
    }
  };
}
