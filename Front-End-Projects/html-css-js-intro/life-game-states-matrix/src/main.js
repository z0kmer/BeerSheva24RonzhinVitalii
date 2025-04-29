import MatrixComponent from './components/MatrixComponent';
import configObj from './config/matrix-config.json';
import MatrixStates from './service/MatrixStates';
import './style.css';
const {rows, columns, interval} = configObj;

new MatrixComponent(rows, columns, document.querySelector(".grid"), new MatrixStates());
