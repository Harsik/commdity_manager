window.onload = function () {

  const bottomCateSectionSelect = document.getElementById('inputSubjectRegSectionName');
  const middleCateSectionSelect = document.getElementById('inputNatureRegSectionName');
  const cateCreditSelect = document.getElementById('inputSubjectRegCreditName');

  if (bottomCateSectionSelect != null) {
    fncFindCateSectionList(middleCateSectionSelect, "/getCateSection", "cateSection");
    fncFindCateSectionList(bottomCateSectionSelect, "/getCateSection", "cateSection");
    fncFindCateSectionList(cateCreditSelect, "/getCateCredit", "cateCredit");

    bottomCateSectionSelect.addEventListener('change', (event) => {
      fncFindCateNatureList(document.getElementById("inputSubjectRegSectionName").value);
    });
  }
};

/**
 * @type    : function
 * @access  : public
 * @desc    : 불러온 데이터들을 select 엘리먼트에 등록하는 메소드
 * @param   : object - 불러진 값들을 등록할 select 엘리먼트
 * @param   : url - 데이터를 요청할 경로
 * @param   : column - 등록할 데이터의 종류
 * @return  : void
 * @author  : 류민송
 */
function fncFindCateSectionList(object, url, column) {
  fncSetGetData(url).then(results => {
    results.forEach(result => {
      const sectionOption = document.createElement('option');
      let sectionText;
      switch (column) {
        case "cateSection" :
          sectionText = document.createTextNode(result.cateSection);
          break;
        case "cateCredit" :
          sectionText = document.createTextNode(result.cateCredit);
          break;
      }
      sectionOption.appendChild(sectionText);
      object.appendChild(sectionOption);
    });
  });
}

/**
 * @type    : function
 * @access  : public
 * @desc    : select 의 값이 변할 때 마다 값을 불러오는 메소드
 * @param   : cateSection - 해당 구분에 해당하는 성격들을 불러 올 때 구분을 판별하는 변수
 * @return  : void
 * @author  : 류민송
 */
function fncFindCateNatureList(cateSection) {
  fncSetGetData("/getCateNatureList?cateSection=" + cateSection).then(results => {
    const cateNatureSelect = document.getElementById('inputSubjectRegNatureName');
    cateNatureSelect.innerHTML = "";
    results.forEach(result => {
      const companyOption = document.createElement('option');
      const companyText = document.createTextNode(result.cateNature);
      companyOption.appendChild(companyText);
      cateNatureSelect.appendChild(companyOption);
    });
  });
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 구분 등록 메소드
 * @param   : cateSection - 등록할 구분
 * @return  : void
 * @author  : 류민송
 */
function fncCreateCateSection() {
  const cateSection = document.getElementById('cateSection');

  if (cateSection.value == '') {
    alert("구분을 입력해 주십시오.");
  } else {
    const data = {
      cateType: "S", cateSection: cateSection.value
    };
    fncSetPostData("/cateSectionReg", data)
      .then(results => {
        if (results === true) {
          alert("이미 있는 구분입니다.");
        } else {
          alert("구분 등록에 성공했습니다.");
          window.location.reload();
        }
      })
  }
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 지급코드 등록 메소드
 * @param   : cateCredit - 등록할 지급코드
 * @return  : void
 * @author  : 류민송
 */
function fncCreateCateCredit() {
  const cateCredit = document.getElementById('cateCredit');

  if (cateCredit.value == '') {
    alert("지급코드를 입력해 주십시오.");
  } else {
    const data = {
      cateType: "S", cateCredit: cateCredit.value
    };
    fncSetPostData("/cateCreditReg", data)
      .then(results => {
        if (results === true) {
          alert("이미 있는 지급코드입니다.");
        } else {
          alert("지급코드 등록에 성공했습니다.");
          window.location.reload();
        }
      })
  }
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 성격 등록 메소드
 * @param   : cateSection - 등록될 구분
 * @param   : cateNature - 등록될 성격
 * @return  : void
 * @author  : 류민송
 */
function fncCreateCateNature() {
  const cateSection = document.getElementById('inputNatureRegSectionName');
  const cateNature = document.getElementById('inputItemRegistItemName');

  if (cateNature.value == '' || cateSection.value == 'default') {
    alert("구분을 선택하거나 성격을 입력해 주십시오.");
  } else {
    const data = {
      cateType: "S",
      cateSection: cateSection.value,
      cateNature: cateNature.value
    };
    fncSetPostData("/cateNatureReg", data)
      .then(results => {
        if (results === true) {
          alert("이미 있는 성격입니다.");
        } else {
          alert("성격 등록에 성공했습니다.");
          window.location.reload();
        }
      })
  }
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 계정과목 등록 메소드
 * @param   : cateSection - 등록될 구분
 * @param   : cateNature - 등록될 성격
 * @param   : cateCredit - 등록될 지급코드
 * @param   : cateSubject - 등록될 계정과목
 * @return  : void
 * @author  : 류민송
 */
function fncCreateCateSubject() {
  const cateSection = document.getElementById('inputSubjectRegSectionName');
  const cateNature = document.getElementById('inputSubjectRegNatureName');
  const cateCredit = document.getElementById('inputSubjectRegCreditName');
  const cateSubject = document.getElementById('inputModelRegistModelName');

  if (cateSection.value == 'default' || cateSubject.value == '') {
    alert("구분을 선택하거나 계정과목을 입력해 주십시오.");
  } else {
    const data = {
      cateType: "S",
      cateSection: cateSection.value,
      cateNature: cateNature.value,
      cateCredit: cateCredit.value,
      cateSubject: cateSubject.value
    };
    fncSetPostData("/cateSubjectReg", data)
      .then(results => {
        if (results === true) {
          alert("이미 있는 계정과목입니다.");
        } else {
          alert("계정과목 등록에 성공했습니다.");
          window.location.reload();
        }
      })
  }
}


/**
 * @type    : function
 * @access  : public
 * @desc    : 구분 수정 모달창을 띄우는 메소드
 * @param   : inputHiddenCateSection - 수정 이전 값을 저장하기 위한 임의의 히든, cateModelName 으로 등록되어 있지만 실제 데이터에는 영향 X
 * @return  : void
 * @author  : 류민송
 */
function fncSaveCateSection() {
  fncSetGetData("/getCateSection").then(results => {
    const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const modifyModal = document.getElementById("modifyModal");
    modifyModal.style.display = "block";
    const modalBody = modifyModal.childNodes[3].childNodes[1];
    modalBody.innerHTML = '';

    const tHead = document.createElement('tr');

    const tTitle = document.createElement('th');
    const textTile = document.createTextNode("구분");
    tTitle.appendChild(textTile);
    tHead.appendChild(tTitle);

    const tDelete = document.createElement('th');
    const textDel = document.createTextNode("삭제");
    tDelete.appendChild(textDel);
    tHead.appendChild(tDelete);

    modalBody.appendChild(tHead);

    results.forEach(result => {
      const trCateSection = document.createElement('tr');

      const tdModify = document.createElement('td');
      const tdDelete = document.createElement('td');

      const divModify = document.createElement('div');
      const formDelete = document.createElement('form');

      const inputCSRFToken = document.createElement('input');
      const inputCateSection = document.createElement('input');
      const inputHiddenCateSection = document.createElement('input');
      const inputModifyCateIdx = document.createElement('input');
      const inputDeleteCateIdx = document.createElement('input');


      const buttonModify = document.createElement('button');
      const buttonDelete = document.createElement('button');

      const textModify = document.createTextNode("수정");
      const textDelete = document.createTextNode("삭제");

      inputCSRFToken.setAttribute("type", "hidden");
      inputCSRFToken.setAttribute("name", "_csrf");
      inputCSRFToken.setAttribute("value", token);

      inputModifyCateIdx.setAttribute("type", "hidden");
      inputModifyCateIdx.setAttribute("name", "cateIdx");
      inputModifyCateIdx.setAttribute("value", result.cateIdx);

      inputCateSection.setAttribute("type", "text");
      inputCateSection.setAttribute("name", "cateSection");
      inputCateSection.setAttribute("value", result.cateSection);

      inputHiddenCateSection.setAttribute("type", "hidden");
      inputHiddenCateSection.setAttribute("name", "cateModelName");
      inputHiddenCateSection.setAttribute("value", result.cateSection);

      buttonModify.setAttribute("type", "button");
      buttonModify.setAttribute("onclick", "fncIsSave('/cateSectionModify', this);");
      buttonModify.appendChild(textModify)

      divModify.appendChild(inputCSRFToken);
      divModify.appendChild(inputModifyCateIdx);
      divModify.appendChild(inputCateSection);
      divModify.appendChild(inputHiddenCateSection);
      divModify.appendChild(buttonModify);

      tdModify.appendChild(divModify);
      trCateSection.appendChild(tdModify);

      inputDeleteCateIdx.setAttribute("type", "hidden");
      inputDeleteCateIdx.setAttribute("name", "cateIdx");
      inputDeleteCateIdx.setAttribute("value", result.cateIdx);

      buttonDelete.setAttribute("type", "submit");
      buttonDelete.appendChild(textDelete);

      formDelete.setAttribute("method", "get");
      formDelete.setAttribute("action", "/cateDeleteService/section");
      formDelete.setAttribute("onsubmit", "return confirm('삭제하시겠습니까?');");
      formDelete.appendChild(inputDeleteCateIdx);
      formDelete.appendChild(buttonDelete);

      tdDelete.appendChild(formDelete);
      trCateSection.appendChild(tdDelete);

      modalBody.appendChild(trCateSection);
    });
  });
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 지급코드 수정 모달창을 띄우는 메소드
 * @param   : inputHiddenCateCredit - 수정 이전 값을 저장하기 위한 임의의 히든, cateModelName 으로 등록되어 있지만 실제 데이터에는 영향 X
 * @return  : void
 * @author  : 류민송
 */
function fncSaveCateCredit() {
  fncSetGetData("/getCateCredit").then(results => {
    const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const modifyModal = document.getElementById("modifyModal");
    modifyModal.style.display = "block";
    const modalBody = modifyModal.childNodes[3].childNodes[1];
    modalBody.innerHTML = '';

    const tHead = document.createElement('tr');

    const tTitle = document.createElement('th');
    const textTile = document.createTextNode("지급코드");
    tTitle.appendChild(textTile);
    tHead.appendChild(tTitle);

    const tDelete = document.createElement('th');
    const textDel = document.createTextNode("삭제");
    tDelete.appendChild(textDel);
    tHead.appendChild(tDelete);

    modalBody.appendChild(tHead);

    results.forEach(result => {
      const trCateCredit = document.createElement('tr');

      const tdModify = document.createElement('td');
      const tdDelete = document.createElement('td');

      const divModify = document.createElement('div');
      const formDelete = document.createElement('form');

      const inputCSRFToken = document.createElement('input');
      const inputCateCredit = document.createElement('input');
      const inputHiddenCateCredit = document.createElement('input');
      const inputModifyCateIdx = document.createElement('input');
      const inputDeleteCateIdx = document.createElement('input');

      const buttonModify = document.createElement('button');
      const buttonDelete = document.createElement('button');

      const textModify = document.createTextNode("수정");
      const textDelete = document.createTextNode("삭제");

      inputCSRFToken.setAttribute("type", "hidden");
      inputCSRFToken.setAttribute("name", "_csrf");
      inputCSRFToken.setAttribute("value", token);

      inputModifyCateIdx.setAttribute("type", "hidden");
      inputModifyCateIdx.setAttribute("name", "cateIdx");
      inputModifyCateIdx.setAttribute("value", result.cateIdx);

      inputCateCredit.setAttribute("type", "text");
      inputCateCredit.setAttribute("name", "cateCredit");
      inputCateCredit.setAttribute("value", result.cateCredit);

      inputHiddenCateCredit.setAttribute("type", "hidden");
      inputHiddenCateCredit.setAttribute("name", "cateModelName");
      inputHiddenCateCredit.setAttribute("value", result.cateCredit);

      buttonModify.setAttribute("type", "button");
      buttonModify.setAttribute("onclick", "fncIsSave('/cateCreditModify', this);");
      buttonModify.appendChild(textModify)

      divModify.appendChild(inputCSRFToken);
      divModify.appendChild(inputModifyCateIdx);
      divModify.appendChild(inputCateCredit);
      divModify.appendChild(inputHiddenCateCredit);
      divModify.appendChild(buttonModify);

      tdModify.appendChild(divModify);
      trCateCredit.appendChild(tdModify);

      inputDeleteCateIdx.setAttribute("type", "hidden");
      inputDeleteCateIdx.setAttribute("name", "cateIdx");
      inputDeleteCateIdx.setAttribute("value", result.cateIdx);

      buttonDelete.setAttribute("type", "submit");
      buttonDelete.appendChild(textDelete);

      formDelete.setAttribute("method", "get");
      formDelete.setAttribute("action", "/cateDeleteService/credit");
      formDelete.setAttribute("onsubmit", "return confirm('삭제하시겠습니까?');");
      formDelete.appendChild(inputDeleteCateIdx);
      formDelete.appendChild(buttonDelete);

      tdDelete.appendChild(formDelete);
      trCateCredit.appendChild(tdDelete);

      modalBody.appendChild(trCateCredit);
    });
  });
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 성격 수정 모달창을 띄우는 메소드
 * @param   : inputHiddenCateNature - 수정 이전 값을 저장하기 위한 임의의 히든, cateModelName 으로 등록되어 있지만 실제 데이터에는 영향 X
 * @return  : void
 * @author  : 류민송
 */
function fncSaveCateNature() {
  fncSetGetData("/getCateNature").then(results => {
    const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const modifyModal = document.getElementById("modifyModal");
    modifyModal.style.display = "block";
    const modalBody = modifyModal.childNodes[3].childNodes[1];
    modalBody.innerHTML = '';

    const tHead = document.createElement('tr');

    const tTitle = document.createElement('th');
    const textTile = document.createTextNode("성격");
    tTitle.appendChild(textTile);
    tHead.appendChild(tTitle);

    const tDelete = document.createElement('th');
    const textDel = document.createTextNode("삭제");
    tDelete.appendChild(textDel);
    tHead.appendChild(tDelete);

    modalBody.appendChild(tHead);

    results.forEach(result => {
      const trCateNature = document.createElement('tr');

      const tdModify = document.createElement('td');
      const tdDelete = document.createElement('td');

      const divModify = document.createElement('div');
      const formDelete = document.createElement('form');

      const inputCSRFToken = document.createElement('input');
      const inputCateSection = document.createElement('input');
      const inputCateNature = document.createElement('input');
      const inputHiddenCateNature = document.createElement('input');
      const inputModifyCateIdx = document.createElement('input');
      const inputDeleteCateIdx = document.createElement('input');

      const buttonModify = document.createElement('button');
      const buttonDelete = document.createElement('button');

      const textModify = document.createTextNode("수정");
      const textDelete = document.createTextNode("삭제");

      inputCSRFToken.setAttribute("type", "hidden");
      inputCSRFToken.setAttribute("name", "_csrf");
      inputCSRFToken.setAttribute("value", token);

      inputModifyCateIdx.setAttribute("type", "hidden");
      inputModifyCateIdx.setAttribute("name", "cateIdx");
      inputModifyCateIdx.setAttribute("value", result.cateIdx);

      inputCateSection.setAttribute("type", "text");
      inputCateSection.setAttribute("name", "cateSection");
      inputCateSection.setAttribute("value", result.cateSection);
      inputCateSection.readOnly = true;

      inputCateNature.setAttribute("type", "text");
      inputCateNature.setAttribute("name", "cateNature");
      inputCateNature.setAttribute("value", result.cateNature);

      inputHiddenCateNature.setAttribute("type", "hidden");
      inputHiddenCateNature.setAttribute("name", "cateModelName");
      inputHiddenCateNature.setAttribute("value", result.cateNature);

      buttonModify.setAttribute("type", "button");
      buttonModify.setAttribute("onclick", "fncIsSave('/cateNatureModify', this);");
      buttonModify.appendChild(textModify)

      divModify.appendChild(inputCSRFToken);
      divModify.appendChild(inputModifyCateIdx);
      divModify.appendChild(inputCateSection);
      divModify.appendChild(inputCateNature);
      divModify.appendChild(inputHiddenCateNature);
      divModify.appendChild(buttonModify);

      tdModify.appendChild(divModify);
      trCateNature.appendChild(tdModify);

      inputDeleteCateIdx.setAttribute("type", "hidden");
      inputDeleteCateIdx.setAttribute("name", "cateIdx");
      inputDeleteCateIdx.setAttribute("value", result.cateIdx);

      buttonDelete.setAttribute("type", "submit");
      buttonDelete.appendChild(textDelete);

      formDelete.setAttribute("method", "get");
      formDelete.setAttribute("action", "/cateDeleteService/nature");
      formDelete.setAttribute("onsubmit", "return confirm('삭제하시겠습니까?');");
      formDelete.appendChild(inputDeleteCateIdx);
      formDelete.appendChild(buttonDelete);

      tdDelete.appendChild(formDelete);
      trCateNature.appendChild(tdDelete);

      modalBody.appendChild(trCateNature);
    });
  });
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 계정 과목 수정 모달 창을 띄우는 메소드
 * @return  : void
 * @author  : 류민송
 */
function fncSaveCateSubject() {
  fncSetGetData("/getCateSubject").then(results => {
    const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const modifyModal = document.getElementById("modifyModal");
    modifyModal.style.display = "block";
    const modalBody = modifyModal.childNodes[3].childNodes[1];
    modalBody.innerHTML = '';

    const tHead = document.createElement('tr');

    const tTitle = document.createElement('th');
    const textTile = document.createTextNode("계정과목");
    tTitle.appendChild(textTile);
    tHead.appendChild(tTitle);

    const tDelete = document.createElement('th');
    const textDel = document.createTextNode("삭제");
    tDelete.appendChild(textDel);
    tHead.appendChild(tDelete);

    modalBody.appendChild(tHead);

    results.forEach(result => {
      const trCateSubject = document.createElement('tr');

      const tdModify = document.createElement('td');
      const tdDelete = document.createElement('td');

      const divModify = document.createElement('div');
      const formDelete = document.createElement('form');

      const inputCSRFToken = document.createElement('input');
      const inputCateSection = document.createElement('input');
      const inputCateNature = document.createElement('input');
      const inputCateCredit = document.createElement('input');
      const inputCateSubject = document.createElement('input');
      const inputModifyCateIdx = document.createElement('input');
      const inputDeleteCateIdx = document.createElement('input');

      const buttonModify = document.createElement('button');
      const buttonDelete = document.createElement('button');

      const textModify = document.createTextNode("수정");
      const textDelete = document.createTextNode("삭제");

      inputCSRFToken.setAttribute("type", "hidden");
      inputCSRFToken.setAttribute("name", "_csrf");
      inputCSRFToken.setAttribute("value", token);

      inputModifyCateIdx.setAttribute("type", "hidden");
      inputModifyCateIdx.setAttribute("name", "cateIdx");
      inputModifyCateIdx.setAttribute("value", result.cateIdx);

      inputCateSection.setAttribute("type", "text");
      inputCateSection.setAttribute("name", "cateSection");
      inputCateSection.setAttribute("value", result.cateSection);
      inputCateSection.readOnly = true;

      inputCateNature.setAttribute("type", "text");
      inputCateNature.setAttribute("name", "cateNature");
      inputCateNature.setAttribute("value", result.cateNature);
      inputCateNature.readOnly = true;

      inputCateCredit.setAttribute("type", "text");
      inputCateCredit.setAttribute("name", "cateCredit");
      inputCateCredit.setAttribute("value", result.cateCredit);
      inputCateCredit.readOnly = true;

      inputCateSubject.setAttribute("type", "text");
      inputCateSubject.setAttribute("name", "cateSubject");
      inputCateSubject.setAttribute("value", result.cateSubject);

      buttonModify.setAttribute("type", "button");
      buttonModify.setAttribute("onclick", "fncIsSave('/cateSubjectModify', this);");
      buttonModify.appendChild(textModify)

      divModify.appendChild(inputCSRFToken);
      divModify.appendChild(inputModifyCateIdx);
      divModify.appendChild(inputCateSection);
      divModify.appendChild(inputCateNature);
      divModify.appendChild(inputCateCredit);
      divModify.appendChild(inputCateSubject);
      divModify.appendChild(buttonModify);

      tdModify.appendChild(divModify);
      trCateSubject.appendChild(tdModify);

      inputDeleteCateIdx.setAttribute("type", "hidden");
      inputDeleteCateIdx.setAttribute("name", "cateIdx");
      inputDeleteCateIdx.setAttribute("value", result.cateIdx);

      buttonDelete.setAttribute("type", "submit");
      buttonDelete.appendChild(textDelete);

      formDelete.setAttribute("method", "get");
      formDelete.setAttribute("action", "/cateDeleteService/subject");
      formDelete.setAttribute("onsubmit", "return confirm('삭제하시겠습니까?');");
      formDelete.appendChild(inputDeleteCateIdx);
      formDelete.appendChild(buttonDelete);

      tdDelete.appendChild(formDelete);
      trCateSubject.appendChild(tdDelete);

      modalBody.appendChild(trCateSubject);


    });
  });
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 수정 시 해당 데이터가 중복되는지 체크하는 메소드
 * @param   : url - 데이터를 전송할 경로
 * @param   : e - 전송할 데이터를 담고 있는 해당 엘리먼트
 * @return  : void
 * @author  : 류민송
 */
function fncIsSave(url, e) {

  const count = e.parentNode.childElementCount;

  if (e.parentNode.childNodes[count - 2].value == '' || e.parentNode.childNodes[count - 3].value == '') {
    alert("수정하려는 값을 입력해 주십시오");
  } else {

    let data;

    switch (url) {
      case "/cateSectionModify" :
        data = {
          cateIdx: e.parentNode.childNodes[1].value,
          cateSection: e.parentNode.childNodes[2].value,
          cateModelName: e.parentNode.childNodes[3].value
        };
        break;
      case "/cateCreditModify" :
        data = {
          cateIdx: e.parentNode.childNodes[1].value,
          cateCredit: e.parentNode.childNodes[2].value,
          cateModelName: e.parentNode.childNodes[3].value
        };
        break;
      case "/cateNatureModify" :
        data = {
          cateIdx: e.parentNode.childNodes[1].value,
          cateSection: e.parentNode.childNodes[2].value,
          cateNature: e.parentNode.childNodes[3].value,
          cateModelName: e.parentNode.childNodes[4].value
        };
        break;
      case "/cateSubjectModify" :
        data = {
          cateIdx: e.parentNode.childNodes[1].value,
          cateSection: e.parentNode.childNodes[2].value,
          cateNature: e.parentNode.childNodes[3].value,
          cateCredit: e.parentNode.childNodes[4].value,
          cateSubject: e.parentNode.childNodes[5].value
        };
        break;
    }

    fncSetPostData(url, data)
      .then(results => {
        if (results === true) {
          alert("중복되는 데이터입니다");
        } else {
          alert("수정되었습니다");
          window.location.reload();
        }
      });
  }


}

/**
 * @type    : function
 * @access  : public
 * @desc    : fetch api 를 통해 post 방식으로 통신 요청하는 메소드
 * @param   : url - 요청을 보낼 주소
 * @param   : data - 요청에 보낼 데이터
 * @return  : fetch
 * @author  : 류민송
 */
function fncSetPostData(url, data) {
  const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
  return fetch("http://localhost:8080" + url, {
    method: 'POST',
    mode: 'cors',
    headers: {
      "X-CSRF-TOKEN": token,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  })
    .then(response => response.json())
    .catch(error => console.error('Error:', error));
}

/**
 * @type    : function
 * @access  : public
 * @desc    : fetch api 를 통해 get 방식으로 통신 요청하는 메소드
 * @param   : url - 요청을 보낼 주소
 * @return  : fetch
 * @author  : 류민송
 */
function fncSetGetData(url) {
  const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
  return fetch("http://localhost:8080" + url, {
    method: 'GET',
    mode: 'cors',
    headers: {
      "X-CSRF-TOKEN": token,
      'Content-Type': 'application/json',
    }
  })
    .then(response => response.json())
    .catch(error => console.error('Error:', error));
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 띄워진 모달창을 닫는 메소드
 * @return  : void
 * @author  : 류민송
 */
function fncModifyModalClose() {
  document.getElementById("modifyModal").style.display = "none";
}
