// Declare the variable
let showPasswordEyeBtn; //Click this button to show password
let hidePasswordEyeBtn; //Click this button to hide password

let redirectLoginPageBtn;
let confirmBtn;
let cancelBtn;

let wrapper;
let successMessage;
let newPasswordInput;
let token; // Authentication token from Url

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

// Allow send 'change password request' to server
const sendChangePasswordRequest = () => {
	fetch('http://localhost:8080/app/forgot/credentials/update', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
		},
		'Access-Control-Allow-Origin': '*',
		body: JSON.stringify({
			password: newPasswordInput.value,
			token: token,
		}),
	})
		.then((response) => {
			console.log(response);
			if (response.status == 200) {
				showSuccessMessage();
			} else {
				console.log('oops something went wrong');
			}
		})
		.catch((error) => console.log(error));

	console.log(
		'sent change password request to server, new password: ' +
			newPasswordInput.value
	);
};

//Redirect to Login Page
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

// Show success messgae if the server returned information about the success of changing the password
const showSuccessMessage = () => {
	wrapper.style.display = 'none';
	successMessage.style.display = 'flex';
};

document.addEventListener('DOMContentLoaded', main);
