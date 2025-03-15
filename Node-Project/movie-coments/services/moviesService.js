const Movie = require('../models/movie');
const Comment = require('../models/comment');

// Получение фильма по ID
exports.getMovie = async (id) => {
  const movie = await Movie.findById(id);
  if (!movie) {
    throw new Error('Фильм не найден');
  }

  return movie;
};

// Получение самых рейтинговых фильмов по заданному фильтру
const getMostRated = async (year, actor, genres, language, amount) => {
  const query = {};
  if (year) query.year = year;
  if (actor) query.actors = { $regex: new RegExp(actor, 'i') };
  if (genres) query.genres = { $in: genres };
  if (language) query.language = language;

  // Убедиться, что rating приводится к числу
  const movies = await Movie.find(query)
    .sort({ 'imdb.rating': -1 })
    .limit(amount)
    .select('_id title imdb.rating imdb.id')
    .lean();

  return movies.filter(movie => movie.imdb.rating && !isNaN(movie.imdb.rating));
};


// Получение самых комментируемых фильмов по заданному фильтру
exports.getMostCommented = async (amount) => {
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

  return movies;
};

// Обновление рейтинга фильма
const addRate = async (imdbId, rating) => {
  const movies = await Movie.find({ 'imdb.id': imdbId });
  if (!movies.length) {
    throw new Error('Фильмы не найдены');
  }

  for (const movie of movies) {
    const newRating = (movie.imdb.rating * movie.imdb.votes + rating) / (movie.imdb.votes + 1);
    movie.imdb.rating = newRating;
    movie.imdb.votes += 1;
    await movie.save();
  }

  return movies.length; 
};