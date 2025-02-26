const express = require('express');
const mongoose = require('mongoose');
const config = require('config');
const bodyParser = require('body-parser');
const moviesRoutes = require('./routes/moviesRoutes');
const commentsRoutes = require('./routes/commentsRoutes');
const favoritesRoutes = require('./routes/favoritesRoutes');
const accountsRoutes = require('./routes/accountsRoutes');

const app = express();
const PORT = process.env.PORT || 5000;

app.use(bodyParser.json());

app.use('/api/movies', moviesRoutes);
app.use('/api/comments', commentsRoutes);
app.use('/api/favorites', favoritesRoutes);
app.use('/api/accounts', accountsRoutes);

mongoose.connect(config.get('mongoURI'), {
  useNewUrlParser: true,
  useUnifiedTopology: true
}).then(() => console.log('MongoDB connected'))
  .catch(err => console.log(err));

app.listen(PORT, () => console.log(`Server started on port ${PORT}`));
