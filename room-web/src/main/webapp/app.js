import {LitElement, html} from 'lit';
import "./app.scss";

export class SimpleGreeting extends LitElement {
    static properties = {
        version:        {type: String},
		dataHelloWorld: {type: String, attribute: 'data-hello-world' },
		socket:         {type: Object, attribute: false},
        sendValue:      {type: String, attribute: false},
        storyValue:     {type: String, attribute: false},
    };
	
    constructor() {
        super();
        this.version = 'STARTING';
        this.sendValue = "";
        this.storyValue = "";
    }
    
    createRenderRoot() {
        return this;
    }

    render() {
        return html`
            <p>Welcome to the Lit tutorial!</p>
            <p>This is the ${this.version} code.</p>
			<p>Data ${JSON.parse(this.dataHelloWorld).name}</p>
            <div class="col">
              <label for="story">Story:</label>
              <textarea id="story" name="story" rows="17" cols="37" .value=${this.storyValue}>
              </textarea>
              <input type="text" size="37" .value=${this.sendValue} @input=${this._handleInput} />
              <div class="row">
                <button type="button" @click="${this.connect}">Connect</button>
                <button type="button" @click="${this.sendMessage}">Send</button>
              </div>
            </div>
        `;
    }
    
    connect() {
        let wsUri = 'ws://${location.host + location.pathname}room/123456'
        this.socket = new WebSocket(wsUri);
        this.socket.addEventListener('open', (event) => {
                           console.log('WebSocket connection established!', event);
                        });
        this.socket.addEventListener('message', (event) => {
                           const data = JSON.parse(event.data);
                           console.log('Message from server:', data);
                           this.storyValue += `${data.from}: ${data.content}\n`;
                       });
    }
    
    sendMessage() {
        const data = {from: 'User1', content: `${this.sendValue.trim()}`};
        const dataString = JSON.stringify(data);
        console.log(dataString);
        this.socket.send(dataString);
        this.sendValue = "";
    }
    
    _handleInput(e) {
        this.sendValue = e.target.value;
    }
}

customElements.define('s-g', SimpleGreeting);