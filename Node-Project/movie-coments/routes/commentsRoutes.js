const express = require('express');
const router = express.Router();
const commentsController = require('../controllers/commentsController');
const { auth, authorize } = require('../middleware/authMiddleware');
const { validateObjectId, validateComment, validateUpdateComment } = require('../middleware/validationMiddleware');

// Получение всех комментариев к фильму
router.get('/:movieid', validateObjectId, commentsController.getMovieComments);

// Добавление комментария к фильму
router.post('/add', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), validateComment, commentsController.addComment);

// Обновление комментария
router.put('/update', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), validateUpdateComment, commentsController.updateComment);

// Получение всех комментариев пользователя
router.get('/user/:email', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), commentsController.getUserComments);

// Удаление комментария
router.delete('/:commentId', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), validateObjectId, commentsController.deleteComment);

module.exports = router;
