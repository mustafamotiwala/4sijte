module.exports = {
  parserOptions: {
    ecmaVersion: 6,
    sourceType: "module"
  },
  // plugins: ["prettier"],
  // extends: ["prettier", "eslint:recommended"],
  env: {
    browser: true,
    commonjs: true,
    es6: true
  },
  rules: {
    indent: ["error", 2],
    "one-var-declaration-per-line": ["error", "always"],
    "no-var": "error"
  }
};
