const Movie = require('../models/movie');
const mongoose = require('mongoose');

// Получение фильма по ID
exports.getMovie = async (req, res) => {
  try {
    const movie = await Movie.findById(req.params.id);
    if (!movie) {
      return res.status(404).json({ message: 'Фильм не найден' });
    }
    res.json(movie);
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Получение самых рейтинговых фильмов по заданному фильтру
exports.getMostRated = async (req, res) => {
  const { amount } = req.body;

  try {
    const movies = await Movie.find()
      .limit(amount)
      .sort({ 'imdb.rating': -1 });

    if (movies.length === 0) {
      return res.status(404).json({ message: 'Фильмы не найдены' });
    }

    res.json(movies);
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера', error: err.message });
  }
};

// Получение самых комментируемых фильмов по заданному фильтру
exports.getMostCommented = async (req, res) => {
  const { year, actor, genres, language, amount } = req.body;

  try {
    const query = {};
    if (year) query.year = year;
    if (actor) query.actors = { $regex: new RegExp(actor, 'i') };
    if (genres) query.genres = { $in: genres };
    if (language) query.language = language;

    const movies = await Movie.find(query)
      .sort({ num_mflix_comments: -1 })
      .limit(amount)
      .select('_id title imdb.id num_mflix_comments');

    res.json(movies);
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Обновление рейтинга фильма
exports.addRate = async (req, res) => {
  const { id, rating } = req.body;

  try {
    // Убедитесь, что идентификатор преобразован в ObjectId
    const movieId = new mongoose.Types.ObjectId(id);

    const movie = await Movie.findById(movieId);
    if (!movie) {
      return res.status(404).json({ message: 'Фильм не найден' });
    }

    const newRating = (movie.imdb.rating * movie.imdb.votes + rating) / (movie.imdb.votes + 1);
    movie.imdb.rating = newRating;
    movie.imdb.votes += 1;
    await movie.save();

    res.json({ message: 'Рейтинг обновлен', movie });
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера', error: err.message });
  }
};
