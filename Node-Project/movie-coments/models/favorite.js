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
  feed_back: {
    type: String
  },
  viewed: {
    type: Boolean,
    default: false
  }
});

module.exports = mongoose.model('Favorite', favoriteSchema);
