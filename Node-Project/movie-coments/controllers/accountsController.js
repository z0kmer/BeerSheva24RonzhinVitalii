const User = require('../models/user');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const config = require('config');

// Добавление учетной записи пользователя
exports.addAccount = async (req, res) => {
  const { email, name, password } = req.body;

  try {
    let user = await User.findOne({ email });
    if (user) {
      return res.status(400).json({ message: 'Учетная запись уже существует' });
    }

    const hashPassword = await bcrypt.hash(password, 10);

    user = new User({
      _id: email,
      name,
      role: 'USER',
      hashPassword,
      blocked: false
    });

    await user.save();
    res.json(user);
  } catch (err) {
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Добавление учетной записи администратора
exports.addAdminAccount = async (req, res) => {
  const { email, name, password } = req.body;

  try {
    let user = await User.findOne({ email });
    if (user) {
      return res.status(409).json({ message: 'Учетная запись уже существует' });
    }

    user = new User({
      _id: email,
      name,
      role: 'ADMIN',
      hashPassword: await bcrypt.hash(password, 10),
      blocked: false
    });

    await user.save();
    res.status(201).json(user);
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Изменение роли пользователя
exports.setRole = async (req, res) => {
  try {
    const { email, role } = req.body;

    let user = await User.findById(email);
    if (!user) {
      return res.status(404).json({ message: 'Пользователь не найден' });
    }

    user.role = role;
    await user.save();

    res.json(user);
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Обновление пароля
exports.updatePassword = async (req, res) => {
  const { email, password } = req.body;

  try {
    let user = await User.findById(email);
    if (!user) {
      return res.status(404).json({ message: 'Пользователь не найден' });
    }

    const hashPassword = await bcrypt.hash(password, 10);
    user.hashPassword = hashPassword;
    await user.save();

    res.json({ message: 'Пароль обновлен', user });
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Получение учетной записи
exports.getAccount = async (req, res) => {
  try {
    const user = await User.findById(req.params.email);
    if (!user) {
      return res.status(404).json({ message: 'Учетная запись не найдена' });
    }
    res.json(user);
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Блокировка учетной записи
exports.blockAccount = async (req, res) => {
  try {
    const user = await User.findById(req.params.email);
    if (!user) {
      return res.status(404).json({ message: 'Учетная запись не найдена' });
    }

    user.blocked = true;
    await user.save();

    res.json({ message: 'Аккаунт заблокирован', user });
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Разблокировка учетной записи
exports.unblockAccount = async (req, res) => {
  try {
    const user = await User.findById(req.params.email);
    if (!user) {
      return res.status(404).json({ message: 'Учетная запись не найдена' });
    }

    user.blocked = false;
    await user.save();

    res.json({ message: 'Учетная запись разблокирована', user });
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Удаление учетной записи
exports.deleteAccount = async (req, res) => {
  try {
    const user = await User.findByIdAndDelete(req.params.email);
    if (!user) {
      return res.status(404).json({ message: 'Учетная запись не найдена' });
    }

    res.json({ message: 'Учетная запись удалена' });
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Логин
exports.login = async (req, res) => {
  const { email, password } = req.body;

  try {
    const user = await User.findOne({ _id: email });
    if (!user) {
      return res.status(400).json({ message: 'Неверные учетные данные' });
    }

    const isMatch = await bcrypt.compare(password, user.hashPassword);
    if (!isMatch) {
      return res.status(400).json({ message: 'Неверные учетные данные' });
    }

    const payload = {
      user: {
        id: user._id,
        role: user.role
      }
    };

    jwt.sign(
      payload,
      config.get('jwtSecret'),
      { expiresIn: '1h' },
      (err, token) => {
        if (err) throw err;
        res.json({ token });
      }
    );
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};