const Favorite = require('../models/favorite');
const Movie = require('../models/movie');
const mongoose = require('mongoose');

// Получение всех избранных фильмов пользователя
exports.getFavoritesByEmail = async (email) => {
  const favorites = await Favorite.find({ email }).populate('movie_id');
  if (!favorites || favorites.length === 0) {
    throw new Error('Избранные фильмы не найдены');
  }

  return favorites;
};

// Добавление фильма в избранное
exports.addFavorite = async (favoriteData) => {
  const { email, movie_id, feed_back, viewed = false } = favoriteData;

  if (!mongoose.Types.ObjectId.isValid(movie_id)) {
    throw new Error('Неверный идентификатор объекта');
  }

  const movie = await Movie.findById(movie_id);
  if (!movie) {
    throw new Error('Фильм не найден');
  }

  const existingFavorite = await Favorite.findOne({ email, movie_id });
  if (existingFavorite) {
    throw new Error('Фильм уже добавлен в избранное');
  }

  const favorite = new Favorite({
    email,
    movie_id,
    feed_back,
    viewed
  });

  await favorite.save();

  return favorite;
};

// Обновление избранного
exports.updateFavorite = async (favoriteData) => {
  const { favoriteId, viewed, feed_back } = favoriteData;

  if (!mongoose.Types.ObjectId.isValid(favoriteId)) {
    throw new Error('Неверный идентификатор объекта');
  }

  const favorite = await Favorite.findById(favoriteId);
  if (!favorite) {
    throw new Error('Избранное не найдено');
  }

  favorite.viewed = viewed;
  favorite.feed_back = feed_back;
  await favorite.save();

  return favorite;
};

// Удаление избранного
exports.deleteFavorite = async (favoriteId) => {
  if (!mongoose.Types.ObjectId.isValid(favoriteId)) {
    throw new Error('Неверный идентификатор объекта');
  }

  const favorite = await Favorite.findById(favoriteId);
  if (!favorite) {
    throw new Error('Избранное не найдено');
  }

  await Favorite.deleteOne({ _id: favoriteId });

  return { message: 'Избранное удалено' };
};
