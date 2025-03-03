const mongoose = require('mongoose');

const movieSchema = new mongoose.Schema({
  title: {
    type: String,
    required: true
  },
  year: {
    type: Number,
    required: true
  },
  imdb: {
    id: {
      type: Number,
      required: true
    },
    rating: {
      type: Number,
      required: true
    },
    votes: {
      type: Number,
      required: true
    }
  },
  genres: [String],
  language: String,
  actors: [String],
  num_mflix_comments: {
    type: Number,
    default: 0
  }
});

module.exports = mongoose.model('Movie', movieSchema);
