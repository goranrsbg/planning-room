import {LitElement, html} from 'lit';

export class SimpleGreeting extends LitElement {
    static properties = {
        version:        {type: String},
		dataHelloWorld: {type: String, attribute: 'data-hello-world' },
		socket:         {type: Object, attribute: false}, 
    };
	

    constructor() {
        super();
        this.version = 'STARTING';
        const wsUri = `ws://${location.host + location.pathname}room/123456`
		this.socket = new WebSocket(wsUri);
		this.socket.addEventListener('open', (event) => {
		           console.log('WebSocket connection established!', event);
		           const data = {from: 'User1', content: 'hello world'};
                   const dataString = JSON.stringify(data);
                   console.log(dataString);
                   this.socket.send(dataString);
		       });
	    this.socket.addEventListener('message', (event) => {
	               const data = JSON.parse(event.data);
	               console.log('Message from server:', data);
	           });
    }

    render() {
        return html`
            <p>Welcome to the Lit tutorial!</p>
            <p>This is the ${this.version} code.</p>
			<p>Data ${JSON.parse(this.dataHelloWorld).name}</p>
        `;
    }

}

customElements.define('s-g', SimpleGreeting);