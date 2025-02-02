//console.log(global)
//console.log(module)

//===================

// (function(exports, __dirname, __filename, require, module) {
// //code

// }) ();

//===================

const logger = require('./logger.cjs');
// logger.log('kiki');
// console.log(logger);
//console.log(process);
const path = require('node:path');
console.log(path.parse(__filename));

//===================