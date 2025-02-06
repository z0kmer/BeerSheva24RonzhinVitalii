export default class FilterLogMessages {
    #words;
    #messages;
    constructor(logger, words, level) {
        this.#messages = [];
        
        this.#words = words.map(w => w.toLowerCase());
        logger.on(level,(message) => this.#messageProcess(message));
    }
    #messageProcess(message) {
        if(this.#matches(message)) {
            this.#messages.push(message);
        }
    }
    getMessage() {
        return this.#messages
    }
    #matches(message) {
        message = message.toLowerCase();
        return this.#words.some(w => message.includes(w));
    }
}