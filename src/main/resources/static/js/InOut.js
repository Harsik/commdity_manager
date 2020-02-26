const searchBtn = document.getElementById('searchBtn');
const downloadBtn = document.getElementById('downloadBtn');
const yearSelector = document.getElementById('yearSelector');
const hiddenYear = document.getElementById('hiddenYear');

if (searchBtn != null) {
  searchBtn.onclick = function () {
    location.href = "/inOutList?year=" + yearSelector.value;
  }
}

if (downloadBtn != null) {
  downloadBtn.onclick = function () {
    location.href = '/inOutListDownLoad?year=' + hiddenYear.value;
  }
}
