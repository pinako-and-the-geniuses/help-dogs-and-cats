import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
// styles - SCSS
import "assets/scss/paper-kit.scss";
import "assets/demo/demo.css";

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById("root")
);
