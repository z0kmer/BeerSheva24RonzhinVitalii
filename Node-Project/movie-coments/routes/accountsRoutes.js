const express = require('express');
const router = express.Router();
const accountsController = require('../controllers/accountsController');
const { auth, authorize, basicAuth } = require('../middleware/authMiddleware');
const { validateObjectId, validateAccount, validateLogin, validateAdminAccount, validateRole, validatePassword } = require('../middleware/validationMiddleware');

// Добавление учетной записи пользователя (доступно всем)
router.post('/', validateAccount, accountsController.addAccount);

// Добавление учетной записи администратора
router.post('/admin', basicAuth, validateAdminAccount, accountsController.addAdminAccount);

// Логин (доступно всем)
router.post('/login', validateLogin, accountsController.login);

// Добавление учетной записи администратора (доступно только через Basic Auth)
router.post('/admin', basicAuth, validateAdminAccount, accountsController.addAdminAccount);

// Изменение роли пользователя (доступно только для ADMIN)
router.put('/role', auth, authorize('ADMIN'), validateRole, accountsController.setRole);

// Обновление пароля (доступно только авторизованным пользователям)
router.put('/update-password', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), validatePassword, accountsController.updatePassword);

// Получение учетной записи (доступно только авторизованным пользователям)
router.get('/:email', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), validateObjectId, accountsController.getAccount);

// Блокировка учетной записи (доступно только для ADMIN)
router.put('/block', auth, authorize('ADMIN'), validateObjectId, accountsController.blockAccount);

// Разблокировка учетной записи (доступно только для ADMIN)
router.put('/unblock/:email', auth, authorize('ADMIN'), validateObjectId, accountsController.unblockAccount);

// Удаление учетной записи (доступно только для ADMIN)
router.delete('/:email', auth, authorize('ADMIN'), validateObjectId, accountsController.deleteAccount);

// Логин (доступно всем)
router.post('/login', validateAccount, accountsController.login);

module.exports = router;
