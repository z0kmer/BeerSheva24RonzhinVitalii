const jwt = require('jsonwebtoken');
const config = require('config');

exports.generateToken = (email, role) => {
  return jwt.sign({ email, role }, config.get('jwtSecret'), { expiresIn: '1h' });
};

exports.verifyToken = (token) => {
  return jwt.verify(token, config.get('jwtSecret'));
};
