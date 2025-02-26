const express = require('express');
const router = express.Router();
const accountsController = require('../controllers/accountsController');
const { auth, authorize, basicAuth } = require('../middleware/authMiddleware');

// Добавление учетной записи пользователя (доступно всем)
router.post('/', accountsController.addAccount);

// Добавление учетной записи администратора (доступно только через Basic Auth)
router.post('/admin', basicAuth, accountsController.addAdminAccount);

// Изменение роли пользователя (доступно только для ADMIN)
router.put('/role', auth, authorize('ADMIN'), accountsController.setRole);

// Обновление пароля (доступно только авторизованным пользователям)
router.put('/update-password', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), accountsController.updatePassword);

// Получение учетной записи (доступно только авторизованным пользователям)
router.get('/:email', auth, authorize('USER', 'PREMIUM_USER', 'ADMIN'), accountsController.getAccount);

// Блокировка учетной записи (доступно только для ADMIN)
router.put('/block', auth, authorize('ADMIN'), accountsController.blockAccount);

// Разблокировка учетной записи (доступно только для ADMIN)
router.put('/unblock/:email', auth, authorize('ADMIN'), accountsController.unblockAccount);

// Удаление учетной записи (доступно только для ADMIN)
router.delete('/:email', auth, authorize('ADMIN'), accountsController.deleteAccount);

// Логин (доступно всем)
router.post('/login', accountsController.login);

module.exports = router;
