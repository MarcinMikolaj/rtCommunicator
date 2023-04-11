// Declare the variable
let showPasswordEyeBtn; //Click this button to show password
let hidePasswordEyeBtn; //Click this button to hide password
let createAccoutLinkBtn; //Click to create new account
let forgotPasswordBtn; //Click to redirect 'forgot password' site

let signInByCredentials; //Click this button to sign in by credentials
let signInByGoogleBtn; //Click this button to sign in by Google
let signInByFacebookBtn; //Click this button to sign in by Facebook

let credentialsLoginInput; // login input
let credentialsPasswordInput; // password input
let rememberMeInput;

let errorMessage; // This error should be display after unauthorized login
let credentialsForm;

let swiper;

const prepareDOMElements = () => {
	// Sign in buttons
	signInByCredentials = document.querySelector('.sign_in_by_credentials_btn');
	signInByGoogleBtn = document.querySelector('.sign_in_by_google_btn');
	signInByFacebookBtn = document.querySelector('.sign_in_by_facebook_btn');
	forgotPasswordBtn = document.querySelector('.forgot_password_btn');

	//login, password, remember-me inputs
	credentialsLoginInput = document.querySelector('.credentials_login_input');
	credentialsPasswordInput = document.querySelector(
		'.credentials_password_input'
	);
	rememberMeInput = document.querySelector('.remember_me_input');
	 
	
	createAccoutLinkBtn = document.querySelector('.create_accout_link_btn');	
	credentialsForm = document.querySelector('.credentials-from');
	
	//error message
	errorMessage = document.querySelector('.error_message');
	
	// show, hide password buttons
	showPasswordEyeBtn = document.querySelector('.show_password_eye_btn');
	hidePasswordEyeBtn = document.querySelector('.hide_password_eye_btn');
};

const prepareDOMEvents = () => {
	// call sign in function by click or enter event
	signInByCredentials.addEventListener('click', signInFunction);
	credentialsPasswordInput.addEventListener('keyup', signInFuction_enter_key);
	credentialsLoginInput.addEventListener('keyup', signInFuction_enter_key);
	
	// sign in by google or facebook redirect
	signInByGoogleBtn.addEventListener('click', signInByGoogleRedirect);
	signInByFacebookBtn.addEventListener('click', signInByFacebookRedirect);

	// show and hide password
	showPasswordEyeBtn.addEventListener('click', showPasswordEye);
	hidePasswordEyeBtn.addEventListener('click', hidePasswordEye);
	
	//redirect to create account
	createAccoutLinkBtn.addEventListener('click', registerRedirect);
	
	//redirect to 'forgot password'
	forgotPasswordBtn.addEventListener('click', forgotPasswordRedirect);
	
};

const main = () => {
	prepareDOMElements();
	prepareDOMEvents();
	reset();
	errorMessage.style.display = 'none';
};

const signInFuction_enter_key = (event) => {
	if (event.keyCode === 13)
		signInFunction();
};

const signInFunction = () => {
	sendCredentialsToServer(credentialsLoginInput.value, credentialsPasswordInput.value, rememberMeInput.checked);
	reset();
};

const reset = () => {
	credentialsLoginInput.value = '';
	credentialsPasswordInput.value = '';
};

//Redirect to sign in by Google site
const signInByGoogleRedirect = () => {window.location.href = "http://localhost:8080/oauth2/authorization/google";}

//Redirect to sign in by Facebook site
const signInByFacebookRedirect = () => {window.location.href = "http://localhost:8080/oauth2/authorization/facebook";}

//Redirect to Forgot password site
const forgotPasswordRedirect = () => {window.location.href = "http://localhost:8080/app/forgot/password";}

//Redirect to Forgot password site
const registerRedirect = () => {window.location.href = "http://localhost:8080/app/register";}

const redirectToAppPanel = () => {window.location.href = "http://localhost:8080/app/panel";};

const sendCredentialsToServer = (email, password, remember_me) => {
	fetch('http://localhost:8080/app/login', {
		method: 'POST',
		headers: {
			'Accept': 'application/json',
			'Content-type': 'application/json',
		},
		'Access-Control-Allow-Origin': '*',
		body: JSON.stringify({
			email: email,
			password: password,
			remember_me: remember_me
		}),
	}).then((response) => {
		if(response.status === 200)
			redirectToAppPanel();
		else if(response.status === 401)
			errorMessage.style.display = 'flex';
		console.log(response.json())
    }).catch((error) => console.log(error))
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


// Swiper js configuration
swiper = new Swiper('.swiper', {
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

	
	pagination: {
		el: '.swiper-pagination',
	},

	navigation: {
		nextEl: '.swiper-button-next',
		prevEl: '.swiper-button-prev',
	},

	scrollbar: {
		el: '.swiper-scrollbar',
	},
});

addEventListener('DOMContentLoaded', main);
