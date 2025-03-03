const express = require('express');
const { getMovieComments, addComment, updateComment, getUserComments, deleteComment } = require('../controllers/commentsController');
const { auth, authorize } = require('../middleware/authMiddleware');
const validateRequest = require('../middleware/validationMiddleware');
const commentValidation = require('../validation/commentValidation');

const router = express.Router();

router.get('/movie/:movieid', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), getMovieComments);
router.post('/', auth, authorize('PREMIUM_USER'), validateRequest(commentValidation.addComment), addComment);
router.put('/', auth, authorize('PREMIUM_USER'), validateRequest(commentValidation.updateComment), updateComment);
router.get('/user/:email', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), getUserComments);
router.delete('/:commentId', auth, authorize('PREMIUM_USER', 'ADMIN'), deleteComment);

module.exports = router;
