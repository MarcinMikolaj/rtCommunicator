// Variables
let registerBtn;
let redirectLoginPageBtn;

let emailInput;
let nickInput;
let passwordInput;
let statementCheckBox;
let profilePictureInput;

let showPasswordBtn;
let hidePasswordBtn;

let errorEmail;
let errorNick;
let errorPassword;
let errorStatement;
let errorPicture;

// Boxes
let wrapper; // the wrapper contains the registration form
let successMessageBox; // the box contains success message

// Prepare DOM Elements
const prepareDOMElements = () => {
	registerBtn = document.querySelector('.register-btn');
	redirectLoginPageBtn = document.querySelectorAll('.redirect-login-page-btn');

	emailInput = document.querySelector('.email-input');
	nickInput = document.querySelector('.nick-input');
	passwordInput = document.querySelector('.password-input');
	statementCheckBox = document.querySelector('.statement-checkbox');
	profilePictureInput = document.querySelector('.profile-picture-input');

	showPasswordBtn = document.querySelector('.show-passwor-btn');
	hidePasswordBtn = document.querySelector('.hide-passwor-btn');

	errorEmail = document.querySelector('.error-email');
	errorPassword = document.querySelector('.error-password');
	errorNick = document.querySelector('.error-nick');
	errorStatement = document.querySelector('.error-statement');
	errorPicture = document.querySelector('.error-picture');

	wrapper = document.querySelector('.wrapper');
	successMessageBox = document.querySelector('.success-message-box');
};

// Prepare DOM Events
const prepareDOMEvents = () => {
	registerBtn.addEventListener('click', send);
	
	
	redirectLoginPageBtn.forEach((btn) => {
		btn.addEventListener('click', redirectloginPage);
	})

	profilePictureInput.addEventListener('change', setAddedUserImgNameInUI);

	showPasswordBtn.addEventListener('click', showPasswordInUI);
	hidePasswordBtn.addEventListener('click', hidePasswordInUI);
};

const main = () => {
	prepareDOMElements();
	prepareDOMEvents();
	reset();
};

const reset = () => {
	emailInput.value = '';
	nickInput.value = '';
	passwordInput.value = '';

	successMessageBox.style.display = 'none';

	resetErrorMessage();
};

const resetErrorMessage = () => {
	errorEmail.style.display = 'none';
	errorNick.style.display = 'none';
	errorPassword.style.display = 'none';
	errorStatement.style.display = 'none';
	errorPicture.style.display = 'none';

	errorEmail.innerHTML = '';
	errorNick.innerHTML = '';
	errorPassword.innerHTML = '';
	errorStatement.innerHTML = '';
	errorPicture.innerHTML = '';
};

const send = () => {
	resetErrorMessage();

	let file = profilePictureInput.files[0];
	let reader = new FileReader();

	reader.onload = () => {
		console.log(reader.result);
		sendRegisterRequestToServer(reader.result);
	};

	reader.onerror = function (error) {
		console.log('Error: ', error);
	};

	if (!!file) {
		reader.readAsDataURL(file);
	} else {
		sendRegisterRequestToServer(null);
	}
};

// This method is responsible for send register request.
const sendRegisterRequestToServer = (fileInBase64) => {
	let profilePicture = createProfilePictureObject(fileInBase64);

	fetch('http://localhost:8080/app/registration/create', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({
			email: emailInput.value,
			nick: nickInput.value,
			password: passwordInput.value,
			provider: 'local',
			statements: statementCheckBox.checked,
			picture: profilePicture,
		}),
	})
		.then(response => {return response.json();})
		.then((data) => {
			 if (data.status === 200)
			 	showSuccessMessage();
			 else
			    showErrors(new Map(Object.entries(data.errors)));
		})
		.catch((error) => console.log(error));
};

// Allows you to create an object of the user's profile picture that is sent to the server
const createProfilePictureObject = (fileInBase64) => {
	let file = profilePictureInput.files[0];
	let pictureFromUser;

	if (!!fileInBase64) {
		pictureFromUser = {
			name: file.name,
			type: file.type,
			size: file.size,
			fileInBase64: fileInBase64,
		};
	} else {
		pictureFromUser = {
			name: '',
			type: '',
			size: '',
			fileInBase64: '',
		};
	}
	return pictureFromUser;
};

// This method allow redirect user to login page
const redirectloginPage = () => {
	window.location.href = 'http://localhost:8080/app/login';
};

const showPasswordInUI = () => {
	showPasswordBtn.style.display = 'none';
	hidePasswordBtn.style.display = 'flex';
	passwordInput.type = 'text';
};

const hidePasswordInUI = () => {
	showPasswordBtn.style.display = 'flex';
	hidePasswordBtn.style.display = 'none';
	passwordInput.type = 'password';
};

const showErrors = (errorMessages) => {
	errorMessages.forEach((value, key) => {
		switch (key) {
			case 'email':
				showEmailErrorsInUI(value);
				break;
			case 'nick':
				showNickErrorsInUI(value);
				break;
			case 'password':
				showPasswordErrorsInUI(value);
				break;
			case 'statements':
				showStatementErrorsInUI(value);
				break;
			case 'picture.name':
				showPictureErrorsInUI(value);
				break;
			default:
			// none
		}
	});
};

const setAddedUserImgNameInUI = (e) => {
	let pictureName = e.target.files[0].name;
	document.querySelector('.picture-name-p').innerHTML = `${pictureName}`;
};

const showEmailErrorsInUI = (message) => {
	errorEmail.style.display = 'flex';
	let li = document.createElement('li');
	li.innerHTML = `- ${message}`;
	errorEmail.append(li);
};
const showNickErrorsInUI = (message) => {
	errorNick.style.display = 'flex';
	let li = document.createElement('li');
	li.innerHTML = `- ${message}`;
	errorNick.append(li);
};
const showPasswordErrorsInUI = (message) => {
	errorPassword.style.display = 'flex';
	let li = document.createElement('li');
	li.innerHTML = `- ${message}`;
	errorPassword.append(li);
};
const showStatementErrorsInUI = (message) => {
	errorStatement.style.display = 'flex';
	let li = document.createElement('li');
	li.innerHTML = `- ${message}`;
	errorStatement.append(li);
};
const showPictureErrorsInUI = (message) => {
	errorPicture.style.display = 'flex';
	let li = document.createElement('li');
	li.innerHTML = `- ${message}`;
	errorPicture.append(li);
};

const showSuccessMessage = () => {
	wrapper.style.display = 'none';
	successMessageBox.style.display = 'flex';
};

document.addEventListener('DOMContentLoaded', main);
