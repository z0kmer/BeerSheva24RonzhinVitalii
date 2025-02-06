// let res = '';
// stream.on('data', chunk => res += chunk);
// stream.on('end', () => console.log(res));
import stream from './file-stream.mjs';
import writableStream from './file-writable-stream.mjs';

(async () => {
    for await(const res of stream) {
        console.log(res.toString());
    }
})()

writableStream.write("Hello ");
writableStream.write("World !!!");
writableStream.end();