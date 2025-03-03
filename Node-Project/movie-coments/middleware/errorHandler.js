const winston = require('../config/winston');

const errorHandler = (err, req, res, next) => {
  winston.error(err.message, { metadata: err });

  console.error(err.stack);

  res.status(500).json({ message: 'Внутренняя ошибка сервера' });
};

module.exports = errorHandler;
