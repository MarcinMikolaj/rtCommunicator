let emailInput;
let sendForgotPasswordReuqestBtn;

const prepareDOMElelemnts = () => {
	emailInput = document.querySelector('.email-input');
	sendForgotPasswordReuqestBtn = document.querySelector('.send-forgot-password-request-btn');
}

const prepareDOMEvents = () => {
	sendForgotPasswordReuqestBtn.addEventListener('click', sendForgotPasswordRequestToServer);
}

const main = () => {
	prepareDOMElelemnts();
	prepareDOMEvents();
}

const sendForgotPasswordRequestToServer = () => {
	
	fetch('http://localhost:8080/app/forgot/send', {
		method: 'POST',
		headers: {
			'Accept': 'application/json',
			'Content-type': 'application/json',
		},
		'Access-Control-Allow-Origin': '*',
		body: JSON.stringify({
			email: emailInput.value,
		}),
	}).then((response) => {console.log(response)})
	  .catch((error) => console.log(error))
}

document.addEventListener('DOMContentLoaded', main);