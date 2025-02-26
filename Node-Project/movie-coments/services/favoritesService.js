const Favorite = require('../models/favorite');

exports.addFavorite = async (email, movie_id, feed_back, viewed = false) => {
  const newFavorite = new Favorite({ email, movie_id, feed_back, viewed });
  return await newFavorite.save();
};

exports.getUserFavorites = async (email) => {
  return await Favorite.find({ email });
};

exports.updateFavorite = async (favoriteId, viewed, feed_back) => {
  const updatedFavorite = await Favorite.findByIdAndUpdate(
    favoriteId,
    { viewed, feed_back },
    { new: true }
  );
  return updatedFavorite;
};

exports.deleteFavorite = async (favoriteId) => {
  return await Favorite.findByIdAndDelete(favoriteId);
};
