const Joi = require('joi');

// Валидация при добавлении избранного
exports.addFavorite = Joi.object({
  email: Joi.string().email().required(),
  movie_id: Joi.string().length(24).required(),
  feed_back: Joi.string().optional(),
  viewed: Joi.boolean().optional()
});

// Валидация при обновлении избранного
exports.updateFavorite = Joi.object({
  favoriteId: Joi.string().length(24).required(),
  email: Joi.string().email().required(),
  viewed: Joi.boolean().optional(),
  feed_back: Joi.string().optional()
});

// Валидация при удалении избранного
exports.deleteFavorite = Joi.object({
  favoriteId: Joi.string().length(24).required(),
  email: Joi.string().email().required()
});
