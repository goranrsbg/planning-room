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
		this.socket = new WebSocket('ws://localhost:8080/room/123456');
		socket.addEventListener('open', (event) => {
		           console.log('WebSocket connection established!', event);
		           const data = {from: 'User1', content: 'hello world'};
				   socket.send(JSON.stringify(data));
		       });
	    socket.addEventListener('message', (event) => {
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