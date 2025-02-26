const Movie = require('../models/movie');
const mongoose = require('mongoose');

// Получение фильма по ID
exports.getMovieById = async (id) => {
  if (!mongoose.Types.ObjectId.isValid(id)) {
    throw new Error('Неверный идентификатор объекта');
  }

  const movie = await Movie.findById(id);
  if (!movie) {
    throw new Error('Фильм не найден');
  }

  return movie;
};

// Получение самых рейтинговых фильмов по заданному фильтру
exports.getMostRatedMovies = async (filter) => {
  const { year, actor, genres, language, amount } = filter;

  const query = {};
  if (year) query.year = year;
  if (actor) query.actors = { $regex: new RegExp(actor, 'i') };
  if (genres) query.genres = { $in: genres };
  if (language) query.language = language;

  const movies = await Movie.find(query)
    .sort({ 'imdb.rating': -1 })
    .limit(amount)
    .select('_id title imdb.rating imdb.id');

  return movies;
};

// Получение самых комментируемых фильмов по заданному фильтру
exports.getMostCommentedMovies = async (filter) => {
  const { year, actor, genres, language, amount } = filter;

  const query = {};
  if (year) query.year = year;
  if (actor) query.actors = { $regex: new RegExp(actor, 'i') };
  if (genres) query.genres = { $in: genres };
  if (language) query.language = language;

  const movies = await Movie.find(query)
    .sort({ num_mflix_comments: -1 })
    .limit(amount)
    .select('_id title imdb.id num_mflix_comments');

  return movies;
};

// Обновление рейтинга фильма
exports.updateMovieRating = async (id, rating) => {
  if (!mongoose.Types.ObjectId.isValid(id)) {
    throw new Error('Неверный идентификатор объекта');
  }

  const movie = await Movie.findById(id);
  if (!movie) {
    throw new Error('Фильм не найден');
  }

  const newRating = (movie.imdb.rating * movie.imdb.votes + rating) / (movie.imdb.votes + 1);
  movie.imdb.rating = newRating;
  movie.imdb.votes += 1;
  await movie.save();

  return movie;
};
