const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
  _id: {
    type: String,
    required: true
  },
  name: {
    type: String,
    required: true
  },
  role: {
    type: String,
    enum: ['USER', 'PREMIUM_USER', 'ADMIN'],
    default: 'USER',
    required: true
  },
  hashPassword: {
    type: String,
    required: true
  },
  expiration: {
    type: Date,
    default: null
  },
  blocked: {
    type: Boolean,
    default: false
  }
});

module.exports = mongoose.model('User', userSchema);
