const express = require('express');
const { addFavorite, getUserFavorites, updateFavorite, deleteFavorite } = require('../controllers/favoritesController');
const { auth, authorize } = require('../middleware/authMiddleware');
const validateRequest = require('../middleware/validationMiddleware');
const favoriteValidation = require('../validation/favoriteValidation');

const router = express.Router();

router.post('/', auth, authorize('PREMIUM_USER'), validateRequest(favoriteValidation.addFavorite), addFavorite);
router.get('/:email', auth, authorize('PREMIUM_USER', 'ADMIN'), getUserFavorites);
router.put('/', auth, authorize('PREMIUM_USER'), validateRequest(favoriteValidation.updateFavorite), updateFavorite);
router.delete('/:favoriteId', auth, authorize('PREMIUM_USER', 'ADMIN'), deleteFavorite);

module.exports = router;
