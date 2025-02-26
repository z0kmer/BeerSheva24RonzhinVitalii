const morgan = require('morgan');
const logger = require('../config/winston');

const morganMiddleware = (app) => {
  if (process.env.NODE_ENV === 'production') {
    app.use(morgan('combined', { stream: logger.stream.write }));
  } else {
    app.use(morgan('dev'));
  }
};

module.exports = morganMiddleware;
