// Declare the variable
let showPasswordEyeBtn; //Click this button to show password
let hidePasswordEyeBtn; //Click this button to hide password

let signInByCredentials; //Click this button to sign in by credentials
let signInByGoogleBtn; //Click this button to sign in by Google
let signInByFacebookBtn; //Click this button to sign in by Facebook

let credentialsPasswordInput; // login input
let credentialsLoginInput;

const prepareDOMElements = () => {
	// Sign in buttons
	signInByCredentials = document.querySelector('.sign_in_by_credentials_btn');
	signInByGoogleBtn = document.querySelector('.sign_in_by_google_btn');
	signInByFacebookBtn = document.querySelector('.sign_in_by_facebook_btn');

	//login , password inputs
	credentialsPasswordInput = document.querySelector(
		'.credentials_password_input'
	);
	credentialsLoginInput = document.querySelector('.credentials_login_input');

	// show, hide password buttons
	showPasswordEyeBtn = document.querySelector('.show_password_eye_btn');
	hidePasswordEyeBtn = document.querySelector('.hide_password_eye_btn');
};

const prepareDOMEvents = () => {
	//Call sign in function by click or enter event
	signInByCredentials.addEventListener('click', signInFuction);
	credentialsPasswordInput.addEventListener('keyup', signInFuction_enter_key);
	credentialsLoginInput.addEventListener('keyup', signInFuction_enter_key);

	// show and hide password
	showPasswordEyeBtn.addEventListener('click', showPasswordEye);
	hidePasswordEyeBtn.addEventListener('click', hidePasswordEye);
};

const main = () => {
	prepareDOMElements();
	prepareDOMEvents();
	reset();
};

const signInFuction_enter_key = (event) => {
	if (event.keyCode === 13) {
		signInFuction();
	}
};

const signInFuction = () => {
	console.log('Sign In by credentials func()');
	reset();
};

const reset = () => {
	credentialsLoginInput.value = '';
	credentialsPasswordInput.value = '';
};

const showPasswordEye = () => {
	credentialsPasswordInput.type = 'text';
	showPasswordEyeBtn.style.display = 'none';
	hidePasswordEyeBtn.style.display = 'flex';
};

const hidePasswordEye = () => {
	credentialsPasswordInput.type = 'password';
	hidePasswordEyeBtn.style.display = 'none';
	showPasswordEyeBtn.style.display = 'flex';
};

const sendCredentialsToServer = () => {
	fetch('http://localhost:8080/loginform', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
		},
		'Access-Control-Allow-Origin': '*',
		body: JSON.stringify({
			login: 'Marcin',
			password: 'Mikołajczyk',
		}),
	}).then((res) => console.log(res));
};

// Swiper js configuration
const swiper = new Swiper('.swiper', {
	effect: 'cube',
	cubeEffect: {
		shadow: false,
		slideShadows: false,
	},

	autoplay: {
		delay: 4000,
		disableOnInteraction: false,
	},

	speed: 500,
	loop: true,
	grabCursor: true,

	// If we need pagination
	pagination: {
		el: '.swiper-pagination',
	},

	// Navigation arrows
	navigation: {
		nextEl: '.swiper-button-next',
		prevEl: '.swiper-button-prev',
	},

	// And if we need scrollbar
	scrollbar: {
		el: '.swiper-scrollbar',
	},
});

addEventListener('DOMContentLoaded', main);
