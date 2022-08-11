// Declare the variable
let showPasswordEyeBtn; //Click this button to show password
let hidePasswordEyeBtn; //Click this button to hide password

let signInByCredentials; //Click this button to sign in by credentials
let signInByGoogleBtn; //Click this button to sign in by Google
let signInByFacebookBtn; //Click this button to sign in by Facebook

let credentialsLoginInput; // login input
let credentialsPasswordInput; // password input

let credentialsForm;


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
	
	credentialsForm = document.querySelector('.credentials-from');
	

	// show, hide password buttons
	showPasswordEyeBtn = document.querySelector('.show_password_eye_btn');
	hidePasswordEyeBtn = document.querySelector('.hide_password_eye_btn');
};

const prepareDOMEvents = () => {
	//Call sign in function by click or enter event
	signInByCredentials.addEventListener('click', signInFuction);
	credentialsPasswordInput.addEventListener('keyup', signInFuction_enter_key);
	credentialsLoginInput.addEventListener('keyup', signInFuction_enter_key);
	
	//Sign in by google or facebook redirect
	signInByGoogleBtn.addEventListener('click', signInByGoogleRedirect);
	signInByFacebookBtn.addEventListener('click', signInByFacebookRedirect);

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
	credentialsForm.submit();
	reset();
};



const reset = () => {
	credentialsLoginInput.value = '';
	credentialsPasswordInput.value = '';
};




//Redirect to sign in by Google site
const signInByGoogleRedirect = () => {
	window.location.href = "http://localhost:8080/oauth2/authorization/google";
}

//Redirect to sign in by Facebook site
const signInByFacebookRedirect = () => {
	window.location.href = "http://localhost:8080/oauth2/authorization/facebook";
}


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
