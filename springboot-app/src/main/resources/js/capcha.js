var cap = document.getElementById('ctl00_ContentPlaceHolder1_ctl00_txtCaptcha');

if(cap){
	cap.value = document.getElementById('ctl00_ContentPlaceHolder1_ctl00_lblCapcha').textContent;
	document.getElementById('ctl00_ContentPlaceHolder1_ctl00_btnXacNhan').click();
}