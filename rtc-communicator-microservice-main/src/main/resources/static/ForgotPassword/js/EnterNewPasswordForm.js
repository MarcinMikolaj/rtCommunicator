// Click this button to show password.
let showPasswordEyeBtn;

// Click this button to hide password.
let hidePasswordEyeBtn;
let redirectLoginPageBtn;
let redirectForgotPasswordBtn;
let confirmBtn;
let cancelBtn;
let wrapper;
let successMessage;
let newPasswordInput;

// Authentication token from Url
let token;

// Error message
let errorMessage;

const prepareDOMElements = () => {
	newPasswordInput = document.querySelector('.new-password-input');
	confirmBtn = document.querySelector('.send-change-password-request-btn');

	// Buttons
	redirectLoginPageBtn = document.querySelector('.redirect-login-page-btn');
	redirectForgotPasswordBtn = document.querySelector('.request-change-password-link');
	cancelBtn = document.querySelector('.cancel-button');

	// Wrappers
	wrapper = document.querySelector('.wrapper');
	successMessage = document.querySelector('.success-message');
	errorMessage = document.querySelector('.error-message');

	// Show, hide password buttons
	showPasswordEyeBtn = document.querySelector('.show_password_eye_btn');
	hidePasswordEyeBtn = document.querySelector('.hide_password_eye_btn');
};

const prepareDOMEvents = () => {

	// Buttons
	confirmBtn.addEventListener('click', sendChangePasswordRequest);
	cancelBtn.addEventListener('click', loginPageRedirect);
	redirectLoginPageBtn.addEventListener('click', loginPageRedirect);
	redirectForgotPasswordBtn.addEventListener('click', forgotPasswordRedirect);

	// Show and hide password
	showPasswordEyeBtn.addEventListener('click', showPasswordEye);
	hidePasswordEyeBtn.addEventListener('click', hidePasswordEye);
};

const main = () => {
	prepareDOMElements();
	prepareDOMEvents();
	getTokenFromUrl();
	newPasswordInput.value = '';
};

const getTokenFromUrl = () => {
	const params = new URLSearchParams(window.location.search);
	token = params.get('token');
};

// Allow sent 'change password request' to server.
const sendChangePasswordRequest = () => {
	let params = new URLSearchParams({
		password: newPasswordInput.value,
		token: token,
	})
	fetch('http://localhost:8080/app/forgot/api/execute' + '?' + params, {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
		},
		'Access-Control-Allow-Origin': '*',
	})
		.then(response => {
			if(response.status === 200)
				return response;
			else
				return response.json()
		})
		.then(response => {
			console.log(response);
			if (response.status === 200)
				showSuccessMessage();
			else if(response.status === 400 && response.hasOwnProperty('errors'))
				showErrorMessageInUI(response.errors[0].defaultMessage);
			else
				showErrorMessageInUI("Looks like something went wrong ! Try again")
		})
		.catch((error) => console.log(error));
};

// Redirect to Login Page.
const loginPageRedirect = () => {window.location.href = 'http://localhost:8080/app/login'};

// Redirect to page where user can start forgot password process.
const forgotPasswordRedirect = () => {window.location.href = 'http://localhost:8080/app/forgot/password'}

const showPasswordEye = () => {
	newPasswordInput.type = 'text';
	showPasswordEyeBtn.style.display = 'none';
	hidePasswordEyeBtn.style.display = 'flex';
};

const showErrorMessageInUI = (message) => {
	errorMessage.innerHTML = message;
	errorMessage.style.display='flex'
}

const hidePasswordEye = () => {
	newPasswordInput.type = 'password';
	hidePasswordEyeBtn.style.display = 'none';
	showPasswordEyeBtn.style.display = 'flex';
};

// Show success message if the server returned information about the success of changing the password.
const showSuccessMessage = () => {
	errorMessage.style.display='none'
	wrapper.style.display = 'none';
	successMessage.style.display = 'flex';
};

document.addEventListener('DOMContentLoaded', main);
