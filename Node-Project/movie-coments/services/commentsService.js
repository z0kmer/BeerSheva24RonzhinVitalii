const Comment = require('../models/comment');
const Movie = require('../models/movie');

// Получение всех комментариев к фильму
exports.getMovieComments = async (movieId) => {
  const comments = await Comment.find({ movie_id: movieId }).select('email text');
  return comments;
};

// Добавление комментария
exports.addComment = async (email, movieId, text) => {
  const movie = await Movie.findById(movieId);
  if (!movie) {
    throw new Error('Фильм не найден');
  }

  const comment = new Comment({
    email,
    movie_id: movieId,
    text,
    date: new Date()
  });

  await comment.save();

  movie.num_mflix_comments += 1;
  await movie.save();

  return comment;
};

// Обновление комментария
exports.updateComment = async (commentId, email, text) => {
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
exports.getUserComments = async (email) => {
  const comments = await Comment.find({ email });
  return comments;
};

// Удаление комментария
exports.deleteComment = async (commentId, user) => {
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
