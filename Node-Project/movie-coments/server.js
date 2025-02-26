const express = require('express');
const mongoose = require('mongoose');
const config = require('config');
const bodyParser = require('body-parser');
const winston = require('./config/winston');
const morganMiddleware = require('./middleware/morgan');
const accountsRoutes = require('./routes/accountsRoutes');
const moviesRoutes = require('./routes/moviesRoutes');
const commentsRoutes = require('./routes/commentsRoutes');
const favoritesRoutes = require('./routes/favoritesRoutes');
const rateLimiter = require('./middleware/rateLimiter');
const errorHandler = require('./middleware/errorHandler');

const app = express();
const PORT = process.env.PORT || 5000;

// Middleware для логирования
morganMiddleware(app);

// Middleware для парсинга JSON
app.use(bodyParser.json());
app.use(express.json());

// Ограничение числа запросов для всех пользователей
app.use(rateLimiter('USER'));

// Маршруты
app.use('/api/accounts', accountsRoutes);
app.use('/api/movies', moviesRoutes);
app.use('/api/comments', commentsRoutes);
app.use('/api/favorites', favoritesRoutes);

// Обработка ошибок
app.use(errorHandler);

// Подключение к базе данных
mongoose.connect(config.get('mongoURI'), {
  useNewUrlParser: true,
  useUnifiedTopology: true
}).then(() => winston.info('MongoDB connected'))
  .catch(err => winston.error('MongoDB connection error:', err));

app.listen(PORT, () => winston.info(`Server running on port ${PORT}`));
