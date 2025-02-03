
import { readFile, writeFile } from 'node:fs/promises';
import logger from './logger.mjs';
const data = await readFile('./file.txt', 'utf-16le')
console.log(data)
writeFile('./file.txt', ["kuku", "kukureku", "Hello World!"].join('\n'), 'utf-16le')
logger.info("function finished");