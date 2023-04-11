// Declare the variable

// Click this button to show password.
let showPasswordEyeBtn;
// Click this button to hide password.
let hidePasswordEyeBtn;

let redirectLoginPageBtn;
let confirmBtn;
let cancelBtn;

let wrapper;
let successMessage;
let newPasswordInput;
// Authentication token from Url
let token;

const prepareDOMElements = () => {
	newPasswordInput = document.querySelector('.new-password-input');
	confirmBtn = document.querySelector('.send-change-password-request-btn');

	// buttons
	redirectLoginPageBtn = document.querySelector('.redirect-login-page-btn');
	cancelBtn = document.querySelector('.cancel-button');

	wrapper = document.querySelector('.wrapper');
	successMessage = document.querySelector('.success-message');

	// show, hide password buttons
	showPasswordEyeBtn = document.querySelector('.show_password_eye_btn');
	hidePasswordEyeBtn = document.querySelector('.hide_password_eye_btn');
};

const prepareDOMEvents = () => {
	confirmBtn.addEventListener('click', sendChangePasswordRequest);
	cancelBtn.addEventListener('click', loginPageRedirect);
	redirectLoginPageBtn.addEventListener('click', loginPageRedirect);

	// show and hide password
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
	fetch('http://localhost:8080/app/forgot/credentials/update' + '?' + params, {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
		},
		'Access-Control-Allow-Origin': '*',
	})
		.then((response) => {
			console.log(response);
			if (response.status == 200)
				showSuccessMessage();
		})
		.catch((error) => console.log(error));
};

// Redirect to Login Page.
const loginPageRedirect = () => {
	window.location.href = 'http://localhost:8080/app/login';
};

const showPasswordEye = () => {
	newPasswordInput.type = 'text';
	showPasswordEyeBtn.style.display = 'none';
	hidePasswordEyeBtn.style.display = 'flex';
};

const hidePasswordEye = () => {
	newPasswordInput.type = 'password';
	hidePasswordEyeBtn.style.display = 'none';
	showPasswordEyeBtn.style.display = 'flex';
};

// Show success message if the server returned information about the success of changing the password.
const showSuccessMessage = () => {
	wrapper.style.display = 'none';
	successMessage.style.display = 'flex';
};

document.addEventListener('DOMContentLoaded', main);
