// Declare the variable
let showPasswordEyeBtn; //Click this button to show password
let hidePasswordEyeBtn; //Click this button to hide password
let createAccoutLinkBtn; //Click to create new account

let signInByCredentials; //Click this button to sign in by credentials
let signInByGoogleBtn; //Click this button to sign in by Google
let signInByFacebookBtn; //Click this button to sign in by Facebook

let credentialsLoginInput; // login input
let credentialsPasswordInput; // password input

let grantedAuthorizationToken;

let credentialsForm;


const prepareDOMElements = () => {
	// Sign in buttons
	signInByCredentials = document.querySelector('.sign_in_by_credentials_btn');
	signInByGoogleBtn = document.querySelector('.sign_in_by_google_btn');
	signInByFacebookBtn = document.querySelector('.sign_in_by_facebook_btn');

	//login , password inputs
	credentialsLoginInput = document.querySelector('.credentials_login_input');
	credentialsPasswordInput = document.querySelector(
		'.credentials_password_input'
	);
	
	createAccoutLinkBtn = document.querySelector('.create_accout_link_btn');
	
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
	
	createAccoutLinkBtn.addEventListener('click', redirectToAppPanel);
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
	//credentialsForm.submit();
	sendCredentialsToServer(credentialsLoginInput.value, credentialsPasswordInput.value)
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

const sendCredentialsToServer = (email, password) => {
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
		}),
	}).then((response) => {grantedAuthorizationToken = response.headers.get('Authorization')})
	  .then(() => console.log(grantedAuthorizationToken))
	  //.then(() => setCookie('token',grantedAuthorizationToken, 1))
	  .then(() => redirectToAppPanel(grantedAuthorizationToken))
};


const redirectToAppPanel = () => {
	
	window.location.href = "http://localhost:8080/app/panel";
	
	//console.log('authorizationToken');
	//console.log(grantedAuthorizationToken);
	
	//fetch('http://localhost:8080/app/redirect/panel', {
	//	method: 'POST',
	//	headers: {
	//		'Content-Type': 'application/json',
	//		'Authorization': grantedAuthorizationToken,
	//	},
		//redirect: 'follow'
	//})
};


//.then((response) => {return response.text()})
//	  .then((html) => {document.body.innerHTML = html})
//	  .catch((err) => console.log(err))



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
