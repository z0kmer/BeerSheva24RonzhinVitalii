const mongoose = require('mongoose');

const favoriteSchema = new mongoose.Schema({
  email: {
    type: String,
    required: true
  },
  movie_id: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Movie',
    required: true
  },
  viewed: {
    type: Boolean,
    default: false
  },
  feed_back: {
    type: String
  }
});

favoriteSchema.index({ email: 1, movie_id: 1 }, { unique: true });

module.exports = mongoose.model('Favorite', favoriteSchema);
