import {LitElement, html} from 'lit';
import "./app.scss";

export class PlanningRoom extends LitElement {
    static properties = {
        version:        {type: String},
		socket:         {type: Object, attribute: false},
        chatValue:      {type: String, attribute: false},
        storyValue:     {type: String, attribute: false},
        nameValue:      {type: String, attribute: false},
        name:           {type: String, attribute: false},
        roomOneValue:   {type: String, attribute: false},
        roomTwoValue:   {type: String, attribute: false},
        roomThreeValue: {type: String, attribute: false},
        roomFourValue:  {type: String, attribute: false},
        roomFiveValue:  {type: String, attribute: false},
        roomSixValue:   {type: String, attribute: false},
        room:           {type: String, attribute: false},
        players:        {type: Array,  attribute: false},
    };
	
    constructor() {
        super();
        this.version = "0.0.0v";
        this.chatValue = "";
        this.storyValue = "";
        this.nameValue = "";
        this.name = "";
        this.clearRoom();
        this.room = "";
        this.socket = {};
        this.players = [];
    }
    
    connectedCallback() {
        super.connectedCallback()
        this.connect();
    }
    
    firstUpdated() {
        this.setPaste();        
    }
    
    createRenderRoot() {
        return this;
    }

    render() {
        return html`
            <!-- JOIN ROOM -->
            <label>${this.version}</label>
            <div id="join-room" class="col">
              <div id="send-name" class="row">
                <input placeholder="name" type="text" size="12" .value=${this.nameValue} @input=${this._handleNameInput} />
                <button type="button" @click="${this.sendName}">Send name</button>
              </div>
              <label>${this.name}</label>
              <div id="room-id" class="room-numbers hide">
                <input id="first-num" type="number" size="1" min="0" max="9" maxlength="1" .value=${this.roomOneValue} @input=${this._handleRoomOneInput} />
                <input type="number" size="1" min="0" max="9" maxlength="1" .value=${this.roomTwoValue} @input=${this._handleRoomTwoInput} />
                <input type="number" size="1" min="0" max="9" maxlength="1" .value=${this.roomThreeValue} @input=${this._handleRoomThreeInput} />
                <input type="number" size="1" min="0" max="9" maxlength="1" .value=${this.roomFourValue} @input=${this._handleRoomFourInput} />
                <input type="number" size="1" min="0" max="9" maxlength="1" .value=${this.roomFiveValue} @input=${this._handleRoomFiveInput} />
                <input type="number" size="1" min="0" max="9" maxlength="1" .value=${this.roomSixValue} @input=${this._handleRoomSixInput} />
                <div class="row">
                  <button type="button" @click="${this.joinRoom}">Join Room</button>
                  <button type="button" @click="${this.createRoom}">Create Room</button>
                </div>
              </div>
            </div>
            <!-- PLANNING ROOM -->
            <div id="planning-room" class="col hide">
              <textarea name="story" rows="17" cols="37" .value=${this.storyValue}></textarea>
              <div class="row">
                <input placeholder="message" type="text" size="37" .value=${this.chatValue} @input=${this._handleChatInput} @keypress="${this._handleChatEnter}" />
                <button id="chat-btn" type="button" @click="${this.sendMessage}">Send message</button>
              </div>
              <h1><label>${this.room}</label></h1>
              ${this.players.map(pl => html`<div class="player-name">${pl}</div>`)}
            </div>
        `;
    }
    
    setPaste() {
        const firstNum = document.getElementById("first-num");
                firstNum.addEventListener("paste", (event) => {
                   event.preventDefault();
                   const pastedText = event.clipboardData.getData('text/plain');
                   for(let i=0; i < pastedText.length; i++) {
                       const ch = pastedText[i];
                       switch(i) {
                           case 0: this.roomOneValue = ch; break;
                           case 1: this.roomTwoValue = ch; break;
                           case 2: this.roomThreeValue = ch; break;
                           case 3: this.roomFourValue = ch; break;
                           case 4: this.roomFiveValue = ch; break;
                           case 5: this.roomSixValue = ch; break;
                       }
                   }
                });
    }
    
    clearRoom() {
        this.roomOneValue = "";
        this.roomTwoValue = "";
        this.roomThreeValue = "";
        this.roomFourValue = "";
        this.roomFiveValue = "";
        this.roomSixValue = "";
    }
    
    connect() {
        let wsUri = `ws://${location.host + location.pathname}planning`
        this.socket = new WebSocket(wsUri);
        this.socket.addEventListener('open', (event) => {
                           console.log('WebSocket connection established!', event);
                        });
        this.socket.addEventListener('message', (event) => {
                           const message = JSON.parse(event.data);
                           switch(message.action) {
                               case 'NAME_IS_SET':
                                  this.name = message.data;
                                  const sendName = document.getElementById("send-name");
                                  const roomId = document.getElementById("room-id");
                                  sendName.classList.add("hide");
                                  roomId.classList.remove("hide");
                                  break;
                               case 'ROOM_CREATED':
                                  this.players = JSON.parse(message.data);
                                  this.hideJoinShowPlanning();
                                  break;
                               case 'ROOM_JOIN':
                                  this.players = JSON.parse(message.data);
                                  this.hideJoinShowPlanning();
                                  break;
                               case 'CHAT':
                                  this.storyValue += `${message.data}\n`;
                                  break;
                               default:
                                  console.log("Missing action:", message);
                           }
                       });
    }
    
    sendMessage() {
        const data = {action: 'CHAT', data: `${this.chatValue}`};
        const dataString = JSON.stringify(data);
        console.log(dataString);
        this.socket.send(dataString);
        this.chatValue = "";
    }
    sendName() {
        const data = {action: 'SET_NAME', data: `${this.nameValue}`};
        const dataString = JSON.stringify(data);
        console.log(dataString);
        this.socket.send(dataString);
        this.nameValue = "";
    }
    createRoom() {
        const roomId = `${this.roomOneValue}${this.roomTwoValue}${this.roomThreeValue}${this.roomFourValue}${this.roomFiveValue}${this.roomSixValue}`
        this.room = roomId;
        const data = {action: 'CREATE_ROOM', data: roomId};
        const dataString = JSON.stringify(data);
        console.log(dataString);
        this.socket.send(dataString);
        this.clearRoom();
    }
    joinRoom() {
        const roomId = `${this.roomOneValue}${this.roomTwoValue}${this.roomThreeValue}${this.roomFourValue}${this.roomFiveValue}${this.roomSixValue}`
        this.room = roomId;
        const data = {action: 'JOIN_ROOM', data: roomId};
        const dataString = JSON.stringify(data);
        console.log(dataString);
        this.socket.send(dataString);
        this.clearRoom();
    }
    hideJoinShowPlanning() {
        const joinRoom = document.getElementById("join-room");
        const planningRoom = document.getElementById("planning-room");
        joinRoom.classList.add("hide");
        planningRoom.classList.remove("hide");
    }
    
    _handleChatInput(e) {
        this.chatValue = e.target.value;
    }
    _handleNameInput(e) {
        this.nameValue = e.target.value;
    }
    _handleRoomOneInput(e) {
        this.roomOneValue = e.target.value;
    }
    _handleRoomTwoInput(e) {
        this.roomTwoValue = e.target.value;
    }
    _handleRoomThreeInput(e) {
        this.roomThreeValue = e.target.value;
    }
    _handleRoomFourInput(e) {
        this.roomFourValue = e.target.value;
    }
    _handleRoomFiveInput(e) {
        this.roomFiveValue = e.target.value;
    }
    _handleRoomSixInput(e) {
        this.roomSixValue = e.target.value;
    }
    _handleChatEnter(e) {
        if(e.key === "Enter") {
            e.preventDefault();
            const chatBtn = document.getElementById("chat-btn");
            chatBtn.click();
        }   
    }
}

customElements.define('planning-room', PlanningRoom);