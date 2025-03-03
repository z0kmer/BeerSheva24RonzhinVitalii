const bcrypt = require('bcryptjs');

// Хеширование пароля
exports.hashPassword = async (password) => {
  const hashPassword = await bcrypt.hash(password, 10);
  return hashPassword;
};

// Сравнение хешированного пароля
exports.comparePassword = async (password, hashPassword) => {
  const isMatch = await bcrypt.compare(password, hashPassword);
  return isMatch;
};
