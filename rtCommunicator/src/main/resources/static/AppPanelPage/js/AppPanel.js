let logoutBtn; //Allow the user to log out

const prepareDOMElements = () => {
	logoutBtn = document.querySelector('.logout-now-btn');
}

const prepareDOMEvents = () => {
	logoutBtn.addEventListener('click', logout);
}

const main = () => {
	prepareDOMElements();
	prepareDOMEvents();
}

const logout = () => {
	console.log('logout');
	
	fetch('http://localhost:8080/app/logout', {
		method: 'POST',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',			
		},
		'Access-Control-Allow-Origin': '*',
		body: JSON.stringify({
			logout: true
		})
	}).then((response) => {
		if(response.status === 200){
			successLogout(response);
		}else {
			failLogout(response);
		}})
	  .catch((error) => console.log(error))
	
}

//Called after successful logout
const successLogout = (response) => {
	console.log(response);
	window.location.href = "http://localhost:8080/app/login";
}

//Called after fail logout
const failLogout = (response) => {
	console.log('logout-fail');
	console.log(response);
}

addEventListener('DOMContentLoaded', main);