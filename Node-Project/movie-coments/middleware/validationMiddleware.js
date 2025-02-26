const Joi = require('joi');
const mongoose = require('mongoose');

const validateObjectId = (req, res, next) => {
  if (!mongoose.Types.ObjectId.isValid(req.params.id)) {
    return res.status(400).json({ message: 'Неверный идентификатор объекта' });
  }
  next();
};

const validateMovieFilter = (req, res, next) => {
  const schema = Joi.object({
    year: Joi.number().integer().min(1888).max(new Date().getFullYear()).optional(),
    actor: Joi.string().optional(),
    genres: Joi.array().items(Joi.string()).optional(),
    language: Joi.string().optional(),
    amount: Joi.number().integer().min(1).optional(),
  });

  const { error } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({ message: error.details[0].message });
  }
  next();
};

const validateRating = (req, res, next) => {
  const schema = Joi.object({
    id: Joi.string().required(),
    rating: Joi.number().min(1).max(10).required(),
  });

  const { error } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({ message: error.details[0].message });
  }
  next();
};

const validateComment = (req, res, next) => {
  const schema = Joi.object({
    email: Joi.string().email().required(),
    movie_id: Joi.string().length(24).required(),
    text: Joi.string().required(),
  });

  const { error } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({ message: error.details[0].message });
  }
  next();
};

const validateUpdateComment = (req, res, next) => {
  const schema = Joi.object({
    commentId: Joi.string().length(24).required(),
    email: Joi.string().email().required(),
    text: Joi.string().required(),
  });

  const { error } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({ message: error.details[0].message });
  }
  next();
};

const validateFavorite = (req, res, next) => {
  const schema = Joi.object({
    email: Joi.string().email().required(),
    movie_id: Joi.string().length(24).required(),
    viewed: Joi.boolean().optional(),
    feed_back: Joi.string().optional(),
  });

  const { error } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({ message: error.details[0].message });
  }
  next();
};

const validateUpdateFavorite = (req, res, next) => {
  const schema = Joi.object({
    favoriteId: Joi.string().length(24).required(),
    email: Joi.string().email().required(),
    viewed: Joi.boolean().optional(),
    feed_back: Joi.string().optional(),
  });

  const { error } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({ message: error.details[0].message });
  }
  next();
};

const validateAccount = (req, res, next) => {
  const schema = Joi.object({
    email: Joi.string().email().required(),
    name: Joi.string().required(),
    password: Joi.string().min(5).required()
  });

  const { error } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({ message: error.details[0].message });
  }
  next();
};

const validateLogin = (req, res, next) => {
  const schema = Joi.object({
    email: Joi.string().email().required(),
    password: Joi.string().min(5).required()
  });

  const { error } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({ message: error.details[0].message });
  }
  next();
};

const validateAdminAccount = (req, res, next) => {
  const schema = Joi.object({
    email: Joi.string().email().required(),
    name: Joi.string().required(),
    password: Joi.string().min(8).regex(/[a-z]/).regex(/[A-Z]/).regex(/[0-9]/).regex(/[\W_]/).required(),
  });

  const { error } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({ message: error.details[0].message });
  }
  next();
};

const validateRole = (req, res, next) => {
  const schema = Joi.object({
    email: Joi.string().email().required(),
    role: Joi.string().valid('USER', 'PREMIUM_USER', 'ADMIN').required(),
  });

  const { error } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({ message: error.details[0].message });
  }
  next();
};

const validatePassword = (req, res, next) => {
  const schema = Joi.object({
    email: Joi.string().email().required(),
    password: Joi.string().min(8).regex(/[a-z]/).regex(/[A-Z]/).regex(/[0-9]/).regex(/[\W_]/).required(),
  });

  const { error } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({ message: error.details[0].message });
  }
  next();
};

const validateAmount = (req, res, next) => {
  const schema = Joi.object({
    amount: Joi.number().integer().min(1).required()
  });

  const { error } = schema.validate(req.body);
  if (error) {
    return res.status(400).json({ message: error.details[0].message });
  }
  next();
};

module.exports = {
  validateObjectId,
  validateMovieFilter,
  validateRating,
  validateComment,
  validateUpdateComment,
  validateFavorite,
  validateUpdateFavorite,
  validateAccount,
  validateLogin,
  validateAdminAccount,
  validateRole,
  validatePassword,
  validateAmount
};
