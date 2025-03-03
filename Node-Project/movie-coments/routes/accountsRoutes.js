const express = require('express');
const { addAccount, addAdminAccount, setRole, updatePassword, getAccount, blockAccount, unblockAccount, deleteAccount, login } = require('../controllers/accountsController');
const { auth, authorize, basicAuth } = require('../middleware/authMiddleware');
const validateRequest = require('../middleware/validationMiddleware');
const accountValidation = require('../validation/accountValidation');

const router = express.Router();

router.post('/', validateRequest(accountValidation.addAccount), addAccount);
router.post('/admin', basicAuth, validateRequest(accountValidation.addAccount), addAdminAccount);
router.put('/set-role', auth, authorize('ADMIN'), validateRequest(accountValidation.setRole), setRole);
router.put('/update-password', auth, authorize('ADMIN', 'USER', 'PREMIUM_USER'), validateRequest(accountValidation.updatePassword), updatePassword);
router.get('/:email', auth, authorize('ADMIN', 'USER', 'PREMIUM_USER'), getAccount);
router.put('/block/:email', auth, authorize('ADMIN'), blockAccount);
router.put('/unblock/:email', auth, authorize('ADMIN'), unblockAccount);
router.delete('/:email', auth, authorize('ADMIN', 'USER', 'PREMIUM_USER'), deleteAccount);
router.post('/login', validateRequest(accountValidation.login), login);

module.exports = router;
