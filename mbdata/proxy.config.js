const proxy = [
  {
    context: '/api',
    target: 'http://localhost:8086',
    pathRewrite: {'^/api' : ''}
  }
];
module.exports = proxy;