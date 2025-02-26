const express = require('express');
const router = express.Router();
const favoritesController = require('../controllers/favoritesController');
const { auth, authorize } = require('../middleware/authMiddleware');
const { validateObjectId, validateFavorite, validateUpdateFavorite } = require('../middleware/validationMiddleware');

// Добавление фильма в избранное (доступно для всех, кроме ADMIN)
router.post('/add', auth, authorize('USER', 'PREMIUM_USER'), validateFavorite, favoritesController.addFavorite);

// Получение всех избранных фильмов пользователя (доступно для всех)
router.get('/:email', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), favoritesController.getUserFavorites);

// Обновление информации об избранном фильме (доступно для всех)
router.put('/update', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), validateUpdateFavorite, favoritesController.updateFavorite);

// Удаление фильма из избранного (доступно для всех, кроме ADMIN)
router.delete('/:favoriteId', auth, authorize('USER', 'PREMIUM_USER'), validateObjectId, favoritesController.deleteFavorite);

module.exports = router;
