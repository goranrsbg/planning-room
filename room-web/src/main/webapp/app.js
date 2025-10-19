import {LitElement, html} from 'lit';

export class SimpleGreeting extends LitElement {
    static properties = {
        version:        {type: String},
		dataHelloWorld: {type: String, attribute: 'data-hello-world' },
    };

    constructor() {
        super();
        this.version = 'STARTING';
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