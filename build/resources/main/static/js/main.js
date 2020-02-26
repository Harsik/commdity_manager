/*
// Get the modal
const modal = document.getElementById("myModal");
// Get the <span> element that closes the modal
const span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
};

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
};

function modal_popup(){
 modal.style.display = "block"
}
*/

/**
 * Request Ajax
 * */
/*

const token = document.querySelector("meta[name='_csrf']").content;
const header = document.querySelector("meta[name='_csrf_header']").content;

var xhttp = new XMLHttpRequest();
*/

/*
if(ischeckbox)
{
  lists = document.getElementsByName('product_chk').values();

}
else
{
  console.log("checkbox empty!");

}*/
/*

document.querySelector("input[name='product_chk']:checked").(function(i) {
    var result = document.getElementsByName("product_chk").values();

    lists.push(result);
});
*/
/*

document.querySelector(".reg_buttons").addEventListener('click',function(e){

  console.log("test start");

  var productLists=[];
  var userNm = document.getElementById("selectbox_chk").value;
  var CarryoutDt = document.getElementById("carryoutdt").value;
  var CarryinDt= document.getElementById("carryindt").value;
  var usePlace = document.getElementById("useplace").value;;
  var useYN = document.querySelectorAll('input[type="radio"]:checked')[0].value;
  var ischeckbox = document.querySelectorAll('input[type="checkbox"]:checked');
  var result;
  var start_nm;
  var finalnm=[];

  var productstatus = (useYN == 'Y') ? '미사용' : '사용중';
  var resultUseYN = useYN == 'Y' ? '반입' : '미반입';

  /!*체크박스 값 List에 담기*!/
  for(var i=0; i <ischeckbox.length; i++){
    productLists.push(ischeckbox[i].value);
    result = ischeckbox[0].value;
    start_nm =result.indexOf(':',0);

    if(start_nm != null)
      finalnm[i] = result.substr(start_nm+1,3);

  }


  const jdata = {
    "boardCompany" : productLists
    /!*"boardCarryoutDt" : CarryoutDt,
    "boardUsernm" : userNm,
    "boardItem" : finalnm,
    "boardCarryYN" : productstatus,
    "boardUsePlace" : usePlace,
    "boardCarryYN" :  resultUseYN*!/
  };
  const xhr = new XMLHttpRequest();

  xhttp.onreadystatechange = function (error) {
    console.log(error);
  };
  xhr.open('POST','/getBoard',true);
  xhr.setRequestHeader('Content-type','application/json');
  xhr.setRequestHeader(header, token);


  xhr.send(JSON.stringify(jdata));

});

*/








// var token = $("meta[name='_csrf']").attr'_csrf_header'("content");
// var header = $("meta[name=]").attr("content");

// document.querySelector(".product_submit_btn").addEventListener('click',function(e){
//   // jsonView = {"cate_company": document.getElementsByName("cate_company")[0]};
//   // jsonView = {"cate_company": document.querySelector("input[name=cate_company]")};
//   const jsonView = {"cate_company": document.querySelector("input[name=cate_company]")};
//   const xhr = new XMLHttpRequest();
//
//   xhr.open('POST', '/cateMainReg',true);
//   // xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
//   xhr.setRequestHeader(header, token);
//
//   xhr.onload = () => {
//     if(this.status == 200){
//       console.log(this.responseText);
//     }
//   }
//   xhr.send(document.querySelector("input[name=cate_company]"));
// });

// document.getElementById("main_cate").addEventListener('submit', fn_save_mainCate);
//
// function fn_save_mainCate(e){
//   alert("dd");
//   e.preventDefault();
//
//   const cate_company = document.getElementsByName("cate_company").values();
//   alert(cate_company);
//
//   return false;
// }

// document.querySelector(".product_submit_btn").addEventListener('click',function(e){
//   console.log("check");
//   const xhr = new XMLHttpRequest();
//   xhr.onreadystatechange = () =>{
//    if(this.readyState == 4 && this.status == 200){
//      // document.getElementsByName("cate_company").values();
//      xhr.open("post", "cateMainReg", true);
//      xhr.setRequestHeader("Contesnt-type", "application/x-www-form-urlencoded");
//      xhr.send(document.getElementsByName("cate_company").values());
//    }
//   }
// });

// document.querySelector(".product_submit_btn").addEventListener('click',function(e){
//   const jSonURL = "/cateMainReg";
//   const getDataAjax = url =>{
//     const xhr = new XMLHttpRequest();
//     xhr.open("post", url, true);
//     xhr.responseType = "json";
//     xhr.onreadystatechange = () =>{
//       if(xhr.readyState === 4 ){  // 4 means request is done
//         if(xhr.status === 200){   // 200 means status is sucessful
//           for(let key in xhr.response)
//         }
//       }
//     }
//   }
//  alert("확인");
// });

// [].forEach.call(pro_btn, function(el){
//   el.addEventListener('click',function(){
//     alert("확인");
//   }, false);
// });


// function main_Category_Reg(){
//   alert("확인");
//   const req = new XMLHttpRequest();
//   req.open("GET","/cateMainReg",true);
//   req.onreadystatechange = function(){
//     if(req.readyState != 4 || req.status != 200) return;
//     alert("정상적으로 등록 되었습니다.");
//   }
// }
