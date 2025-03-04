const express = require('express');
const { addAccount, addAdminAccount, setRole, updatePassword, getAccount, blockAccount, unblockAccount, deleteAccount, login } = require('../controllers/accountsController');
const validateRequest = require('../middleware/validationMiddleware');
const { addAccount: addAccountValidation, updatePassword: updatePasswordValidation, setRole: setRoleValidation, login: loginValidation } = require('../validation/accountValidation');
const { auth, authorize } = require('../middleware/authMiddleware');

const router = express.Router();

router.post('/add', validateRequest(addAccountValidation), addAccount);
router.post('/add-admin', validateRequest(addAccountValidation), addAdminAccount);
router.put('/set-role', auth, authorize('ADMIN'), validateRequest(setRoleValidation), setRole);
router.put('/update-password', auth, validateRequest(updatePasswordValidation), updatePassword);
router.get('/:email', auth, authorize('ADMIN', 'USER'), getAccount);
router.put('/block/:email', auth, authorize('ADMIN'), blockAccount);
router.put('/unblock/:email', auth, authorize('ADMIN'), unblockAccount);
router.delete('/:email', auth, authorize('ADMIN', 'USER'), deleteAccount);
router.post('/login', validateRequest(loginValidation), login);

module.exports = router;
