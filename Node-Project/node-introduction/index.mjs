import stream from './sender-data.mjs';
let res = '';
stream.on('data', chunk => res += chunk);
stream.on('end', () => console.log(res));