// import winston from 'winston';

// const logger = winston.createLogger({
//     level:process.env.LEVEL ?? 'info',
//     format: winston.format.simple(),
//     transports: [
//         new winston.transports.Console
//     ]

// })
// export default logger;

import { EventEmitter } from "node:events";
import winston from "winston";

class Logger extends EventEmitter {
  #logger;
  constructor() {
    super();
    this.#logger = winston.createLogger({
      level: process.env.LEVEL ?? "info",
      format: winston.format.simple(),
      transports: [new winston.transports.Console()],
    });
  }
  log(level, message) {
    this.#logger.log(level, message);
    this.emit(level, message);
    this.emit('message', {level, message});
  }
}
const logger = new Logger();
export default logger;