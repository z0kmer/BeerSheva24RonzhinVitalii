const Comment = require('../models/comment');
const Movie = require('../models/movie');
const mongoose = require('mongoose');

// Получение всех комментариев к фильму
exports.getCommentsByMovieId = async (movieId) => {
  if (!mongoose.Types.ObjectId.isValid(movieId)) {
    throw new Error('Неверный идентификатор объекта');
  }

  const comments = await Comment.find({ movie_id: movieId }).select('email text');
  if (!comments || comments.length === 0) {
    throw new Error('Комментарии к этому фильму не найдены');
  }

  return comments;
};

// Добавление комментария к фильму
exports.addComment = async (commentData) => {
  const { email, movie_id, text } = commentData;

  if (!mongoose.Types.ObjectId.isValid(movie_id)) {
    throw new Error('Неверный идентификатор объекта');
  }

  const movie = await Movie.findById(movie_id);
  if (!movie) {
    throw new Error('Фильм не найден');
  }

  const comment = new Comment({
    email,
    movie_id,
    text,
    date: new Date()
  });

  await comment.save();

  movie.num_mflix_comments += 1;
  await movie.save();

  return comment;
};

// Обновление комментария
exports.updateComment = async (commentData) => {
  const { commentId, email, text } = commentData;

  if (!mongoose.Types.ObjectId.isValid(commentId)) {
    throw new Error('Неверный идентификатор объекта');
  }

  const comment = await Comment.findById(commentId);
  if (!comment) {
    throw new Error('Комментарий не найден');
  }

  if (comment.email !== email) {
    throw new Error('Доступ запрещен');
  }

  comment.text = text;
  await comment.save();

  return comment;
};

// Получение всех комментариев пользователя
exports.getCommentsByEmail = async (email) => {
  const comments = await Comment.find({ email });
  if (!comments || comments.length === 0) {
    throw new Error('Комментарии не найдены');
  }

  return comments;
};

// Удаление комментария
exports.deleteComment = async (commentId, user) => {
  if (!mongoose.Types.ObjectId.isValid(commentId)) {
    throw new Error('Неверный идентификатор объекта');
  }

  const comment = await Comment.findById(commentId);
  if (!comment) {
    throw new Error('Комментарий не найден');
  }

  if (comment.email !== user.id && user.role !== 'ADMIN') {
    throw new Error('Доступ запрещен');
  }

  const movie = await Movie.findById(comment.movie_id);
  if (movie) {
    movie.num_mflix_comments -= 1;
    await movie.save();
  }

  await comment.remove();

  return { message: 'Комментарий удален' };
};
