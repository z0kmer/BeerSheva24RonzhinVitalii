const jwt = require('jsonwebtoken');
const config = require('config');

// Генерация JWT токена
exports.generateToken = (payload) => {
  const token = jwt.sign(payload, config.get('jwtSecret'), { expiresIn: '1h' });
  return token;
};

// Верификация JWT токена
exports.verifyToken = (token) => {
  const decoded = jwt.verify(token, config.get('jwtSecret'));
  return decoded;
};
