const Favorite = require('../models/favorite');
const Movie = require('../models/movie');

// Получение всех избранных фильмов пользователя
exports.getUserFavorites = async (req, res) => {
  try {
    const favorites = await Favorite.find({ email: req.params.email }).populate('movie_id');
    res.json(favorites);
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Добавление в избранное
exports.addFavorite = async (req, res) => {
  const { email, movie_id, feed_back, viewed = false } = req.body;

  try {
    const movie = await Movie.findById(movie_id);
    if (!movie) {
      return res.status(404).json({ message: 'Фильм не найден' });
    }

    const existingFavorite = await Favorite.findOne({ email, movie_id });
    if (existingFavorite) {
      return res.status(409).json({ message: 'Фильм уже добавлен в избранное' });
    }

    const favorite = new Favorite({
      email,
      movie_id,
      feed_back,
      viewed
    });

    await favorite.save();
    res.status(201).json(favorite);
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Обновление избранного
exports.updateFavorite = async (req, res) => {
  const { favoriteId, viewed, feed_back } = req.body;

  try {
    const favorite = await Favorite.findById(favoriteId);
    if (!favorite) {
      return res.status(404).json({ message: 'Избранное не найдено' });
    }

    favorite.viewed = viewed;
    favorite.feed_back = feed_back;
    await favorite.save();

    res.json({ message: 'Избранное обновлено', favorite });
  } catch (err) {
    res.status(500).json({ message: 'Ошибка сервера', error: err.message });
  }
};

// Удаление избранного
exports.deleteFavorite = async (req, res) => {
  const { favoriteId } = req.params;

  try {
    const favorite = await Favorite.findById(favoriteId);
    if (!favorite) {
      return res.status(404).json({ message: 'Избранное не найдено' });
    }

    await Favorite.deleteOne({ _id: favoriteId });
    res.json({ message: 'Избранное удалено' });
  } catch (err) {
    res.status(500).json({ message: 'Ошибка сервера', error: err.message });
  }
};
