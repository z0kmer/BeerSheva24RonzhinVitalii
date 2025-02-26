const Movie = require('../models/movie');

exports.getMovie = async (id) => {
  return await Movie.findById(id);
};

exports.getMostRated = async (filter) => {
  const movies = await Movie.find(filter)
    .sort({ 'imdb.rating': -1 })
    .limit(filter.amount || 10)
    .select('_id title imdb.rating');
  return movies;
};

exports.getMostCommented = async (filter) => {
  const movies = await Movie.find(filter)
    .sort({ num_mflix_comments: -1 })
    .limit(filter.amount || 10)
    .select('_id title imdb.id num_mflix_comments');
  return movies;
};

exports.addRate = async (id, rating) => {
  const movies = await Movie.updateMany(
    { 'imdb.id': id },
    { $set: { 'imdb.rating': rating } }
  );
  return movies.nModified;
};
