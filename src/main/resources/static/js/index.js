/**
 * @type    : function
 * @access  : public
 * @desc    : Post방식으로 form태그를 통해서 검색 조건에 필요한 데이터 서버에 전송
 * @return  :
 * @author  : 김주찬
 */
function searchBtn() {
  /*입력한 검색 데이터 form 태그를 통해서 서버에 전송 */
  let searchInfoForm = document.getElementById("searchData");
  searchInfoForm.submit();
  // searchInfoForm.action = "/";
  // searchInfoForm.submit();
}

/**
 * @type    : function
 * @access  : public
 * @desc    : Post방식으로 해당 페이지값과  검색 조건에 필요한 데이터 서버에 전송
 * @return  :
 * @author  : 김주찬
 */
function searchPaging(page){
  let searchData = document.getElementById("searchData");
  searchData.childNodes[2].value = page;
  searchData.action="/";
  searchData.submit();
}
