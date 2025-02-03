
import { readFile, writeFile } from 'node:fs/promises';
import logger from './logger.mjs';
const data = await readFile('./file.txt','utf-16le')
console.log(data)
writeFile('./file.txt', ["kuku", "kukureku", "שלום"].join('\n'), 'utf-16le')
logger.info("function finished");
