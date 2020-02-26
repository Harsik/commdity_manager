const userIdx = document.getElementById('userIdx');
const updateBtn = document.getElementById('updateBtn');
const deleteBtn = document.getElementById('deleteBtn');
const refererBtn = document.getElementById('refererBtn');

if (deleteBtn != null && userIdx != null && updateBtn != null && refererBtn != null) {
  refererBtn.onclick = function () {
    history.back();
  }

  deleteBtn.onclick = function () {
    if (confirm("삭제하시겠습니까?")) {
      window.location.href = '/admin/userDelete/' + userIdx.innerHTML;
    }
  }
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 유저 정보 등록 시 선택하지 않은 값이 있는지 체크하는 함수
 * @return  : boolean
 * @author  : 류민송
 */
function fncIsValued() {
  const userPosition = document.getElementById('userPosition');
  const userUseYN = document.getElementById('userUseYN');

  if (userPosition.value == 'nonSelected' || userUseYN.value == 'nonSelected') {
    alert("값이 선택되지 않았습니다");
    return false;
  }
  return true;
}
