// Variables

//buttons
let cancelButton; // cancel change password action btn
let redirectLoginPageBtn; // redirect to login page btn
let sendForgotPasswordReuqestBtn; // send change password requets to server btn

//inputs
let emailInput;

// boxes
let failureMessage; // show when status other than 200
let successMessage; // show when status = 200
let wrapper; // main wrapper

const prepareDOMElelemnts = () => {
	emailInput = document.querySelector('.email-input');
	sendForgotPasswordReuqestBtn = document.querySelector('.send-forgot-password-request-btn');

	//buttons
	cancelButton = document.querySelector('.cancel-button');
	redirectLoginPageBtn = document.querySelector('.redirect-login-page-btn');

	// boxes
	failureMessage = document.querySelector('.failure-message');
	successMessage = document.querySelector('.success-message');
	wrapper = document.querySelector('.wrapper');
};

const prepareDOMEvents = () => {
	sendForgotPasswordReuqestBtn.addEventListener('click', sendForgotPasswordRequestToServer);
	cancelButton.addEventListener('click', loginPageRedirect);
	redirectLoginPageBtn.addEventListener('click', loginPageRedirect);
};

const main = () => {
	prepareDOMElelemnts();
	prepareDOMEvents();
};

const sendForgotPasswordRequestToServer = () => {
	let params = new URLSearchParams({email: emailInput.value})
	fetch('http://localhost:8080/app/forgot/send' + '?'+ params, {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
		},
		'Access-Control-Allow-Origin': '*'
	})
		.then((response) => {
			if (response.status === 200)
				showSuccessMessage();
			 else
				 showFailureMessage();
			console.log(response);
		})
		.catch((error) => console.log(error));
};

const showFailureMessage = () => {
	wrapper.style.display = 'none';
	successMessage.style.display = 'none';
	failureMessage.style.display = 'flex';
};
const showSuccessMessage = () => {
	wrapper.style.display = 'none';
	failureMessage.style.display = 'none';
	successMessage.style.display = 'flex';
};

//Redirect to Login Page
const loginPageRedirect = () => {
	window.location.href = 'http://localhost:8080/app/login';
}

document.addEventListener('DOMContentLoaded', main);
