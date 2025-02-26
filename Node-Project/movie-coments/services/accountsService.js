const User = require('../models/user');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const config = require('config');

// Добавление учетной записи пользователя
exports.addUserAccount = async (accountData) => {
  const { email, name, password } = accountData;

  let user = await User.findOne({ email });
  if (user) {
    throw new Error('Учетная запись уже существует');
  }

  user = new User({
    _id: email,
    name,
    role: 'USER',
    hashPassword: await bcrypt.hash(password, 10),
    blocked: false
  });

  await user.save();

  return user;
};

// Добавление учетной записи администратора
exports.addAdminAccount = async (accountData) => {
  const { email, name, password } = accountData;

  let user = await User.findOne({ email });
  if (user) {
    throw new Error('Учетная запись уже существует');
  }

  user = new User({
    _id: email,
    name,
    role: 'ADMIN',
    hashPassword: await bcrypt.hash(password, 10),
    blocked: false
  });

  await user.save();

  return user;
};

// Изменение роли пользователя
exports.setUserRole = async (roleData) => {
  const { email, role } = roleData;

  let user = await User.findById(email);
  if (!user) {
    throw new Error('Пользователь не найден');
  }

  user.role = role;
  await user.save();

  return user;
};

// Обновление пароля пользователя
exports.updateUserPassword = async (passwordData) => {
  const { email, password } = passwordData;

  let user = await User.findById(email);
  if (!user) {
    throw new Error('Пользователь не найден');
  }

  user.hashPassword = await bcrypt.hash(password, 10);
  await user.save();

  return user;
};

// Получение учетной записи пользователя
exports.getUserAccount = async (email) => {
  let user = await User.findById(email);
  if (!user) {
    throw new Error('Учетная запись не найдена');
  }

  return user;
};

// Блокировка учетной записи пользователя
exports.blockUserAccount = async (email) => {
  let user = await User.findById(email);
  if (!user) {
    throw new Error('Учетная запись не найдена');
  }

  user.blocked = true;
  await user.save();

  return user;
};

// Разблокировка учетной записи пользователя
exports.unblockUserAccount = async (email) => {
  let user = await User.findById(email);
  if (!user) {
    throw new Error('Учетная запись не найдена');
  }

  user.blocked = false;
  await user.save();

  return user;
};

// Удаление учетной записи пользователя
exports.deleteUserAccount = async (email) => {
  let user = await User.findByIdAndDelete(email);
  if (!user) {
    throw new Error('Учетная запись не найдена');
  }

  return { message: 'Учетная запись удалена' };
};

// Логин пользователя
exports.userLogin = async (loginData) => {
  const { email, password } = loginData;

  const user = await User.findOne({ _id: email });
  if (!user) {
    throw new Error('Неверные учетные данные');
  }

  const isMatch = await bcrypt.compare(password, user.hashPassword);
  if (!isMatch) {
    throw new Error('Неверные учетные данные');
  }

  const payload = {
    user: {
      id: user._id,
      role: user.role
    }
  };

  const token = jwt.sign(payload, config.get('jwtSecret'), { expiresIn: '1h' });

  return { token };
};
