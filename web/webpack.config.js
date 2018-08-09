const path = require("path");

module.exports = {
  entry: {
    main: "./src/index.js"
  },
  output: {
    path: path.resolve(__dirname, "public/js"),
    filename: "index.js"
  }
};
