const express = require('express');
const router = express.Router();
const moviesController = require('../controllers/moviesController');
const { auth, authorize } = require('../middleware/authMiddleware');

// Получение фильма по ID (доступно для всех, кроме ADMIN)
router.get('/:id', auth, authorize('USER', 'PREMIUM_USER'), moviesController.getMovie);

// Получение самых рейтинговых фильмов по заданному фильтру (доступно для всех, кроме ADMIN)
router.post('/most-rated', auth, authorize('USER', 'PREMIUM_USER'), moviesController.getMostRated);

// Получение самых комментируемых фильмов по заданному фильтру (доступно для всех, кроме ADMIN)
router.post('/most-commented', auth, authorize('USER', 'PREMIUM_USER'), moviesController.getMostCommented);

// Обновление рейтинга фильма (доступно только для PREMIUM_USER)
router.put('/rate', auth, authorize('PREMIUM_USER'), moviesController.addRate);

module.exports = router;
