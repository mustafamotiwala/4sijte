'use strict';

const page = require("page");

const homePage = require("./home");

const ensureLogin = (context, next) => {
  //TODO: validate a user authentication is present...
  next();
};

const renderHome = (context, next) => {
  let renderElement = document.body;
  m.mount(renderElement, homePage);
  if (next) {
    next();
  }
};

page("/", ensureLogin, renderHome);
