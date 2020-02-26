
/**
 * @type    : function
 * @access  : public
 * @desc    : 검색 년도에 해당하는 미지급 리스트 페이지로 이동함
 * @param    : e - 클릭시 파라미터로 받는 html element
 * @return  : void
 * @author  : 홍성관
 */
function fncSearchUnpaidInList(e){
  const year = e.parentNode.childNodes[3].value;
  location.href = "/admin/unpaidInList/" + year;
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 검색 년도에 해당하는 미지급 엑셀 파일을 다운로드함
 * @param    : e - 클릭시 파라미터로 받는 html element
 * @return  : void
 * @author  : 홍성관
 */
function fncUnpaidInListExcelDown(e){
  const year = e.parentNode.childNodes[3].value;
  location.href = "/admin/unpaidInList/writerExcel/" + year;
}
