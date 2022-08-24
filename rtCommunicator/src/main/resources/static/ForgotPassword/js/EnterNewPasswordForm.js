let newPasswordInput;
let changePasswordBtn;
let resultResponse;
let token;

const prepareDOMElements = () => {
	newPasswordInput = document.querySelector('.new-password-input');
	changePasswordBtn = document.querySelector('.send-change-password-request-btn');
	resultResponse = document.querySelector('.result-response');
}

const prepareDOMEvents = () => {
	changePasswordBtn.addEventListener('click', sendChangePasswordRequest);
}

const main = () => {
	prepareDOMElements();
	prepareDOMEvents();
	getTokenFromUrl();
}

const getTokenFromUrl = () => {
	const params = new URLSearchParams(window.location.search);
    token = params.get('token');
}

// Allow send 'change password request' to server
const sendChangePasswordRequest = () => {
	
	fetch('http://localhost:8080/app/forgot/credentials/update', {
		method: 'POST',
		headers: {
			'Accept': 'application/json',
			'Content-type': 'application/json',
		},
		'Access-Control-Allow-Origin': '*',
		body: JSON.stringify({
			password: newPasswordInput.value,
			token: token
		}),
	}).then((response) => {
		console.log(response);
		if(response.status == 200){
			resultResponse.textContent = 'Your password has been changed correctly';
		} else{
			resultResponse.textContent = 'It looks like something has gone wrong:' +'<br />'+'- Make sure you are using the correct link.'+'<br />'
			+' (the link should be delivered to your e-mail after sending the request to change the password)' +'<br />' 
			+'- The time elapsed from the request was not more than 30min Your password has been changed correctly.';
		}})
	  .catch((error) => console.log(error))
	
	console.log('sent change password request to server, new password: ' + newPasswordInput.value);
}

document.addEventListener('DOMContentLoaded', main);