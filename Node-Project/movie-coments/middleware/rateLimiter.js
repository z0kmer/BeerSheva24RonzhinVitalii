const rateLimit = require('express-rate-limit');
const config = require('config');

module.exports = (role) => {
  const limits = config.get(`requestLimit.${role}`);
  return rateLimit({
    windowMs: limits.windowMs,
    max: limits.maxRequests,
    handler: (req, res) => {
      res.status(429).json({ message: 'Слишком много запросов, пожалуйста, попробуйте позже' });
    }
  });
};
