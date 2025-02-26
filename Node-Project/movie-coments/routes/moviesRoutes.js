const express = require('express');
const router = express.Router();
const moviesController = require('../controllers/moviesController');
const { auth, authorize } = require('../middleware/authMiddleware');
const rateLimiter = require('../middleware/rateLimiter');
const { validateAmount, validateObjectId, validateMovieFilter, validateRating } = require('../middleware/validationMiddleware');

// Получение фильма по ID (доступно для всех, кроме ADMIN)
router.get('/:id', auth, authorize('USER', 'PREMIUM_USER'), validateObjectId, moviesController.getMovie);

// Получение самых рейтинговых фильмов по заданному фильтру (доступно для всех, кроме ADMIN)
router.post('/most-rated', auth, authorize('USER', 'PREMIUM_USER'), rateLimiter('USER'), validateMovieFilter, moviesController.getMostRated);

// Получение самых комментируемых фильмов по заданному фильтру (доступно для всех, кроме ADMIN)
router.post('/most-commented', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), validateAmount, moviesController.getMostCommented);

// Обновление рейтинга фильма (доступно только для PREMIUM_USER)
router.put('/rate', auth, authorize('PREMIUM_USER'), validateRating, moviesController.addRate);

module.exports = router;
