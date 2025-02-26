const mongoose = require('mongoose');

const imdbSchema = new mongoose.Schema({
  rating: {
    type: Number,
    default: 0,
  },
  votes: {
    type: Number,
    default: 0,
  },
  id: {
    type: Number,
    required: true,
  }
});

const movieSchema = new mongoose.Schema({
  title: String,
  year: Number,
  genres: [String],
  runtime: Number,
  rated: String,
  cast: [String],
  fullplot: String,
  languages: [String],
  released: Date,
  directors: [String],
  writers: [String],
  awards: {
    wins: Number,
    nominations: Number,
    text: String,
  },
  lastupdated: String,
  num_mflix_comments: {
    type: Number,
    default: 0,
  },
  imdb: imdbSchema,
  countries: [String],
  type: String,
  tomatoes: {
    viewer: {
      rating: Number,
      numReviews: Number,
    },
    production: String,
    lastUpdated: Date,
  },
});

module.exports = mongoose.model('Movie', movieSchema);
