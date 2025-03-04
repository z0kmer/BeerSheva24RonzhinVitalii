const Joi = require('joi');

// Валидация при добавлении комментария
exports.addComment = Joi.object({
  email: Joi.string().email().required(),
  movie_id: Joi.string().length(24).required(),
  text: Joi.string().required()
});

// Валидация при обновлении комментария
exports.updateComment = Joi.object({
  commentId: Joi.string().length(24).required(),
  email: Joi.string().email().required(),
  text: Joi.string().required()
});
