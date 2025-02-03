
//import logger from './logger.mjs';
import fs from 'node:fs';
const data = fs.readFileSync('./index.mjs', 'utf8');
// logger.info(data);
console.log(data);
fs.writeFileSync('./file.txt', ["kuku", "kukureku", "Hello World!"].join('\n'), 'utf-16le')
