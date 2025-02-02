class Logger {
    constructor() {

    }
    log(message) {
        console.log(message);
    }
}
const logger = new Logger();
module.exports.logger = logger;