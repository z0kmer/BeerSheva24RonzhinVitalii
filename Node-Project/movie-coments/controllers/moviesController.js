const Movie = require('../models/movie');
const mongoose = require('mongoose');
const Comment = require('../models/comment');

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
  const { year, actor, genres, language, amount } = req.body;

  try {
    const query = {};
    if (year) query.year = year;
    if (actor) query.actors = { $regex: new RegExp(actor, 'i') };
    if (genres) query.genres = { $in: genres };
    if (language) query.language = language;

    const movies = await Movie.find(query)
      .sort({ 'imdb.rating': -1 })
      .limit(amount)
      .select('_id title imdb.rating imdb.id');

    res.json(movies);
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера', error: err.message });
  }
};

// Получение самых комментируемых фильмов по заданному фильтру
exports.getMostCommented = async (req, res) => {
  const { amount } = req.body;

  console.log('Получение самых комментируемых фильмов. Запрошенное количество:', amount);

  try {
    const commentCountMap = {};

    const comments = await Comment.find({});
    comments.forEach(comment => {
      if (comment.movie_id in commentCountMap) {
        commentCountMap[comment.movie_id]++;
      } else {
        commentCountMap[comment.movie_id] = 1;
      }
    });

    const sortedMovieIds = Object.keys(commentCountMap).sort((a, b) => commentCountMap[b] - commentCountMap[a]);
    const topMovieIds = sortedMovieIds.slice(0, amount);

    const movies = await Movie.find({ _id: { $in: topMovieIds } });

    console.log('Полученные фильмы:', movies);
    res.json(movies);
  } catch (err) {
    console.error('Ошибка при получении самых комментируемых фильмов:', err);
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
