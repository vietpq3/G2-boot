'use strict';
 
var messageArea = document.querySelector('#messageArea');
var messageElement = document.querySelector('#message');
var size = document.querySelector('#size').value;
 
var stompClient = null;
var chatStompClient = null;
var playerId = null;
var position = null;
var socket = null;

function draw(){
	for(var i = 0; i < size; i++){
		var tr = document.createElement("tr");
		$('#board').append(tr);
		for(var j = 0; j < size; j++){
			
			var btn = document.createElement("button");
			btn.id = i + "-" + j;
			btn.value = " ";
			btn.className = "child";
			
			var td = document.createElement("td");
			td.append(btn);
			tr.append(td);
		}
	}
}

draw();
//Connect to WebSocket Server.
connect();

function connect() {
	socket = new SockJS('/ws');
	stompClient = Stomp.over(socket);
	
	stompClient.connect({}, onConnected, onError);
}

function chatConnect() {
    var socket = new SockJS('/ws');
    chatStompClient = Stomp.over(socket);
 
    chatStompClient.connect({}, onChatConnected, onError);
}
 
function onConnected() {
	// Subscribe to the Public Topic
	stompClient.subscribe('/topic/gameRoom', onMessageReceived);
	
	// Tell your username to the server
	stompClient.send("/app/addPlayer", {}, "{}");
	
	messageElement.textContent = '';
}

function onChatConnected() {
    // Subscribe to the Public Topic
	chatStompClient.subscribe('/topic/chatRoom', onChatMessageReceived);
 
    // Tell your username to the server
	chatStompClient.send("/app/chatJoin",
        {},
        JSON.stringify({playerId: playerId})
    )
 
    messageElement.textContent = '';
}
 
 
function onError(error) {
    messageElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    messageElement.style.color = 'red';
}
 
 
function sendMessage(actionType) {
    if(stompClient) {
        var playMessage = {
            playerId: playerId,
            x: position.split("-")[0],
            y: position.split("-")[1],
            actionType: actionType
        };
        stompClient.send("/app/play", {}, JSON.stringify(playMessage));
    }
}
 
 
function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    var messageText = '-';
    var btnId;
    
    if(!playerId){
    	playerId = message.playerId;
    }
 
    if(message.actionType === 'WATCH' && playerId == message.playerId) {
    	messageText = 'The room is full now. You just able to watch this match!';
    } else if (message.actionType === 'WAIT') {
        messageText = 'Please wait for one more player to start';
    } else if (message.actionType === 'START') {
    } else if (message.actionType === 'END') {
    	if(playerId == message.playerId){
    		alert("You win!");
    	} else {
    		alert("You lose!");
    	}
    	reset();
    } else if (message.actionType === 'LEAVE') {
    	alert("Opponent has left!");
    	reset();
    } else if (message.actionType === 'PLAY') {
    	btnId = message.x + "-" + message.y;
    	$('#' + btnId).attr("class", "child " + message.symbol);
    	$('#' + btnId).val(message.symbol);
    	$('.child').attr("style", "");
    	$('#' + btnId).attr("style", "background-color: yellow;")
    	
    	if(playerId == message.playerId){
    		messageText = "Opponent's turn";
    	} else {
    		messageText = "Your turn";
    	}
    } else {
    }
 
    var textElement = document.createElement('span');
    var messageNode = document.createTextNode(messageText);
    textElement.appendChild(messageNode);
 
    messageElement.textContent = '';
    messageElement.appendChild(textElement);
}

function reset(){
	$('.child').each(function(){
		$(this).val(" ");
		$(this).attr("class", "child");
		$(this).prop("style", false);
	});
}

$(document).ready(function(){
	$('.child').click(function(){
		if($(this).val() != " ") {
			return;
		}
		position = $(this).prop("id");
		sendMessage('PLAY');
	});
});