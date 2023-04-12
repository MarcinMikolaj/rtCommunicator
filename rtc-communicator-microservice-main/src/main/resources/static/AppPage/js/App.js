// Variables
let currentRoomList; // The list includes all rooms assigned to the logged in user
let currentSelectedRoom; // The conversation room selected by the user
let currentluSelectedRoomId; // Is the identifier of the currently selected room

let currentlyLoggedUser; // Contain email, nick, profilePicture, roomsId (list)
let unreadMessages; // Represents the number of unread messages for each room on a key-value basis (room id: number)

let client; // WebSocket Client

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
let menuBox;
let addFriendBox;
let managerRoomBox;
let manageAccountBox;
let invitationsBox;

// Open buttons
let openVideoCallBtn;
let openPhoneCallBtn;
let openManageRoomBtn;

// Menu buttons
let openRoomsBoxBtn;
let openAddFriendBoxBtn;
let openCreateGroupBoxBtn;
let openManageAccountBoxBtn;
let logoutBtn;
let openInvitationBoxBtn;

// other buttons
let showOnlyFriendsBoxBtn; // Show friend-box button, ony for devices 769 breakpoint
let showMenuBtn; // Allow you to display menu box
let closeManagerRoomBoxBtn;

//-----Actual Logged user information -----
let myProfileImg;

//----- Information about the currently selected room  -----
//provides information to the user about the selected room picture and name
let selectedRoomPicture; // <img> element
let selectedRoomName; // <p> element

//----- Manager Invitations -----
let acceptInvitationBtnCollection;
let declineInvitationBtnCollection;

//----- Manager Room -----
// manage room inputs
let createRoomInput;
let addFriendInput;
let addNewUserToRoomInput; 
let removeUserFromRoomInput;
let removeRoomInput; 
let renameRoomInput;

// manage room buttons
let createRoomBtn;
let addNewFriendBtn;
let addNewUserToRoomBtn; 
let removeUserFromRoomBtn; 
let removeRoomBtn;
let renameRoomBtn;
let leaveRoomBtn;

//----- Manager Account -----
// manage account inputs
let changeNickInput;
let removeAccountInput;
let updateUserEmailInput;
let updateUserPasswordInput;
let updateUserPasswordConfirmByLoginInput;
let updateProfilePictureInput;
// manage account buttons
let changeNickBtn;
let removeAccountBtn;
let updateUserEmailBtn;
let updateUserPasswordBtn;
let updateProfilePictureBtn;

const login_page_adress = 'http://localhost:8080/app/panel'

// Prepare DOM Elements
const prepareDOMElements = () => {
	friendList = document.getElementById('room-list');
	searchFriendInput = document.querySelector('.search-friend-input');
	communicatorContent = document.querySelector('.communicator-content');
	enterMessageInput = document.querySelector('.enter-message-input');
	sendMessageBtn = document.querySelector('.send-message-btn');
	// boxes
	roomBox = document.querySelector('.friends-box');
	communicatorBox = document.querySelector('.communicator-box');
	menuBox = document.querySelector('.menu-box');
	managerRoomBox = document.querySelector('.manage-room-box');
	addFriendBox = document.querySelector('.add-friend-box');
	createGroupBox = document.querySelector('.create-group-box');
	manageAccountBox = document.querySelector('.manage-account-box');
	invitationsBox = document.querySelector('.invitations-box');
	// open buttons
	openVideoCallBtn = document.querySelector('.open-video-call-btn');
	openPhoneCallBtn = document.querySelector('.open-phone-call-btn');
	openManageRoomBtn = document.querySelector('.open-manage-room-btn');
	// menu buttons
	openRoomsBoxBtn = document.querySelector('.open-friends-box-btn');
	openAddFriendBoxBtn = document.querySelector('.open-add-friend-box-btn');
	openCreateGroupBoxBtn = document.querySelector('.open-create-group-box-btn');
	openManageAccountBoxBtn = document.querySelector('.open-manager-account-box-btn');
	logoutBtn = document.querySelector('.logout-btn');
	openInvitationBoxBtn = document.querySelector('.open-invitations-box-btn');
	// other buttons
	showOnlyFriendsBoxBtn = document.querySelector('.show-only-friends-box-btn');
	showMenuBtn = document.querySelectorAll('.show-menu-btn');
	closeManagerRoomBoxBtn = document.querySelector('.close-manage-room-box-btn');
	// actual logged user information
	myProfileImg = document.querySelector('.my-profile-img');
	// Information about the currently selected room
    selectedRoomPicture = document.querySelector('.chosen-friend-img');
	selectedRoomName = document.querySelector('.chosen-friend-nick');
	// manage invitations btn 
    acceptInvitationBtnCollection = document.getElementsByClassName('invitation-accept-button') ;
    declineInvitationBtnCollection = document.getElementsByClassName('invitation-reject-button');
	// manage account inputs
	changeNickInput = document.querySelector('.change-nick-input');
	removeAccountInput = document.querySelector('.remove-account-input');
	updateUserEmailInput = document.querySelector('.update-user-email-input');
	updateUserPasswordInput = document.querySelector('.update-user-password-input');
	updateUserPasswordConfirmByLoginInput = document.querySelector('.update-user-password-confirm-by-login-input');
	updateProfilePictureInput = document.querySelector('.update-profile-picture-input');
    // manage account buttons
    changeNickBtn = document.querySelector('.change-nick-btn');
    removeAccountBtn = document.querySelector('.remove-account-btn');
    updateUserEmailBtn = document.querySelector('.update-user-email-btn');
    updateUserPasswordBtn = document.querySelector('.update-user-password-btn');
    updateProfilePictureBtn = document.querySelector('.update-profile-picture-btn');
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
	communicatorContent.addEventListener('scroll', getMessagePageForScrollApi);

    friendList.addEventListener('click', showCommunicatorBoxForMobile);
    friendList.addEventListener('click', setRoom);

	enterMessageInput.addEventListener(
		'keypress',
		addRightMessageToUIByEnterKeyPress
	);
	sendMessageBtn.addEventListener('click', sendMessageByWebSocketOverSTOMP);

	document.querySelector('.communicator-box').addEventListener('click', clearNofification);

	// Menu open box buttons
	openRoomsBoxBtn.addEventListener('click', openFriendsBox);
	openAddFriendBoxBtn.addEventListener('click', openAddFriendBox);
	openCreateGroupBoxBtn.addEventListener('click', openCreateGroupBox);
	openManageAccountBoxBtn.addEventListener('click', openManageAccountBox);
	myProfileImg.addEventListener('click', openManageAccountBox);
	openInvitationBoxBtn.addEventListener('click', openInvitationBox);

	openInvitationBoxBtn.addEventListener('click', getInvitationsRequest);

	showOnlyFriendsBoxBtn.addEventListener('click', showFriendsBoxForMobile);
	showMenuBtn.forEach((btn) => btn.addEventListener('click', openMenuBox));

	openManageRoomBtn.addEventListener('click', openManageRoomBox);
	closeManagerRoomBoxBtn.addEventListener('click', closeManagerRoomBox);
	
	// manage account actions
	changeNickBtn.addEventListener('click', changeNickRequest);
	removeAccountBtn.addEventListener('click', deleteAccountRequest);
	updateUserEmailBtn.addEventListener('click', changeEmailRequest);
	updateUserPasswordBtn.addEventListener('click', changePasswordRequest);
	updateProfilePictureBtn.addEventListener('click', updateProfilePictureRequest);

	// manage room actions
	createRoomBtn.addEventListener('click', createRoomRequest);
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
	reset(); // reset user interface before load data

	// get user information like profile picture, nick etc.
	getLoggedUser();

	// get connect webSocket
	//connectWebSocket();
	updateInvitationList();
};

const reset = () => {
	searchFriendInput.value = '';
	enterMessageInput.value = '';
	addFriendBox.style.display = 'none';
	menuBox.style.display = 'none';
	createGroupBox.style.display = 'none';
	manageAccountBox.style.display = 'none';
	communicatorContent.innerHTML = ''; // clear message list
	resteManageRoomInputs();
	resteAccountManagerInputs();
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

//reset manage room inputs
const resteAccountManagerInputs = () => {
    changeNickInput.value = ''; 
    removeAccountInput.value = ''; 
    updateUserEmailInput.value = ''; 
    updateUserPasswordInput.value = ''; 
    updateUserPasswordConfirmByLoginInput.value = ''; 
}

// **************************************************************************
// -------------------- Message manager WebSocket STOMP ---------------------
// **************************************************************************

const connectWebSocket = (data) => {
	client = Stomp.client('ws://localhost:8080/messenger');
	client.connect({ username: currentlyLoggedUser.email}, function (frame) {
		client.subscribe('/users/queue/messages', function (data) {
			if(data.body){
				let message = JSON.parse(data.body);
				if(message.roomId === currentluSelectedRoomId){
					let elementHTML = createLeftMessage(message);
					communicatorContent.appendChild(elementHTML);
					setScrollCommunicationPanelToBottom();
				}

				// Allows you to add an unread notification counter item to the room item.
				addMessageCounterElementToRoom(message, unreadMessages);
			} else
				console.log("websocket: got empty message")
		});
	});
}

// Allows you to add a message from you that will be visible in UI by enter keypress
const addRightMessageToUIByEnterKeyPress = (event) => {
	if (event.key !== 'Enter')
		return;

	if(enterMessageInput.value.length < 1)
		return;

	sendMessageByWebSocketOverSTOMP();
};

const sendMessageByWebSocketOverSTOMP = () => {
	let messageContent = enterMessageInput.value
	
	if(enterMessageInput.value.length < 1)
		return;

	if(currentluSelectedRoomId == null)
		return;

	let body = {
		roomId: currentluSelectedRoomId,
		content: messageContent,
		userId: currentlyLoggedUser.userId,
		userNick: currentlyLoggedUser.nick,
		creationTimeInMillisecondsUTC: new Date().getTime()
	}
		
	client.send('/app/messenger', {}, JSON.stringify(body));

	// Create message element and add to communication panel.
	let elementHTML = createRightMessage(messageContent);
	communicatorContent.appendChild(elementHTML);

	// After send message set scroll bottom.
	setScrollCommunicationPanelToBottom();
};

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
		}),})
		.then((response) => {
			if(response.redirected === true)
				window.location.replace(response.url);
		})
		.catch((error) => console.log(error));
};

// ***********************************************************
// ----------------------- Room Requests ---------------------
// ***********************************************************

function sendHttpRequestRoom(url, body){
	fetch(url, {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify(body),
	})
		.then((response) => {return response.json()})
		.then((data) => {
			console.log(data);
			if(data.status === 200){
				console.log("LOAD ROOMS FROM RECEIVED DTO");
				setRoomsInPanel(data.rooms, data.unreadMessages);
			} else if(data.status === 400)
				console.log("LOAD VALIDATION MESSAGE FORM DTO");
			else
				console.log("RECEIVED EXCEPTION FROM SERVER");

			    // TODO: Display valid info for user.
				// if(data.status === 201){
				// 	currentRoomList = data.rooms;
				// 	unreadMessages = data.unreadMessages;
				//	setRoomsInPanel(data.rooms, data.unreadMessages);
			    // 	addStatementMessageToRoomManagerInUI(data.statements);
				// }
		})
		.catch((error) => console.log(error));
		resteManageRoomInputs();
}

const getRooms = () => {
	sendHttpRequestRoom('http://localhost:8080/app/rtc/room/get',
	{
		userId: currentlyLoggedUser.userId
		, roomId: currentluSelectedRoomId
		, userNick: 'none'
		, roomName: 'none'
	})};
const createRoomRequest = () => {
	sendHttpRequestRoom('http://localhost:8080/app/rtc/room/create',
	{
		userId: currentlyLoggedUser.userId
		, roomId: 'none'
		, userNick: 'none'
		, roomName: createRoomInput.value
	})};
const addUserToRoomRequest = () => {
	sendHttpRequestRoom('http://localhost:8080/app/rtc/room/user/add',
	{
		userId: currentlyLoggedUser.userId
		, roomId: currentluSelectedRoomId
		, changedUserId: getUserIdByNick(addNewUserToRoomInput.value , currentSelectedRoom)
		, userNick: addNewUserToRoomInput.value
		, roomName: 'none'
	})};
const removeUserFromRoomRequest = () => {
	sendHttpRequestRoom('http://localhost:8080/app/rtc/room/user/remove',
	{
		userId: currentlyLoggedUser.userId
		, roomId: currentluSelectedRoomId
		, changedUserId: getUserIdByNick(removeUserFromRoomInput.value, currentSelectedRoom)
		, userNick: removeUserFromRoomInput.value
		, roomName: 'none'
	})};
const renameRoomRequest = () => {
	sendHttpRequestRoom('http://localhost:8080/app/rtc/room/name/update',
	{
		userId: currentlyLoggedUser.userId
		, roomId: currentluSelectedRoomId
		, userNick: 'none'
		, roomName: renameRoomInput.value
	})};
const removeRoomRequest = () => {
	sendHttpRequestRoom('http://localhost:8080/app/rtc/room/remove',
	{
		userId: currentlyLoggedUser.userId
		, roomId: currentluSelectedRoomId
		, userNick: 'none'
		, roomName: removeRoomInput.value
	})};
const leaveRoomRequest = () => {
	sendHttpRequestRoom('http://localhost:8080/app/rtc/room/user/leave',
	{
		userId: currentlyLoggedUser.userId
		, roomId: currentluSelectedRoomId
		, userNick: 'none'
		, roomName: 'none'
	}) };

// ***********************************************************
// ---------------------- Account Requests -------------------
// ***********************************************************
const getLoggedUser = () => {
	fetch('http://localhost:8080/app/account/get', {
		method: 'GET',
		headers: {'Content-Type': 'application/json'},
	})
		.then((response) => response.json())
		.then((data) => {
			currentlyLoggedUser = data.user;
			setLoggedUserInPanel(data.user);
		})
		.then(data => getRooms())
		.then((data) => connectWebSocket(data))
		.catch((error) => console.log('err: ', error));
};


function sendHttpRequestAccount(url, body) {
	fetch(url, {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify(body),
	})
		.then((response) => {return response.json()})
		.then((data) => {
			currentlyLoggedUser = data.user;
			setLoggedUserInPanel(data.user)
			addStatementMessageToRoomManagerInUI(data.statements);
		})
		.catch((error) => console.log(error));
	resteAccountManagerInputs();
}

const deleteAccountRequest = () => {	
	fetch('http://localhost:8080/app/account/delete', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({email: removeAccountInput.value}),
	}).then((response) => {return response.json()})
	  .then((data) => {
			if(data.success === true){			
				console.log('Adress replace: ' + login_page_adress);
				window.location.replace(login_page_adress);
			} else {
				addStatementMessageToRoomManagerInUI(data.statements);
			}
		})
		.catch((error) => console.log(error));		
		resteAccountManagerInputs();
};

const changeNickRequest = () => {
	sendHttpRequestAccount('http://localhost:8080/app/account/update/nick'
		, {nick: changeNickInput.value})};
const changeEmailRequest = () => {
	sendHttpRequestAccount('http://localhost:8080/app/account/update/email'
		, {email: updateUserEmailInput.value
	})};
const changePasswordRequest = () => {
	sendHttpRequestAccount('http://localhost:8080/app/account/update/password'
		, {
		email: updateUserPasswordConfirmByLoginInput.value
			, password: updateUserPasswordInput.value
	})};

const updateProfilePictureRequest = () => {
	let file = updateProfilePictureInput.files[0];
	let reader = new FileReader();

	reader.onload = () => {
		console.log(reader.result);
		document.querySelector('.b').src = reader.result; 
		s(reader.result);
	};

	reader.onerror = function (error) {console.log('Error: ', error);};

	if (!!file)
		reader.readAsDataURL(file);
	 else
		 s(null);
};

const s = (fileInBase64) => {
	let file = updateProfilePictureInput.files[0];
	fetch('http://localhost:8080/app/account/update/picture', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({
			profilePicture: {
				name: file.name,
				type: file.type,
				size: file.size,
				fileInBase64: fileInBase64,
			}}),
	})
		.then((response) => {return response.json()})
		.then((data) => {
			if(data.success === true){
				//TODO: setRoomsInPanel(data.rooms);
			}
			addStatementMessageToRoomManagerInUI(data.statements);
		})
		.catch((error) => console.log(error));
		resteAccountManagerInputs();
};

// Method allows you to set information such as nickname or profile picture in the user interface.
const setLoggedUserInPanel =(user) => {
	myProfileImg.src = user.profilePicture.fileInBase64;
	document.querySelector('.b').src = user.profilePicture.fileInBase64;	
}

// ***********************************************************
// ----------- Box Manager and additional features -----------
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

const showCommunicatorBoxForMobile = () => {
	if (window.innerWidth > 769)
		return;

	roomBox.style.display = 'none';
	communicatorBox.style.display = 'flex';
	communicatorContent.scrollTop = communicatorContent.scrollHeight;
};

const openManageAccountBox = () => {
	roomBox.style.display = 'none';
	menuBox.style.display = 'none';
	manageAccountBox.style.display = 'flex';
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
	if (window.innerWidth < 769)
		managerRoomBox.style.display = 'none';

	addFriendBox.style.display = 'none';
	roomBox.style.display = 'none';
	createGroupBox.style.display = 'none';
	manageAccountBox.style.display = 'none';
	invitationsBox.style.display = 'none';
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

const openInvitationBox = () => {
	menuBox.style.display = 'none';
	invitationsBox.style.display = 'flex';
};

// *************************************************************
// ----------------------- Set Room ---------------------
// *************************************************************
// Point to current message page which.
let currentMessagePageCounter = 0;
const currentMessagePageSize = 8;

const setRoom = (e) => {
	let currentRoom = e.target.closest('.friend');
	console.log("selected roomId: " + e.target.closest('.friend').getAttribute('roomId'));
	currentluSelectedRoomId = currentRoom.getAttribute('roomId')

	fetch('http://localhost:8080/app/rtc/room/get', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify({
			userId: currentlyLoggedUser.userId,
			roomId: currentluSelectedRoomId,
			userNick: currentlyLoggedUser.nick,
			roomName: 'none'
		}),
	})
		.then((response) => {return response.json();})
		.then((data) => {
			if(data.status === 200){
				// Set message page counter for selected room.
				currentMessagePageCounter = 0;

				// Set info about rooms.
				setCurrentSelectedRoomAndCurrentRoomList(data)

				// Show selected room in left panel.
				setRoomsInPanel(data.rooms, data.unreadMessages);

				// Show selected room in conversation panel.
				setSelectedRoomInPanel();
			}
			return data;
		})
		.then((data) => {
			getMessagePageApi(currentMessagePageCounter,  15);
			currentMessagePageCounter++;
		})
		.then(data => {return data})
		.catch((error) => console.log(error));

	resteManageRoomInputs();
}

const setCurrentSelectedRoomAndCurrentRoomList = (data) => {
	currentRoomList = data.rooms;
	unreadMessages = data.unreadMessages;
	currentRoomList.forEach(room => {
		if(currentluSelectedRoomId === room.roomId)
			currentSelectedRoom = room;
	})
}

// Allows you to display information about the currently selected room in interface.
const setSelectedRoomInPanel = (room) => {
	// If no room is selected
	if(currentluSelectedRoomId === null){
		console.log("Can't display selected room: selected room id is null !");
		return;
	}

	// clear before load new content
	selectedRoomPicture.src = '';
	selectedRoomName.innerHTML = '';

	// set room picture
	let picture;
	currentSelectedRoom.users.forEach((user) => {picture = user.profilePicture.fileInBase64;})
	selectedRoomPicture.src = picture;

	// set room name
	selectedRoomName.innerHTML = `${currentSelectedRoom.name}`;
}

// *************************************************************
// ----------------------- Message Manager ---------------------
// *************************************************************

// This function call getMessagePageApi(page, size) on scrollTop === 0.
const getMessagePageForScrollApi = (e) => {
	if(e.target.scrollTop === 0){
		getMessagePageApi(currentMessagePageCounter,  currentMessagePageSize);
		currentMessagePageCounter++;
	}
}

// Fetch message page and call setMessagePageInPanel() on status OK.
const getMessagePageApi = (page, size) => {
	if(!currentluSelectedRoomId){
		console.log("Cant set message page for user because no room selected !");
		return;
	}

	const params = new URLSearchParams({
		roomId: currentluSelectedRoomId,
		size: size,
		page: page
	})

	fetch('http://localhost:8080/app/api/message/get/page' + '?' + params, {
		method: 'GET',
		headers: {'Content-type': 'application/json',}
	})
		.then(response => {return response.json()})
		.then(response => {return setMessagePageInPanel(response.messages, response.currentPage)})
		.catch((error) => console.log(error));

	return 1;
}

// Allow get userId form selected room
// example. getUserIdByNick(removeUserFromRoomInput.value, currentSelectedRoom);
function getUserIdByNick(nick, room){
	let result;
	room.users.forEach(u => {if(u.nick === nick) result =  u.userId;});
	return result;
}

// Allows you to load all messages from the currently selected room and display them in UI
function setMessagePageInPanel(messages, page)  {
	// If no message return.
	if(messages.length <= 0)
		return;

	// Clear message list if this is first page.
	// This condition should be called from the first fetch (get message page api)
	if(page <= 0)
		communicatorContent.innerHTML = '';

	let messageCollection = [];

	// Create and prepare Message HTMl collection.
	messages.forEach((message, index) => {
		if(message.userNick === currentlyLoggedUser.nick)
			messageCollection.push(createRightMessage(message.content));
		 else
			messageCollection.push(createLeftMessage(message));
	})

	// Add created message HTMl Collection to panel.
	messageCollection.forEach((element, index) => {communicatorContent.prepend(element);});
	setScrollCommunicationPanelToBottom();
	return messageCollection;
}

// Allows you to add a message from you that will be visible in UI.
// If direction false add new element to the end, if true add at the beginning.
const createRightMessage = (content) => {
	if(content.length < 1){
		console.log("Can't add message: message must not be empty");
		return;
	}
	let element = document.createElement('div');
	element.classList.add('right-message-box');
	element.innerHTML = `<li class="right-message">${content}</li>`;
	enterMessageInput.value = '';
	return element;
};

// Allows you to add a message from you that will be visible in UI.
// If direction false add new element to the end, if true add at the beginning.
const createLeftMessage = (message) => {
	let picture = getUserPictureFromRoom(currentSelectedRoom, message.userNick);
			
	let element = document.createElement('div');
	element.classList.add('left-message-box');
	
	element.innerHTML = `<li class="left-message-box">
                    <img class="current-friend-img" src="${picture}" alt="img">
                    <p class="left-message">${message.content}</p>
                </li>`;
	return element;
};

// Set scroll to bottom position.
const setScrollCommunicationPanelToBottom = () => {communicatorContent.scrollTop = communicatorContent.scrollHeight;}

// The method allows you to find the user in the room and download and return his profile picture
function getUserPictureFromRoom(room, nick) {
	let picture;
	for (let i = 0; i < room.users.length; i++) {
		if(room.users[i].nick === nick){
			picture = room.users[i].profilePicture.fileInBase64;
			break;
		}
	}
	return picture;
}

const addTimeMessageToUI = (dateMilisecondsUTCLast, dateMilisecondsUTCOneBeforeLast) => {
	let current_date = new Date();
	let delivered_date = new Date(parseInt(dateMilisecondsUTCLast));
	let result_to_print = '';
	
	let a = new Date (parseInt(dateMilisecondsUTCLast));
	let b = new Date (parseInt(dateMilisecondsUTCOneBeforeLast));

	let diffBetweenLastTwoMessage = diff_minutes(a, b);

	if(diffBetweenLastTwoMessage <= 3)
		return;

	//choose a format
	let diff = diff_days(current_date, delivered_date);
	//console.log("diff: " + diff)
	
	if(diff < 1){
		result_to_print = delivered_date.getHours() + ':' + delivered_date.getMinutes();
	} else if(diff >= 1 && diff < 7) {
		let days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
		result_to_print = days[delivered_date.getDay()] + ', ' + delivered_date.getHours() + ':' + delivered_date.getMinutes();
	} else {
		result_to_print = delivered_date.getDay() + "." + delivered_date.getMonth() + "." + delivered_date.getFullYear() + ', ' + delivered_date.getHours() + ':' + delivered_date.getMinutes();
	} 

	// Create element and add to user interface (message list)
	let element = document.createElement('p');
	element.classList.add('message-date');
	element.innerHTML = `${result_to_print}`;
	communicatorContent.appendChild(element);
}

const addTimeMessageToUIOnlyForFirstMessage = (dateMilisecondsUTCLast) => {
	let current_date = new Date();
	let delivered_date = new Date(parseInt(dateMilisecondsUTCLast));
	let result_to_print = '';
	
	//choose a format
	let diff = diff_days(current_date, delivered_date);
	
	if(diff < 1)
		result_to_print = delivered_date.getHours() + ':' + delivered_date.getMinutes();
	else if(diff >= 1 && diff < 7) {
		let days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
		result_to_print = days[delivered_date.getDay()] + ', ' + delivered_date.getHours() + ':' + delivered_date.getMinutes();
	} else
		result_to_print = delivered_date.getDay() + "." + delivered_date.getMonth() + "." + delivered_date.getFullYear() + ', ' + delivered_date.getHours() + ':' + delivered_date.getMinutes();

	
	// Create element and add to user interface (message list)
	let element = document.createElement('p');
	element.classList.add('message-date');
	element.innerHTML = `${result_to_print}`;
	communicatorContent.appendChild(element);
}

// ******************************************************************************
// ---------------- Room manager: display rooms in UI -------------------
// ******************************************************************************

const setRoomsInPanel = (rooms, unreadMessages) => {
	friendList.innerHTML = '';
	rooms.forEach((room) => loadRooms(room, unreadMessages));
}

// This function allow add new friend in UI to FriendList,
// attributes: user nick (nick), who send last message (lastMessageCreator), last message in convertation (lastmessageContent), last message time or date (lastMessagetime)
const loadRooms = (room, unreadMessages) => {
	let lastmessageContent = "Write first message !";
	let lastMessageCreator = "...";
	let lastMessagetime = "...";
	let numberOfUnreadMessages = unreadMessages[room.roomId];


	//  TODO: change.
	// if(room.messages.length > 0){
	// 	room.messages.forEach((message) => {
	// 	  lastmessageContent = message.content;
	// 	  lastMessageCreator = message.userNick;
	// 	  lastMessagetime = getDateDiffirence(parseInt(message.creationTimeInMillisecondsUTC));
	//     })
	// }

	// if (lastmessageContent.length > 15) {
	// 	lastmessageContent = lastmessageContent.substring(0, 13);
	// 	lastmessageContent += '..';
	// }
	
	const friend = document.createElement('li');
	friend.classList.add('friend');
	friend.setAttribute('roomId', room.roomId);
	
	let lastUserPicture;

	//load picture
	room.users.forEach((user) => {
		lastUserPicture = user.profilePicture.fileInBase64
	});

	if(unreadMessages !== null && numberOfUnreadMessages >= 1){
		
		friend.innerHTML = `<div class="friend-img">
	    <img class="profile-img" src="${lastUserPicture}" alt="img">
	    <div class="activity-light activity-light-red"></div>
	</div>
	<div class="friend-describe">
	    <p class="friend-name">${room.name}</p>
	    <p class="friend-last-message">${lastMessageCreator}: ${lastmessageContent} ${lastMessagetime}</p>
	</div>
	<div class="new-message-notification"><i class="fa-solid fa-comment new-message-notification-img"></i>
                        <p class="new-message-notification-text">new message</p>
                        <p class="new-message-notification-counter">${numberOfUnreadMessages}</p></div>`;
	} else {
		
		friend.innerHTML = `<div class="friend-img">
	    <img class="profile-img" src="${lastUserPicture}" alt="img">
	    <div class="activity-light activity-light-red"></div>
	</div>
	<div class="friend-describe">
	    <p class="friend-name">${room.name}</p>
	    <p class="friend-last-message">${lastMessageCreator}: ${lastmessageContent} ${lastMessagetime}</p>
	</div>
	<div class="new-message-notification"></div>`;
	}

	friendList.append(friend);
};


// Allows you to add an unread notification counter item to the room item
function addMessageCounterElementToRoom(message) {
	let roomListDOM;
	let roomId;
	let notificationElement;
	
	roomListDOM = document.querySelectorAll('.room-list .friend');

	roomListDOM.forEach(room => {
		roomId = room.getAttribute('roomId');
		unreadMessages[roomId] = unreadMessages[roomId] + 1;
		
		if(message.roomId === roomId) {
			notificationElement = room.querySelector('.new-message-notification');
			notificationElement.innerHTML = `<i class="fa-solid fa-comment new-message-notification-img"></i>
                        <p class="new-message-notification-text">new message</p>
                        <p class="new-message-notification-counter">${unreadMessages[roomId]}</p>`
		}
	})
}

// clear notifications after clicking on communicator because
const clearNofification = () => {
	let roomListDOM;
	let notificationElement;
	let roomId;
	
	if(currentluSelectedRoomId === null || typeof currentluSelectedRoomId === "undefined")
		return;

	roomListDOM = document.querySelectorAll('.room-list .friend');
	roomListDOM.forEach(room => {
		roomId = room.getAttribute('roomId');
		
		if(currentluSelectedRoomId === roomId) {
			notificationElement = room.querySelector('.new-message-notification');
			notificationElement.innerHTML = ``;
		}
	})
	if(unreadMessages[currentluSelectedRoomId] > 0)
	     sendUpdateReadMessagesRequest({roomId: currentluSelectedRoomId})

	unreadMessages[currentluSelectedRoomId] = 0;
}

const sendUpdateReadMessagesRequest = (body) => {
	fetch('http://localhost:8080/app/api/message/update/read', {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		},
		body: JSON.stringify(body),
	})
		.catch((error) => console.log(error));
}

// **************************************************************************************************************
// ---------------- Display query result messages in UI for Room Manager and Account Manager --------------------
// **************************************************************************************************************

// Handles the display of the room management response message for the appropriate list.
const addStatementMessageToRoomManagerInUI = (statements) => {
	
	removeAllQueryResultMessageForRoomManager();
	removeAllQueryResultMessageForAccountManager();
	removeAllQueryResultMessageForAddFriend();
	
	statements.forEach((statements) => {
		switch (statements.action) {
			case 'CREATE_ROOM':
				loadResultMessageForRoomManagerQuery(statements, 'm_r_o_b_create_room');
				break;
			case 'GET_ROOMS':
				console.log('GET_ROOMS');
				break;
			case 'CREATE_ROOM_WITH_FRIEND':
				loadResultMessageForAddFriendQuery(statements, 'm_r_o_b_create_room_with_friend');
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
			case 'UPDATE_USER_NICK':
			    loadResultMessageForAccountManagerQuery(statements, 'm_a_change_user_nick');
			    break;
			case 'UPDATE_USER_EMAIL':
			    loadResultMessageForAccountManagerQuery(statements, 'm_a_update_user_email');
			    break;
			case 'UPDATE_USER_PASSWORD':
			    loadResultMessageForAccountManagerQuery(statements, 'm_a_update_user_password');
			    break;
			case 'UPDATE_USER_PICTURE':
			    loadResultMessageForAccountManagerQuery(statements, 'm_a_update_profile_picture');
			    break;
			case 'DELETE_ACCOUNT':
			    loadResultMessageForAccountManagerQuery(statements, 'm_a_delete_account');
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


// This method adds and displays a message and the execution status of the query operation for the server for account management.
const loadResultMessageForAccountManagerQuery = (statements, className) => {
	let li = document.createElement('li');
	li.classList.add('manager-account-option-message');
	li.addEventListener('click', () => li.remove());

	switch (statements.type) {
     case 'SUCCES_STATEMENT':    
          li.classList.add('manager-account-option-success-message');
	      li.innerHTML = `<i class="fa-solid fa-thumbs-up"></i><p>${statements.message}</p>`;
          break;
     case 'ERROR_STATEMENT':
         li.classList.add('manager-account-option-fail-message');
	     li.innerHTML = `<i class="fa-solid fa-bomb"></i><p>${statements.message}</p>`;
         break;        
     default:
         console.log(`No message handling of this type. message:${statements.message}`);
    }
	
	document.querySelector('.' + className).appendChild(li);
};

// This method adds and displays a message and the execution status of the query operation for the server for add friend feature.
const loadResultMessageForAddFriendQuery = (statements, className) => {
	let li = document.createElement('li');
	li.classList.add('add-friend-message');
	li.addEventListener('click', () => li.remove());

	switch (statements.type) {
     case 'SUCCES_STATEMENT':    
          li.classList.add('add-friend-success-message');
	      li.innerHTML = `<i class="fa-solid fa-thumbs-up"></i><p>${statements.message}</p>`;
          break;
     case 'ERROR_STATEMENT':
         li.classList.add('add-friend-fail-message');
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
    //liElementsArray[i].remove();
   }
}

// Allows you to delete all created messages related to the query result for account management.
const removeAllQueryResultMessageForAccountManager = () => {
	let liElementsArray = document.querySelectorAll('.manager-account-option-message');
	for (var i = 0, len = liElementsArray.length; i < len; i++) {
    liElementsArray[i].innerHTML = '';
     liElementsArray[i].remove();
   }
}

// Allows you to delete all created messages related to the query result for account management.
const removeAllQueryResultMessageForAddFriend = () => {
	let liElementsArray = document.querySelectorAll('.add-friend-message');
	for (var i = 0, len = liElementsArray.length; i < len; i++) {
    liElementsArray[i].innerHTML = '';
    liElementsArray[i].remove();
   }
}

// ***********************************************************
// -------------------- Invitation Requests ------------------
// ***********************************************************

// The method enables querying the server for a list of invitations held by the user.
//30s
const updateInvitationList = () => {
	// Get first invitation.
	getInvitationsRequest()
	// Get invitations periodically.
	setInterval(() => getInvitationsRequest(), 30000);
};

function getInvitationsRequest() {
	fetch('http://localhost:8080/app/api/invitation/get/all', {
		method: 'GET',
		headers: {'Content-Type': 'application/json',},
	})
		.then((response) => response.json())
		.then((data) => {
            console.log("Updated invitations");
            console.log(data);
			displayInvitationsInUI(data);
		})
		.catch((error) => console.log('err: ', error));
}

function acceptOrDeclineInvitationRequest(invitationId, path) {
	let params = new URLSearchParams({invitationId: invitationId});
	fetch(path + '?' + params, {
		method: 'POST',
		headers: {
			Accept: 'application/json',
			'Content-type': 'application/json',
			'Access-Control-Allow-Origin': '*',
		}
	})
		.then((response) => {return response.json();})
		.then(data => {
			console.log(data)
			displayInvitationsInUI(data)
		})
		.catch((error) => console.log(error));
};

// ***********************************************************
// ------------------- Invitation UI --------------------
// ***********************************************************

// This section is responsible for the ability to add and display invitations for a given user in the user interface.
// The method allows you to add a new friend request to the invitations list;
function displayInvitationsInUI(invitations) {
	let invitationList = document.querySelector('.invitation-list');
	// Clear invitation html list before add new elements.
	invitationList.innerHTML = '';
	// Append prepared invitation html elements.
	invitations.forEach((invitation) => {invitationList.appendChild(createInvitationHtmlElement(invitation))})
}

// Create invitation html element.
function createInvitationHtmlElement(invitation) {
	let element = document.createElement('li');
	element.classList.add('invitation-box');
	element.setAttribute('invitationId', invitation.invitationId);
	element.innerHTML = `<img class="invitation-img" src="${invitation.user.profilePicture.fileInBase64}" alt="img">
	<div class="invitation-description-box">
		<div class="invitation-name-date-box">
			<p class="invitation-name-field">${invitation.inviting}</p>
			<p class="invitation-date-field">${invitation.creation_date}</p>
		</div>
		<div class="mutual-friends-box">
			<i class="fa-solid fa-user-group"></i>
			<p class="invitation-mutual-friends-field">0 wspólnych znajomych</p>
		</div>
		<div class="invitation-buttons-box">
			<button class="invitation-button invitation-accept-button" invitationId=${invitation.invitationId}>Accept</button>
			<button class="invitation-button invitation-reject-button" invitationId=${invitation.invitationId}>Reject</button>
		</div>
	</div>`;
	element.querySelector('.invitation-accept-button').addEventListener('click', CallAcceptInvitationRequest);
	element.querySelector('.invitation-reject-button').addEventListener('click', CallDeclineInvitation);
	return element;
}

const CallAcceptInvitationRequest = (event) => {
	let invitationId = event.target.getAttribute('invitationId')
	if(invitationId !== null)
		acceptOrDeclineInvitationRequest(invitationId , 'http://localhost:8080/app/api/invitation/accept');
}

const CallDeclineInvitation = (event) => {
	let invitationId = event.target.getAttribute('invitationId');
	if(invitationId !== null)
		acceptOrDeclineInvitationRequest(invitationId, 'http://localhost:8080/app/api/invitation/decline');
}

// ***********************************************************
// ------------------------ Time Utils -----------------------
// ***********************************************************
// It is used to tell the user when the last message was delivered.
// Function returns the time difference between the current date and the delivered date
// The result returns the year if is greater than one, otherwise returns the number of weeks.
// If the difference is less than a week, it returns the number of days.
// If the difference is less than the day, the time in hours is returned.
// If the difference is less than the hour, the time in minuts is returned.
// If the difference is less than the one minute, a message is returned "now".
function getDateDiffirence(delivered_date_in_miliseconds_utc) {
	let result;
	let current_date = new Date();
	let delivered_date = new Date(delivered_date_in_miliseconds_utc);
	
	//console.log(current_date);
	//console.log("delivered_date_in_miliseconds_utc" + delivered_date_in_miliseconds_utc);
	//console.log(delivered_date);

	if (current_date < delivered_date) {
		console.log('delivered_date is less then current_date');
		return undefined;
	}

	// Years
	result = diff_years(current_date, delivered_date);

	if (result >= 1)
		return Math.trunc(result) + ' year';

	// Weeks
	result = diff_weeks(current_date, delivered_date);

	if (result >= 1)
		return Math.trunc(result) + ' week';

	// Days
	result = diff_days(current_date, delivered_date);

	if (result >= 1 && result <= 6)
		return Math.trunc(result) + ' days';

	// Hours
	result = diff_hours(current_date, delivered_date);

	if (result >= 1 && result <= 23)
		return Math.trunc(result) + ' hour';

	// minuts
	result = diff_minutes(current_date, delivered_date);

	if (result >= 1 && result <= 59)
		return Math.trunc(result) + ' min';

	return 'now';
}

// Allows you to calculate the difference between the dates in minutes
function diff_minutes(dt2, dt1) {
	// One day in seconds
	const oneMintute = 60;

	// Calculating the time diffirence between two dates
	let diffInTime = (dt2.getTime() - dt1.getTime()) / 1000;

	// Calculating the number of minuts between two dates
	return diffInTime / oneMintute;
}

// Allows you to calculate the difference between the dates in hours
function diff_hours(dt2, dt1) {
	// One day in seconds
	const oneHour = 60 * 60;

	// Calculating the time diffirence between two dates
	let diffInTime = (dt2.getTime() - dt1.getTime()) / 1000;

	// Calculating the number of hours between two dates
	return diffInTime / oneHour;
}

// Allows you to calculate the difference between the dates in days
function diff_days(dt2, dt1) {
	// One day in seconds
	const oneDay = 60 * 60 * 24;

	// Calculating the time diffirence between two dates
	let diffInTime = (dt2.getTime() - dt1.getTime()) / 1000;

	// Calculating the number of days between two dates
	return diffInTime / oneDay;
}

// Allows you to calculate the difference between the dates in weeks
function diff_weeks(dt2, dt1) {
	// One week in seconds
	let oneWeek = 60 * 60 * 24 * 7;

	// Calculating the time diffirence between two dates
	let diff = (dt2.getTime() - dt1.getTime()) / 1000;

	// Calculating the number of weeks between two dates
	return Math.abs(diff / oneWeek);
}

// Allows you to calculate the difference between the dates in years
function diff_years(dt2, dt1) {
	// Calculating the time diffirence between two dates
	let diff = (dt2.getTime() - dt1.getTime()) / 1000;
	diff /= 60 * 60 * 24;

	return Math.abs(diff / 365.25);
}


// ***********************************************************
// --------------------- DOM Content Load --------------------
// ***********************************************************

// call main function
document.addEventListener('DOMContentLoaded', main);
