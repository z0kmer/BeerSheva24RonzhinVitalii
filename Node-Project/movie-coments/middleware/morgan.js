const morgan = require('morgan');
const logger = require('../config/winston');

const morganMiddleware = (app) => {
  if (process.env.NODE_ENV === 'production') {
    app.use(morgan('combined', {
      stream: {
        write: (message) => {
          if (message.includes('401') || message.includes('403')) {
            logger.warn(message.trim());
          } else {
            logger.info(message.trim());
          }
        }
      }
    }));
  } else {
    app.use(morgan('dev', {
      stream: {
        write: (message) => logger.debug(message.trim())
      }
    }));
  }
};

module.exports = morganMiddleware;
