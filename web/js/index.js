const m = require("mithril");
const page = require("page");
//TODO: Document this better

const ensureLogin = (context, next) => {
  //TODO: validate a user authentication is present...
  next();
};

const renderHome = (context, next) => {
  const homePage = require("./home");
  let renderElement = document.body;
  m.mount(renderElement, homePage);
  if (next) {
    next();
  }
};

page("/", ensureLogin, renderHome);
