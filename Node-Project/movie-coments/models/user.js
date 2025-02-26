const mongoose = require('mongoose');

const UserSchema = new mongoose.Schema({
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
    default: 'USER'
  },
  hashPassword: {
    type: String,
    required: true
  },
  blocked: {
    type: Boolean,
    default: false
  }
});

module.exports = mongoose.model('User', UserSchema);
