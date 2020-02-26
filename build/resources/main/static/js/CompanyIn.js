/*
 * @type		: function
 * @access	: public
 * @desc		: 입력한 검색조건으로 목록을 조회한다.
 * @param		: sfrm - searchForm 객체를 대입하기 위한 변수
 * @param		: kinds - 조회하려는 리스트의 종류를 대입하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncCompanyInList(kinds){
  var sfrm = document.searchForm;
  sfrm.searchMoney.value = kinds;
  sfrm.action = "/companyInList";
  sfrm.submit();

}

/*
 * @type		: function
 * @access	: public
 * @desc		: 입력한 검색조건으로 목록을 조회한다.
 * @param		: sfrm - searchForm 객체를 대입하기 위한 변수
 * @param		: kinds - 조회하려는 리스트의 종류를 대입하기 위한 변수
 * @return	: void
 * @author	: 강병수
 */
function fncCompanyInListExcelDown(kinds){
  var sfrm = document.searchForm;
  sfrm.searchMoney.value = kinds;
  sfrm.action = "/companyInListExcelDownload";
  sfrm.submit();
}
