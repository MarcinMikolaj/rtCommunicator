// Variables
let currentluSelectedRoomId;

let friendList; // Presents list of friends, ul element
let searchFriendInput; // Field to enter a friend nick to find him

// Communicator
let communicatorContent; // Present list of messages between friends
let enterMessageInput; // This is input for client messages (this messages will be sent to friend)
let sendMessageBtn; // Send message to friend button

// Main boxes
let roomBox;
let communicatorBox;
let createGroupBox;

// Menu boxes
let addFriendBox;
let managerRoomBox;
let menuBox;

// Open buttons
let openVideoCallBtn;
let openPhoneCallBtn;
let openManageRoomBtn;

// Menu buttons
let openRoomsBoxBtn;
let openAddFriendBoxBtn;
let openCreateGroupBoxBtn;
let logoutBtn;

// other buttons
let showOnlyFriendsBoxBtn; // Show friend-box button, ony for devices 769 breakpoint
let showMenuBtn; // Allow you to display menu box
let closeManagerRoomBoxBtn;


// manage room inputs
let createRoomInput;
let addFriendInput;
let addNewUserToRoomInput; 
let removeUserFromRoomInput;
let removeRoomInput; 
let renameRoomInput;

//manage room buttons
let createRoomBtn;
let addNewFriendBtn;
let addNewUserToRoomBtn; 
let removeUserFromRoomBtn; 
let removeRoomBtn;
let renameRoomBtn;
let leaveRoomBtn;

// Prepare DOM Elements
const prepareDOMElements = () => {
	friendList = document.getElementById('room-list');
	
	searchFriendInput = document.querySelector('.search-friend-input');

	communicatorContent = document.querySelector('.communicator-content');
	enterMessageInput = document.querySelector('.enter-message-input');
	sendMessageBtn = document.querySelector('.send-message-btn');

	// boxes
	roomBox = document.querySelector('.friends-box');
	addFriendBox = document.querySelector('.add-friend-box');
	communicatorBox = document.querySelector('.communicator-box');
	managerRoomBox = document.querySelector('.manage-room-box');
	menuBox = document.querySelector('.menu-box');
	createGroupBox = document.querySelector('.create-group-box');

	// open buttons
	openVideoCallBtn = document.querySelector('.open-video-call-btn');
	openPhoneCallBtn = document.querySelector('.open-phone-call-btn');
	openManageRoomBtn = document.querySelector('.open-manage-room-btn');

	// menu buttons
	openRoomsBoxBtn = document.querySelector('.open-friends-box-btn');
	openAddFriendBoxBtn = document.querySelector('.open-add-friend-box-btn');
	openCreateGroupBoxBtn = document.querySelector('.open-create-group-box-btn');
	logoutBtn = document.querySelector('.logout-btn');
	

	// other buttons
	showOnlyFriendsBoxBtn = document.querySelector('.show-only-friends-box-btn');
	showMenuBtn = document.querySelectorAll('.show-menu-btn');
	closeManagerRoomBoxBtn = document.querySelector('.close-manage-room-box-btn');
	
	// manage room inputs
	createRoomInput = document.querySelector('.create-room-input');
    addFriendInput = document.querySelector('.add-friend-input');
	addNewUserToRoomInput = document.querySelector('.add-new-user-to-room-input');
	removeUserFromRoomInput = document.querySelector('.remove-user-from-room-input');
	removeRoomInput = document.querySelector('.remove-room-input');
	renameRoomInput = document.querySelector('.rename-room-input');
	
	
	//manage room buttons
	createRoomBtn = document.querySelector('.create-room-btn');
	addNewFriendBtn = document.querySelector('.add-new-friend-btn');
	addNewUserToRoomBtn = document.querySelector('.add-new-user-to-room-btn');
	removeUserFromRoomBtn = document.querySelector('.remove-user-from-room-btn');
	removeRoomBtn = document.querySelector('.remove-room-btn');
	renameRoomBtn = document.querySelector('.rename-room-btn');
	leaveRoomBtn = document.querySelector('.leave-room-btn');
	
};


// Prepare DOM Events
const prepareDOMEvents = () => {
	searchFriendInput.addEventListener('keyup', searchFriend);

    friendList.addEventListener('click', showCommunicatorBoxForMobile);

	enterMessageInput.addEventListener(
		'keypress',
		addRightMessageToUIByEnterKeyPress
	);
	sendMessageBtn.addEventListener('click', addRightMessageToUI);

	// Menu open box buttons
	openRoomsBoxBtn.addEventListener('click', openFriendsBox);
	openAddFriendBoxBtn.addEventListener('click', openAddFriendBox);
	openCreateGroupBoxBtn.addEventListener('click', openCreateGroupBox);
	

	showOnlyFriendsBoxBtn.addEventListener('click', showFriendsBoxForMobile);
	showMenuBtn.forEach((btn) => btn.addEventListener('click', openMenuBox));

	openManageRoomBtn.addEventListener('click', openManageRoomBox);
	closeManagerRoomBoxBtn.addEventListener('click', closeManagerRoomBox);
	
	
	// manage room actions
	createRoomBtn.addEventListener('click', createRoomRequest);
	addNewFriendBtn.addEventListener('click', addFiendRequest);
	addNewUserToRoomBtn.addEventListener('click', addUserToRoomRequest);
	removeUserFromRoomBtn.addEventListener('click', removeUserFromRoomRequest); 
    removeRoomBtn.addEventListener('click', removeRoomRequest);
    renameRoomBtn.addEventListener('click', renameRoomRequest);
    leaveRoomBtn.addEventListener('click', leaveRoomRequest);
    
    // logout
	logoutBtn.addEventListener('click', logoutRequest);
    
};

const main = () => {
	prepareDOMElements();
	prepareDOMEvents();
	reset();
	getRooms();
};

const reset = () => {
	searchFriendInput.value = '';
	enterMessageInput.value = '';

	addFriendBox.style.display = 'none';
	menuBox.style.display = 'none';
	createGroupBox.style.display = 'none';

	// communicatorContent.innerHTML = ''; // clear message list
	
	resteManageRoomInputs();
	
};

//reset manage room inputs
const resteManageRoomInputs = () => {
	createRoomInput.value = '';
	addFriendInput.value = '';
	addNewUserToRoomInput.value = ''; 
    removeUserFromRoomInput.value = '';
    removeRoomInput.value = ''; 
    renameRoomInput.value = ''; 
}

// ***********************************************************
// ---------------- Logout Requests -----------------
// ***********************************************************

const logoutRequest = () => {
	fetch('http://localhost:8080/app/logout', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({
			logout: true
		}),
	})
		.then((response) => {
			console.log(response);
			
			if(response.redirected === true){
				window.location.replace(response.url);
			}
		})
		.catch((error) => console.log(error));
};


// ***********************************************************
// ---------------- Room Manager Requests -----------------
// ***********************************************************

// This method is responsible for sending add friend request
const createRoomRequest = () => {
	fetch('http://localhost:8080/app/rtc/room/create', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({
			roomName: createRoomInput.value
		}),
	})
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			console.log(data);
			if(data.success === true){
				loadDeliveredRooms(data.rooms);
			}
			addStatementMessageToRoomManagerInUI(data.statements);
		})
		.catch((error) => console.log(error));
		
		resteManageRoomInputs();
		
};

const getRooms = () => {
	fetch('http://localhost:8080/app/rtc/room/get', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({
			userNick: 'none',
		}),
	})
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			console.log(data);
			if(data.success === true){
				loadDeliveredRooms(data.rooms);
			}
			addStatementMessageToRoomManagerInUI(data.statements);
		})
		.catch((error) => console.log(error));
		
		resteManageRoomInputs();
};

// This method is responsible for sending add friend request
const addFiendRequest = () => {
	fetch('http://localhost:8080/app/rtc/room/create/with/user', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({
			userNick: addFriendInput.value,
			roomId: currentluSelectedRoomId
		}),
	})
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			console.log(data);
			if(data.success === true){
				loadDeliveredRooms(data.rooms);
			}
			addStatementMessageToRoomManagerInUI(data.statements);
		})
		.catch((error) => console.log(error));
		
		resteManageRoomInputs();
		
};


// This method is responsible for sending 'add user to actual choosen room' request
const addUserToRoomRequest = () => {
	fetch('http://localhost:8080/app/rtc/room/user/add', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({
			userNick: addNewUserToRoomInput.value,
			roomId: currentluSelectedRoomId
		}),
	})
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			console.log(data);
			if(data.success === true){
				loadDeliveredRooms(data.rooms);
			}
			addStatementMessageToRoomManagerInUI(data.statements);
		})
		.catch((error) => console.log(error));
		
		resteManageRoomInputs();
		
};

// This method is responsible for sending 'add user to actual choosen room' request
const removeUserFromRoomRequest = () => {
	fetch('http://localhost:8080/app/rtc/room/user/remove', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({
			userNick: removeUserFromRoomInput.value,
			roomId: currentluSelectedRoomId
		}),
	})
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			console.log(data);
			if(data.success === true){
				loadDeliveredRooms(data.rooms);
			}
			addStatementMessageToRoomManagerInUI(data.statements);
		})
		.catch((error) => console.log(error));
		
		resteManageRoomInputs();
		
};


// This method is responsible for sending 'rename room' request
const renameRoomRequest = () => {
	fetch('http://localhost:8080/app/rtc/room/name/update', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({
			roomId: currentluSelectedRoomId,
			roomName: renameRoomInput.value
		}),
	})
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			console.log(data);
			if(data.success === true){
				loadDeliveredRooms(data.rooms);
			}
			addStatementMessageToRoomManagerInUI(data.statements);
		})
		.catch((error) => console.log(error));
		
		resteManageRoomInputs();
		
};

// This method is responsible for sending remove room request
const removeRoomRequest = () => {
	fetch('http://localhost:8080/app/rtc/room/remove', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({
			roomName: removeRoomInput.value,
			roomId: currentluSelectedRoomId
		}),
	})
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			console.log(data);
			
			if(data.success === true){
				loadDeliveredRooms(data.rooms);
			}
			addStatementMessageToRoomManagerInUI(data.statements);
		})
		.catch((error) => console.log(error));
		
		resteManageRoomInputs();
		
};

// This method is responsible for sending 'leave room' request
const leaveRoomRequest = () => {
	fetch('http://localhost:8080/app/rtc/room/user/leave', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({
			roomId: currentluSelectedRoomId
		}),
	})
		.then((response) => {
			return response.json();
		})
		.then((data) => {
			console.log(data);
			
			if(data.success === true){
				loadDeliveredRooms(data.rooms);
			}
			addStatementMessageToRoomManagerInUI(data.statements);
		})
		.catch((error) => console.log(error));
		
		resteManageRoomInputs();
		
};


// ***********************************************************
// ---------------- UI Manage -----------------
// ***********************************************************


// Allows you to search for friends using the given pattern
const searchFriend = (event) => {
	let searchStringValue = event.target.value.toLowerCase().trim();

	for (const friend of friendList.children) {
		const friendNameValue = friend
			.querySelector('.friend-name')
			.innerHTML.toLowerCase();

		if (friendNameValue.indexOf(searchStringValue) !== -1) {
			friend.style.display = 'flex';
		} else {
			friend.style.display = 'none';
		}
	}
};




const showCommunicatorBoxForMobile = (e) => {
	
	let currentRoom = e.target.closest('.friend');
	
	console.log(e.target.closest('.friend'))
	console.log(e.target.closest('.friend').getAttribute('roomId'));
	
	currentluSelectedRoomId = currentRoom.getAttribute('roomId')
	
	if (window.innerWidth > 769) {
		return;
	}

	roomBox.style.display = 'none';
	communicatorBox.style.display = 'flex';
	communicatorContent.scrollTop = communicatorContent.scrollHeight;
};

const openCreateGroupBox = () => {
	menuBox.style.display = 'none';
	createGroupBox.style.display = 'flex';
};

const closeCreateGroupBox = () => {
	createGroupBox.style.display = 'none';
	menuBox.style.display = 'flex';
};

const openManageRoomBox = () => {
	if (window.innerWidth > 769) {
		managerRoomBox.style.display = 'flex';
		return;
	}

	managerRoomBox.style.display = 'flex';
	communicatorBox.style.display = 'none';
	roomBox.style.display = 'none';
};

const closeManagerRoomBox = () => {
	if (window.innerWidth > 769) {
		managerRoomBox.style.display = 'none';
		return;
	}

	managerRoomBox.style.display = 'none';
	roomBox.style.display = 'flex';
};

const showFriendsBoxForMobile = () => {
	roomBox.style.display = 'flex';
	communicatorBox.style.display = 'none';
};

const openMenuBox = () => {
	managerRoomBox.style.display = 'none';
	addFriendBox.style.display = 'none';
	roomBox.style.display = 'none';
	createGroupBox.style.display = 'none';
	menuBox.style.display = 'flex';
};

const openFriendsBox = () => {
	addFriendBox.style.display = 'none';
	menuBox.style.display = 'none';
	roomBox.style.display = 'flex';
};

const openAddFriendBox = () => {
	menuBox.style.display = 'none';
	roomBox.style.display = 'none';
	addFriendBox.style.display = 'flex';
};

// Allows you to add a message from you that will be visible in UI by enter keypress
const addRightMessageToUIByEnterKeyPress = (event) => {
	if (event.key !== 'Enter') {
		return;
	}
	addRightMessageToUI();
};

// Allows you to add a message from you that will be visible in UI
const addRightMessageToUI = () => {
	let messageContent = enterMessageInput.value;

	if (messageContent.length >= 1) {
		let message = document.createElement('div');
		message.classList.add('right-message-box');
		message.innerHTML = `<li class="right-message">${messageContent}</li>`;
		communicatorContent.appendChild(message);
		enterMessageInput.value = '';
		communicatorContent.scrollTop = communicatorContent.scrollHeight;
	}
};

// Allows you to add a message from you that will be visible in UI
const addLeftMessageToUI = (content) => {
	let message = document.createElement('div');
	message.classList.add('left-message-box');
	message.innerHTML = `<li class="left-message">${content}</li>`;
	communicatorContent.appendChild(message);
	communicatorContent.scrollTop = communicatorContent.scrollHeight;
};

// ******************************************************************************
// ---------------- Room manager: display rooms in UI -------------------
// ******************************************************************************

const loadDeliveredRooms = (rooms) => { 
	friendList.innerHTML = '';
	rooms.forEach((room) => addFriendToUserInterfaceInUserFriendList(room, 'none', 'none', 'none'));
}

// This function allow add new friend in UI to FrienList,
// attributes: user nick (nick), who send last message (lastMessageCreator), last message in convertation (lastmessageContent), last message time or date (lastMessagetime)
const addFriendToUserInterfaceInUserFriendList = (
	room,
	lastMessageCreator,
	lastmessageContent,
	lastMessagetime
) => {
	if (lastmessageContent.length > 15) {
		lastmessageContent = lastmessageContent.substring(0, 13);
		lastmessageContent += '..';
	}
	
	const friend = document.createElement('li');
	friend.classList.add('friend');
	friend.setAttribute('roomId', room.roomId);
	
	let users = room.users;
	let picture
	
	//load picture
	users.forEach((user) => {
		picture = user.profilePicture.fileInBase64
	});
	
	friend.innerHTML = `<div class="friend-img">
	    <img class="profile-img" src="${picture}" alt="img">
	    <div class="activity-light activity-light-red"></div>
	</div>
	<div class="friend-describe">
	    <p class="friend-name">${room.name}</p>
	    <p class="friend-last-message">${lastMessageCreator}: ${lastmessageContent}${lastMessagetime}</p>
	</div>
	<div class="new-message-notification">
	    <i class="fa-solid fa-comment new-message-notification-img"></i>
	    <p class="new-message-notification-text">New Message</p>
	</div>`;

	friendList.append(friend);
};


// ******************************************************************************
// ---------------- Room manager: display query result messages in UI -------------------
// ******************************************************************************

// Handles the display of the room management response message for the appropriate list.
const addStatementMessageToRoomManagerInUI = (statements) => {
	
	removeAllQueryResultMessageForRoomManager();
	
	statements.forEach((statements) => {
		switch (statements.roomAction) {
			case 'CREATE_ROOM':
			
				loadResultMessageForRoomManagerQuery(statements, 'm_r_o_b_create_room');
				break;
			case 'REMOVE_ROOM':
			
				loadResultMessageForRoomManagerQuery(statements, 'm_r_o_b_remove_room');
				break;
			case 'RENAME_ROOM':
			
				loadResultMessageForRoomManagerQuery(statements, 'm_r_o_b_rename_room');
				break;
			case 'LEAVE_ROOM':
			
				loadResultMessageForRoomManagerQuery(statements, 'm_r_o_b_leave_room');
				break;
			case 'ADD_USER_TO_ROOM':
			
				loadResultMessageForRoomManagerQuery(statements, 'm_r_o_b_add_user_to_room');
				break;
			case 'REMOVE_USER_FROM_ROOM':
			
				loadResultMessageForRoomManagerQuery(statements, 'm_r_o_b_remove_user_from_room');
				break;
			default:
			    console.log(`Unable to handle the type statement, statement.type: ${statements.roomAction}`);
		}
	});
};

// This method adds and displays a message and the execution status of the query operation for the server for room management.
const loadResultMessageForRoomManagerQuery = (statements, className) => {
	
	let li = document.createElement('li');
	li.classList.add('manager-room-option-message');
	li.addEventListener('click', () => li.remove());
	
	
	switch (statements.type) {
     case 'SUCCES_STATEMENT':    
      
          li.classList.add('manager-room-option-success-message');
	      li.innerHTML = `<i class="fa-solid fa-thumbs-up"></i><p>${statements.message}</p>`;
          break;
     case 'ERROR_STATEMENT':
     
         li.classList.add('manager-room-option-fail-message');
	     li.innerHTML = `<i class="fa-solid fa-bomb"></i><p>${statements.message}</p>`;
         break;        
     default:
         console.log(`No message handling of this type. message:${statements.message}`);
    }
	
	document.querySelector('.' + className).appendChild(li);
	
};

// Allows you to delete all created messages related to the query result for room management.
const removeAllQueryResultMessageForRoomManager = () => {
	let liElementsArray = document.querySelectorAll('.manager-room-option-message-box');
	for (var i = 0, len = liElementsArray.length; i < len; i++) {
    liElementsArray[i].innerHTML = '';
   }
}


// ***********************************************************
// ---------------- DOM Content Load -----------------
// ***********************************************************

// call main function
document.addEventListener('DOMContentLoaded', main);
