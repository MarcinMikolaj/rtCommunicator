// Declare the variable
let signInBtn; //Click this button to sign in
let credentialsPasswordInput;

let showPasswordEyeBtn;
let hidePasswordEyeBtn;

let signInByGoogleBtn;
let signInByFacebookBtn;

const prepareDOMElements = () => {
	signInBtn = document.querySelector('.sign_in_by_credentials_btn');
	signInByGoogleBtn = document.querySelector('.sign_in_by_google_btn');
	signInByFacebookBtn = document.querySelector('.sign_in_by_facebook_btn');

	credentialsPasswordInput = document.querySelector(
		'.credentials_password_input'
	);

	showPasswordEyeBtn = document.querySelector('.show_password_eye_btn');
	hidePasswordEyeBtn = document.querySelector('.hide_password_eye_btn');
};

const prepareDOMEvents = () => {
	signInBtn.addEventListener('click', signIn);

	// show and hide password
	showPasswordEyeBtn.addEventListener('click', showPasswordEye);
	hidePasswordEyeBtn.addEventListener('click', hidePasswordEye);
};

const main = () => {
	prepareDOMElements();
	prepareDOMEvents();
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

const signIn = () => {
	document.querySelector('.sign_in_by_credentials_btn').value = 'sas';
	console.log(signInBtn.value);
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
