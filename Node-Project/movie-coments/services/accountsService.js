const User = require('../models/user');
const bcrypt = require('bcryptjs');

// Добавление учетной записи пользователя
exports.addAccount = async (email, name, password) => {
  let user = await User.findOne({ _id: email });
  if (user) {
    throw new Error('Учетная запись уже существует');
  }

  const hashPassword = await bcrypt.hash(password, 10);
  user = new User({ _id: email, name, role: 'USER', hashPassword });
  await user.save();

  return user;
};

// Добавление учетной записи администратора
exports.addAdminAccount = async (email, name, password) => {
  let user = await User.findOne({ _id: email });
  if (user) {
    throw new Error('Учетная запись уже существует');
  }

  const hashPassword = await bcrypt.hash(password, 10);
  user = new User({ _id: email, name, role: 'ADMIN', hashPassword });
  await user.save();

  return user;
};

// Изменение роли пользователя
exports.setRole = async (email, role) => {
  const user = await User.findById(email);
  if (!user) {
    throw new Error('Пользователь не найден');
  }

  user.role = role;
  await user.save();

  return user;
};

// Обновление пароля
exports.updatePassword = async (email, newPassword) => {
  const user = await User.findById(email);
  if (!user) {
    throw new Error('Пользователь не найден');
  }

  const hashPassword = await bcrypt.hash(newPassword, 10);
  user.hashPassword = hashPassword;
  await user.save();

  return user;
};

// Получение учетной записи
exports.getAccount = async (email) => {
  const user = await User.findById(email);
  if (!user) {
    throw new Error('Учетная запись не найдена');
  }

  return user;
};

// Блокировка учетной записи
exports.blockAccount = async (email) => {
  const user = await User.findById(email);
  if (!user) {
    throw new Error('Учетная запись не найдена');
  }

  user.blocked = true;
  await user.save();

  return user;
};

// Разблокировка учетной записи
exports.unblockAccount = async (email) => {
  const user = await User.findById(email);
  if (!user) {
    throw new Error('Учетная запись не найдена');
  }

  user.blocked = false;
  await user.save();

  return user;
};

// Удаление учетной записи
exports.deleteAccount = async (email) => {
  const user = await User.findByIdAndDelete(email);
  if (!user) {
    throw new Error('Учетная запись не найдена');
  }

  return { message: 'Учетная запись удалена' };
};

// Логин
exports.login = async (email, password, jwtSecret) => {
  const user = await User.findOne({ _id: email });
  if (!user) {
    throw new Error('Неверные учетные данные');
  }

  const isMatch = await bcrypt.compare(password, user.hashPassword);
  if (!isMatch) {
    throw new Error('Неверные учетные данные');
  }

  const payload = { user: { id: user._id, role: user.role } };
  const token = jwt.sign(payload, jwtSecret, { expiresIn: '1h' });

  return token;
};
