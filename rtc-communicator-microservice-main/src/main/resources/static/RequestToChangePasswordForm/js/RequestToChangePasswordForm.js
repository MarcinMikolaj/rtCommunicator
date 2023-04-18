// Buttons
// Cancel change password action btn.
let cancelButton;

// Redirect to login page btn.
let redirectLoginPageBtn;

// Send change password requests to server btn.
let sendForgotPasswordRequestBtn;

// Inputs
let emailInput;

// Wrappers.
// Show when status other than 200.
let failureMessage;

// Show when status = 200.
let successMessage;

// Main wrapper.
let wrapper;

const prepareDOMElements = () => {
	// Inputs
	emailInput = document.querySelector('.email-input');

	// Buttons
	sendForgotPasswordRequestBtn = document.querySelector('.send-forgot-password-request-btn');
	cancelButton = document.querySelector('.cancel-button');
	redirectLoginPageBtn = document.querySelector('.redirect-login-page-btn');

	// Wrappers
	failureMessage = document.querySelector('.failure-message');
	successMessage = document.querySelector('.success-message');
	wrapper = document.querySelector('.wrapper');
};

const prepareDOMEvents = () => {
	sendForgotPasswordRequestBtn.addEventListener('click', sendForgotPasswordRequestToServer);
	cancelButton.addEventListener('click', loginPageRedirect);
	redirectLoginPageBtn.addEventListener('click', loginPageRedirect);
};

const main = () => {prepareDOMElements(); prepareDOMEvents();};

const sendForgotPasswordRequestToServer = () => {
	let params = new URLSearchParams({email: emailInput.value})
	fetch('http://localhost:8080/app/forgot/api/initialize' + '?' + params, {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
		},
		'Access-Control-Allow-Origin': '*'
	})
		.then((response) => {
			console.log(response);
			if (response.status === 200)
				showSuccessMessage();
			 else
				 showFailureMessage();
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
const loginPageRedirect = () => {window.location.href = 'http://localhost:8080/app/login';}

document.addEventListener('DOMContentLoaded', main);
