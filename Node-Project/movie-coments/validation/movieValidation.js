const Joi = require('joi');

// Валидация при получении самых рейтинговых фильмов
exports.getMostRated = Joi.object({
  year: Joi.number().optional(),
  actor: Joi.string().optional(),
  genres: Joi.array().items(Joi.string()).optional(),
  language: Joi.string().optional(),
  amount: Joi.number().required()
});

// Валидация при получении самых комментируемых фильмов
exports.getMostCommented = Joi.object({
  year: Joi.number().optional(),
  actor: Joi.string().optional(),
  genres: Joi.array().items(Joi.string()).optional(),
  language: Joi.string().optional(),
  amount: Joi.number().required()
});

// Валидация при добавлении рейтинга к фильму
exports.addRate = Joi.object({
  id: Joi.string().length(24).required(),
  rating: Joi.number().min(1).max(10).required()
});
