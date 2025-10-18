import {LitElement, html} from 'lit';

export class SimpleGreeting extends LitElement {
    static properties = {
        version: {},
		data-hello-world: {state: true},
    };

    constructor() {
        super();
        this.version = 'STARTING';
    }

    render() {
        return html`
            <p>Welcome to the Lit tutorial!</p>
            <p>This is the ${this.version} code.</p>
			<p>Data ${this.data-hello-world}</p>
        `;
    }

}

customElements.define('s-g', SimpleGreeting);