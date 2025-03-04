const Joi = require('joi');

// Валидация при добавлении аккаунта
exports.addAccount = Joi.object({
  email: Joi.string().email().required(),
  name: Joi.string().regex(/^[a-zA-Z0-9_]+$/).required(),
  password: Joi.string().min(8).regex(/(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])/).required()
});

// Валидация при обновлении пароля
exports.updatePassword = Joi.object({
  email: Joi.string().email().required(),
  password: Joi.string().min(8).regex(/(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])/).required()
});

// Валидация при изменении роли
exports.setRole = Joi.object({
  email: Joi.string().email().required(),
  role: Joi.string().valid('USER', 'PREMIUM_USER', 'ADMIN').required()
});

// Валидация при логине
exports.login = Joi.object({
  email: Joi.string().email().required(),
  password: Joi.string().required()
});
