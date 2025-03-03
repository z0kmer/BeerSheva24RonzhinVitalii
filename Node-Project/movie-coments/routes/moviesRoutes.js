const express = require('express');
const { getMovie, getMostRated, getMostCommented, addRate } = require('../controllers/moviesController');
const { auth, authorize } = require('../middleware/authMiddleware');
const validateRequest = require('../middleware/validationMiddleware');
const movieValidation = require('../validation/movieValidation');

const router = express.Router();

router.get('/:id', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), getMovie);
router.post('/most-rated', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), validateRequest(movieValidation.getMostRated), getMostRated);
router.post('/most-commented', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), validateRequest(movieValidation.getMostCommented), getMostCommented);
router.post('/rate', auth, authorize('PREMIUM_USER'), validateRequest(movieValidation.addRate), addRate);

module.exports = router;
