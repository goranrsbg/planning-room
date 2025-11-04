import {LitElement, html} from 'lit';
import "./app.scss";

export class PlanningRoom extends LitElement {
    static properties = {
		socket:         {type: Object, attribute: false},
        sendValue:      {type: String, attribute: false},
        storyValue:     {type: String, attribute: false},
        nameValue:      {type: String, attribute: false},
        name:           {type: String, attribute: false},
    };
	
    constructor() {
        super();
        this.sendValue = "";
        this.storyValue = "";
        this.nameValue = "";
        this.name = "";
    }
    
    createRenderRoot() {
        return this;
    }

    render() {
        return html`
            <div class="col">
              <label for="story">Story:</label>
              <textarea id="story" name="story" rows="17" cols="37" .value=${this.storyValue}></textarea>
              <input type="text" size="37" .value=${this.sendValue} @input=${this._handleInput} />
              <input type="text" size="37" .value=${this.nameValue} @input=${this._handleNameInput} />
              <label>${this.name}</label>
              <div class="row">
                <button type="button" @click="${this.connect}">Connect</button>
                <button type="button" @click="${this.sendMessage}">Send</button>
                <button type="button" @click="${this.sendName}">Send name</button>
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
                                  break;
                               case 'CHAT':
                                  this.storyValue += `${data.data}\n`;
                                  break;
                               default:
                           }
                       });
    }
    
    sendMessage() {
        const data = {action: 'CHAT', data: `${this.sendValue.trim()}`};
        const dataString = JSON.stringify(data);
        console.log(dataString);
        this.socket.send(dataString);
        this.sendValue = "";
    }
    sendName() {
        const data = {action: 'SET_NAME', data: `${this.nameValue}`};
        const dataString = JSON.stringify(data);
        console.log(dataString);
        this.socket.send(dataString);
        this.nameValue = "";
    }
    
    _handleInput(e) {
        this.sendValue = e.target.value;
    }
    _handleNameInput(e) {
        this.nameValue = e.target.value;
    }
}

customElements.define('planning-room', PlanningRoom);