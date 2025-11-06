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
        roomValue:      {type: String, attribute: false},
        room:           {type: String, attribute: false},
    };
	
    constructor() {
        super();
        this.version = "0.0.0v";
        this.chatValue = "";
        this.storyValue = "";
        this.nameValue = "";
        this.name = "";
        this.roomValue = "";
        this.room = "";
        this.socket = {};
        this.connect();
    }
    
    createRenderRoot() {
        return this;
    }

    render() {
        return html`
            <div class="col">
              <label>${this.version}</label>
              <label for="story">Story:</label>
              <textarea id="story" name="story" rows="17" cols="37" .value=${this.storyValue}></textarea>
              <input type="text" size="37" .value=${this.chatValue} @input=${this._handleChatInput} />
              <input id="input-name" type="text" size="37" .value=${this.nameValue} @input=${this._handleNameInput} />
              <label>${this.name}</label>
              <input id="input-room" type="text" size="37" .value=${this.roomValue} @input=${this._handleRoomInput} />
              <label>${this.room}</label>
              <div class="row">
                <button id="btn-room" type="button" @click="${this.createRoom}">Create Room</button>
                <button type="button" @click="${this.sendMessage}">Send</button>
                <button id="btn-name" type="button" @click="${this.sendName}">Send name</button>
              </div>
            </div>
        `;
    }
    
    connect() {
        let wsUri = `ws://${location.host + location.pathname}planning`
        this.socket = new WebSocket(wsUri);
        this.socket.addEventListener('open', (event) => {
                           console.log('WebSocket connection established!', event);
                        });
        this.socket.addEventListener('message', (event) => {
                           const data = JSON.parse(event.data);
                           console.log('Message from server:', data);
                           switch(data.action) {
                               case 'NAME_IS_SET':
                                  this.name = data.data;
                                  const inp = document.getElementById("input-name");
                                  const btn = document.getElementById("btn-name");
                                  inp.classList.add("hide");
                                  btn.classList.add("hide");
                                  break;
                               case 'ROOM_CREATED':
                                  this.room = data.data;
                                  const inpr = document.getElementById("input-room");
                                  const btnr = document.getElementById("btn-room");
                                  inpr.classList.add("hide");
                                  btnr.classList.add("hide");
                                  break;
                               case 'CHAT':
                                  this.storyValue += `${data.data}\n`;
                                  break;
                               default:
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
        const data = {action: 'CREATE_ROOM', data: `${this.roomValue}`};
        const dataString = JSON.stringify(data);
        console.log(dataString);
        this.socket.send(dataString);
        this.roomValue = "";
    }
    
    _handleChatInput(e) {
        this.chatValue = e.target.value;
    }
    _handleNameInput(e) {
        this.nameValue = e.target.value;
    }
    _handleRoomInput(e) {
            this.roomValue = e.target.value;
        }
}

customElements.define('planning-room', PlanningRoom);