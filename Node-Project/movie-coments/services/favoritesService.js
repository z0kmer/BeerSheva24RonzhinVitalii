const Favorite = require('../models/favorite');
const Movie = require('../models/movie');

// Получение всех избранных фильмов пользователя
exports.getUserFavorites = async (email) => {
  const favorites = await Favorite.find({ email }).populate('movie_id');
  return favorites;
};

// Добавление в избранное
const addFavorite = async (email, movieId, feedBack, viewed = false) => {
  const movie = await Movie.findById(movieId);
  if (!movie) {
    throw new Error('Фильм не найден');
  }

  const existingFavorite = await Favorite.findOne({ email, movie_id: movieId });
  if (existingFavorite) {
    throw new Error('Фильм уже добавлен в избранное');
  }

  const favorite = new Favorite({
    email,
    movie_id: movieId,
    feed_back: feedBack,
    viewed
  });

  await favorite.save();
  return favorite;
};


// Обновление избранного
exports.updateFavorite = async (favoriteId, viewed, feedBack) => {
  const favorite = await Favorite.findById(favoriteId);
  if (!favorite) {
    throw new Error('Избранное не найдено');
  }

  favorite.viewed = viewed;
  favorite.feed_back = feedBack;
  await favorite.save();

  return favorite;
};

// Удаление избранного
exports.deleteFavorite = async (favoriteId) => {
  const favorite = await Favorite.findById(favoriteId);
  if (!favorite) {
    throw new Error('Избранное не найдено');
  }

  await Favorite.deleteOne({ _id: favoriteId });
  return { message: 'Избранное удалено' };
};
