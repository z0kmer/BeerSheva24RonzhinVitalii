const Comment = require('../models/comment');
const Movie = require('../models/movie');

// Получение всех комментариев к фильму
exports.getMovieComments = async (req, res) => {
  try {
    let movie;
    let comments;

    // Сначала пытаемся найти фильм по названию
    movie = await Movie.findOne({ title: req.params.movieid });

    if (movie) {
      // Если фильм найден по названию, используем его _id для поиска комментариев
      comments = await Comment.find({ movie_id: movie._id }).select('email text');
    } else {
      // Если фильм не найден по названию, пытаемся использовать movie_id из параметров запроса
      comments = await Comment.find({ movie_id: req.params.movieid }).select('email text');
    }

    if (comments && comments.length > 0) {
      res.json(comments);
    } else {
      res.status(404).json({ message: 'Комментарии к этому фильму не найдены' });
    }
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Добавление комментария
exports.addComment = async (req, res) => {
  const { email, movie_id, text } = req.body;

  try {
    const movie = await Movie.findById(movie_id);
    if (!movie) {
      return res.status(404).json({ message: 'Фильм не найден' });
    }

    const comment = new Comment({
      email,
      movie_id,
      text,
      date: new Date()
    });

    await comment.save();

    movie.num_mflix_comments += 1;
    await movie.save();

    res.status(201).json(comment);
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Обновление комментария
exports.updateComment = async (req, res) => {
  const { commentId, email, text } = req.body;

  try {
    const comment = await Comment.findById(commentId);
    if (!comment) {
      return res.status(404).json({ message: 'Комментарий не найден' });
    }

    if (comment.email !== email) {
      return res.status(403).json({ message: 'Доступ запрещен' });
    }

    comment.text = text;
    await comment.save();

    res.json(comment);
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};

// Получение всех комментариев пользователя
exports.getUserComments = async (req, res) => {
  const { email } = req.params;

  try {
    const comments = await Comment.find({ email });
    res.json(comments);
  } catch (err) {
    res.status(500).json({ message: 'Ошибка сервера', error: err.message });
  }
};

// Удаление комментария
exports.deleteComment = async (req, res) => {
  try {
    const comment = await Comment.findById(req.params.commentId);
    if (!comment) {
      return res.status(404).json({ message: 'Комментарий не найден' });
    }

    if (comment.email !== req.user.id && req.user.role !== 'ADMIN') {
      return res.status(403).json({ message: 'Доступ запрещен' });
    }

    const movie = await Movie.findById(comment.movie_id);
    if (movie) {
      movie.num_mflix_comments -= 1;
      await movie.save();
    }

    await comment.remove();

    res.json({ message: 'Комментарий удален' });
  } catch (err) {
    console.error(err);
    res.status(500).json({ message: 'Ошибка сервера' });
  }
};
