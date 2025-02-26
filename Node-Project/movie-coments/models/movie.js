const mongoose = require('mongoose');

const movieSchema = new mongoose.Schema({
  imdb: {
    id: { type: String, required: true },
    rating: { type: Number, default: 0 },
    votes: { type: Number, default: 0 }
  },
  plot: { type: String },
  genres: [{ type: String }],
  runtime: { type: Number },
  rated: { type: String },
  cast: [{ type: String }],
  title: { type: String, required: true },
  fullplot: { type: String },
  languages: [{ type: String }],
  released: { type: Date },
  directors: [{ type: String }],
  writers: [{ type: String }],
  awards: {
    wins: { type: Number, default: 0 },
    nominations: { type: Number, default: 0 },
    text: { type: String }
  },
  lastupdated: { type: String },
  year: { type: Number },
  countries: [{ type: String }],
  type: { type: String },
  tomatoes: {
    viewer: {
      rating: { type: Number },
      numReviews: { type: Number }
    },
    dvd: { type: Date },
    production: { type: String },
    lastUpdated: { type: Date }
  },
  num_mflix_comments: { type: Number, default: 0 }
});

module.exports = mongoose.model('Movie', movieSchema);
