const Comment = require('../models/comment');
const Movie = require('../models/movie');

exports.getMovieComments = async (movieid) => {
  return await Comment.find({ movie_id: movieid }).select('email text');
};

exports.addComment = async (email, movie_id, text) => {
  const newComment = new Comment({ email, movie_id, text });
  const savedComment = await newComment.save();
  
  // Обновление поля num_mflix_comments в фильме
  await Movie.findByIdAndUpdate(movie_id, { $inc: { num_mflix_comments: 1 } });

  return savedComment;
};

exports.updateComment = async (commentId, email, text) => {
  const updatedComment = await Comment.findOneAndUpdate(
    { _id: commentId, email },
    { text },
    { new: true }
  );
  return updatedComment;
};

exports.getUserComments = async (email) => {
  return await Comment.find({ email });
};

exports.deleteComment = async (commentId) => {
  const comment = await Comment.findByIdAndDelete(commentId);
  
  // Обновление поля num_mflix_comments в фильме
  await Movie.findByIdAndUpdate(comment.movie_id, { $inc: { num_mflix_comments: -1 } });

  return comment;
};
