const path = require("path");

module.exports = {
  entry: {
    main: "./src/web/js/index.js"
  },
  output: {
    path: path.resolve(__dirname, "public/js"),
    filename: "index.js"
  }
};
