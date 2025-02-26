const User = require('../models/user');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const config = require('config');

exports.addAccount = async (email, name, password) => {
  // Проверка, существует ли аккаунт
  let user = await User.findById(email);
  if (user) {
    throw new Error('Аккаунт уже существует');
  }

  // Хэширование пароля
  const hashPassword = await bcrypt.hash(password, 10);

  // Создание нового пользователя
  user = new User({
    _id: email,
    name,
    role: 'USER',
    hashPassword,
    expiration: Date.now() + 1000 * 60 * 60 * 24, // Пример: 1 день
    blocked: false
  });

  return await user.save();
};

exports.login = async (email, password) => {
  // Проверка аккаунта
  const user = await User.findById(email);
  if (!user) {
    throw new Error('Неверный email или пароль');
  }

  // Проверка пароля
  const isMatch = await bcrypt.compare(password, user.hashPassword);
  if (!isMatch) {
    throw new Error('Неверный email или пароль');
  }

  // Генерация JWT
  const token = jwt.sign(
    { email: user._id, role: user.role },
    config.get('jwtSecret'),
    { expiresIn: '1h' }
  );

  return token;
};

exports.getAccount = async (email) => {
  return await User.findById(email);
};

exports.updatePassword = async (email, password) => {
  // Проверка аккаунта
  const user = await User.findById(email);
  if (!user) {
    throw new Error('Аккаунт не найден');
  }

  // Хэширование нового пароля
  const hashPassword = await bcrypt.hash(password, 10);
  user.hashPassword = hashPassword;

  await user.save();
  return 'Пароль обновлен';
};

exports.blockAccount = async (email) => {
  const user = await User.findById(email);
  if (!user) {
    throw new Error('Аккаунт не найден');
  }

  user.blocked = true;
  await user.save();

  return 'Аккаунт заблокирован';
};


exports.unblockAccount = async (email) => {
  const user = await User.findById(email);
  if (!user) {
    throw new Error('Аккаунт не найден');
  }

  user.blocked = false;
  await user.save();

  return 'Аккаунт разблокирован';
};


exports.deleteAccount = async (email) => {
  const user = await User.findByIdAndDelete(email);
  if (!user) {
    throw new Error('Аккаунт не найден');
  }
  return 'Аккаунт удален';
};
